import { ItineraryDestinationResponse, ItineraryDestinationUpdateRequest, toItineraryDestinationResponse, AddItineraryDestinationRequest, toItineraryDestinationResponseList } from "../model/itinerary-destinations-model";
import { Validation } from "../validation/validation";
import { Activity, Itinerary, Itinerary_Destinations, Schedule_Per_Day, User } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ItineraryDestinationValidation } from "../validation/itinerary-destination-validation";
import { logger } from "../application/logging"
import { DestinationService } from "./destination-service";

export class ItineraryDestinationService{
    static async getAllItinenaryDestination(itinerary_id:number): Promise<ItineraryDestinationResponse[]> {
        const itinerary_destinations = await prismaClient.itinerary_Destinations.findMany({
            where: {
                itinerary_id: itinerary_id,
            },
        })

        return toItineraryDestinationResponseList(itinerary_destinations)
    }

    static async getItineraryDestination(itinerary_Destination_id: number): Promise<ItineraryDestinationResponse> {
        const itinerary_Destination = await this.checkItineraryDestination(itinerary_Destination_id);
        return toItineraryDestinationResponse(itinerary_Destination)
    }

    static async checkItineraryDestination(
        itinerary_Destination_id: number
    ): Promise<Itinerary_Destinations> {
        const itinerary = await prismaClient.itinerary_Destinations.findUnique({
            where: {
                id: itinerary_Destination_id
            },
        })

        if (!itinerary) {
            throw new ResponseError(400, "Itinerary not found!")
        }

        return itinerary
    }





    static async createItineraryDestination(
        req: AddItineraryDestinationRequest,
        user: User
    ): Promise<string> {
        // validate request
        const itinerary_Destinations_Request = Validation.validate(ItineraryDestinationValidation.CREATE, req)

        const authentication = await prismaClient.itinerary_Users.findUnique({
            where: {
                user_id_itinerary_id_unique: {
                    user_id: user.id,
                    itinerary_id: req.itinerary_id,
                },
            },
        }
        )

        if(authentication?.role == "admin" || authentication?.role == "owner"){

            let destination = await prismaClient.destination.findUnique({
                where: {
                    destination_api_id: req.destination_id,
                },
            });

            if (!destination) {
                destination = await DestinationService.addDestination(req.destination_id);
            }

            const itinerary_Destinations = await prismaClient.itinerary_Destinations.create({
                data: {
                    itinerary_id: itinerary_Destinations_Request.itinerary_id,
                    destination_id: destination.id,
                    accomodation_id: null,
                    start_date: itinerary_Destinations_Request.start_date,
                    end_date: itinerary_Destinations_Request.end_date
                },
            })
    
    
            const startDate = itinerary_Destinations.start_date
            const endDate = itinerary_Destinations.end_date
            const currentDate = startDate
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
                    await prismaClient.schedule_Per_Day.createMany({
                        data: datesToInsert,
                    });
                    datesToInsert.length = 0; 
                    batchCounter++;
                }
            }
            await prismaClient.schedule_Per_Day.createMany({
                data: datesToInsert,
            });
            return "Data created successfully!"
        }else{
            return "Unauthorized Access"
        }

        
    }


    static async updateItineraryDestination(req: ItineraryDestinationUpdateRequest){
        const itinerary_destination = Validation.validate(ItineraryDestinationValidation.UPDATE, req)
        
                await this.checkItineraryDestination(itinerary_destination.id)
        
                const itineraryDestinationUpdate = await prismaClient.itinerary_Destinations.update({
                    where: {
                        id: itinerary_destination.id,
                    },
                    data: itinerary_destination,
                })

                logger.info("UPDATE RESULT: " + itineraryDestinationUpdate)
    }

}