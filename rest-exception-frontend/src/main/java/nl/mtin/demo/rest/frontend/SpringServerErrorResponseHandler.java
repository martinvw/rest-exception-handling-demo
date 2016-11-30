package nl.mtin.demo.rest.frontend;

import java.io.IOException;

import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

import org.apache.commons.io.IOUtils;

public class SpringServerErrorResponseHandler implements ResponseErrorHandler {
    private final ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
    private final ObjectMapper mapper;

    public SpringServerErrorResponseHandler() {
        mapper = new ObjectMapper();
    }

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        String responseBody = IOUtils.toString(response.getBody());
        SpringRestException.Builder builder = mapper.readValue(responseBody, SpringRestException.Builder.class);
        throw builder.build();
    }
}
