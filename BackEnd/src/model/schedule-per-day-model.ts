import { Schedule_Per_Day } from "@prisma/client";

export interface CreateScheduleRequest {
  itinenary_destination_id: number;
  date: Date;
}

export interface ScheduleResponse {
  id: number;
  itinenary_destination_id: number;
  date: Date;
}

export function toScheduleResponse(schedule: Schedule_Per_Day): ScheduleResponse {
  return {
    id: schedule.id,
    itinenary_destination_id: schedule.itinerary_destination_id,
    date: schedule.date,
  };
}

export function toScheduleResponseList(prismaTodo: Schedule_Per_Day[]): ScheduleResponse[] {
  const result = prismaTodo.map((schedule) => {
    return {
      id: schedule.id,
      itinenary_destination_id: schedule.itinerary_destination_id,
      date: schedule.date,
    };
  })

  return result
}
