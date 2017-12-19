package action;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import service.IBaseService;


public abstract class BaseAction<T,PK extends Serializable> extends ActionSupport implements Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected PK id;

	protected T entity;

	protected Class<T> entityClass;

	@Autowired
	protected IBaseService<T> baseService;

	protected String ids;

	protected List<T> list;
	
	protected Map<String,Object> msg = new HashMap<String,Object>();

	public PK getId() {
		return id;
	}


	public void setId(PK id) {
		this.id = id;
	}


	public T getEntity() {
		return entity;
	}


	public void setEntity(T entity) {
		this.entity = entity;
	}


	public Class<T> getEntityClass() {
		return entityClass;
	}


	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}

	public Map<String, Object> getMsg() {
		return msg;
	}


	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}


	@SuppressWarnings("unchecked")
	public BaseAction() {
		entityClass = (Class<T>) getSuperClassGenricType(this.getClass());
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class<Object> getSuperClassGenricType(final Class clz) {  
		Type type = clz.getGenericSuperclass();  
		if(!(type instanceof ParameterizedType)) {  
			return Object.class;  
		}  
		Type[] params = ((ParameterizedType)type).getActualTypeArguments();  
		if(!(params[0] instanceof Class)) {  
			return Object.class;  
		}  
		return (Class) params[0];  
	}  
	@Override
	public void prepare() throws Exception {

	}
	public void prepareSave() throws Exception{
		if(id == null ||id.equals("")) {
			entity = entityClass.newInstance();
		}else {
			entity = baseService.findById(Integer.parseInt(id.toString()));
		}
	}
	public void prepareView() throws Exception{
		if(id == null ||id.equals("")) {
			entity = entityClass.newInstance();
		}else {
			entity = baseService.findById(Integer.parseInt(id.toString()));
		}
	}
	public void prepareEdit() throws Exception{
		if(id == null ||id.equals("")) {
			entity = entityClass.newInstance();
		}else {
			entity = baseService.findById(Integer.parseInt(id.toString()));
		}
	}
	
	public String save() {
		try {
			baseService.saveOrUpdate(entity);
			msg.put("state", true);
			msg.put("msg", "保存成功");
		} catch (Exception e) {
			msg.put("state", false);
			msg.put("msg", "保存失败");
			e.printStackTrace();
		}
		return "save";
	}
	public String delete() {
		try {
			if(ids != null) {
				String[] id = ids.split(",");
				for(int i = 0;i<id.length;i++) {
					T entityDelete = baseService.findById(Integer.parseInt(id[i]));
					baseService.delete(entityDelete);
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String list() {
		list = baseService.findAll();
		return SUCCESS;
	}
	public String view() {
		return SUCCESS;
	}
	public String edit() {
		return SUCCESS;
	}

}
