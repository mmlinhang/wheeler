package lh.wheel.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类 工具类
 */
public class ClassUtils {
    private static final Logger LOGGER = Logger.getLogger(ClassUtils.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * 封装了类加载失败的情况
     */
    public static Class loadClass(String className, boolean initialize) {
        try {
            return Class.forName(className, initialize, getClassLoader());
        }
        catch (ClassNotFoundException e) {
            LOGGER.error(className + " 类加载失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到 basePath 下的所有 class 对象
     */
    public static Set<Class> getClassSet(String basePath) {
        Set<Class> classSet = new HashSet<Class>();

        try {
            Enumeration<URL> urls = getClassLoader().getResources(basePath);
            if(urls != null) {
                while(urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")) {
                        String filePath = URLDecoder.decode(url.getPath(), "utf-8");
                        loadFileClass(classSet, filePath, basePath);
                    }
                    else if(protocol.equals("jar")) {
                        loadJarClass(classSet, url);
                    }
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("读取 "+basePath+"失败");
            throw new RuntimeException(e);
        }

        return classSet;
    }

    /**
     * 加载文件中的类
     */
    private static void loadFileClass(Set<Class> classSet, String filePath, String basePath) {
        File file = new File(filePath);
        File[] files =
        file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if(pathname.getName().endsWith(".class") || pathname.isDirectory())
                    return true;
                return false;
            }
        });

        for(File file_1:files) {
            String fileName = file_1.getName();
            filePath = file_1.getPath();
            if(file_1.isFile() && fileName.endsWith(".class")) {
                String className = basePath.replace("/", ".") + "."
                        + fileName.substring(0, fileName.lastIndexOf(".class"));
                classSet.add(loadClass(className, false));
            }
            else
                loadFileClass(classSet, filePath, basePath+"/"+fileName);
        }
    }

    /**
     * 加载 jar 包中的类
     */
    private static void loadJarClass(Set<Class> classSet, URL url) {
        try {
            JarURLConnection connection = (JarURLConnection)url.openConnection();
            JarFile jarFile = connection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if(entryName.endsWith(".class")) {
                    String className =
                            entryName.substring(0, entryName.lastIndexOf(".")).replace("/", ".");
                    classSet.add(loadClass(className, false));
                }
            }
        }
        catch (IOException e) {
            LOGGER.error(url+"URLConnection 打开失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到 superClass 的所有子类 Class对象
     * @param classSet 项目类集
     * @param superClass 父类 Class 对象
     */
    public static Set<Class> getClassSetBySuperClass(Set<Class> classSet, Class superClass) {
        Set<Class> subClassSet = new HashSet<Class>();
        for(Class clazz:classSet) {
            if(superClass.isAssignableFrom(clazz))
                subClassSet.add(clazz);
        }

        return subClassSet;
    }

    /**
     * 获取带有 annotationClass 注解的类对象
     * @param classSet
     * @param annotationClass
     * @return
     */
    public static Set<Class> getClassSetWithAnnotation(Set<Class> classSet, Class<? extends Annotation> annotationClass) {
        Set<Class> annotatedClassSet = new HashSet<Class>();
        for(Class clazz:classSet) {
            if(clazz.isAnnotationPresent(annotationClass))
                annotatedClassSet.add(clazz);
        }

        return annotatedClassSet;
    }
}
