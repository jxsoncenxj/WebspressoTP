package com.group15.Webspresso.classes;

import java.util.Base64;

public class ImageUtil {
    public String getImgData(byte[] imgBytes) {
        if (imgBytes != null) {
            return Base64.getEncoder().encodeToString(imgBytes);
        } else {
            return null;
        }
    }

}