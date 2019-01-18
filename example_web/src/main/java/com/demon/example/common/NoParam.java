package com.demon.example.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标识的方法，是否不需要携带任何参数
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NoParam {
}
