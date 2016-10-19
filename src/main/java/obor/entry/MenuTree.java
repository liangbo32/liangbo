package obor.entry;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {
	private String resource_id;
	private String resource_name;
	private String resource_string;
	private String parentid;
	private String isselect="false";
	private String app_id;
	private List<MenuTree> childList=new ArrayList<MenuTree>();
	
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public String getResource_name() {
		return resource_name;
	}
	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getIsselect() {
		return isselect;
	}
	public void setIsselect(String isselect) {
		this.isselect = isselect;
	}
	public List<MenuTree> getChildList() {
		return childList;
	}
	public void setChildList(List<MenuTree> childList) {
		this.childList = childList;
	}
	public String getResource_string() {
		return resource_string;
	}
	public void setResource_string(String resource_string) {
		this.resource_string = resource_string;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	
}
