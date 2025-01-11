import { Schedule_Per_Day } from "@prisma/client";

export interface CreateScheduleRequest {
  itinenary_destination_id: number;
  date: Date;
}

export interface ScheduleResponse {
  id: number;
  date: Date;
}

export function toScheduleResponse(schedule: Schedule_Per_Day): ScheduleResponse {
  return {
    id: schedule.id,
    date: schedule.date,
  };
}