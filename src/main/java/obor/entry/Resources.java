package obor.entry;

import java.io.Serializable;

public class Resources implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3305008028755392901L;
	private String resource_id;
	private String resource_name;
	private String resource_desc;
	private String resource_type;
	private String resource_string;
	private String parentid;
	private String app_id;
	private String enabled;
	private String createTime;
	private String updateTime; 
	private String isselect="false";
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
	public String getResource_desc() {
		return resource_desc;
	}
	public void setResource_desc(String resource_desc) {
		this.resource_desc = resource_desc;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
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
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getIsselect() {
		return isselect;
	}
	public void setIsselect(String isselect) {
		this.isselect = isselect;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
