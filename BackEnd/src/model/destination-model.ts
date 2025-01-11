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

export function toDestinationResponseList(prismaTodo: Destination[]): DestinationResponse[] {
const result = prismaTodo.map((destination) => {
  return {
    id: destination.id,
    name: destination.name,
    province: destination.province,
  };
})

return result
}