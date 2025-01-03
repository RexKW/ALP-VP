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
const itinerary_destination_validation_1 = require("../validation/itinerary-destination-validation");
const logging_1 = require("../application/logging");
const destination_service_1 = require("./destination-service");
class ItineraryDestinationService {
    static getAllItinenaryDestination(itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_destinations = yield database_1.prismaClient.itinerary_Destinations.findMany({
                where: {
                    itinerary_id: itinerary_id,
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
    static createItineraryDestination(req, user) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
            const itinerary_Destinations_Request = validation_1.Validation.validate(itinerary_destination_validation_1.ItineraryDestinationValidation.CREATE, req);
            const authentication = yield database_1.prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id_unique: {
                        user_id: user.id,
                        itinerary_id: req.itinerary_id,
                    },
                },
            });
            if ((authentication === null || authentication === void 0 ? void 0 : authentication.role) == "admin" || (authentication === null || authentication === void 0 ? void 0 : authentication.role) == "owner") {
                let destination = yield database_1.prismaClient.destination.findUnique({
                    where: {
                        destination_api_id: req.destination_id,
                    },
                });
                if (!destination) {
                    destination = yield destination_service_1.DestinationService.addDestination(req.destination_id);
                }
                const itinerary_Destinations = yield database_1.prismaClient.itinerary_Destinations.create({
                    data: {
                        itinerary_id: itinerary_Destinations_Request.itinerary_id,
                        destination_id: destination.id,
                        accomodation_id: null,
                        start_date: itinerary_Destinations_Request.start_date,
                        end_date: itinerary_Destinations_Request.end_date
                    },
                });
                const startDate = itinerary_Destinations.start_date;
                const endDate = itinerary_Destinations.end_date;
                const currentDate = startDate;
                const BATCH_SIZE = 20;
                const datesToInsert = [];
                let batchCounter = 0;
                while (currentDate <= endDate) {
                    datesToInsert.push({
                        itinerary_destination_id: itinerary_Destinations.id,
                        date: currentDate,
                    });
                    currentDate.setDate(currentDate.getDate() + 1);
                    if (datesToInsert.length >= BATCH_SIZE) {
                        yield database_1.prismaClient.schedule_Per_Day.createMany({
                            data: datesToInsert,
                        });
                        datesToInsert.length = 0;
                        batchCounter++;
                    }
                }
                yield database_1.prismaClient.schedule_Per_Day.createMany({
                    data: datesToInsert,
                });
                return "Data created successfully!";
            }
            else {
                return "Unauthorized Access";
            }
        });
    }
    static updateItineraryDestination(req) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_destination = validation_1.Validation.validate(itinerary_destination_validation_1.ItineraryDestinationValidation.UPDATE, req);
            yield this.checkItineraryDestination(itinerary_destination.id);
            const itineraryDestinationUpdate = yield database_1.prismaClient.itinerary_Destinations.update({
                where: {
                    id: itinerary_destination.id,
                },
                data: itinerary_destination,
            });
            logging_1.logger.info("UPDATE RESULT: " + itineraryDestinationUpdate);
        });
    }
}
exports.ItineraryDestinationService = ItineraryDestinationService;
