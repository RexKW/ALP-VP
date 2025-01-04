"use strict";
import { Response, NextFunction } from 'express';
import { UserRequest } from '../types/user-request';
import { ItineraryService } from "../services/itinerary-service";

class ItineraryController {
    static async getItinerary(req: UserRequest, res: Response, next: NextFunction): Promise<void> {
        try {
            const response = await ItineraryService.getItinerary(Number(req.params.todoId));
            res.status(200).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }

    static async getAllItinerary(req: UserRequest, res: Response, next: NextFunction): Promise<void> {
        try {
            const user = req.user; // Ensure user is available on the request object
            if (!user) {
                throw new Error("User is not authenticated");
            }
            const response = await ItineraryService.getAllItinerary({ ...user, id: Number(user.id), token: user.token ?? null });
            res.status(200).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }

    static async createNewItinerary(req: UserRequest, res: Response, next: NextFunction): Promise<void> {
        try {
            const user = req.user; // Ensure user is available on the request object
            if (!user) {
                throw new Error("User is not authenticated");
            }
            const request = req.body;
            const response = await ItineraryService.createItinerary(request, { ...user, id: Number(user.id), token: user.token ?? null });
            res.status(201).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }

    static async deleteItinerary(req: UserRequest, res: Response, next: NextFunction): Promise<void> {
        try {
            const response = await ItineraryService.deleteItinerary(Number(req.params.todoId));
            res.status(200).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }

    static async updateItinerary(req: UserRequest, res: Response, next: NextFunction): Promise<void> {
        try {
            const request = req.body;
            request.id = Number(req.params.itineraryId);
            const response = await ItineraryService.updateItinerary(request);
            res.status(201).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }
}

export { ItineraryController };