function initAutocomplete() {
    const input = document.getElementById('autocomplete');
    const autocomplete = new google.maps.places.Autocomplete(input, { types: ['geocode'] });

    autocomplete.addListener('place_changed', function () {
        const place = autocomplete.getPlace();
        if (!place.geometry) return;

        document.getElementById('latitud').value = place.geometry.location.lat();
        document.getElementById('longitud').value = place.geometry.location.lng();
    });
}
