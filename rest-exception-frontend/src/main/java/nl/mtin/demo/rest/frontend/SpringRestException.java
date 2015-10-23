package nl.mtin.demo.rest.frontend;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;

public class SpringRestException extends RuntimeException {
	private static final long serialVersionUID = -325748740667380625L;

	private final LocalDateTime dateTime;
	private final HttpStatus status;
	private final String message;
	private final String exception;
	private final String path;

	private SpringRestException(LocalDateTime dateTime, HttpStatus status, String message, String exception, String path){
		super(message);
		this.dateTime = dateTime;
		this.status = status;
		this.message = message;
		this.exception = exception;
		this.path = path;
	}

	public static class Builder {
		private final static String NO_MESSAGE = "No message available";
		
		private long timestamp;
		private int status;
		private String error;
		private String exception;
		private String message;
		// TODO
		// an exception might be available here to?
		private String path;

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		private LocalDateTime getTimestamp() {
			return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
		}
		
		public Builder setStatus(int status) {
			this.status = status;
			return this;
		}
		
		public Builder setError(String error) {
			this.error = error;
			return this;
		}
		
		public Builder setException(String exception) {
			this.exception = exception;
			return this;
		}
		
		public Builder setMessage(String message) {
			if (!NO_MESSAGE.equals(message)){
				this.message = message;
			}
			return this;
		}
		
		private String getMessage()
		{
			return (message == null) ? error : message;
		}
		
		public Builder setPath(String path) {
			this.path = path;
			return this;
		}

		public SpringRestException build()
		{
			return new SpringRestException(this.getTimestamp(), 
					HttpStatus.valueOf(this.status), this.getMessage(), exception, path);
		}
		
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getErrorMessage() {
		return message;
	}

	public String getException() {
		return exception;
	}	

	public String getPath() {
		return path;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public String toString() {
		return "SpringRestException [dateTime=" + dateTime + ", status=" + status + ", message=" + message
				+ ", path=" + path + ", exception=" + exception + "]";
	}
}
