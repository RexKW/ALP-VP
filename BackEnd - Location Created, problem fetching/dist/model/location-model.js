"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toLocationResponse = toLocationResponse;
// Function to map Prisma Location model to LocationResponse
function toLocationResponse(location) {
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
