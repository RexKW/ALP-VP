"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryDestinationResponse = toItineraryDestinationResponse;
exports.toItineraryDestinationResponseList = toItineraryDestinationResponseList;
function toItineraryDestinationResponse(itineraryDestination) {
    return {
        id: itineraryDestination.id,
        destination_id: itineraryDestination.destination_id,
        accomodation_id: itineraryDestination.accomodation_id,
        start_date: itineraryDestination.start_date,
        end_date: itineraryDestination.end_date,
    };
}
function toItineraryDestinationResponseList(prismaTodo) {
    const result = prismaTodo.map((itineraryDestination) => {
        return {
            id: itineraryDestination.id,
            destination_id: itineraryDestination.destination_id,
            accomodation_id: itineraryDestination.accomodation_id,
            start_date: itineraryDestination.start_date,
            end_date: itineraryDestination.end_date,
        };
    });
    return result;
}
