import { Location } from "@prisma/client";

// Interface for creating a new location
export interface CreateLocationRequest {
  name: string;                // Name of the location
  address: string;             // Address of the location
  description: string;         // Description of the location
  location_image: string;      // Image URL
  place_id: string;            // Unique identifier from the external API
  categories: string[];        // Array of categories
  opening_hours?: string;      // Optional: Opening hours
  website?: string;            // Optional: Website URL
  phone?: string;              // Optional: Phone number
}

// Interface for returning a location as a response
export interface LocationResponse {
  id: number;                  // ID of the location
  name: string;                // Name of the location
  address: string;             // Address of the location
  description: string;         // Description of the location
  location_image: string;      // Image URL
  place_id: string;            // Unique identifier
  categories: string[];        // Array of categories
  opening_hours?: string | null; // Allow null values
  website?: string | null;      // Allow null values
  phone?: string | null;        // Allow null values
}

// Function to map Prisma Location model to LocationResponse
export function toLocationResponse(location: Location): LocationResponse {
  return {
    id: location.id,
    name: location.name,
    address: location.address,
    description: location.description,
    location_image: location.location_image,
    place_id: location.place_id,
    categories: location.categories,
    opening_hours: location.opening_hours,
    website: location.website,
    phone: location.phone,
  };
}
