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
    static deleteItineraryDestination(itinerary_Destination_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = yield database_1.prismaClient.itinerary_Destinations.delete({
                where: {
                    id: itinerary_Destination_id
                }
            });
            return "Data Deleted";
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
                const datesToInsert = [];
                let batchCounter = 0;
                while (currentDate <= endDate) {
                    datesToInsert.push({
                        itinerary_destination_id: itinerary_Destinations.id,
                        date: new Date(currentDate),
                    });
                    currentDate.setDate(currentDate.getDate() + 1);
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
            let destination = yield database_1.prismaClient.destination.findUnique({
                where: {
                    destination_api_id: req.destination_id,
                },
            });
            destination = yield database_1.prismaClient.destination.findUnique({
                where: {
                    id: req.destination_id
                }
            });
            if (!destination) {
                destination = yield destination_service_1.DestinationService.addDestination(req.destination_id);
            }
            itinerary_destination.destination_id = destination.id;
            if (itinerary_destination.destination_id == req.destination_id) { //if the destination is the same, it'll retain the previous activitites
                const oldItineraryDestination = yield database_1.prismaClient.itinerary_Destinations.findUnique({
                    where: {
                        id: itinerary_destination.id
                    }
                });
                console.log(`Old itinerary Destination start: ${oldItineraryDestination === null || oldItineraryDestination === void 0 ? void 0 : oldItineraryDestination.start_date} Old itinerary Destination end: ${oldItineraryDestination === null || oldItineraryDestination === void 0 ? void 0 : oldItineraryDestination.end_date}`);
                const startDate2 = new Date((oldItineraryDestination === null || oldItineraryDestination === void 0 ? void 0 : oldItineraryDestination.start_date) || itinerary_destination.start_date);
                const endDate2 = new Date((oldItineraryDestination === null || oldItineraryDestination === void 0 ? void 0 : oldItineraryDestination.end_date) || itinerary_destination.end_date);
                console.log(`Start Date 2 ${startDate2} End Date 2 ${endDate2}`);
                const itineraryDestinationUpdate = yield database_1.prismaClient.itinerary_Destinations.update({
                    where: {
                        id: itinerary_destination.id,
                    },
                    data: itinerary_destination,
                });
                const startDate = new Date(itineraryDestinationUpdate.start_date);
                const endDate = new Date(itineraryDestinationUpdate.end_date);
                const createdDates = yield database_1.prismaClient.schedule_Per_Day.findMany({
                    where: {
                        itinerary_destination_id: itinerary_destination.id
                    }
                });
                const datesToInsert = [];
                const listofDates = [];
                const currentDate = startDate;
                while (currentDate <= endDate) {
                    if (currentDate < startDate2 || currentDate > endDate2) {
                        datesToInsert.push({
                            itinerary_destination_id: itinerary_destination.id,
                            date: currentDate,
                        });
                    }
                    listofDates.push({
                        itinerary_destination_id: itinerary_destination.id,
                        date: currentDate,
                    });
                    currentDate.setDate(currentDate.getDate() + 1);
                }
                const currentDate2 = startDate2;
                while (currentDate2 <= endDate2) {
                    if (currentDate2 < startDate || currentDate2 > endDate) {
                        yield database_1.prismaClient.schedule_Per_Day.delete({
                            where: {
                                itinerary_destination_id_date: {
                                    itinerary_destination_id: itinerary_destination.id,
                                    date: currentDate2
                                }
                            }
                        });
                    }
                    currentDate2.setDate(currentDate2.getDate() + 1);
                }
                yield database_1.prismaClient.schedule_Per_Day.createMany({
                    data: datesToInsert,
                });
            }
            else { // if it is a different destination, it deletes all previous activities and adds new ones
                const itineraryDestinationUpdate = yield database_1.prismaClient.itinerary_Destinations.update({
                    where: {
                        id: itinerary_destination.id,
                    },
                    data: itinerary_destination,
                });
                const startDate = new Date(itineraryDestinationUpdate.start_date);
                const endDate = new Date(itineraryDestinationUpdate.end_date);
                const deletedDates = yield database_1.prismaClient.schedule_Per_Day.deleteMany({
                    where: {
                        itinerary_destination_id: itinerary_destination.id
                    }
                });
                const currentDate = startDate;
                const BATCH_SIZE = 20;
                const datesToInsert = [];
                let batchCounter = 0;
                while (currentDate <= endDate) {
                    datesToInsert.push({
                        itinerary_destination_id: itinerary_destination.id,
                        date: new Date(currentDate),
                    });
                    currentDate.setDate(currentDate.getDate() + 1);
                }
                yield database_1.prismaClient.schedule_Per_Day.createMany({
                    data: datesToInsert,
                });
            }
            return "Destination Updated";
        });
    }
}
exports.ItineraryDestinationService = ItineraryDestinationService;
