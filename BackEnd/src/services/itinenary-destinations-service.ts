import { ItineraryDestinationResponse, toItineraryDestinationResponse, AddItinenaryDestinationRequest, toItinenaryDestinationResponseList } from "../model/itinenary-destinations-model";
import { Validation } from "../validation/validation";
import { Activity, Itinenary, Itinenary_Destinations, Schedule_Per_Day } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";

export class DestinationService{
    static async getAllItinenaryDestination(itinenary: Itinenary): Promise<ItineraryDestinationResponse[]> {
        const itinenary_destinations = await prismaClient.itinenary_Destinations.findMany({
            where: {
                itinenary_id: itinenary.id,
            },
        })

        return toItinenaryDestinationResponseList(itinenary_destinations)
    }

    static async getItinenaryDestination(itinenary_Destination_id: number): Promise<ItineraryDestinationResponse> {
        const itinenary_Destination = await this.checkItineraryDestination(itinenary_Destination_id);
        return toItineraryDestinationResponse(itinenary_Destination)
    }

    static async checkItineraryDestination(
        itinenary_Destination_id: number
    ): Promise<Itinenary_Destinations> {
        const itinenary = await prismaClient.itinenary_Destinations.findUnique({
            where: {
                id: itinenary_Destination_id
            },
        })

        if (!itinenary) {
            throw new ResponseError(400, "Itinenary not found!")
        }

        return itinenary
    }





    static async createItinenaryDestination(
        itinenary: Itinenary,
        req: AddItinenaryDestinationRequest
    ): Promise<string> {
        // validate request
        const itinenary_Destinations_Request = Validation.validate(ActivityValidation.CREATE, req)

        const itinenary_Destinations = await prismaClient.itinenary_Destinations.create({
            data: {
                itinenary_id: itinenary_Destinations_Request.itinenary_id,
                destination_id: itinenary_Destinations_Request.destination_id,
                accomodation_id: itinenary_Destinations_Request.accomodation_id,
                start_date: itinenary_Destinations_Request.start_date,
                end_date: itinenary_Destinations_Request.end_date
            },
        })


        const startDate = itinenary_Destinations.start_date
        const endDate = itinenary_Destinations.end_date
        const currentDate = startDate
        while(currentDate<=endDate){
        const Schedule_Per_Day = await prismaClient.schedule_Per_Day.create({
            data:{
                itinenary_destination_id: itinenary_Destinations.id,
                date: currentDate
            }
            })
            currentDate.setDate(currentDate.getDate()+1)
        }

        return "Data created successfully!"
    }


    static async updateItinenaryDestination(){
        const 
    }

}