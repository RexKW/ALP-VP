"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryResponse = toItineraryResponse;
exports.toItineraryResponseList = toItineraryResponseList;
function toItineraryResponse(itinerary, travellerNumber, start_date, end_date) {
    return {
        id: itinerary.id,
        name: itinerary.name,
        travellers: travellerNumber,
        from: start_date,
        to: end_date
    };
}
function toItineraryResponseList(itineraries) {
    return itineraries;
}
