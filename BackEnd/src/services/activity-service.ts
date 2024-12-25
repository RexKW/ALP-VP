import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";

export class ActivityService{
    static async getAllActivity(day: Schedule_Per_Day): Promise<ActivityResponse[]> {
        const activity = await prismaClient.activity.findMany({
            where: {
                day_id: day.id,
            },
        })

        return toActivityResponseList(activity)
    }

    static async getActivity(day: Schedule_Per_Day, activity_id: number): Promise<ActivityResponse> {
        const todo = await this.checkActivity(day.id, activity_id)

        return toActivityResponse(todo)
    }

    static async checkActivity(
        day_id: number,
        activity_id: number
    ): Promise<Activity> {
        const activity = await prismaClient.activity.findUnique({
            where: {
                id: activity_id,
                day_id: day_id,
            },
        })

        if (!activity) {
            throw new ResponseError(400, "Activity not found!")
        }

        return activity
    }


    static async createActivity(
        day: Schedule_Per_Day,
        req: CreateActivityRequest
    ): Promise<string> {
        // validate request
        const activityRequest = Validation.validate(ActivityValidation.CREATE, req)

        const activity = await prismaClient.activity.create({
            data: {
                name: activityRequest.name,
                description: activityRequest.description,
                start_time: activityRequest.start_time,
                end_time: activityRequest.end_time,
                cost: activityRequest.cost,
                type: activityRequest.type,
                location_id: activityRequest.location_id,
                day_id: day.id,
            },
        })

        return "Data created successfully!"
    }


    static async updateActivity(){
        
    }

}