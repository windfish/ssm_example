package com.demon.example.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 */
public class ReflectUtils {

	/**
	 * 根据索引获得超类的参数类型
	 * 
	 * @param clazz	超类类型
	 * @param index	索引
	 */
	@SuppressWarnings("rawtypes")
	public static Class getClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public static boolean isParameterizedType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return false;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return false;
		}
		if (params[index] instanceof ParameterizedType) {
			return true;
		}
		return false;
	}

	public static Class getActualClassGenricType(final Class clazz, final int index, final int subIndex) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (params[index] instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) params[index];
			Type[] subTypes = type.getActualTypeArguments();
			return (Class) subTypes[subIndex];
		}
		return Object.class;
	}
	
	public static Class getRawClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (params[index] instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) params[index];
			return (Class) type.getRawType();
		}
		return Object.class;
	}

}
