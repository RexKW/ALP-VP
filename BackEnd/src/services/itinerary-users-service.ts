"use strict";
import { Validation } from "../validation/validation";
import { toItineraryUserResponseList } from "../model/itinerary-users-model";
import { prismaClient } from "../application/database";
import { ItineraryUserValidation } from "../validation/itinerary-user-validation";

class ItineraryUserService {
    static getAllItineraryUsers(itinerary: any): Promise<any> {
        return (async () => {
            const itinerary_users = await prismaClient.itinerary_Users.findMany({
                where: {
                    itinerary_id: itinerary.id,
                },
            });
            return toItineraryUserResponseList(itinerary_users);
        })();
    }

    static addItineraryUser(req: any): Promise<void> {
        return (async () => {
            const itinerary_user_request = Validation.validate(ItineraryUserValidation.CREATE, req);
            const checkUserItinerary = await prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id_unique: {
                        user_id: itinerary_user_request.user_id,
                        itinerary_id: itinerary_user_request.itinerary_id,
                    },
                },
            });
            if (!checkUserItinerary) {
                await prismaClient.itinerary_Users.create({
                    data: {
                        itinerary_id: itinerary_user_request.itinerary_id,
                        user_id: itinerary_user_request.user_id,
                        role: "member"
                    }
                });
            }
        })();
    }

    static kickUser(itinerary: any, user: any): Promise<string> {
        return (async () => {
            const getUser = await prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id_unique: {
                        user_id: user.id,
                        itinerary_id: itinerary.id,
                    },
                }
            });
            if (getUser?.role === "owner") {
                await prismaClient.itinerary_Users.delete({
                    where: {
                        user_id_itinerary_id_unique: {
                            user_id: user.id,
                            itinerary_id: itinerary.id,
                        },
                    }
                });
            }
            return "User has been kicked successfully!";
        })();
    }

    static updateUserRole(changer: any, changed: any, itinerary: any): Promise<string> {
        return (async () => {
            const userChanger = await prismaClient.itinerary_Users.findUnique({
                where: {
                    user_id_itinerary_id_unique: {
                        user_id: changer.id,
                        itinerary_id: itinerary.id,
                    },
                }
            });
            if (userChanger?.role === "owner" || userChanger?.role === "admin") {
                const userChanged = await prismaClient.itinerary_Users.findUnique({
                    where: {
                        user_id_itinerary_id_unique: {
                            user_id: changed.id,
                            itinerary_id: itinerary.id,
                        },
                    }
                });
                if (userChanged?.role === "admin") {
                    await prismaClient.itinerary_Users.update({
                        where: {
                            user_id_itinerary_id_unique: {
                                user_id: userChanged.id,
                                itinerary_id: itinerary.id,
                            },
                        },
                        data: {
                            role: "member"
                        }
                    });
                } else if (userChanged?.role === "member") {
                    await prismaClient.itinerary_Users.update({
                        where: {
                            user_id_itinerary_id_unique: {
                                user_id: userChanged.id,
                                itinerary_id: itinerary.id,
                            },
                        },
                        data: {
                            role: "admin"
                        }
                    });
                } else if (userChanged?.role === "owner") {
                    return "Can't change owner role";
                }
            }
            return "Data update was successful!";
        })();
    }
}

export { ItineraryUserService };