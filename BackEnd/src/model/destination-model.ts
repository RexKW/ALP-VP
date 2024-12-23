import { Destination } from "@prisma/client";

export interface CreateDestinationRequest {
  name: string;
  province: string;
}

export interface DestinationResponse {
  id: number;
  name: string;
  province: string;
}

export function toDestinationResponse(destination: Destination): DestinationResponse {
  return {
    id: destination.id,
    name: destination.name,
    province: destination.province,
  };
}
