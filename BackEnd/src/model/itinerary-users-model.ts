"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryUserResponse = toItineraryUserResponseList;
exports.toItineraryUserResponseList = toItineraryUserResponseList;
"use strict";
import { Itinerary_Users } from "@prisma/client";

interface ItineraryUserResponse {
    id: string;
    user_id: string;
    itinerary_id: string;
    role: string;
}

// function toItineraryUserResponseList(prismaItineraryUsers: any[]): ItineraryUserResponse[] {
//     return prismaItineraryUsers.map(user => ({
//         id: user.id.toString(),
//         user_id: user.user_id.toString(),
//         itinerary_id: user.itinerary_id.toString(),
//         role: user.role
//     }));
// }

// export { toItineraryUserResponseList, ItineraryUserResponse };



export function toItineraryUserResponse(user: Itinerary_Users): ItineraryUserResponse {
  return {
    id: user.id,
    user_id: user.user_id,
    itinerary_id: user.itinerary_id,
    role: user.role,
  };
}

export function toItineraryUserResponseList(prismaTodo: Itinerary_Users[]): ItineraryUserResponse[] {
  const result = prismaTodo.map((itinerary) => {
      return {
        id: itinerary.id,
        user_id: itinerary.user_id,
        itinerary_id: itinerary.itinerary_id,
        role: itinerary.role
      }
  })

  return result
}
