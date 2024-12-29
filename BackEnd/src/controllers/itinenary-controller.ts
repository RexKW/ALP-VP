import { NextFunction, Request, Response } from "express";
import { CreateItineraryRequest, ItineraryResponse, GetItineraryRequest, ItineraryUpdateRequest } from "../model/itinerary-model";
import { ItineraryService } from "../services/itinerary-service";
import { UserRequest } from "../type/user-request";

export class ItineraryController{
    static async getItinerary(req: UserRequest, res: Response, next: NextFunction){
        try {
			const response = await ItineraryService.getItinerary(
				Number(req.params.todoId)
			)

			res.status(200).json({
				data: response,
			})
		} catch (error) {
			next(error)
		}
    }

    static async getAllItinerary(req: UserRequest, res: Response, next: NextFunction){
        try {
			const response = await ItineraryService.getAllItinerary(
				req.user!
			)

			res.status(200).json({
				data: response,
			})
		} catch (error) {
			next(error)
		}
    }

    static async createNewItinerary(req: UserRequest, res: Response, next: NextFunction) {
        try {
			const request = req.body as CreateItineraryRequest
			const response = await ItineraryService.createItinerary(request, req.user! )

			res.status(201).json({
				data: response,
			})
		} catch (error) {
			next(error)
		}
	}
    

    static async deleteItinerary(req: UserRequest, res: Response, next: NextFunction) {
        try {
			const response = await ItineraryService.deleteItinerary(
				Number(req.params.todoId)
			)

			res.status(200).json({
				data: response,
			})
		} catch (error) {
			next(error)
		}
    }

    static async updateItinerary(req: Request, res: Response, next: NextFunction) {
        try {
			const request = req.body as ItineraryUpdateRequest
			request.id = Number(req.params.itineraryId)
			const response = await ItineraryService.updateItinerary(request)

			res.status(201).json({
				data: response,
			})
		} catch (error) {
			next(error)
		}
    }


}