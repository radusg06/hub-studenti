"use client";

import { MapContainer, TileLayer, Marker, Circle, Popup, useMapEvents } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import styles from "./HousingMap.module.css";

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
    iconUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
    shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

const centerIcon = new L.Icon({
    iconUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
    iconRetinaUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
    shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
    iconSize: [30, 46],
});

const DEFAULT_CENTER = [44.4268, 26.1025]; // Bucharest

function ClickHandler({ onSetCenter }) {
    useMapEvents({
        click(e) {
            onSetCenter(e.latlng.lat, e.latlng.lng);
        },
    });
    return null;
}

export default function HousingMap({ listings, center, radius, onSetCenter }) {
    const withCoords = listings.filter((l) => l.lat && l.lng);

    return (
        <div className={styles.wrap}>
            <MapContainer
                center={center ? [center.lat, center.lng] : DEFAULT_CENTER}
                zoom={12}
                className={styles.map}
            >
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution="&copy; OpenStreetMap contributors"
                />
                <ClickHandler onSetCenter={onSetCenter} />

                {center && (
                    <>
                        <Marker position={[center.lat, center.lng]} icon={centerIcon} />
                        <Circle
                            center={[center.lat, center.lng]}
                            radius={radius * 1000}
                            pathOptions={{ color: "#8b5cf6", fillColor: "#8b5cf6", fillOpacity: 0.08 }}
                        />
                    </>
                )}

                {withCoords.map((l) => (
                    <Marker key={l.id} position={[l.lat, l.lng]}>
                        <Popup>
                            <strong>{l.name}</strong>
                            <br />€{l.price} / month
                        </Popup>
                    </Marker>
                ))}
            </MapContainer>
            <p className={styles.hint}>Click the map to set your search area.</p>
        </div>
    );
}