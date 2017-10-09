package com.jumper.weight.common.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	//protected Logger logger = Logger.getLogger(this.getClass());

	protected abstract BaseMapper<T> getDao();
	
	@Override
	public T findById(Integer id) {
		return getDao().findById(id);
	}
	
	@Override
	public boolean insert(T paramT) {
		try {
			return (getDao().insert(paramT)>0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean insertBatch(List<T> list) {
		try {
			if ((list != null) && (list.size() > 0)) {
				return (getDao().insertBatch(list)>0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		try {
			return (getDao().delete(id)>0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean update(T Po) {
		try {
			return (getDao().update(Po)>0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean updateBatch(List<T> paramList) {
		try {
			return (getDao().updateBatch(paramList)>0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}