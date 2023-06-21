package com.user.main.payload.response;

public class RegistrationMessage {
	String message;
	Boolean status;

	public RegistrationMessage(String message, Boolean status) {
		super();
		this.message = message;
		this.status = status;
	}


	public RegistrationMessage() {
		super();
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RegistrationMessage [message=" + message + ", status=" + status + "]";
	}
}
