package com.zzz.cj2356inputMethod.utils;

public class StringUtils {

    public static boolean hasText(String args) {
        return null != args && args.length() > 0;
    }

    public static String decimalToBinary(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--)
            sb.append(n >>> i & 1);
        return sb.toString();
    }
}
