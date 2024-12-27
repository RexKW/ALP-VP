import { Itinerary } from "@prisma/client";

export interface CreateItinenaryRequest {
  name: string;
  user_id: number;
  created_date: Date;
  updated_date: Date;
}

export interface ItineraryResponse {
  id: number;
  name: string;
}

export interface ItineraryUpdateRequest {
  id: number
  name: string;
}

export function toItineraryResponse(itinerary: Itinerary): ItineraryResponse {
  return {
    id: itinerary.id,
    name: itinerary.name,
  };
}

export function toItineraryResponseList(prismaitinerary: Itinerary[]): ItineraryResponse[] {
  const result = prismaitinerary.map((itinerary) => {
      return {
        id: itinerary.id,
        name: itinerary.name,
      }
  })

  return result
}


