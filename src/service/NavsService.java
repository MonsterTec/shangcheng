package service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Navs;

@Transactional 
@Component(value = "navsService")
public class NavsService extends BaseService<Navs> implements INavsService{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Navs> findByType(String type) {
		String sql="select * from system_navs where type like ? order by parentId";
		Query query = getSession().createSQLQuery(sql).addEntity(Navs.class).setString(0,"%"+type+"%");
		return query.list();
	}

}
