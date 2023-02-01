(function(element, target) {
    var location = navigator.geolocation;

    location.getCurrentPosition(geocodeLatLng, locationError);

    function locationError() {
        window.alert("location not supported");
    }

    function geocodeLatLng(position) {
        var geocoder = new google.maps.Geocoder;
        var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

        geocoder.geocode({'location': latlng}, function (results, status) {
            if (status === 'OK') {
                if (results[0]) {
                    var field = element.querySelector('.location-field');
                    target.value = results[0].formatted_address;
                } else {
                    window.alert('No results found');
                }
            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
    }
})($0)
