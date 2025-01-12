"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ItineraryController = void 0;
const itinerary_service_1 = require("../services/itinerary-service");
const itinerary_destinations_service_1 = require("../services/itinerary-destinations-service");
const itinerary_users_service_1 = require("../services/itinerary-users-service");
class ItineraryController {
    static getItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.getItinerary(Number(req.params.todoId));
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static exploreItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.explore();
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getAllItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.getAllItinerary(req.user);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getAllInvitedItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.getAllInvitedItinerary(req.user);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static createNewItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield itinerary_service_1.ItineraryService.createItinerary(request, req.user);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static deleteItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.deleteItinerary(Number(req.params.todoId));
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static updateItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                request.id = Number(req.params.itineraryId);
                const response = yield itinerary_service_1.ItineraryService.updateItinerary(request);
                res.status(201).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    //Destinations 
    static selectDestination(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                request.destination_id = Number(req.params.destinationId);
                const dateRequest = Object.assign(Object.assign({}, request), { start_date: new Date(request.start_date), end_date: new Date(request.end_date) });
                const response = yield itinerary_destinations_service_1.ItineraryDestinationService.createItineraryDestination(dateRequest, req.user);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static allJourney(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const itinerary_id = parseInt(req.params.itineraryId, 10);
                const response = yield itinerary_destinations_service_1.ItineraryDestinationService.getAllItinenaryDestination(itinerary_id);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getJourney(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const itinerary_destination_id = Number(req.params.itineraryDestinationId);
                const response = yield itinerary_destinations_service_1.ItineraryDestinationService.getItineraryDestination(itinerary_destination_id);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static updateJourney(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                request.id = Number(req.params.itineraryDestinationId);
                const dateRequest = Object.assign(Object.assign({}, request), { start_date: new Date(request.start_date), end_date: new Date(request.end_date) });
                const response = yield itinerary_destinations_service_1.ItineraryDestinationService.updateItineraryDestination(dateRequest);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static deleteJourney(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_destinations_service_1.ItineraryDestinationService.deleteItineraryDestination(Number(req.params.itineraryDestinationId));
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    //Users
    static addUser(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield itinerary_users_service_1.ItineraryUserService.addItineraryUser(request);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static updateAccomodation(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const itineraryDestinationId = Number(req.params.itineraryDestinationId);
                const accomodationData = req.body;
                console.log(`API called: updateAccomodation`);
                console.log(`Request params: ${JSON.stringify(req.params)}`);
                console.log(`Request body: ${JSON.stringify(req.body)}`);
                const response = yield itinerary_destinations_service_1.ItineraryDestinationService.updateItineraryDestinationAccomodation(itineraryDestinationId, accomodationData);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                console.error(`Error in updateAccomodation: ${error.message}`);
                next(error);
            }
        });
    }
    // static async updateAccomodation(req: Request, res: Response, next: NextFunction) {
    // 	try {
    // 	  const itineraryDestinationId = Number(req.params.itineraryDestinationId);
    // 	  const accomodationData = req.body; // Assuming the accommodation details are in the body
    // 	  const response = await ItineraryDestinationService.updateItineraryDestinationAccomodation(
    // 		itineraryDestinationId,
    // 		accomodationData
    // 	  );
    // 	  res.status(200).json({
    // 		data: response,
    // 	  });
    // 	} catch (error) {
    // 	  next(error);
    // 	}
    //   }
    static checkAccommodation(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const itineraryDestinationId = parseInt(req.params.itineraryDestinationId, 10);
                // Validate the ID
                if (isNaN(itineraryDestinationId)) {
                    res.status(400).json({ error: "Invalid itinerary destination ID." });
                    return;
                }
                // Fetch the accommodation ID
                const accommodationId = yield itinerary_destinations_service_1.ItineraryDestinationService.getAccommodationId(itineraryDestinationId);
                res.status(200).json({ accommodation_id: accommodationId });
            }
            catch (error) {
                console.error("Error in checkAccommodation:", error);
                next(error);
            }
        });
    }
    static getAccommodationDetails(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const accommodationId = parseInt(req.params.accommodationId, 10);
                // Validate the ID
                if (isNaN(accommodationId)) {
                    res.status(400).json({ error: "Invalid accommodation ID." });
                    return;
                }
                // Fetch the accommodation details
                const accommodation = yield itinerary_destinations_service_1.ItineraryDestinationService.getAccommodationDetails(accommodationId);
                if (!accommodation) {
                    res.status(404).json({ error: "Accommodation not found." });
                    return;
                }
                res.status(200).json({ data: accommodation });
            }
            catch (error) {
                console.error("Error in getAccommodationDetails:", error);
                next(error);
            }
        });
    }
}
exports.ItineraryController = ItineraryController;
