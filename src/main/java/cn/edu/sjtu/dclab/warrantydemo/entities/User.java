package cn.edu.sjtu.dclab.warrantydemo.entities;

public class User {
	
	
	private long id;
	private String name;
	private String role; // possible values: normal, worker
		
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
