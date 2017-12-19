package action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

import entity.Admin;
import entity.StudentInfo;
import service.IAdminService;
import service.IStudentInfoService;

@ParentPackage("default")
@Namespace("/Admin")
public class AdminAction extends BaseAction<Admin, String> implements Preparable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	private IAdminService adminService;
	
	private Admin admin; //页面上传过来的对象
	
	private String id;
	
	private String code;//输入的验证码
	
	private String userName;//用户名
	
	private Map<String,Object> message = new HashMap<String,Object>();//后台返回信息
	
	private String type;//返回页面的type
	
	private String name;//名称
	
	private ActionContext actionContext = ActionContext.getContext();  
	
    private Map<String,Object> session = actionContext.getSession(); 
    
    private String password;//密码

    private HttpServletRequest request= ServletActionContext.getRequest();
	
	@Autowired
	private IStudentInfoService studentInfoService;
	
	private String image;
	
	private File file;
	
	private String fileFileName;

	
	//文件上传
	@Action(value="upload",results={@Result(name = "upload", type="json",params={"root","image"})})
	public String upload() throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File uploadfile = new File(path);
		if(!file.exists())file.mkdirs();
		String suffixName = fileFileName.substring(fileFileName.lastIndexOf("."));
		String hash = Integer.toHexString(new Random().nextInt());//自定义随机数（字母+数字）作为文件名
		String fileName = hash + suffixName;
		FileUtils.copyFile(file, new File(uploadfile,fileName));
		image = fileName;
		return "upload";
	}
	
	@Action(value="save",results = {
            @Result(name = "save", type="json",params={"root","msg"})})
	public String save() {
		return "save";
	}
	@Action(value="edit",results = {
            @Result(name = "edit", location = "/admin/page/user/userInfo.jsp")})
	public String edit() {
		admin=entity;
		return "edit";
	}
	
	@Action(value="quit",results = { @Result(name = "quit", location = "/admin/login/login.jsp")})
	public String quit() {
		session.clear();
		return "quit";
	}
	@Action(value="lockScreen",results = { 
			@Result(name = "lockScreen", type="json",params={"root","msg"})})
	public String lockSceen() {
		try {
			if(admin != null) {
				Admin adminResult =  adminService.checkLogin(admin);
				StudentInfo studentInfo = studentInfoService.checkLogin(admin.getUsername(), admin.getPassword());
				if(adminResult == null) {
					if(studentInfo != null) {
						name = studentInfo.getStu_name();
						type = studentInfo.getType();
						id=Integer.toString(studentInfo.getId());
						userName = studentInfo.getStu_id();
						message.put("state", true);
						return "lockScreen";
					}else {
						message.put("state", false);
						return "lockScreen";
					}
				}else {
					name=adminResult.getName();
					type = adminResult.getType();
					id=Integer.toString(adminResult.getId());
					userName = adminResult.getUsername();
					message.put("state", true);
					return "lockScreen";
				}
			}
		} catch (Exception e) {
		}
		return "lockScreen";
	}
	@Action(value="changePwd",results = { 
			@Result(name = "changePwd", type="json",params={"root","msg"})})
	public String changePwd() {
		try {
			if(entity != null) {
				Admin adminResult =  adminService.checkLogin(entity);
				StudentInfo studentInfo = studentInfoService.checkLogin(entity.getUsername(), entity.getPassword());
				if(adminResult == null) {
					if(studentInfo != null) {
						studentInfo.setPassword(password);
						studentInfoService.saveOrUpdate(studentInfo);
						message.put("state", true);
						message.put("msg", "密码修改成功");
						return "changePwd";
					}else {
						message.put("state", false);
						message.put("msg", "旧密码错误");
						return "changePwd";
					}
				}else {
					adminResult.setPassword(password);
					adminService.saveOrUpdate(adminResult);
					message.put("state", true);
					message.put("msg", "密码修改成功");
					return "changePwd";
				}
			}else {
				message.put("state", false);
				message.put("msg", "旧密码错误");
				return "changePwd";
			}
		} catch (Exception e) {
			message.put("state", false);
			message.put("msg", "密码修改失败");
			return "lockScreen";
		}
	}
	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, Object> getMessage() {
		return message;
	}

	public void setMessage(Map<String, Object> message) {
		this.message = message;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	
}
