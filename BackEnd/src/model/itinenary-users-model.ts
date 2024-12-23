import { Itinenary_Users } from "@prisma/client";

export interface AddItineraryUserRequest {
  user_id: number;
  itinenary_id: number;
  role: string;
}

export interface ItineraryUserResponse {
  id: number;
  role: string;
}

export function toItineraryUserResponse(user: Itinenary_Users): ItineraryUserResponse {
  return {
    id: user.id,
    role: user.role,
  };
}
