package nl.mtin.demo.rest.frontend;

import org.springframework.http.HttpStatus;

import java.lang.reflect.Constructor;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.logging.Logger;

public class SpringRestException extends RuntimeException {
    private final static Logger LOGGER = Logger.getLogger(SpringRestException.class.getName());
    private static final long serialVersionUID = -325748740667380625L;

    private final LocalDateTime dateTime;
    private final HttpStatus status;
    private final String message;
    private final String exception;
    private final String path;

    private SpringRestException(Builder builder) {
        super(getDetailMessage(builder));
        this.dateTime = builder.getTimestamp();
        this.status = HttpStatus.valueOf(builder.status);
        this.message = builder.message;
        this.exception = builder.exception;
        this.path = builder.path;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    private static String getDetailMessage(Builder builder) {
        if (builder.exception == null){
            return String.format("Status code %d was returned by remote server at %s, " +
                            "when calling path '%s'.",
                    builder.status,
                    builder.getTimestamp(),
                    builder.path);
        } else {
            return String.format("Error of type '%s' with message '%s' was thrown by remote server at %s, " +
                            "when calling path '%s', status %d was returned",
                    builder.exception,
                    builder.message,
                    builder.getTimestamp(),
                    builder.path,
                    builder.status);
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
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
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
            if (!NO_MESSAGE.equals(message)) {
                this.message = message;
            }
            return this;
        }

        private String getMessage() {
            return (message == null) ? error : message;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        @SuppressWarnings("rawtypes")
        public RuntimeException build() {
            SpringRestException restException = new SpringRestException(this);

            if (exception != null && !exception.equals("")) {
                try {
                    Class<? extends RuntimeException> clazz = Class.forName(exception).asSubclass(RuntimeException.class);
                    Class[] argTypes = {String.class, Throwable.class};
                    Constructor<? extends RuntimeException> constructor;
                    constructor = clazz.getDeclaredConstructor(argTypes);
                    return constructor.newInstance(this.getMessage(), restException);
                } catch (ClassCastException | SecurityException | ReflectiveOperationException e) {
                    LOGGER.warning("Could not rethrow exception of type: " + e.getClass().getSimpleName());
                }
            }

            return restException;
        }
    }

    @Override
    public String toString() {
        return "SpringRestException [dateTime=" + dateTime + ", status=" + status + ", message=" + message
                + ", path=" + path + ", exception=" + exception + "]";
    }
}
