
import { toItineraryResponseList, ItineraryResponse, toItineraryResponse, CreateItinenaryRequest, ItineraryUpdateRequest } from "../model/itinerary-model";
import { Validation } from "../validation/validation";
import { Activity, Itinerary, Itinerary_Destinations, Schedule_Per_Day, User } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ItineraryValidation } from "../validation/itinerary-validation";
import { logger } from "../application/logging"

export class ItineraryService{
    static async getAllItinerary(user: User): Promise<ItineraryResponse[]> {
        const itinerary_users = await prismaClient.itinerary_Users.findMany({
            where: {
                user_id: user.id,
                role: 'owner'
            },
        })

        const itineraryIds = itinerary_users.map((item) => item.itinerary_id);

        const itineraries = await prismaClient.itinerary.findMany({
            where:{
                id: { in: itineraryIds }
            }
        })

        return toItineraryResponseList(itineraries)
    }

    static async getItinerary(itinerary_id: number): Promise<ItineraryResponse> {
        const itinerary = await this.checkItinerary(itinerary_id);
        return toItineraryResponse(itinerary)
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
        req: CreateItinenaryRequest
    ): Promise<string> {
        // validate request
        const itinerary_Request = Validation.validate(ItineraryValidation.CREATE, req)

        const itinerary1 = await prismaClient.itinerary.create({
            data: {
                name: itinerary_Request.name,
                created_date: Date.now().toString(),
                updated_date: Date.now().toString()
            },
        })

        const owner = await prismaClient.itinerary_Users.create({
            data:{
                user_id: itinerary_Request.user_id,
                itinerary_id: itinerary1.id,
                role: "owner"
            }
        })
        return "Itinerary created successfully!"
    }


    static async updateItinerary(
        req: ItineraryUpdateRequest
    ): Promise<string> {
        const itinerary = Validation.validate(ItineraryValidation.UPDATE, req)

        await this.checkItinerary(itinerary.id)

        const todoUpdate = await prismaClient.itinerary.update({
            where: {
                id: itinerary.id,
            },
            data: ItineraryValidation,
        })

        logger.info("UPDATE RESULT: " + todoUpdate)

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