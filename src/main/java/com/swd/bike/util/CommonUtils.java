package com.swd.bike.util;

import java.util.Base64;

public class CommonUtils {
    public static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
}
