package service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Dictionary;

@Transactional 
@Component(value = "dictionaryService")
public class DictionaryService extends BaseService<Dictionary> implements IDictionaryServcie{

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> findByParentId(int parentId) {
		String sql = "select * from system_dictionary where parentId = ?";
		Query query = getSession().createSQLQuery(sql).addEntity(Dictionary.class).setInteger(0, parentId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> findByName(String name) {
		String sql = "select * from system_dictionary where name like ?";
		Query query = getSession().createSQLQuery(sql).addEntity(Dictionary.class).setString(0, "%"+name+"%");
		return query.list();
		
	}
}
