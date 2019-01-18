package com.demon.example.action;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.demon.example.common.ErrorCode;
import com.demon.example.exception.ValidateException;
import com.demon.example.protocol.Paging;

public interface Validation {

    public default void validate(boolean isError, int code, String message){
        if (!isError){
            return ;
        }
        throw new ValidateException(code, message);
    }

    public default void validateNotNull(Object obj, int code, String message){
        validate(obj == null, code, message);
    }

    public default void validateNotEmptyColls(Collection<?> colls, int code, String message){
        validate(colls == null || colls.isEmpty(), code, message);
    }

    public default void validateGtZero(Number num, int code, String message){
        validate(num == null || num.longValue() <= 0, code, message);
    }

    public default void validateAllGtZero(String numString, String regex, int code, String message){
        validateNotEmptyString(numString, code, message);
        for (String num : numString.split(regex)){
            validateGtZero(NumberUtils.toLong(num), code, message);
        }
    }

    public default void validateGeZero(Number num, int code, String message){
        validate(num == null || num.longValue() < 0, code, message);
    }

    public default void validateNotEmptyString(String str, int code, String message){
        validate(str == null || str.trim().isEmpty(), code, message);
    }

    public default void validateNumberEquals(Number num1, Number num2, int code, String message){
        validate(num1 == null || num2 == null || num1.longValue() != num2.longValue(), code, message);
    }

    public default void validateContains(List<?> objs, Object obj, int code, String message){
        validate(objs.stream().filter(o -> o != null && o.equals(obj)).limit(1).count() == 0, code, message);
    }

    public default void validatePaging(Paging paging){
        validateNotNull(paging, ErrorCode.Paging.code(), "paging not found");
        validateGtZero(paging.getPageNo(), ErrorCode.Paging.code(), "paging.pageNo le 0");
        validateGtZero(paging.getPageSize(), ErrorCode.Paging.code(), "paging.pageSize le 0");

        if (paging.getTotalRows() == null || paging.getTotalRows() < 0){
            paging.setTotalRows(0);
        }
    }

    public default boolean numberEquals(Number num1, Number num2){
        return num1 != null && num2 != null && num1.longValue() == num2.longValue();
    }

}
