"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryResponse = toItineraryResponse;
exports.toItineraryResponseList = toItineraryResponseList;
function toItineraryResponse(itinerary) {
    return {
        id: itinerary.id,
        name: itinerary.name,
    };
}
function toItineraryResponseList(prismaitinerary) {
    const result = prismaitinerary.map((itinerary) => {
        return {
            id: itinerary.id,
            name: itinerary.name,
        };
    });
    return result;
}
