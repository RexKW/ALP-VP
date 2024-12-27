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
exports.ItineraryDestinationService = void 0;
const itinerary_destinations_model_1 = require("../model/itinerary-destinations-model");
const validation_1 = require("../validation/validation");
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const activity_validation_1 = require("../validation/activity-validation");
class ItineraryDestinationService {
    static getAllItinenaryDestination(itinerary) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_destinations = yield database_1.prismaClient.itinerary_Destinations.findMany({
                where: {
                    itinerary_id: itinerary.id,
                },
            });
            return (0, itinerary_destinations_model_1.toItineraryDestinationResponseList)(itinerary_destinations);
        });
    }
    static getItineraryDestination(itinerary_Destination_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_Destination = yield this.checkItineraryDestination(itinerary_Destination_id);
            return (0, itinerary_destinations_model_1.toItineraryDestinationResponse)(itinerary_Destination);
        });
    }
    static checkItineraryDestination(itinerary_Destination_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = yield database_1.prismaClient.itinerary_Destinations.findUnique({
                where: {
                    id: itinerary_Destination_id
                },
            });
            if (!itinerary) {
                throw new response_error_1.ResponseError(400, "Itinerary not found!");
            }
            return itinerary;
        });
    }
    static createItineraryDestination(req) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
            const itinerary_Destinations_Request = validation_1.Validation.validate(activity_validation_1.ActivityValidation.CREATE, req);
            const itinerary_Destinations = yield database_1.prismaClient.itinerary_Destinations.create({
                data: {
                    itinerary_id: itinerary_Destinations_Request.itinerary_id,
                    destination_id: itinerary_Destinations_Request.destination_id,
                    accomodation_id: itinerary_Destinations_Request.accomodation_id,
                    start_date: itinerary_Destinations_Request.start_date,
                    end_date: itinerary_Destinations_Request.end_date
                },
            });
            const startDate = itinerary_Destinations.start_date;
            const endDate = itinerary_Destinations.end_date;
            const currentDate = startDate;
            while (currentDate <= endDate) {
                const Schedule_Per_Day = yield database_1.prismaClient.schedule_Per_Day.create({
                    data: {
                        itinerary_destination_id: itinerary_Destinations.id,
                        date: currentDate
                    }
                });
                currentDate.setDate(currentDate.getDate() + 1);
            }
            return "Data created successfully!";
        });
    }
    static updateItineraryDestination() {
        return __awaiter(this, void 0, void 0, function* () {
        });
    }
}
exports.ItineraryDestinationService = ItineraryDestinationService;
