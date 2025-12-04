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
const form = document.querySelector('form');

// Función para limpiar hidden inputs
function limpiarLatLng() {
    latInput.value = '';
    lonInput.value = '';
}

// Manejar envío del formulario
form.addEventListener('submit', function(e) {
    if (!latInput.value || !lonInput.value) {
        e.preventDefault();
        alert('Por favor seleccioná una ubicación válida de las sugerencias.');
        input.focus();
    }
});

// Detectar cambios en el input
input.addEventListener('input', async function() {
    const query = this.value.trim();
    sugerenciasDiv.innerHTML = '';

    if (query.length < 3) {
        limpiarLatLng(); // limpiar si no hay suficiente texto
        return;
    }

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
        limpiarLatLng(); // limpiar si falla la búsqueda
    }
});

// Cerrar sugerencias si clickeo afuera
document.addEventListener('click', function(e) {
    if (!sugerenciasDiv.contains(e.target) && e.target !== input) {
        sugerenciasDiv.innerHTML = '';
    }
});
