var map;
var geocoder;
var marker;
var browserSupportFlag = new Boolean();
var initialLocation;

function displaymap() {
    if (!map) {
        var latlng = new google.maps.LatLng(53.346043, -6.265743);
        geocoder = new google.maps.Geocoder();
        document.getElementById('maps_api').style.width = "50%";
        document.getElementById('maps_api').style.height = "50%";
        var mapOptions = {
            zoom: 12,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById('maps_api'), mapOptions);

        google.maps.event.addListener(map, 'rightclick', function (event) {
            setPosition(event.latLng);
            getAddressFromPosition(event.latLng);
        });

        getLocation();
    } else {
        getLocation();
    }



}

function getLocation() {
    var address = document.getElementById("address").value;
    geocoder.geocode({
        'address': address,
        'region': "IE"
    }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            setPosition(results[0].geometry.location);
            setAddress(results[0].formatted_address);
        }
    });
}

function useCurrentLocation() {

    if (navigator.geolocation) {
        browserSupportFlag = true;
        navigator.geolocation.getCurrentPosition(function (position) {
            var initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            setPosition(initialLocation);
            getAddressFromPosition(initialLocation);
        }, function () {
            handleNoGeolocation(browserSupportFlag);
        });
    }
    // Browser doesn't support Geolocation
    else {
        browserSupportFlag = false;
        handleNoGeolocation(browserSupportFlag);
    }

}

function setPosition(position) {
    //map.setCenter(position);
    if (!marker) {
        marker = new google.maps.Marker({
            map: map,
            position: position
        });
    } else {
        marker.setPosition(position);
    }
    document.getElementById("latlong").value = position.toString();
}

function setAddress(address) {
    document.getElementById("address").value = address;
}

function getAddressFromPosition(position) {
    geocoder.geocode({
        'latLng': position
    }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[0]) {
                setAddress(results[0].formatted_address);
            }
        } else {
            alert("Geocoder failed due to: " + status);
        }
    });
}


function handleNoGeolocation(errorFlag) {
    if (errorFlag == true) {
        alert("Geolocation service failed.");
        initialLocation = newyork;
    } else {
        alert("Your browser doesn't support geolocation. We've placed you in Siberia.");
        initialLocation = siberia;
    }
    map.setCenter(initialLocation);
}





//google.maps.event.addDomListener(window, 'load', initialize);
