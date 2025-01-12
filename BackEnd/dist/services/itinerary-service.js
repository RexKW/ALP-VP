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
exports.ItineraryService = void 0;
const itinerary_model_1 = require("../model/itinerary-model");
const validation_1 = require("../validation/validation");
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const itinerary_validation_1 = require("../validation/itinerary-validation");
const logging_1 = require("../application/logging");
class ItineraryService {
    static explore() {
        return __awaiter(this, void 0, void 0, function* () {
            const allItineraries = yield database_1.prismaClient.itinerary.findMany();
            return (0, itinerary_model_1.toItineraryExploreResponseList)(allItineraries);
        });
    }
    static getAllItinerary(user) {
        return __awaiter(this, void 0, void 0, function* () {
            var _a, _b;
            const itinerariesOwned = yield database_1.prismaClient.itinerary_Users.findMany({
                where: {
                    user_id: user.id,
                    role: 'owner'
                },
            });
            const itineraryIds = itinerariesOwned.map((item) => item.itinerary_id);
            const itineraries = yield database_1.prismaClient.itinerary.findMany({
                where: {
                    id: { in: itineraryIds }
                }
            });
            const itinerariesWithUserCount = [];
            for (const itinerary of itineraries) {
                const userCount = yield database_1.prismaClient.itinerary_Users.count({
                    where: {
                        itinerary_id: itinerary.id
                    }
                });
                const dateRange = yield database_1.prismaClient.itinerary_Destinations.aggregate({
                    where: {
                        itinerary_id: itinerary.id,
                    },
                    _min: {
                        start_date: true,
                    },
                    _max: {
                        end_date: true,
                    },
                });
                const start_date = (_a = dateRange._min.start_date) !== null && _a !== void 0 ? _a : new Date(0);
                const end_date = (_b = dateRange._max.end_date) !== null && _b !== void 0 ? _b : new Date(0);
                const itinerariesWithUser = {
                    id: itinerary.id,
                    name: itinerary.name,
                    travellers: userCount,
                    from: start_date,
                    to: end_date
                };
                itinerariesWithUserCount.push(itinerariesWithUser);
            }
            return (0, itinerary_model_1.toItineraryResponseList)(itinerariesWithUserCount);
        });
    }
    static getAllInvitedItinerary(user) {
        return __awaiter(this, void 0, void 0, function* () {
            var _a, _b;
            const itinerariesOwned = yield database_1.prismaClient.itinerary_Users.findMany({
                where: {
                    user_id: user.id,
                    role: {
                        in: ['admin', 'member']
                    }
                },
            });
            const itineraryIds = itinerariesOwned.map((item) => item.itinerary_id);
            const itineraries = yield database_1.prismaClient.itinerary.findMany({
                where: {
                    id: { in: itineraryIds }
                }
            });
            const itinerariesWithUserCount = [];
            for (const itinerary of itineraries) {
                const userCount = yield database_1.prismaClient.itinerary_Users.count({
                    where: {
                        itinerary_id: itinerary.id
                    }
                });
                const dateRange = yield database_1.prismaClient.itinerary_Destinations.aggregate({
                    where: {
                        itinerary_id: itinerary.id,
                    },
                    _min: {
                        start_date: true,
                    },
                    _max: {
                        end_date: true,
                    },
                });
                const start_date = (_a = dateRange._min.start_date) !== null && _a !== void 0 ? _a : new Date(0);
                const end_date = (_b = dateRange._max.end_date) !== null && _b !== void 0 ? _b : new Date(0);
                const itinerariesWithUser = {
                    id: itinerary.id,
                    name: itinerary.name,
                    travellers: userCount,
                    from: start_date,
                    to: end_date
                };
                itinerariesWithUserCount.push(itinerariesWithUser);
            }
            return (0, itinerary_model_1.toItineraryResponseList)(itinerariesWithUserCount);
        });
    }
    static cloneItinerary(itinerary_id, user) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = yield this.checkItinerary(itinerary_id);
            const itinerary1 = yield database_1.prismaClient.itinerary.create({
                data: {
                    name: itinerary.name,
                    created_date: new Date().toISOString(),
                    updated_date: new Date().toISOString()
                },
            });
            const owner = yield database_1.prismaClient.itinerary_Users.create({
                data: {
                    user_id: user.id,
                    itinerary_id: itinerary1.id,
                    role: "owner"
                }
            });
            const itineraryDestinations = yield database_1.prismaClient.itinerary_Destinations.findMany({
                where: {
                    itinerary_id: itinerary_id
                }
            });
            for (const destination of itineraryDestinations) {
                const itineraryDestination = yield database_1.prismaClient.itinerary_Destinations.create({
                    data: {
                        itinerary_id: itinerary1.id, // Use the new itinerary ID
                        destination_id: destination.destination_id,
                        start_date: destination.start_date,
                        end_date: destination.end_date,
                    },
                });
                // Clone days for each destination
                const days = yield database_1.prismaClient.schedule_Per_Day.findMany({
                    where: { itinerary_destination_id: destination.id },
                });
                for (const day of days) {
                    const daySchedule = yield database_1.prismaClient.schedule_Per_Day.create({
                        data: {
                            itinerary_destination_id: itineraryDestination.id,
                            date: day.date,
                        },
                    });
                    // Clone activities for each day
                    const activities = yield database_1.prismaClient.activity.findMany({
                        where: { day_id: day.id },
                    });
                    for (const activity of activities) {
                        yield database_1.prismaClient.activity.create({
                            data: {
                                location_id: activity.location_id,
                                day_id: daySchedule.id,
                                description: activity.description,
                                start_time: activity.start_time,
                                end_time: activity.end_time,
                                name: activity.name,
                                cost: activity.cost,
                                type: activity.type,
                            },
                        });
                    }
                }
            }
            return "Data Cloned";
        });
    }
    static getItinerary(itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            var _a, _b;
            const itinerary = yield this.checkItinerary(itinerary_id);
            const userCount = yield database_1.prismaClient.itinerary_Users.count({
                where: {
                    itinerary_id: itinerary.id
                }
            });
            const dateRange = yield database_1.prismaClient.itinerary_Destinations.aggregate({
                where: {
                    itinerary_id: itinerary.id,
                },
                _min: {
                    start_date: true,
                },
                _max: {
                    end_date: true,
                },
            });
            const start_date = (_a = dateRange._min.start_date) !== null && _a !== void 0 ? _a : new Date(0);
            const end_date = (_b = dateRange._max.end_date) !== null && _b !== void 0 ? _b : new Date(0);
            return (0, itinerary_model_1.toItineraryResponse)(itinerary, userCount, start_date, end_date);
        });
    }
    static checkItinerary(itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = yield database_1.prismaClient.itinerary.findUnique({
                where: {
                    id: itinerary_id
                },
            });
            if (!itinerary) {
                throw new response_error_1.ResponseError(400, "Itinerary not found!");
            }
            return itinerary;
        });
    }
    static createItinerary(req, user) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
            const itinerary_Request = validation_1.Validation.validate(itinerary_validation_1.ItineraryValidation.CREATE, req);
            const itinerary1 = yield database_1.prismaClient.itinerary.create({
                data: {
                    name: itinerary_Request.name,
                    created_date: new Date().toISOString(),
                    updated_date: new Date().toISOString()
                },
            });
            yield database_1.prismaClient.itinerary_Users.create({
                data: {
                    user_id: user.id,
                    itinerary_id: itinerary1.id,
                    role: "owner"
                }
            });
            return itinerary1;
        });
    }
    static updateItinerary(req) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = validation_1.Validation.validate(itinerary_validation_1.ItineraryValidation.UPDATE, req);
            yield this.checkItinerary(itinerary.id);
            const itineraryUpdate = yield database_1.prismaClient.itinerary.update({
                where: {
                    id: itinerary.id,
                },
                data: itinerary,
            });
            logging_1.logger.info("UPDATE RESULT: " + itineraryUpdate);
            return "Data update was successful!";
        });
    }
    static deleteItinerary(id) {
        return __awaiter(this, void 0, void 0, function* () {
            yield this.checkItinerary(id);
            yield database_1.prismaClient.itinerary.delete({
                where: {
                    id: id,
                },
            });
            return "Data has been deleted successfully!";
        });
    }
}
exports.ItineraryService = ItineraryService;
