package action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Preparable;

import entity.Navs;
import service.INavsService;



@ParentPackage("default") 
@Namespace("/Navs")
public class NavsAction extends BaseAction<Navs, String> implements Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	private INavsService navsService;
	
	private List<Navs> navs;
	
	private List<Navs> result;
	
	private Navs nav;//前台传入的对象
	
	private String parentId;//父模块的ID
	
	private String type;//登录用户类型
	
	
	@Action(value="findAll", results = { @Result(name = "navs", type="json",params={"root","result"})})
	public String findAll() {
		navs = navsService.findByType(type);
		Iterator<Navs> it = navs.iterator();
		result = new ArrayList<Navs>();
		while(it.hasNext()) {
			Navs navsSingle = it.next();
			Iterator<Navs> it2 = navs.iterator();
			List<Navs> navsList = new ArrayList<Navs>();
			while(it2.hasNext()) {
				Navs navsSingle2 = it2.next();
				if(navsSingle.getId() == navsSingle2.getParentId()) {
					navsList.add(navsSingle2);
				}
			}
			navsSingle.setChildren(navsList);
			if(navsSingle.getParentId() == 0) {
				result.add(navsSingle);
			}
		}
		
		return "navs";
	}
	@Action(value="list", results = { @Result(name = "list", type="json",params={"root","list"})})
	public String list() {
		return "list";
	}
	@Action(value="view",results = {
            @Result(name = "success", location = "/admin/page/navs/navsAdd.jsp")})
	public String view() {
		nav = entity;
		return "success";
	}
	@Action(value="save")
	public String save() {
		if(parentId != null) {
			nav.setParentId(Integer.parseInt(parentId));
		}
		navsService.saveOrUpdate(nav);
		return NONE;
	}
	@Action(value="delete")
	public String delete() {
		return "delete";
	}
	public INavsService getNavsService() {
		return navsService;
	}

	public void setNavsService(INavsService navsService) {
		this.navsService = navsService;
	}

	public List<Navs> getNavs() {
		return navs;
	}

	public void setNavs(List<Navs> navs) {
		this.navs = navs;
	}

	public List<Navs> getResult() {
		return result;
	}

	public void setResult(List<Navs> result) {
		this.result = result;
	}
	public Navs getNav() {
		return nav;
	}
	public void setNav(Navs nav) {
		this.nav = nav;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
