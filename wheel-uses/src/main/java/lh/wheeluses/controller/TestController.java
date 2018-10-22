package lh.wheeluses.controller;

import lh.wheel.annotation.mvc.Action;
import lh.wheel.annotation.mvc.Controller;
import lh.wheel.annotation.mvc.Inject;
import lh.wheel.constant.RequestMethod;
import lh.wheel.servlet.ModelAndView;
import lh.wheel.servlet.Param;
import lh.wheeluses.service.TestService;

@Controller
public class TestController {

    @Inject
    private TestService testService;

    @Action(method = RequestMethod.GET, url = "/test")
    public ModelAndView test(Param param) {
        String arg0 = param.get("arg0");
        String arg1 = param.get("arg1");

        ModelAndView mv = new ModelAndView();
        mv.setView("jsp/test.jsp");
        mv.addObject("arg0", arg0);
        mv.addObject("arg1", arg1);

        System.out.println("in controller: "+this.getClass());

        return mv;
    }

    @Action(method = RequestMethod.GET, url = "/testTransaction")
    public ModelAndView testTransaction(Param param) {
        testService.testTransaction();

        ModelAndView mv = new ModelAndView();
        mv.setView("jsp/test.jsp");
        mv.addObject("arg0", 1);
        mv.addObject("arg1", 2);

        return mv;
    }
}
