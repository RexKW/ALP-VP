
import { toItineraryResponseList, ItineraryResponse, ItineraryExploreResponse, toItineraryResponse, toItineraryExploreResponseList, CreateItineraryRequest, ItineraryUpdateRequest } from "../model/itinerary-model";
import { Validation } from "../validation/validation";
import { Itinerary, User } from "@prisma/client";
import { prismaClient } from "../application/database";
import { ResponseError } from "../error/response-error";
import { ItineraryValidation } from "../validation/itinerary-validation";
import { logger } from "../application/logging";

export class ItineraryService{
    static async explore(): Promise<ItineraryExploreResponse[]>{
        const allItineraries = await prismaClient.itinerary.findMany()

        return toItineraryExploreResponseList(allItineraries)
    }


    static async getAllItinerary(user: User): Promise<ItineraryResponse[]> {
        const itinerariesOwned = await prismaClient.itinerary_Users.findMany({
            where: {
                user_id: user.id,
                role: 'owner'
            },
        });

        const itineraryIds = itinerariesOwned.map((item) => item.itinerary_id);
        const itineraries = await prismaClient.itinerary.findMany({
            where: {
                id: { in: itineraryIds }
            }
        });

        const itinerariesWithUserCount: ItineraryResponse[] = [];
        for (const itinerary of itineraries) {
            const userCount = await prismaClient.itinerary_Users.count({
                where: {
                    itinerary_id: itinerary.id
                }
            });

            const dateRange = await prismaClient.itinerary_Destinations.aggregate({
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

            const start_date = dateRange._min.start_date ?? new Date(0);
            const end_date = dateRange._max.end_date ?? new Date(0);

            const itinerariesWithUser = {
                id: itinerary.id,
                name: itinerary.name,
                travellers: userCount,
                from: start_date,
                to: end_date
            };

            itinerariesWithUserCount.push(itinerariesWithUser);
        }

        return toItineraryResponseList(itinerariesWithUserCount);
    }

    static async getAllInvitedItinerary(user: User): Promise<ItineraryResponse[]> {
        const itinerariesOwned = await prismaClient.itinerary_Users.findMany({
            where: {
                user_id: user.id,
                role: {
                    in: ['admin','member']
                }
            },
        })

        const itineraryIds = itinerariesOwned.map((item) => item.itinerary_id);
        const itineraries = await prismaClient.itinerary.findMany({
            where:{
                id: { in: itineraryIds }
            }
        })

        const itinerariesWithUserCount = [];
        for(const itinerary of itineraries){
            const userCount = await prismaClient.itinerary_Users.count({
                where:{
                    itinerary_id : itinerary.id
                }
            })

            const dateRange = await prismaClient.itinerary_Destinations.aggregate({
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

            const start_date = dateRange._min.start_date ?? new Date(0)
            const end_date = dateRange._max.end_date ?? new Date(0)

            const itinerariesWithUser = {
                id: itinerary.id,
                name: itinerary.name,
                travellers: userCount,
                from: start_date,
                to: end_date
            }

            itinerariesWithUserCount.push(itinerariesWithUser)

        }
        

        return toItineraryResponseList(itinerariesWithUserCount)
    }

    static async cloneItinerary(itinerary_id: number, user: User): Promise<String>{
        const itinerary = await this.checkItinerary(itinerary_id);
        const itinerary1 = await prismaClient.itinerary.create({
            data: {
                name: itinerary.name,
                created_date: new Date().toISOString(),
                updated_date: new Date().toISOString()
            },
        })
        const owner = await prismaClient.itinerary_Users.create({
            data:{
                user_id: user.id,
                itinerary_id: itinerary1.id,
                role: "owner"
            }
        })

        const itineraryDestinations = await prismaClient.itinerary_Destinations.findMany({
            where:{
                itinerary_id: itinerary_id
            }
        })

        for (const destination of itineraryDestinations) {
            const itineraryDestination = await prismaClient.itinerary_Destinations.create({
                data: {
                    itinerary_id: itinerary1.id, // Use the new itinerary ID
                    destination_id: destination.destination_id,
                    start_date: destination.start_date,
                    end_date: destination.end_date,
                },
            });
    
            // Clone days for each destination
            const days = await prismaClient.schedule_Per_Day.findMany({
                where: { itinerary_destination_id: destination.id },
            });
    
            for (const day of days) {
                const daySchedule = await prismaClient.schedule_Per_Day.create({
                    data: {
                        itinerary_destination_id: itineraryDestination.id,
                        date: day.date,
                    },
                });
    
                // Clone activities for each day
                const activities = await prismaClient.activity.findMany({
                    where: { day_id: day.id },
                });
    
                for (const activity of activities) {
                    await prismaClient.activity.create({
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
    }

    static async getItinerary(itinerary_id: number): Promise<ItineraryResponse> {
        const itinerary = await this.checkItinerary(itinerary_id);
        const userCount = await prismaClient.itinerary_Users.count({
            where: {
                itinerary_id: itinerary.id
            }
        });

        const dateRange = await prismaClient.itinerary_Destinations.aggregate({
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

        const start_date = dateRange._min.start_date ?? new Date(0);
        const end_date = dateRange._max.end_date ?? new Date(0);

        return toItineraryResponse(itinerary, userCount, start_date, end_date);
    }

    static async checkItinerary(itinerary_id: number): Promise<Itinerary> {
        const itinerary = await prismaClient.itinerary.findUnique({
            where: {
                id: itinerary_id
            },
        });

        if (!itinerary) {
            throw new ResponseError(400, "Itinerary not found!");
        }

        return itinerary;
    }





    static async createItinerary(
        req: CreateItineraryRequest,
        user: User
    ): Promise<Itinerary> {
        // validate request
        const itinerary_Request = Validation.validate(ItineraryValidation.CREATE, req)

        const itinerary1 = await prismaClient.itinerary.create({
            data: {
                name: itinerary_Request.name,
                created_date: new Date().toISOString(),
                updated_date: new Date().toISOString()
            },
        });

        await prismaClient.itinerary_Users.create({
            data: {
                user_id: user.id,
                itinerary_id: itinerary1.id,
                role: "owner"
            }
        })

        

        return itinerary1
    }


    static async updateItinerary(
        req: ItineraryUpdateRequest
    ): Promise<string> {
        const itinerary = Validation.validate(ItineraryValidation.UPDATE, req)

        await this.checkItinerary(itinerary.id)

        const itineraryUpdate = await prismaClient.itinerary.update({
            where: {
                id: itinerary.id,
            },
            data: itinerary,
        });

        logger.info("UPDATE RESULT: " + itineraryUpdate);

        return "Data update was successful!";
    }

    static async deleteItinerary(id: number): Promise<string> {
        await this.checkItinerary(id);

        await prismaClient.itinerary.delete({
            where: {
                id: id,
            },
        });

        return "Data has been deleted successfully!";
    }
}