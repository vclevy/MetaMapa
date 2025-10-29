 console.log("📍 map.js cargado");

    // === Inicializar mapa ===
    const map = L.map('map').setView([-34.6037, -58.3816], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

    // === Cluster de markers ===
    const markersCluster = L.markerClusterGroup();
    map.addLayer(markersCluster);

    // === Función para crear ícono por categoría ===
    function crearIcono(categoria) {
    const url = `/img/monoPin.png`; // Cambiá si querés íconos distintos por categoría
    return L.icon({
    iconUrl: url,
    iconSize: [93, 70],
    iconAnchor: [46, 70],
    popupAnchor: [0, -70]
});
}

    // === Función para agregar hechos al mapa ===
    function agregarHechos(hechos) {
    console.log("🗺️ Agregando hechos al mapa:", hechos);

    markersCluster.clearLayers();

    if (!hechos || hechos.length === 0) {
    console.warn("⚠️ No se recibieron hechos para mostrar en el mapa.");
    return;
}

    hechos.forEach(h => {
    const lat = h?.lugar?.latitud;
    const lng = h?.lugar?.longitud;

    if (!lat || !lng) {
    console.warn("❌ Hecho sin coordenadas:", h);
    return;
}

    const popupContent = `
            <div class="card-popup">
                ${h.multimedia && h.multimedia.length > 0 ? `
                    <div>
                        <a href="/hechos/${h.id}">
                            <img src="${h.multimedia[0]}" alt="${h.titulo}" 
                                 style="width:100%; height:auto; border-radius:5px;" />
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
});

    // Ajustar el mapa a los marcadores
    if (markersCluster.getLayers().length > 0) {
    map.fitBounds(markersCluster.getBounds(), { padding: [50, 50] });
    console.log("✅ Marcadores agregados:", markersCluster.getLayers().length);
} else {
    console.warn("⚠️ Ningún marcador válido fue agregado (ver coordenadas).");
}
}
    console.log("🧾 Hechos recibidos desde Thymeleaf:", hechos);

    // === Agregar al mapa ===
    agregarHechos(hechos);

    /*]]>*/

