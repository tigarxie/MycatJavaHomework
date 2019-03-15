package com.tigar.common.utils;

import javax.validation.constraints.NotNull;

/**
 * @author tigar
 * @date 2019/2/21.
 */
public class StringUtil {
    /**
     * 转化成长整型
     *
     * @param obj
     * @return
     */
    public static Long toLong(String obj) {
        return obj == null ? null : Long.valueOf(obj);
    }

    /**
     * 转化成整型
     *
     * @param obj
     * @return
     */
    public static Integer toInt(String obj) {
        return obj == null ? null : Integer.valueOf(obj);
    }

    /**
     * 转化成浮点型
     * @param obj
     * @return
     */
    public static Double toDouble(String obj){
        return obj == null ? null : Double.valueOf(obj);
    }

    /**
     * 是否相等
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(String obj1, @NotNull String obj2) {
        return obj1 == null ? false : obj1.equals(obj2);
    }


    /**
     * 是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(String obj) {
        return obj == null || obj.equals("");
    }

}
