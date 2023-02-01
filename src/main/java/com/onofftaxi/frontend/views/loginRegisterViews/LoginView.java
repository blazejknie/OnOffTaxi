package com.onofftaxi.frontend.views.loginRegisterViews;

import com.onofftaxi.backend.model.User;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.backend.service.LoginAttemptService;
import com.onofftaxi.backend.service.UserService;
import com.onofftaxi.frontend.components.buttons.navigationBarButtons.RegisterButton;
import com.onofftaxi.frontend.components.dialogs.BlockedUserDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.LoadMode;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "login")
@StyleSheet("frontend://styles/login-style.css")
@HtmlImport("frontend://styles/shared-styles.html")
@HtmlImport(value = "frontend://styles/custom-dialog-styles.html", loadMode = LoadMode.LAZY)
@HtmlImport("frontend://bower_components/iron-form/iron-form.html")
@Viewport("user-scalable=no,initial-scale=1.0")
public class LoginView extends FormLayout implements AfterNavigationObserver {

    public LoginView(@Autowired DriverService driverService,
                     @Autowired LoginAttemptService loginAttemptService,
                     @Autowired UserService userService) {
        if (driverService.getDriver().isPresent()) {
            UI.getCurrent().getPage().executeJavaScript("window.open(\"/driver/account\", \"_self\");");
        } else {

            TextField username = new TextField(null, "Podaj login lub email");
            username.setClassName("login-fields");
            username.getElement().setAttribute("name", "username");

            PasswordField password = new PasswordField(null, "Hasło");
            password.setClassName("login-fields");
            password.setId("password-field");
            password.getElement().setAttribute("name", "password");

            Binder<User> userBinder = new Binder<>();
            userBinder.forField(username).withValidator(t -> t.length() >= 1, "").asRequired().bind(User::getLogin, User::setLogin);
            userBinder.forField(password).withValidator(t -> t.length() >= 1, "").asRequired().bind(User::getPassword, User::setPassword);

            Button button = new Button("Zaloguj");
            button.setClassName("login-button");
            button.setId("submitbutton");

            UI.getCurrent().getPage().executeJavaScript(
                    "document.getElementById('submitbutton')" +
                            ".addEventListener('click', () => document.getElementById('ironform').submit());" +
                            "document.getElementById(\"password-field\")\n" +
                            ".addEventListener(\"keyup\", function(event) {\n" +
                            "event.preventDefault();\n" +
                            "if (event.keyCode === 13) {\n" +
                            "document.getElementById(\"submitbutton\").click();\n" +
                            "}\n" +
                            "});"
            );

            Anchor forgotPassAnchor = new Anchor("/forgotpassword", "Zapomniałem hasła");
            Button registerAnchor =  new RegisterButton();
            registerAnchor.setClassName("register-butAnch");
            registerAnchor.setText("Zarejestruj się");

            Div forgotanchDiv = new Div(forgotPassAnchor);
            forgotanchDiv.setClassName("forgotAnchDiv");
            HorizontalLayout anchors = new HorizontalLayout();
            anchors.add(registerAnchor, forgotanchDiv);

            Anchor logoanch = new Anchor("/", "onofftaxi.pl");
            logoanch.setClassName("logoanch");
            FormLayout formLayout = new FormLayout(logoanch, username, password, button, anchors);

            VerticalLayout layout = new VerticalLayout(formLayout);
            Element formElement = new Element("form");
            formElement.setAttribute("method", "post");
            formElement.setAttribute("action", "login");
            formElement.appendChild(formLayout.getElement());

            Element ironForm = new Element("iron-form");
            ironForm.setAttribute("id", "ironform");
            ironForm.setAttribute("allow-redirect", true);
            ironForm.appendChild(formElement);

            getElement().appendChild(ironForm);

            if (loginAttemptService.isBlocked(userService.getClientIP())){
                setClassName("login-view-blocked");
                getChildren().forEach(e-> e.getElement().setEnabled(false));

                forgotPassAnchor.setHref("/");
                getBlockedDialog().open();
            }else {
                setClassName("login-view");
            }
            add(layout);
        }
    }

    private Dialog getBlockedDialog() {
        return new BlockedUserDialog();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            Notification.show("Niepoprawny login lub hasło").setPosition(Notification.Position.TOP_CENTER);
        }
    }
}