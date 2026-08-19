"use client";

import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import styles from "./LocationPicker.module.css";

// Leaflet's default marker icons break under Next.js bundling —
// this points them at a CDN instead of the (broken) relative paths.
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
    iconUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
    shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

const DEFAULT_CENTER = [44.4268, 26.1025]; // Bucharest

function ClickHandler({ onPick }) {
    useMapEvents({
        click(e) {
            onPick(e.latlng.lat, e.latlng.lng);
        },
    });
    return null;
}

export default function LocationPicker({ lat, lng, onChange }) {
    const position = lat && lng ? [lat, lng] : null;

    return (
        <div className={styles.wrap}>
            <MapContainer
                center={position || DEFAULT_CENTER}
                zoom={position ? 15 : 12}
                className={styles.map}
            >
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution="&copy; OpenStreetMap contributors"
                />
                <ClickHandler onPick={onChange} />
                {position && <Marker position={position} />}
            </MapContainer>
            <p className={styles.hint}>
                {position
                    ? "Pin placed — click again to move it."
                    : "Click the map to place a pin at the listing's location (optional)."}
            </p>
        </div>
    );
}