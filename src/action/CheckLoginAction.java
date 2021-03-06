package action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.Admin;
import entity.StudentInfo;
import service.IAdminService;
import service.IStudentInfoService;

@Namespace(value="/checkLogin")
public class CheckLoginAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	private IAdminService adminService;
	
	private Admin admin; //页面上传过来的对象
	
<<<<<<< HEAD
	
	private String id;
	
=======
>>>>>>> branch 'master' of git@github.com:seakyle/shangcheng.git
	private String code;//输入的验证码
	
	private String userName;//用户名
	
	private Map<String,Object> msg = new HashMap<String,Object>();//后台返回信息
	
	private String type;//返回页面的type
	
	private String name;//名称
	
	private ActionContext actionContext = ActionContext.getContext();  
	
    private Map<String,Object> session = actionContext.getSession(); 
    
    private String image;
    
    private String id;
    
    @Autowired
	private IStudentInfoService studentInfoService;

    @Action(value="checkLogin",results = {
            @Result(name = "success", location = "/admin/index.jsp"),
            @Result(name = "error", location = "/admin/login/login.jsp"),
            @Result(name = "input", location = "/admin/login/login.jsp")}
			)
	public String checkLogin() {
		try {
			if(admin != null) {
				Admin adminResult =  adminService.checkLogin(admin);
				StudentInfo studentInfo = studentInfoService.checkLogin(admin.getUsername(), admin.getPassword());
				if(adminResult == null || !code.equals(getRand())) {
					if(studentInfo != null && code.equals(getRand())) {
						name = studentInfo.getStu_name();
						type = studentInfo.getType();
						id=Integer.toString(studentInfo.getId());
						userName = studentInfo.getStu_id();
				        session.put("studentInfo", studentInfo);
						return "success";
					}else {
						return "error";
					}
				}else {
					name=adminResult.getName();
					type = adminResult.getType();
					id=Integer.toString(adminResult.getId());
					userName = adminResult.getUsername();
					image = adminResult.getImage();
					session.put("admin", adminResult);
					return "success";
				}
			}
			return "input";
		} catch (Exception e) {
			
			return "input";
		}
				
	}
	
	//获取页面生成的验证码
		private String getRand() {
			// TODO Auto-generated method stub
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			String rand = (String) session.get("rand");
			return rand;
		}

		public Admin getAdmin() {
			return admin;
		}

		public void setAdmin(Admin admin) {
			this.admin = admin;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Map<String, Object> getMsg() {
			return msg;
		}

		public void setMsg(Map<String, Object> msg) {
			this.msg = msg;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public IAdminService getAdminService() {
			return adminService;
		}

		public void setAdminService(IAdminService adminService) {
			this.adminService = adminService;
		}

		public IStudentInfoService getStudentInfoService() {
			return studentInfoService;
		}

		public void setStudentInfoService(IStudentInfoService studentInfoService) {
			this.studentInfoService = studentInfoService;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}
		
	
}
