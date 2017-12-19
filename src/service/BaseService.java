package service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional 
@Component(value = "baseService")
public class BaseService<T> implements IBaseService<T>{

	@Autowired(required = true)
	private SessionFactory sessionFactory;
	
	private Class<T> t;
	
	public BaseService() {
		try {
			t = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception ex) {
			// 忽略
		}
	}

	public BaseService(Class<T> t) {
		this.t = t;
	}
	@Override
	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Criteria c = getSession().createCriteria(t);
		List<T> list = c.list();
		return list;
	}

	@Override
	public T findById(int id) {
		Criteria c = getSession().createCriteria(t).add(Restrictions.eq("id", id));
		T t = (T) c.uniqueResult();
		return t;
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}

}
