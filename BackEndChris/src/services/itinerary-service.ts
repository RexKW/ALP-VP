
import { toItineraryResponseList, ItineraryResponse, ItineraryExploreResponse, toItineraryResponse, toItineraryExploreResponseList, CreateItineraryRequest, ItineraryUpdateRequest } from "../model/itinerary-model";
import { Validation } from "../validation/validation";
import { Activity, Itinerary, Itinerary_Destinations, Schedule_Per_Day, User } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ItineraryValidation } from "../validation/itinerary-validation";
import { logger } from "../application/logging"

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

    static async cloneItinerary(){
        
    }

    static async getItinerary(itinerary_id: number): Promise<ItineraryResponse> {
        const itinerary = await this.checkItinerary(itinerary_id);
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


        return toItineraryResponse(itinerary, userCount, start_date, end_date)
    }

    static async checkItinerary(
        itinerary_id: number
    ): Promise<Itinerary> {
        const itinerary = await prismaClient.itinerary.findUnique({
            where: {
                id: itinerary_id
            },
        })

        if (!itinerary) {
            throw new ResponseError(400, "Itinerary not found!")
        }

        return itinerary
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
        })

        const owner = await prismaClient.itinerary_Users.create({
            data:{
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
        })

        logger.info("UPDATE RESULT: " + itineraryUpdate)

        return "Data update was successful!"
    }
    static async deleteItinerary(id: number): Promise<String> {
        await this.checkItinerary(id)

        await prismaClient.itinerary.delete({
            where: {
                id: id,
            },
        })

        return "Data has been deleted successfully!"
    }
}