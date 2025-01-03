import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day , Destination} from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { ScheduleResponse, toScheduleResponse, toScheduleResponseList } from "../model/schedule-per-day-model";

export class LocationService{
    static async getAllDays(itinerary_destination_id: number): Promise<ScheduleResponse[]>{
        const allDays = await prismaClient.schedule_Per_Day.findMany({
            where: {
                itinerary_destination_id: itinerary_destination_id
            },
        })

        return toScheduleResponseList(allDays)
    }
}