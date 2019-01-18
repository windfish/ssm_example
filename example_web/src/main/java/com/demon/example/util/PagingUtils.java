package com.demon.example.util;

public class PagingUtils {

    public static int parseOffset(int pageNo, int pageSize){
        int start = 0;
        if (pageNo >= 0 && pageSize > 0) {
            if (pageNo == 0) {
                return start;
            }
            start = (pageNo - 1) * pageSize;
        }
        return start;
    }
    
}
