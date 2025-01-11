import { Accomodation } from "@prisma/client";
import { Decimal } from "@prisma/client/runtime/library";

export interface CreateAcomodationRequest {
    name: string;
    address: string;
    description: string;
    rating: number;
    cost: Decimal;
    type:string;
  }

  export interface AccomodationResponse {
    id: number;
    name: string;
    address: string;
    description: string;
    rating: number;
    cost: Decimal;
    type:string;
  }

  export function toAccomodationResponse(accomodation: Accomodation): AccomodationResponse {
    return {
        id: accomodation.id,
        name: accomodation.name,
        address: accomodation.address,
        description: accomodation.description,
        rating: accomodation.rating,
        cost: accomodation.cost,
        type:accomodation.type
    };
  }
  