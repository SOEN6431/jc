package com.ays.javachat.common.utils;

/**
 * @author yevgenys
 */
public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || s.length() < 1;
    }
}
