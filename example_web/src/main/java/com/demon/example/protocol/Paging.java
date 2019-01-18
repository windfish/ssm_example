package com.demon.example.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础分页数据封装类
 */
public class Paging {

	private Integer totalRows;	// 总行数，传0或上次查询返回的值
	private Integer pageNo;		// 页码，从1开始
	private Integer pageSize;	// 每页大小，不大于1000
	
	public Paging() {
	}
	
	public Paging(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalRows = 0;
	}
	
	/**
	 * first index: 0
	 */
	public int parseOffset(){
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * 截取分页数据
	 */
	public <E> List<E> subList(List<E> list){
		if (list == null || list.isEmpty()){
			return list;
		}
		int start = parseOffset();
		if (start >= list.size()){
			return new ArrayList<>();
		}
		int end = start + pageSize <= list.size() ? start + pageSize : list.size();
		return list.subList(start, end);
	}
	
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
