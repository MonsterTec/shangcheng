package service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Dictionary;
import entity.StudentInfo;

@Transactional 
@Component(value = "studentInfoService")
public class StudentInfoService extends BaseService<StudentInfo> implements IStudentInfoService{

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> findByPId(int parentId) {
		String sql = "select * from system_dictionary where parentId = ?";
		Query query = getSession().createSQLQuery(sql).addEntity(Dictionary.class).setInteger(0, parentId);
		return query.list();
	}

	@Override
	public StudentInfo checkLogin(String stu_id, String password) {
		String sql = "select * from t_stuinfo where stu_id = ? and password = ?";
		Query query = getSession().createSQLQuery(sql).addEntity(StudentInfo.class).setString(0, stu_id).setString(1, password);
		return (StudentInfo) query.uniqueResult();
	}


}
