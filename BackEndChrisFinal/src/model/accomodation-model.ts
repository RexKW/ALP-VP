import { Accomodation } from "@prisma/client";
import { Decimal } from "@prisma/client/runtime/library";

// Request interface for creating an accommodation
export interface CreateAcomodationRequest {
  name: string;
  address: string;
  location_image: string;
  place_id: string;
  place_api: string;
  categories: string[];
  opening_hours?: string;
  website?: string;
  phone?: string;
  cost: Decimal;
  people: number;
}

// Response interface for returning an accommodation
export interface AccomodationResponse {
  id: number;
  name: string;
  address: string;
  location_image: string;
  place_id: string;
  place_api: string;
  categories: string[];
  opening_hours?: string | null;
  website?: string | null;
  phone?: string | null;
  cost: Decimal;
  people: number;
}

// Convert Prisma Accomodation object to AccomodationResponse
export function toAccomodationResponse(accomodation: Accomodation): AccomodationResponse {
  return {
    id: accomodation.id,
    name: accomodation.name,
    address: accomodation.address,
    location_image: accomodation.location_image,
    place_id: accomodation.place_id,
    place_api: accomodation.place_api,
    categories: accomodation.categories,
    opening_hours: accomodation.opening_hours,
    website: accomodation.website,
    phone: accomodation.phone,
    cost: accomodation.cost,
    people: accomodation.people,
  };
}

// import { Accomodation } from "@prisma/client";
// import { Decimal } from "@prisma/client/runtime/library";

// export interface CreateAcomodationRequest {
//     name: string;
//     address: string;
//     description: string;
//     rating: number;
//     cost: Decimal;
//     type:string;
//   }

//   export interface AccomodationResponse {
//     id: number;
//     name: string;
//     address: string;
//     description: string;
//     rating: number;
//     cost: Decimal;
//     type:string;
//   }

//   export function toAccomodationResponse(accomodation: Accomodation): AccomodationResponse {
//     return {
//         id: accomodation.id,
//         name: accomodation.name,
//         address: accomodation.address,
//         description: accomodation.description,
//         rating: accomodation.rating,
//         cost: accomodation.cost,
//         type:accomodation.type
//     };
//   }
  