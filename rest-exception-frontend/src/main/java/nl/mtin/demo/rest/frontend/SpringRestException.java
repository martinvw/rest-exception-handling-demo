package nl.mtin.demo.rest.frontend;

import java.lang.reflect.Constructor;
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

		@SuppressWarnings("rawtypes")
		public RuntimeException build()
		{
			SpringRestException restException = new SpringRestException(this.getTimestamp(), 
					HttpStatus.valueOf(this.status), this.getMessage(), exception, path);
			
			if (exception != null && !exception.equals(""))
			{
				try {
					Class<? extends RuntimeException> clazz = Class.forName(exception).asSubclass(RuntimeException.class);
					Class[] argTypes = {String.class, Throwable.class};
					Constructor<? extends RuntimeException> constructor;
					constructor = clazz.getDeclaredConstructor(argTypes);
					return constructor.newInstance(this.getMessage(), restException);					
				} catch (SecurityException | ReflectiveOperationException e) {
					// TODO log this 
					System.err.printf("Could not rethrow exception: %s", exception);
				}
			}
			
			return restException;
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
