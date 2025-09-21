// Ocultar flash message después de 5 segundos
const flash = document.getElementById('flashMessage');
if (flash) {
    setTimeout(() => flash.style.display = 'none', 5000);
}

// Autocomplete ubicación
const input = document.getElementById('ubicacion');
const latInput = document.getElementById('latitud');
const lonInput = document.getElementById('longitud');
const sugerenciasDiv = document.getElementById('sugerencias');

input.addEventListener('input', async function() {
    const query = this.value.trim();
    sugerenciasDiv.innerHTML = '';
    if (query.length < 3) return;

    try {
        const response = await fetch(
            `https://nominatim.openstreetmap.org/search?format=json&addressdetails=1&limit=5&q=${encodeURIComponent(query)}`
        );
        const results = await response.json();

        results.forEach(place => {
            const li = document.createElement('li');
            li.textContent = place.display_name;

            li.addEventListener('click', () => {
                input.value = place.display_name;
                latInput.value = place.lat;
                lonInput.value = place.lon;
                sugerenciasDiv.innerHTML = ''; // cerrar lista
            });

            sugerenciasDiv.appendChild(li);
        });
    } catch (error) {
        console.error('Error buscando ubicación:', error);
    }
});

// Cerrar sugerencias si clickeo afuera
document.addEventListener('click', function(e) {
    if (!sugerenciasDiv.contains(e.target) && e.target !== input) {
        sugerenciasDiv.innerHTML = '';
    }
});
