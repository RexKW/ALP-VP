import { Itinenary_Destinations } from "@prisma/client";

export interface AddItinenaryDestinationRequest {
  itinenary_id: number;
  destination_id: number;
  accomodation_id?: number |null;
  start_date: Date;
  end_date: Date;
}

export interface ItineraryDestinationResponse {
  id: number;
  destination_id: number;
  accomodation_id?: number | null;
  start_date: Date;
  end_date: Date;
}



export function toItineraryDestinationResponse(
  itinenaryDestination: Itinenary_Destinations
): ItineraryDestinationResponse {
  return {
    id: itinenaryDestination.id,
    destination_id: itinenaryDestination.destination_id,
    accomodation_id: itinenaryDestination.accomodation_id,
    start_date: itinenaryDestination.start_date,
    end_date: itinenaryDestination.end_date,
  };
}

export function toItinenaryDestinationResponseList(prismaTodo: Itinenary_Destinations[]): ItineraryDestinationResponse[] {
  const result = prismaTodo.map((itinenaryDestination) => {
      return {
        id: itinenaryDestination.id,
        destination_id: itinenaryDestination.destination_id,
        accomodation_id: itinenaryDestination.accomodation_id,
        start_date: itinenaryDestination.start_date,
        end_date: itinenaryDestination.end_date,
      }
  })

  return result
}
