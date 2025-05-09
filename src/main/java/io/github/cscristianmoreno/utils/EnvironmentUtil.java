package io.github.cscristianmoreno.utils;

public abstract class EnvironmentUtil {
    
    
    /** 
     * @param value
     * @return String
     */
    public static String getEnv(final String value) {
        String mpValue = null;
        
        if (value.startsWith("$")) {
            mpValue = System.getenv(value.substring(1));
        }
        else {
            mpValue = value;
        }

        return mpValue;
    }
}
