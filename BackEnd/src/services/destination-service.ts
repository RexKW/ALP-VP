import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { toDestinationResponse, DestinationResponse } from "../model/destination-model";

export class DestinationService{
    static async getAllDestination(day: Schedule_Per_Day): Promise<ActivityResponse[]> {
        const activity = await prismaClient.activity.findMany({
            where: {
                day_id: day.id,
            },
        })

        return toActivityResponseList(activity)
    }

    static async getDestinationbyID(destination_id: number): Promise<DestinationResponse> {
        const destination = await prismaClient.activity.findUnique({
            where: {
                id: destination_id
            },
        })

        return toDestinationResponse(destination)
    }

    static async getDestinationbyName(name: string): Promise<DestinationResponse>{

    }

    static async 

    static async checkDestination(
        name: string
    ): Promise<Activity> {
        

        if (!destination) {
            throw new ResponseError(400, "Destination not found!")
        }

        return destination
    }


    

}