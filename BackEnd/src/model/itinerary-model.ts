"use strict";
import { Itinerary } from "@prisma/client";

interface ItineraryResponse {
    id: number;
    name: string;
    travellers: number;
    from: Date;
    to: Date;
}

interface CreateItineraryRequest {
    name: string;
}

interface ItineraryUpdateRequest {
    id: number;
    name: string;
}

function toItineraryResponse(itinerary: Itinerary, travellerNumber: number, start_date: Date, end_date: Date): ItineraryResponse {
    return {
        id: itinerary.id,
        name: itinerary.name,
        travellers: travellerNumber,
        from: start_date,
        to: end_date
    };
}

function toItineraryResponseList(itineraries: ItineraryResponse[]): ItineraryResponse[] {
    return itineraries;
}

export { toItineraryResponse, toItineraryResponseList, ItineraryResponse, CreateItineraryRequest, ItineraryUpdateRequest };