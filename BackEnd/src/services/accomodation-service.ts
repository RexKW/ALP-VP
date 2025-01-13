import { Decimal } from "@prisma/client/runtime/library";
import { prismaClient } from "../application/database";
import { Accomodation } from "@prisma/client";

export class AccomodationService {
    static async getOrCreateAccomodation(data: {
      place_id: string;
      name: string;
      address: string;
      location_image: string;
      place_api: string;
      categories: string[];
      cost: Decimal;
      people: number;
      opening_hours?: string | undefined;
      website?: string | undefined;
      phone?: string | undefined;
    }): Promise<Accomodation> {
      // Check if accommodation exists by place_id
      let accomodation = await prismaClient.accomodation.findUnique({
        where: { place_id: data.place_id },
      });
  
      if (!accomodation) {
        // If accommodation doesn't exist, create a new one
        accomodation = await prismaClient.accomodation.create({
          data: {
            place_id: data.place_id,
            name: data.name,
            address: data.address,
            cost: data.cost,
            people: data.people,
            location_image: data.location_image || "default_image_url", // Default image
            place_api: data.place_api,
            categories: data.categories,
            opening_hours: data.opening_hours || null,
            website: data.website || null,
            phone: data.phone || null,
          },
        });
      } else {
        // If it already exists, update cost and people if needed
        accomodation = await prismaClient.accomodation.update({
          where: { id: accomodation.id },
          data: {
            cost: data.cost || accomodation.cost,
            people: data.people || accomodation.people,
          },
        });
      }
  
      return accomodation;
    }
  }