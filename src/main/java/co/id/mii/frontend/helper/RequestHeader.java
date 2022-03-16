package co.id.mii.frontend.helper;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class RequestHeader {

    public static HttpHeaders getHeaders() {
        return new HttpHeaders() {
            {
                String auth = getAuth().getName() + ":" + getAuth().getCredentials().toString();
                byte[] encodeAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodeAuth);
                set("Authorization", authHeader);
            }
        };
    }

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
