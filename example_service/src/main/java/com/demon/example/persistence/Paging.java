package com.demon.example.persistence;


/**
 * 分页接口规范
 */
public interface Paging {

	/**
	 * 查询的开始下标
	 */
	Integer getOffset();
	
	/**
	 * 查询的行数
	 */
	Integer getRows();

}
