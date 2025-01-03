import { NextFunction, Request, Response } from "express";
import { CreateItineraryRequest, ItineraryResponse, GetItineraryRequest, ItineraryUpdateRequest } from "../model/itinerary-model";
import { AddItineraryDestinationRequest } from "../model/itinerary-destinations-model";
import { ItineraryService } from "../services/itinerary-service";
import { UserRequest } from "../type/user-request";
import { ItineraryDestinationService } from "../services/itinerary-destinations-service";
import { AddItineraryUserRequest } from "../model/itinerary-users-model";
import { ItineraryUserService } from "../services/itinerary-users-service";
import moment from 'moment';

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

			res.status(200).json({
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


	//Destinations 

	static async selectDestination(req: UserRequest, res: Response, next: NextFunction){
		try{
			const request = req.body as AddItineraryDestinationRequest
			request.destination_id = Number(req.params.destinationId)
			
			const dateRequest = {
				...request,
				start_date: new Date(request.start_date),
				end_date: new Date(request.end_date)
			}
			const response = await ItineraryDestinationService.createItineraryDestination(dateRequest, req.user!)
			res.status(200).json({
				data: response,
			})
		}catch (error){
			next(error)
		}
	}

	static async allJourney(req: Request, res: Response, next: NextFunction){
		try{
			const itinerary_id = parseInt(req.params.itineraryId, 10)
			const response = await ItineraryDestinationService.getAllItinenaryDestination(itinerary_id)
			res.status(200).json({
				data:response
			})
		}catch(error){
			next(error)
		}
	}


	//Users

	static async addUser(req: UserRequest, res: Response, next: NextFunction){
		try{
			const request = req.body as AddItineraryUserRequest
			const response = await ItineraryUserService.addItineraryUser(request)
		}catch (error){
			next(error)
		}
	}


}