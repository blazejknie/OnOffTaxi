//package com.onofftaxi.frontend.views.loginRegisterViews;
//
//import com.onofftaxi.backend.service.DriverService;
//import com.onofftaxi.backend.service.LoginAttemptService;
//import com.onofftaxi.backend.service.UserService;
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class LoginViewTest {
//
//    @Mock
//    LoginView loginView;
//    @Autowired DriverService driverService;
//    @Autowired LoginAttemptService loginAttemptService;
//    @Autowired UserService userService;
//
//    @Test
//    public void shouldOpenDialog() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        // Mockito.whens() for your authorization object
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        loginView = new LoginView(driverService, loginAttemptService, userService);
//        Component wantedButton = loginView.getChildren().filter(child -> {
//            if (child.getId().get().equals("submitbutton")) {
//                return true;
//            } else {
//                return false;
//            }
//        } ).findFirst().get();
//
//        System.out.println(wantedButton);
//    }
//
//}
