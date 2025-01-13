"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toAccomodationResponse = toAccomodationResponse;
// Convert Prisma Accomodation object to AccomodationResponse
function toAccomodationResponse(accomodation) {
    return {
        id: accomodation.id,
        name: accomodation.name,
        address: accomodation.address,
        location_image: accomodation.location_image,
        place_id: accomodation.place_id,
        place_api: accomodation.place_api,
        categories: accomodation.categories,
        opening_hours: accomodation.opening_hours,
        website: accomodation.website,
        phone: accomodation.phone,
        cost: accomodation.cost,
        people: accomodation.people,
    };
}
