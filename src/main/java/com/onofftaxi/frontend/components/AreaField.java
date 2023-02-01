package com.onofftaxi.frontend.components;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.textfield.TextField;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import elemental.json.JsonObject;
import lombok.Getter;
import lombok.ToString;

public class AreaField extends Composite<TextField> {
    private TextField textField;

    private Place place;

    public AreaField() {
        textField = getContent();
        textField.setAutocorrect(false);
        textField.setClearButtonVisible(true);
        scriptInput();
    }

    private void scriptInput() {
        textField.getElement().executeJavaScript(
                "(function(c,d,e,f){var j=d.shadowRoot.querySelector('input');" +
                        "var g=new google['maps']['places']['Autocomplete'](j);" +
                        "g['setFields'](['address_component','geometry']);" +
                        "g['addListener']('place_changed',function(){var h=g['getPlace']();" +
                        "if(!h['geometry']){" +
                        "window['alert']('Nie\\x20ma\\x20takiego\\x20miejsca\\x20jak:\\x20\\x27'+h['name']+'\\x27." +
                        "\\x20Jest\\x20Lądek,\\x20Lądek\\x20Zdrój...');" +
                        "return;}" +
                        "var i={" +
                        "'name':h['address_components'][0x0]['long_name']," +
                        "'district':h['address_components'][0x1]['long_name']['replace']('Powiat\\x20','')," +
                        "'lng':h['geometry']['location']['lng']()," +
                        "'lat':h['geometry']['location']['lat']()};c[e][f](i);});}($0,$1,$2,$3));",
                getElement(), textField.getElement(), "$server", "pushPlace");
    }

    private void getInputUsingJS() {
        textField.getElement().executeJavaScript("return this");
    }

    @ClientCallable
    private void pushPlace(JsonObject data) {
        place = new Place(data);
    }

    public Place getPlace() {
        return place;
    }

    @Getter
    @ToString
    public class Place {
        String name, district;
        Double lng, lat;

        public Place(JsonObject json) {
            name = json.getString("name");
            district = json.getString("district");
            lng = json.getNumber("lng");
            lat = json.getNumber("lat");
        }

        public Point createPoint() {
            Coordinate[] coordinates = {new Coordinate(lng, lat)};
            CoordinateSequence coordinateSequence = new CoordinateArraySequence(coordinates);
            return new Point(coordinateSequence, new GeometryFactory());

        }
    }
}