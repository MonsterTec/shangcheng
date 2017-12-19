package service;


import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Admin;

@Transactional 
@Component(value = "adminService")
public class AdminService extends BaseService<Admin> implements IAdminService{
	
	
	@Override
	public Admin checkLogin(Admin admin) {
		String sql = "select * from t_admin where username=? and password=?";
		Query query = getSession().createSQLQuery(sql).addEntity(Admin.class).setString(0, admin.getUsername()).setString(1, admin.getPassword());
		Admin result =  (Admin) query.uniqueResult();
		return result;
	}

}
