import { Activity } from "@prisma/client";
import { Decimal } from "@prisma/client/runtime/library";

export interface CreateActivityRequest {
  name: string;
  description: string;
  start_time: Date;
  end_time: Date;
  type: string;
  cost: number;
  day_id: number;
  location_id: number;
}

export interface ActivityResponse {
  id: number;
  name: string;
  description: string;
  start_time: Date;
  end_time: Date;
  type: string;
  cost: Decimal;
}

export function toActivityResponse(activity: Activity): ActivityResponse {
  return {
    id: activity.id,
    name: activity.name,
    description: activity.description,
    start_time: activity.start_time,
    end_time: activity.end_time,
    type: activity.type,
    cost: activity.cost,
  };
}
