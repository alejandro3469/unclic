/**
 * Arcos del globo: hub Ciudad de México → otras regiones (México como base, alcance global).
 */
export interface GlobeArc {
  startLat: number;
  startLng: number;
  endLat: number;
  endLng: number;
  name?: string;
  color?: string | [string, string];
}

const CDMX = { lat: 19.4326, lng: -99.1332 };

export const mexicoHubGlobeArcs: GlobeArc[] = [
  {
    startLat: CDMX.lat,
    startLng: CDMX.lng,
    endLat: 40.7128,
    endLng: -74.006,
    name: 'México → Norteamérica',
    color: ['#22d3ee', '#38bdf8'],
  },
  {
    startLat: CDMX.lat,
    startLng: CDMX.lng,
    endLat: 37.7749,
    endLng: -122.4194,
    name: 'México → Costa Oeste',
    color: ['#818cf8', '#a78bfa'],
  },
  {
    startLat: CDMX.lat,
    startLng: CDMX.lng,
    endLat: -23.5505,
    endLng: -46.6333,
    name: 'México → Sudamérica',
    color: ['#34d399', '#2dd4bf'],
  },
  {
    startLat: CDMX.lat,
    startLng: CDMX.lng,
    endLat: 40.4168,
    endLng: -3.7038,
    name: 'México → Europa',
    color: ['#f472b6', '#fb7185'],
  },
  {
    startLat: CDMX.lat,
    startLng: CDMX.lng,
    endLat: 51.5074,
    endLng: -0.1278,
    name: 'México → Reino Unido',
    color: ['#fbbf24', '#f59e0b'],
  },
  {
    startLat: CDMX.lat,
    startLng: CDMX.lng,
    endLat: 35.6762,
    endLng: 139.6503,
    name: 'México → Asia',
    color: ['#60a5fa', '#3b82f6'],
  },
];

/** @deprecated usar mexicoHubGlobeArcs en la landing */
export const defaultGlobeArcs: GlobeArc[] = mexicoHubGlobeArcs;
