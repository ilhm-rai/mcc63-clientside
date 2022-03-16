package co.id.mii.frontend.helper;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

public class RestTemplateInterceptorHeader implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        if (RequestHeader.getAuth() != null && !(RequestHeader.getAuth() instanceof AnonymousAuthenticationToken)) {
            request.getHeaders().addAll(RequestHeader.getHeaders());
        }

        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }

}
