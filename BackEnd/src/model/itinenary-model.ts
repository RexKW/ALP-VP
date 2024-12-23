import { Itinenary } from "@prisma/client";

export interface CreateItineraryRequest {
  name: string;
  created_date: Date;
  updated_date: Date;
}

export interface ItineraryResponse {
  id: number;
  name: string;
}

export function toItineraryResponse(itinenary: Itinenary): ItineraryResponse {
  return {
    id: itinenary.id,
    name: itinenary.name,
  };
}
