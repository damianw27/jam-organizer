package pl.wilenskid.jamorganizer.rest;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseRest {
    public void redirectTo(HttpServletResponse response, String path) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String currentUrl = response.encodeRedirectURL(baseUrl + path);
        response.setHeader("Location", currentUrl);
        response.setStatus(302);
    }
}
