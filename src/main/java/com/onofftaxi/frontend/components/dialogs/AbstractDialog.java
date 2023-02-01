package com.onofftaxi.frontend.components.dialogs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;

import java.util.Arrays;

abstract class AbstractDialog extends Dialog {

    /**
     * dialogs aren't close
     * by outside click
     * and esc
     */

    void isNotCloseOnClickAndEsc() {
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);
    }

    /**
     * all dialogs have to
     *
     * @return VerticalLayout
     */

    VerticalLayout createVerticalDialogLayout() {
        return new VerticalLayout();
    }

    /**
     * There is no labels goddamn
     *
     * @param passwordFields
     */

    protected void setNoLabel(PasswordField... passwordFields) {
        Arrays.stream(passwordFields).forEach(component -> component.setLabel(null));
    }

    /**
     * dialogs class names
     */

    protected void setDialogPasswordFieldClassName(PasswordField... passwordFields) {
        Arrays.stream(passwordFields).forEach(passwordField -> passwordField.setClassName("dialog-passwordfields"));
    }

    protected void setDialogButtonsClassName(String className, Button... buttons) {
        Arrays.stream(buttons).forEach(button -> button.setClassName(className));
    }
}
