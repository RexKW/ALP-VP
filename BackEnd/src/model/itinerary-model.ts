"use strict";
import { Itinerary } from "@prisma/client";
import { prismaClient } from "../application/database"

export interface ItineraryResponse {
    id: number;
    name: string;
    travellers: number;
    from: Date;
    to: Date;
}

export interface ItineraryModel {
  id: number
  name: string;
  created_date: Date;
  updated_date: Date;
}

export interface GetItineraryRequest{
  id: number;
  user_id: number;
}

export interface ItineraryUpdateRequest {
    id: number;
    name: string;
}

export interface ItineraryExploreResponse{
  id: number;
  name: string;
  destinations: number;
}




export async function toItineraryExploreResponseList(prismaItinerary: ItineraryModel[]): Promise<ItineraryExploreResponse[]> {
  const result: ItineraryExploreResponse[] = []

  for (const itinerary of prismaItinerary){
    const destinations = await prismaClient.itinerary_Destinations.count({
      where:{
          itinerary_id: itinerary.id
      }
    })

    result.push({
      id: itinerary.id,
      name: itinerary.name,
      destinations: destinations
    })
  }

  return result
}

export async function toItineraryResponseList(prismaitinerary: ItineraryResponse[]): Promise<ItineraryResponse[]> {
  const result: ItineraryResponse[] = [];

  for (const itinerary of prismaitinerary) {
    const travellerNumber = await prismaClient.itinerary_Users.count({
      where: {
        itinerary_id: itinerary.id,
      },
    });

    const dateRange = await prismaClient.itinerary_Destinations.aggregate({
      where: {
          itinerary_id: itinerary.id,
      },
      _min: {
          start_date: true,
      },
      _max: {
          end_date: true,
      },
  });

  const start_date = dateRange._min.start_date?? new Date(0)
  const end_date = dateRange._max.end_date?? new Date(0)

    result.push({
      id: itinerary.id,
      name: itinerary.name,
      travellers: travellerNumber,
      from: start_date,
      to: end_date
    });
  }

  return result;
}


