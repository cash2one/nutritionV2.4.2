package com.jumper.weight.common.base;

import java.util.List;

/**
 * 对数据库进行增删改时需要抛出Exception
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface BaseMapper<T> {
	
	public T findById(Integer id);
	
	public List<T> listByIds(List<Integer> ids);
	
	public List<T> listAll();
	
	public Integer insert(T paramT) throws Exception;

	public Integer insertBatch(List<T> paramList) throws Exception;

	public Integer delete(Integer id) throws Exception;
	
	public Integer deleteBatch(List<Integer> ids) throws Exception;

	public Integer update(T paramT) throws Exception;
	
	public Integer updateBatch(List<T> paramList) throws Exception;

}