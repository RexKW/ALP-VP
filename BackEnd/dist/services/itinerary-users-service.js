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
exports.ItineraryUserService = void 0;
const validation_1 = require("../validation/validation");
const itinerary_users_model_1 = require("../model/itinerary-users-model");
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const itinerary_user_validation_1 = require("../validation/itinerary-user-validation");
class ItineraryUserService {
    static getAllItineraryUsers(itinerary) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_users = yield database_1.prismaClient.itinerary_Users.findMany({
                where: {
                    itinerary_id: itinerary.id,
                },
            });
            return (0, itinerary_users_model_1.toItineraryUserResponseList)(itinerary_users);
        });
    }
    static addItineraryUser(req) {
        return __awaiter(this, void 0, void 0, function* () {
            const itinerary_user_request = validation_1.Validation.validate(itinerary_user_validation_1.ItineraryUserValidation.CREATE, req);
            const checkUserItinerary = yield database_1.prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id: {
                        user_id: itinerary_user_request.user_id,
                        itinerary_id: itinerary_user_request.itinerary_id,
                    },
                },
            });
            if (!checkUserItinerary) {
                const itinerary_user = yield database_1.prismaClient.itinerary_Users.create({
                    data: {
                        itinerary_id: itinerary_user_request.itinerary_id,
                        user_id: itinerary_user_request.user_id,
                        role: "member"
                    }
                });
            }
        });
    }
    static kickUser(itinerary, user) {
        return __awaiter(this, void 0, void 0, function* () {
            const getUser = yield database_1.prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id: {
                        user_id: user.id,
                        itinerary_id: itinerary.id,
                    },
                }
            });
            if ((getUser === null || getUser === void 0 ? void 0 : getUser.role) === "owner") {
                yield database_1.prismaClient.itinerary_Users.delete({
                    where: {
                        user_id_itinerary_id: {
                            user_id: user.id,
                            itinerary_id: itinerary.id,
                        },
                    }
                });
            }
            return "User has been kicked successfully!";
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
    static createItineraryDestination(req) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
            const itinerary_Request = validation_1.Validation.validate(itinerary_user_validation_1.ItineraryUserValidation.CREATE, req);
            const itinerary1 = yield database_1.prismaClient.itinerary.create({
                data: {
                    name: itinerary_Request.name,
                    created_date: Date.now().toString(),
                    updated_date: Date.now().toString()
                },
            });
            return "Data created successfully!";
        });
    }
    static updateUserRole(changer, changed, itinerary) {
        return __awaiter(this, void 0, void 0, function* () {
            const userChanger = yield database_1.prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id: {
                        user_id: changer.id,
                        itinerary_id: itinerary.id,
                    },
                }
            });
            if ((userChanger === null || userChanger === void 0 ? void 0 : userChanger.role) == "owner" || (userChanger === null || userChanger === void 0 ? void 0 : userChanger.role) == "admin") {
                const userChanged = yield database_1.prismaClient.itinerary_Users.findUnique({
                    where: {
                        user_id_itinerary_id: {
                            user_id: changer.id,
                            itinerary_id: itinerary.id,
                        },
                    }
                });
                if ((userChanged === null || userChanged === void 0 ? void 0 : userChanged.role) == "admin") {
                    const userUpdate = yield database_1.prismaClient.itinerary_Users.update({
                        where: {
                            user_id_itinerary_id: {
                                user_id: userChanged.id,
                                itinerary_id: itinerary.id,
                            },
                        },
                        data: {
                            role: "member"
                        }
                    });
                }
                else if ((userChanged === null || userChanged === void 0 ? void 0 : userChanged.role) == "member") {
                    const userUpdate = yield database_1.prismaClient.itinerary_Users.update({
                        where: {
                            user_id_itinerary_id: {
                                user_id: userChanged.id,
                                itinerary_id: itinerary.id,
                            },
                        },
                        data: {
                            role: "admin"
                        }
                    });
                }
                else if ((userChanged === null || userChanged === void 0 ? void 0 : userChanged.role) == "owner") {
                    return "Can't change owner role";
                }
            }
            return "Data update was successful!";
        });
    }
}
exports.ItineraryUserService = ItineraryUserService;
