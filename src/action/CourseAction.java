package action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import com.opensymphony.xwork2.Preparable;

import entity.Course;
import entity.StudentInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import service.ICourseService;

@ParentPackage("default")  
@Namespace("/course")
public class CourseAction extends BaseAction<Course, String> implements Preparable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICourseService courseService;
	
	private List<Course> courseList;
	
	private Course course;

	public ICourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(ICourseService courseService) {
		this.courseService = courseService;
	}
	
	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	

	
	@SuppressWarnings("unchecked")
	@Action(value="list",results = { @Result(name = "list", type="json",params={"root","courseList"})})
	public String list() {
		courseList = new ArrayList<Course>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//设置循环策略为忽略    解决json最头疼的问题 死循环
		jsonConfig.setExcludes(new String[] {"course"});//此处是亮点，只要将所需忽略字段加到数组中即可
		JSONArray json=JSONArray.fromObject(courseService.findAll(),jsonConfig);
		for (int i = 0; i < json.size(); i++) { 
			JSONObject jsonObject = json.getJSONObject(i);
			JSONArray student = jsonObject.getJSONArray("student");
			List<StudentInfo> studentList= (List<StudentInfo>) JSONArray.toCollection(student , StudentInfo.class);
			Set<StudentInfo> studentSet= new HashSet<StudentInfo>();
			for(int k = 0;k<studentList.size();k++) {
				studentSet.add(studentList.get(k));
			}
			Course course =  (Course) JSONObject.toBean(jsonObject, Course.class);
			course.setStudent(studentSet);
			courseList.add(course);
		}
		return "list";
	}
	
	@Action(value="save")
	public String save() {
		StudentInfo studentInfo = (StudentInfo) ActionContext.getContext().getSession().get("studentInfo");
		Set<StudentInfo> student = new HashSet<StudentInfo>();
		if(studentInfo != null) {
			student.add(studentInfo);
			course.setStudent(student);
		}
		courseService.saveOrUpdate(course);
		return NONE;
	}
	
	@Action(value="delete")
	public String delete() {
		return "delete";
	}
	@Action(value="edit",results = {
            @Result(name = "edit", location = "/admin/page/course/courseAdd.jsp")})
	public String edit() {
		course = entity;
		return "edit";
	}
	@Action(value="withdrawal")
	public String withdrawal() {
		Set<StudentInfo> studentSet = course.getStudent();
		Iterator<StudentInfo> it = studentSet.iterator();
		while(it.hasNext()) {
			StudentInfo student = it.next();
			studentSet.remove(student);
		}
		course.setStudent(studentSet);
		courseService.saveOrUpdate(course);
		return NONE;
	}
}
