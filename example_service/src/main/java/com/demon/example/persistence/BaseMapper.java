package com.demon.example.persistence;

import java.util.List;


/**
 * 常规的CRUD接口
 */
public interface BaseMapper<T extends Object, K extends Object> {

	/**
	 * 插入新记录
	 */
	int insert(T t);

	/**
	 * 根据主键更新单个记录
	 */
	int updateById(T t);

	/**
	 * 根据主键查询单个记录
	 */
	T findById(K k);

	/**
	 * 根据对象条件查询集合
	 */
	List<T> findList(T t);

	/**
	 * 分页查询，参数必须包含offset和rows属性
	 */
	List<T> paging(T t);

	/**
	 * 总数查询
	 */
	int count(T t);
	
	/**
	 * 批量IN查询
	 * @param ids	查询键集合
	 * @return
	 */
	List<T> batchFindListByIds(K[] ids);
	
	/**
	 * 根据主键删除单个对象
	 */
	int deleteById(K k);
}
