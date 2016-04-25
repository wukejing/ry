package com.mv.service.base;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mv.dao.base.BaseDao;
import com.mv.domain.common.Page;

public abstract class BaseServiceImpl<T, KEY extends Serializable> implements BaseService<T, KEY> {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

	/**
	 * 获取DAO操作类
	 */
	public abstract BaseDao<T, KEY> getDao();
	
	@Override
	public int insertEntry(T...t) {
		return getDao().insertEntry(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int insertEntryCreateId(T t) {
	    return getDao().insertEntryCreateId(t);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int deleteByKey(KEY...key) {
		return getDao().deleteByKey(key);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int deleteByCondtion(T condtion) {
		return getDao().deleteByKey(condtion);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int updateByKey(T condtion) {
		return getDao().updateByKey(condtion);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public long saveOrUpdate(T t) {
		Long id = 0L;
		try {
			Class<?> clz = t.getClass();
			id = (Long)clz.getMethod("getId").invoke(t);
		} catch (Exception e) {
			LOGGER.warn("获取对象主键值失败!");
		}
		if(id != null && id > 0) {
			return this.updateByKey(t);
		} 
		return this.insertEntryCreateId(t);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
	public T selectEntry(KEY key) {
		return getDao().selectEntry(key);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
	public List<T> selectEntryList(KEY...key) {
		return getDao().selectEntryList(key);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
	public List<T> selectEntryList(T condtion) {
		return getDao().selectEntryList(condtion);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
	public Page<T> selectPage(T condtion, Page<T> page) {
		try {
			Class<?> clz = condtion.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condtion, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condtion, page.getEndIndex());
		} catch (Exception e) {
			throw new RuntimeException("设置分页参数失败", e);
		}
		Long size = getDao().selectEntryListCount(condtion);
		if(size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(this.selectEntryList(condtion));
		return page;
	}
}
