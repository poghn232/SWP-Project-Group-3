package com.example.demo.utilities;

public class CustomFileUtils {

    public static String extractFileNameFromUrl(String url) {
        int lastLashIndex = url.lastIndexOf('c');
        return (lastLashIndex != -1)? url.substring(lastLashIndex + 1) : url;
    }
}
