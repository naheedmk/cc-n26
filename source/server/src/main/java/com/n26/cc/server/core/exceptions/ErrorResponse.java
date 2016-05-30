package com.n26.cc.server.core.exceptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.net.URI;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {

	@XmlElement
	private Long timestamp;

	@XmlElement
	private Integer status;

	@XmlElement
	private String error;

	@XmlElement
	private String message;

	@XmlElement
	private URI path;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public URI getPath() {
		return path;
	}

	public void setPath(final URI path) {
		this.path = path;
	}

}
