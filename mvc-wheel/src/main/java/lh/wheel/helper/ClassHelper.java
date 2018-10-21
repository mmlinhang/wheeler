package lh.wheel.helper;

import lh.wheel.util.ClassUtils;

import java.util.Set;

public class ClassHelper {
    private static Set<Class> classSet;

    static {
        String basePackage = ConfigHelper.getBasePackage();
        classSet = ClassUtils.getClassSet(basePackage.replace(".", "/"));
    }

    public static Set<Class> getClassSet() {
        return classSet;
    }

    public static void setClassSet(Set<Class> classSet) {
        ClassHelper.classSet = classSet;
    }
}
