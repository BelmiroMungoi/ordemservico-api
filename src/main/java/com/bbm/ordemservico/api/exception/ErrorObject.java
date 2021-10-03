package com.bbm.ordemservico.api.exception;

import java.time.LocalDateTime;

public class ErrorObject {

	private String status;
	private LocalDateTime time;
	private String title;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
