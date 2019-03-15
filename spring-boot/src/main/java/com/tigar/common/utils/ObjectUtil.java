package com.tigar.common.utils;

import javax.validation.constraints.NotNull;

/**
 * @author tigar
 * @date 2019/2/21.
 */
public class ObjectUtil {
    /**
     * 转化成字符串
     *
     * @param obj
     * @return
     */
    public static String toStr(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * 转化成长整型
     *
     * @param obj
     * @return
     */
    public static Long toLong(Object obj) {
        return obj == null ? null : Long.valueOf(obj.toString());
    }

    /**
     * 转化成整型
     *
     * @param obj
     * @return
     */
    public static Integer toInt(Object obj) {
        return obj == null ? null : Integer.valueOf(obj.toString());
    }

    /**
     * 转化成浮点型
     * @param obj
     * @return
     */
    public static Double toDouble(Object obj){
        return obj == null ? null : Double.valueOf(obj.toString());
    }

    /**
     * 是否相等
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(Object obj1, @NotNull Object obj2) {
        return obj1 == null ? false : obj1.equals(obj2);
    }
}
