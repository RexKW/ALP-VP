"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toAccomodationResponse = toAccomodationResponse;
function toAccomodationResponse(accomodation) {
    return {
        id: accomodation.id,
        name: accomodation.name,
        address: accomodation.address,
        description: accomodation.description,
        rating: accomodation.rating,
        cost: accomodation.cost,
        type: accomodation.type
    };
}
