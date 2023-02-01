package com.onofftaxi.frontend.components.frontendStrings;

import com.vaadin.flow.server.VaadinService;

import javax.servlet.http.Cookie;
import java.util.Optional;

public class CookieSearcher {

    public Optional<Cookie> getCookieByName(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }
}
