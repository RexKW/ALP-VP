import { ActivityResponse, ActivityUpdateRequest, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { logger } from "../application/logging"

export class ActivityService{
    static async getAllActivity(day_id: number): Promise<ActivityResponse[]> {
        const activity = await prismaClient.activity.findMany({
            where: {
                day_id: day_id,
            },
        })

        activity.sort((a, b) => new Date(a.start_time).getTime() - new Date(b.start_time).getTime());
        return toActivityResponseList(activity)
    }

    static async getActivity(activity_id: number): Promise<ActivityResponse> {
        const todo = await this.checkActivity(activity_id)

        return toActivityResponse(todo)
    }

    static async checkActivity(
        activity_id: number
    ): Promise<Activity> {
        const activity = await prismaClient.activity.findUnique({
            where: {
                id: activity_id,
            },
        })

        if (!activity) {
            throw new ResponseError(400, "Activity not found!")
        }

        return activity
    }


    static async createActivity(
        day_id: number,
        req: CreateActivityRequest
    ): Promise<string> {
        console.log("Data Before Validation:", req);

        const activityRequest = Validation.validate(ActivityValidation.CREATE, req)

        console.log("Validated Data:", activityRequest);

        const activity = await prismaClient.activity.create({
            data: {
                name: activityRequest.name,
                description: activityRequest.description,
                start_time: activityRequest.start_time,
                end_time: activityRequest.end_time,
                cost: activityRequest.cost,
                type: activityRequest.type,
                location_id: activityRequest.location_id,
                day_id: day_id,
            },
        })

        console.log("Created Activity:", activity);

        return "Data created successfully!"
    }


    static async updateActivity(
        activity_id: number,
            req: ActivityUpdateRequest
        ): Promise<string> {
            const activity = Validation.validate(ActivityValidation.UPDATE, req)
    
            await this.checkActivity(activity_id)
    
            const activityUpdate = await prismaClient.activity.update({
                where: {
                    id: activity_id,
                },
                data: activity,
            })
    
            logger.info("UPDATE RESULT: " + activityUpdate)
    
            return "Data update was successful!"
        }

        static async deleteActivity(aId: number,): Promise<String> {
            await this.checkActivity(aId)
    
            await prismaClient.activity.delete({
                where: {
                    id: aId,
                },
            })

            return "Data deletion successful!"
        }
    
          

}