package com.onofftaxi.frontend.components.dialogs;

import com.onofftaxi.frontend.components.frontendStrings.CookieSearcher;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@HtmlImport("frontend://styles/custom-dialog-styles.html")
@StyleSheet("frontend://styles/register-style.css")
@HtmlImport("frontend://styles/shared-styles.html")
public class BaseDialog extends AbstractDialog implements HasComponents {

    /**
     * base dialog
     * should be inherited
     * by all our
     * dialogs
     */

    private VerticalLayout layout;
    @Getter
    private CookieSearcher cookieSearcher;

    public BaseDialog() {
        setStyle();
        cookieSearcher = new CookieSearcher();
        layout = createVerticalDialogLayout();
        //this.add(layout);
    }

    /**
     * all dialogs have to get same style
     */

    private void setStyle() {
        this.getElement().setAttribute("theme", "custom-dialog");
    }

    /**
     * all
     * @param components
     * are added to vertical layout
     */

    @Override
    public void add(Component... components) {
        super.add(layout);
        layout.add(components);
    }

    /**
     * methods from
     *
     * @see AbstractDialog
     */

    @Override
    public void isNotCloseOnClickAndEsc() {
        super.isNotCloseOnClickAndEsc();
    }

    @Override
    VerticalLayout createVerticalDialogLayout() {
        return super.createVerticalDialogLayout();
    }
}
