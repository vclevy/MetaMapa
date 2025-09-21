// Inicializar mapa
const map = L.map('map').setView([-34.6037, -58.3816], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

// Cluster de markers
const markersCluster = L.markerClusterGroup();
map.addLayer(markersCluster);

// Últimos bounds cargados para evitar redraw innecesario
let lastBounds = null;

// Debounce
let timeoutId = null;

// Función para crear ícono por categoría
function crearIcono(categoria) {
    const url = `/img/beanosPin.png`; // Podés cambiar según categoría
    return L.icon({
        iconUrl: url,
        iconSize: [30, 70],
        iconAnchor: [15, 30],
        popupAnchor: [0, -30]
    });
}

// Agregar hechos al mapa
function agregarHechos(hechos) {
    markersCluster.clearLayers();

    hechos.forEach(h => {
        const lat = h.lugar?.latitud;
        const lng = h.lugar?.longitud;

        if (lat && lng) {
            const popupContent = `
                <div class="card-popup">
                    ${h.multimedia && h.multimedia.length > 0 ? `
                        <div>
                            <a href="/hechos/${h.id}">
                                <img src="${h.multimedia[0]}" alt="${h.titulo}" style="width:100%; height:auto; border-radius:5px;" />
                            </a>
                        </div>
                    ` : ''}
                    <div class="card-body">
                        <h3>${h.titulo}</h3>
                        <p><small>${new Date(h.fechaDeAcontecimiento).toLocaleDateString()}</small></p>
                        <p>${h.descripcion ?? ''}</p>
                        <a href="/hechos/${h.id}" class="card-link">Ver detalle</a>
                    </div>
                </div>
            `;

            const marker = L.marker([lat, lng], { icon: crearIcono(h.categoria?.nombre) });
            marker.bindPopup(popupContent);
            markersCluster.addLayer(marker);
        }
    });

    if (markersCluster.getLayers().length > 0) {
        map.fitBounds(markersCluster.getBounds(), { padding: [50, 50] });
    }
}

// Cargar todos los hechos al inicio
async function cargarTodosHechos() {
    try {
        const response = await fetch('/hechos/all');
        if (!response.ok) throw new Error('Error al cargar todos los hechos');
        const hechos = await response.json();
        agregarHechos(hechos);
    } catch (error) {
        console.error(error);
    }
}

// Cargar hechos según bounds
async function cargarHechosEnVista() {
    const bounds = map.getBounds();

    // Evitar redraw si bounds no cambiaron
    if (lastBounds && lastBounds.equals(bounds)) return;
    lastBounds = bounds;

    const url = `/hechos/bounds?south=${bounds.getSouth()}&west=${bounds.getWest()}&north=${bounds.getNorth()}&east=${bounds.getEast()}`;

    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error('Error al cargar hechos en vista');
        const hechos = await response.json();
        agregarHechos(hechos);
    } catch (error) {
        console.error(error);
    }
}

// Primer carga
cargarTodosHechos();

// Actualizar al mover/zoom con debounce
map.on('moveend', () => {
    if (timeoutId) clearTimeout(timeoutId);
    timeoutId = setTimeout(cargarHechosEnVista, 300); // 300ms
});
