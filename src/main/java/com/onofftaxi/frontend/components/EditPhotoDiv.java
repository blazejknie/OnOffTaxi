package com.onofftaxi.frontend.components;

import com.onofftaxi.backend.model.Driver;
import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.service.DriverService;
import com.onofftaxi.frontend.components.dialogs.BaseDialog;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import org.springframework.beans.factory.annotation.Autowired;

public class EditPhotoDiv extends Composite<Div> {

    private DriverService driverService;
    private DriverDto driver;

    public EditPhotoDiv(@Autowired DriverService driverService, Upload upload) {
        this.driverService = driverService;
        if (this.driverService.getDriver().isPresent()) {
            driver = this.driverService.getDriver().get();
        }
        getContent().add(getImageLayout(),getEditPhotoButton(upload));
    }

    private HorizontalLayout getImageLayout() {
        HorizontalLayout imageLayout = new HorizontalLayout();
        imageLayout.setClassName("imageLayout");
        String imageUrl = driver.getImageUrl();
        Image driverImage = new Image();
        driverImage.setClassName("driver-image");
        imageUrl = (imageUrl != null) ? imageUrl : "https://files.slack.com/files-pri/TJ1DNHXN3-FM568TQEN/avatar.jpg";
        driverImage.setSrc(imageUrl);
        driverImage.setAlt("broken image url");
        imageLayout.add(driverImage);
        return imageLayout;
    }

    private HorizontalLayout getEditPhotoButton(Upload upload) {
        HorizontalLayout vImageLayout = new HorizontalLayout();

        vImageLayout.setClassName("imageLayoutbutton");
        Button editPhoto = new Button("Edytuj zdjęcie");
        editPhoto.setClassName("show-hide-buttons");
        BaseDialog editPhotoDialog = new BaseDialog();

        editPhotoDialog.add(upload);
        editPhoto.addClickListener(e -> editPhotoDialog.open());
        vImageLayout.add(editPhoto);

        return vImageLayout;
    }

}
