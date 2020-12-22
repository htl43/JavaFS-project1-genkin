package com.revature.ers.model;

public class ErsReimbursmentStatus {
	
	private int statusId;
	private String status;
	
	public ErsReimbursmentStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ErsReimbursmentStatus(int statusId, String status) {
		super();
		this.statusId = statusId;
		this.status = status;
	}
	
	public ErsReimbursmentStatus(int statusId) {
		super();
		this.statusId = statusId;
	}

	public int getStatusId() {
		return statusId;
	}
	
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + statusId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErsReimbursmentStatus other = (ErsReimbursmentStatus) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusId != other.statusId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErsRedimbursementStatus [statusId=" + statusId + ", status=" + status + "]";
	}
	
}
