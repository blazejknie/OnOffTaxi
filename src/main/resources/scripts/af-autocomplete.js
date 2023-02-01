(function (element, field, server, callback) {
    var input = field.shadowRoot.querySelector('input');
    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.setFields(['address_component', 'geometry']);

    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();

        if (!place.geometry) {
            window.alert("Nie ma takiego miejsca jak: '" + place.name + "'. Jest Lądek, Lądek Zdrój...");
            return;
        }

        var placeData = {
            name: place.address_components[0].long_name,
            district: place.address_components[1].long_name.replace('Powiat ', ''),
            lng: place.geometry.location.lng(),
            lat: place.geometry.location.lat()
        };
        element[server][callback](placeData);
    });
})