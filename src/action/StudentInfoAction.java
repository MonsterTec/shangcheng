package action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;


import entity.Course;
import entity.Dictionary;
import entity.StudentInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import service.IDictionaryServcie;
import service.IStudentInfoService;

@ParentPackage("default")
@Namespace("/studentInfo")
public class StudentInfoAction extends BaseAction<StudentInfo, String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IStudentInfoService studentInfoService;
	
	private List<StudentInfo> stuInfo;
	
	private StudentInfo stu;
	
	private List<Dictionary> listForDictionary;
	
	private String parentId;
	
	@Autowired
	private IDictionaryServcie dictionaryService;
	
	private String name;
	
	private Set<Course> course;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IDictionaryServcie getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(IDictionaryServcie dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Dictionary> getListForDictionary() {
		return listForDictionary;
	}

	public void setListForDictionary(List<Dictionary> listForDictionary) {
		this.listForDictionary = listForDictionary;
	}

	public IStudentInfoService getStudentInfoService() {
		return studentInfoService;
	}

	public void setStudentInfoService(IStudentInfoService studentInfoService) {
		this.studentInfoService = studentInfoService;
	}
	
	public List<StudentInfo> getStuInfo() {
		return stuInfo;
	}

	public void setStuInfo(List<StudentInfo> stuInfo) {
		this.stuInfo = stuInfo;
	}
	
	public Set<Course> getCourse() {
		return course;
	}

	public void setCourse(Set<Course> course) {
		this.course = course;
	}
	public StudentInfo getStu() {
		return stu;
	}

	public void setStu(StudentInfo stu) {
		this.stu = stu;
	}

	@SuppressWarnings("unchecked")
	@Action(value="list",results = { @Result(name = "list", type="json",params={"root","stuInfo"})})
	public String list() {
		stuInfo = new ArrayList<StudentInfo>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//设置循环策略为忽略    解决json最头疼的问题 死循环
		jsonConfig.setExcludes(new String[] {"studet"});//此处是亮点，只要将所需忽略字段加到数组中即可
		JSONArray json=JSONArray.fromObject(studentInfoService.findAll(),jsonConfig);
		for (int i = 0; i < json.size(); i++) { 
			JSONObject jsonObject = json.getJSONObject(i);
			JSONArray course = jsonObject.getJSONArray("course");
			List<Course> courseList=  (List<Course>) JSONArray.toCollection(course , Course.class);
			Set<Course> courseSet= new HashSet<Course>();
			for(int k = 0;k<courseList.size();k++) {
				courseSet.add(courseList.get(k));
			}
			StudentInfo student =  (StudentInfo) JSONObject.toBean(jsonObject, StudentInfo.class);
			student.setCourse(courseSet);
			stuInfo.add(student);
		}
		
		return "list";
	}

	@Action(value="save")
	public String save() {
		return "save";
	}

	@Action(value="delete")
	public String delete() {
		return "delete";
	}

	@Action(value="view",results = {
            @Result(name = "view", location = "/admin/page/studentInfo/studentInfoView.jsp")})
	public String view() {
		stu = entity;
		return "view";
	}

	@Action(value="edit",results = {
            @Result(name = "edit", location = "/admin/page/studentInfo/studentInfoAdd.jsp")})
	public String edit() {
		stu = entity;
		return "edit";
	}
	
	@Action(value="perInfoedit",results = {
            @Result(name = "success", location = "/admin/page/studentInfo/studentInfo.jsp")})
	public String perInfoedit() {
		stu = studentInfoService.findById(Integer.parseInt(id));
		return "success";
	}



	@Action(value="listForSelect",results = { @Result(name = "listForSelect", type="json",params={"root","listForDictionary"})})
	public String listForSelect() {
		listForDictionary = studentInfoService.findByPId(Integer.parseInt(parentId));
		return "listForSelect";
	}

	@Action(value="listForProvice",results = { @Result(name = "listForSelect", type="json",params={"root","listForDictionary"})})
	public String listForProvice() {
		try {
			listForDictionary = dictionaryService.findByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listForSelect";
	}
	@Action(value="courseSelected",results = { @Result(name = "courseSelected", type="json",params={"root","course"})})
	public String courseSelected() {
		course = stu.getCourse();
		return "courseSelected";
	}


}
