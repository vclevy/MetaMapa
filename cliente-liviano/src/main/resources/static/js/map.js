// Inicializar mapa
const map = L.map('map').setView([-34.6037, -58.3816], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

// Cluster de markers
const markersCluster = L.markerClusterGroup();
map.addLayer(markersCluster);

// Función para crear ícono por categoría
function crearIcono(categoria) {
    const url = `/img/pin.png`; //TODO Bonus, poner un pin distinto por categoria o chiche asi
    return L.icon({
        iconUrl: url,
        iconSize: [30, 30],
        iconAnchor: [15, 30],
        popupAnchor: [0, -30]
    });
}

// Función para agregar hechos al mapa
function agregarHechos(hechos) {
    markersCluster.clearLayers();

    hechos.forEach(h => {
        // Acceder a latitud y longitud dentro de lugar
        const lat = h.lugar?.latitud;
        const lng = h.lugar?.longitud;

        if (lat && lng) {
            let popupContent = `
                <div class="card-popup">
                    ${h.multimedia && h.multimedia.length > 0 ? `
                        <div>
                            <a href="/hechos/${h.id}">
                                <img src="${h.multimedia[0]}" alt="Imagen del hecho ${h.titulo}" style="width:100%; height:auto; border-radius:5px;" />
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

    // Ajustar mapa para mostrar todos los markers
    if (markersCluster.getLayers().length > 0) {
        map.fitBounds(markersCluster.getBounds(), { padding: [50, 50] });
    }
}

// Cargar todos los hechos
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

// Actualizar al mover/zoom
map.on('moveend', cargarHechosEnVista);
