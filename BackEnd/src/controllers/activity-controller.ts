import { NextFunction, Request, response, Response } from "express";
import { LoginUserRequest, RegisterUserRequest, UserResponse } from "../model/user-model";
import { UserService } from "../services/auth-service";
import { CreateActivityRequest } from "../model/activity-model";
import { ActivityService } from "../services/activity-service";
import { DayService } from "../services/day-service";

export class ActivityController{
    static async getAllDays(req: Request, res: Response, next: NextFunction){
        try{
            const itineraryDestinationId = Number(req.params.itineraryDestinationId)
            const response = await DayService.getAllDays(itineraryDestinationId)

            res.status(200).json({
                data: response
            })
        } catch (error){
            next(error)
        }
    }

    static async getAllActivities(req: Request, res: Response, next: NextFunction) {
        try {
            const dayId = Number(req.params.dayId);
            const response = await ActivityService.getAllActivity(dayId)

            res.status(200).json({
                data: response,
            })
        } catch (error) {
            next(error)
        }
    }

    static async getActivity(req: Request, res: Response, next: NextFunction) {
        try {
            const activityId = Number(req.params.activityId)
            const response = await ActivityService.getActivity(activityId)

            res.status(200).json({
                data: response,
            })
        } catch (error) {
            next(error)
        }
    }

    static async createActivity(req: Request, res: Response, next: NextFunction){
        try{
            const request = req.body as CreateActivityRequest
            const dayId = Number(req.params.dayId);
            const timeRequest = {
				...request,
				start_time: new Date(request.start_time),
				end_time: new Date(request.end_time)
			}
            const response = await ActivityService.createActivity(dayId,timeRequest)


            res.status(200).json({
                data: response
            })

        }catch (error){
            next(error)
        }
    }
}