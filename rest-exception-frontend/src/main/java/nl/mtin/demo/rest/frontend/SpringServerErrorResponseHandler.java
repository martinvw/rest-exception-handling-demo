package nl.mtin.demo.rest.frontend;

import java.io.IOException;

import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

import org.apache.commons.io.IOUtils;

public class SpringServerErrorResponseHandler implements ResponseErrorHandler {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        String responseBody = IOUtils.toString(response.getBody());
        // TODO use a logger
        System.err.println(responseBody);
        // TODO is the ObjectMApper expensive???
        ObjectMapper mapper = new ObjectMapper();
        SpringRestException.Builder builder = mapper.readValue(responseBody, SpringRestException.Builder.class);
        throw builder.build();
    }
}
