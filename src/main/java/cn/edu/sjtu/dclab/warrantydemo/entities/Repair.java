package cn.edu.sjtu.dclab.warrantydemo.entities;

public class Repair {

	private long id;
	private String time;
	private String status; // possible values: waiting, doing, done.
	
	// Relations
	private Warranty warranty;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Warranty getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}
}
