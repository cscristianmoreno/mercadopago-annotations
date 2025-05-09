package io.github.cscristianmoreno.utils;

public abstract class MessageUtil {
    
    /** 
     * @param message
     * @param params
     * @return String
     */
    public static String message(String message, Object... params) {
        return String.format(message, params);
    }
}
