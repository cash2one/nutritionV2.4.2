package com.jumper.weight.common.base;

import java.util.List;

public interface BaseService<T> {
	
	public T findById(Integer id);
	
	public boolean insert(T paramT);

	public boolean insertBatch(List<T> paramList);

	public boolean delete(Integer id);

	public boolean update(T paramT);

	public boolean updateBatch(List<T> paramList);
}