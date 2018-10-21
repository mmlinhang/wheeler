package lh.wheel.helper;

import lh.wheel.util.ClassUtils;

public class HelperLoader {
    static {
        ClassUtils.loadClass("lh.wheel.helper.ConfigHelper", true);
        ClassUtils.loadClass("lh.wheel.helper.ClassHelper", true);
        ClassUtils.loadClass("lh.wheel.helper.BeanHelper", true);
        ClassUtils.loadClass("lh.wheel.helper.AopHelper", true);
        ClassUtils.loadClass("lh.wheel.helper.InjectHelper", true);
        ClassUtils.loadClass("lh.wheel.helper.ActionHelper", true);
    }
}
