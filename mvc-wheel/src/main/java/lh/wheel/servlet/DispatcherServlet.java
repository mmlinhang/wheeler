package lh.wheel.servlet;

import lh.wheel.helper.ActionHelper;
import lh.wheel.helper.BeanHelper;
import lh.wheel.helper.ConfigHelper;
import lh.wheel.util.ClassUtils;
import lh.wheel.util.JsonUtils;
import lh.wheel.util.ReflectionUtils;
import lh.wheel.util.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet{

    private final Logger LOGGER = Logger.getLogger(DispatcherServlet.class);
    @Override
    public void init() {
        //加载 helper 类
        ClassUtils.loadClass("lh.wheel.helper.HelperLoader", true);

        ServletConfig servletConfig = getServletConfig();
        ServletContext servletContext = servletConfig.getServletContext();
        //添加 jsp 映射
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJspPath()+"/*");
        //添加默认资源访问 映射
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath()+"/*");
    }

    /**
     * 获取 request 所对应的被 @Action 注解的 Method 对象
     */
    private Method getActionMethod(HttpServletRequest request) {
        String requestMethod = request.getMethod().toUpperCase();
        String requestURI = request.getRequestURI();
        String requestURL = requestURI.substring(request.getContextPath().length());
        Method actionMethod = ActionHelper.getMappedServiceMethod(requestMethod, requestURL);

        return actionMethod;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 获取 request 所对应的被 @Action 注解的 Method 对象
         * actionMethod 为空则设置状态 404 并返回
         */
        Method actionMethod = getActionMethod(request);
        if(actionMethod == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /**
         * 获取请求参数
         */
        Map<String, String> params = new HashMap<String, String>();
        Enumeration<String> parameterNames = request.getParameterNames();
        //遍历 parameter
        while(parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        //在一些特殊的情况下(非post方法等) 请求体中的数据不会被 request.getParameter() 获取，
        //所以需要通过 InputStream 方式获取
        InputStream is;
        String requestBodyString;
        try {
            is = request.getInputStream();
        }
        catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        requestBodyString = StringUtils.getString(is);
        if(StringUtils.isNotEmpty(requestBodyString)) {
            String[] requestBodyParams = requestBodyString.split("&");
            for(String param:requestBodyParams) {
                String[] keyValue = param.split("=");
                if(keyValue.length != 2) {
                    String errorMess = "解析请求体错误";
                    LOGGER.error(errorMess);
                    throw new RuntimeException(errorMess);
                }
                params.put(keyValue[0], keyValue[1]);
            }
        }

        Param param = new Param();
        param.setParams(params);

        //调用对应的 action 方法
        Object controller = BeanHelper.getControllerMap().get(actionMethod.getDeclaringClass());
        Object actionRet = ReflectionUtils.invokeMethod(controller, actionMethod, param);

        //返回值为 ModelAndView
        if(actionRet instanceof ModelAndView) {
            ModelAndView mv = ((ModelAndView) actionRet);
            String view = mv.getView();
            if(view.startsWith("/")) {
                try {
                    response.sendRedirect(view);
                }
                catch (IOException e) {
                    LOGGER.error(view+"重定向失败");
                    throw new RuntimeException(e);
                }
            }
            else {
                String jspPath = ConfigHelper.getJspPath()+"/"+view;
                Map<String, Object> attributes = mv.getObjects();
                for(String attributeName:attributes.keySet())
                    request.setAttribute(attributeName, attributes.get(attributeName));

                try {
                    request.getRequestDispatcher(jspPath).forward(request, response);
                }
                catch (Exception e) {
                    LOGGER.error(view+"请求转发失败");
                    throw new RuntimeException(e);
                }
            }
        }
        else if(actionRet instanceof Data) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Data data = (Data) actionRet;
            Object model = data.getModel();
            String json = JsonUtils.toJson(model);

            Writer writer;
            try {
                writer = response.getWriter();
                writer.write(json);
                writer.flush();
                writer.close();
            }
            catch (IOException e) {
            }
        }
    }
}