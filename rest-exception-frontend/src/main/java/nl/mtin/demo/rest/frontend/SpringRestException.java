package nl.mtin.demo.rest.frontend;

import org.springframework.http.HttpStatus;

public class SpringRestException extends RuntimeException {
	private static final long serialVersionUID = -325748740667380625L;

	// TODO some date field
	private final HttpStatus status;
	private final String error;
	private final String errorMessage;
	private final String path;

	private SpringRestException(HttpStatus status, String error, String message, String path){
		super("Test mtin" + error + message);
		this.status = status;
		this.error = error;
		this.errorMessage = message;
		this.path = path;
	}

	public static class Builder {
		// TODO do something with the date field
		private long timestamp;
		private int status;
		private String error;
		private String message;
		// TODO
		// an exception might be available here to?
		private String path;

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		public Builder setStatus(int status) {
			this.status = status;
			return this;
		}
		public Builder setError(String error) {
			this.error = error;
			return this;
		}
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public Builder setPath(String path) {
			this.path = path;
			return this;
		}

		public SpringRestException build()
		{
			return new SpringRestException(HttpStatus.valueOf(this.status), error, message, path);
		}
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getPath() {
		return path;
	}  

}
