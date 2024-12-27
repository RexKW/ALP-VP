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
    static getAllItinerary(user) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_users = yield database_1.prismaClient.itinerary_Users.findMany({
                where: {
                    user_id: user.id,
                    role: 'owner'
                },
            });
            const itineraryIds = itinerary_users.map((item) => item.itinerary_id);
            const itineraries = yield database_1.prismaClient.itinerary.findMany({
                where: {
                    id: { in: itineraryIds }
                }
            });
            return (0, itinerary_model_1.toItineraryResponseList)(itineraries);
        });
    }
    static getItinerary(itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = yield this.checkItinerary(itinerary_id);
            return (0, itinerary_model_1.toItineraryResponse)(itinerary);
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
    static createItinerary(req) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
            const itinerary_Request = validation_1.Validation.validate(itinerary_validation_1.ItineraryValidation.CREATE, req);
            const itinerary1 = yield database_1.prismaClient.itinerary.create({
                data: {
                    name: itinerary_Request.name,
                    created_date: Date.now().toString(),
                    updated_date: Date.now().toString()
                },
            });
            const owner = yield database_1.prismaClient.itinerary_Users.create({
                data: {
                    user_id: itinerary_Request.user_id,
                    itinerary_id: itinerary1.id,
                    role: "owner"
                }
            });
            return "Itinerary created successfully!";
        });
    }
    static updateItinerary(req) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary = validation_1.Validation.validate(itinerary_validation_1.ItineraryValidation.UPDATE, req);
            yield this.checkItinerary(itinerary.id);
            const todoUpdate = yield database_1.prismaClient.itinerary.update({
                where: {
                    id: itinerary.id,
                },
                data: itinerary_validation_1.ItineraryValidation,
            });
            logging_1.logger.info("UPDATE RESULT: " + todoUpdate);
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
