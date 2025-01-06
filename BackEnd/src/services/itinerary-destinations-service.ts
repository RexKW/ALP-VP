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

    static async deleteItineraryDestination(
        itinerary_Destination_id:number
    ): Promise<String>{
        const itinerary = await prismaClient.itinerary_Destinations.delete({
            where:{
                id: itinerary_Destination_id
            }
        })

        return "Data Deleted"
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
                    date: new Date(currentDate),
                });
                currentDate.setDate(currentDate.getDate() + 1);
            }
            await prismaClient.schedule_Per_Day.createMany({
                data: datesToInsert,
            });
            return "Data created successfully!"
        }else{
            return "Unauthorized Access"
        }

        
    }


    static async updateItineraryDestination(req: ItineraryDestinationUpdateRequest): Promise<string> {
        const itinerary_destination = Validation.validate(ItineraryDestinationValidation.UPDATE, req)
        
                await this.checkItineraryDestination(itinerary_destination.id)
                

                let destination = await prismaClient.destination.findUnique({
                    where: {
                        destination_api_id: req.destination_id,
                    },
                });

                destination = await prismaClient.destination.findUnique({
                    where:{
                        id: req.destination_id
                    }
                });
                
    
                if (!destination) {
                    destination = await DestinationService.addDestination(req.destination_id);
                }

                itinerary_destination.destination_id = destination.id

                if(itinerary_destination.destination_id == req.destination_id){ //if the destination is the same, it'll retain the previous activitites
                   const oldItineraryDestination = await prismaClient.itinerary_Destinations.findUnique({
                    where:{
                        id: itinerary_destination.id
                    }
                   })
                   console.log(`Old itinerary Destination start: ${oldItineraryDestination?.start_date} Old itinerary Destination end: ${oldItineraryDestination?.end_date}`)
                   
                    const itineraryDestinationUpdate = await prismaClient.itinerary_Destinations.update({
                        where: {
                            id: itinerary_destination.id,
                        },
                        data: itinerary_destination,
                    })
    
                    const startDate = new Date(itineraryDestinationUpdate.start_date)
                    const endDate = new Date(itineraryDestinationUpdate.end_date)
    
                    const createdDates = await prismaClient.schedule_Per_Day.findMany({
                        where:{
                            itinerary_destination_id: itinerary_destination.id
                        }
                    })
    
                    
                    const datesToInsert = [];
                    const listofDates: { itinerary_destination_id: number; date: Date }[]= [];
                    const prevListofDates = []
    
                    const currentDate = startDate
    
                    while (currentDate <= endDate) {
                        if(currentDate <  itinerary_destination.start_date || currentDate > itinerary_destination.end_date ){
                            datesToInsert.push({
                                itinerary_destination_id: itineraryDestinationUpdate.id,
                                date: currentDate,
                            });
                        }
                        listofDates.push({
                            itinerary_destination_id: itineraryDestinationUpdate.id,
                                date: currentDate,
                        })
    
                        
                        currentDate.setDate(currentDate.getDate() + 1);
    
                    }
                    const currentDate2 = new Date(oldItineraryDestination?.start_date|| itinerary_destination.start_date)
    
                    while(currentDate2 <=  (new Date(oldItineraryDestination?.end_date || itinerary_destination.end_date || new Date()))){
                        prevListofDates.push({
                            itinerary_destination_id : itineraryDestinationUpdate.id,
                            date: currentDate2
                        })
                        currentDate2.setDate(currentDate2.getDate() + 1);
                    }
    
                    const areDatesEqual = (date1: Date, date2: Date) => {
                        return date1.getTime() === date2.getTime();
                    };

                    prevListofDates.forEach(async (date) => {
                        const matchFound = listofDates.some(d => 
                            d.itinerary_destination_id === date.itinerary_destination_id && areDatesEqual(d.date, date.date)
                        );
                        
                        console.log(`Checking date: ${date.date} for itinerary_destination_id: ${date.itinerary_destination_id}`);
                        
                        if (!matchFound) {
                            console.log(`No match found for date: ${date.date}. Proceeding with record lookup.`);
                            
                            const recordToDelete = await prismaClient.schedule_Per_Day.findUnique({
                                where: {
                                    itinerary_destination_id_date: {
                                        itinerary_destination_id: date.itinerary_destination_id,
                                        date: date.date
                                    }
                                }
                            });
                    
                            if (recordToDelete) {
                                console.log(`Found record to delete with ID: ${recordToDelete.id}`);
                                await prismaClient.schedule_Per_Day.delete({
                                    where: {
                                        id: recordToDelete.id
                                    }
                                });
                                console.log(`Record with ID: ${recordToDelete.id} deleted successfully.`);
                            } else {
                                console.log(`No record found for itinerary_destination_id: ${date.itinerary_destination_id} and date: ${date.date}`);
                            }
                        } else {
                            console.log(`Match found for date: ${date.date}. Skipping deletion.`);
                        }
                    });
                    
                }else{ // if it is a different destination, it deletes all previous activities and adds new ones
                    const itineraryDestinationUpdate = await prismaClient.itinerary_Destinations.update({
                        where: {
                            id: itinerary_destination.id,
                        },
                        data: itinerary_destination,
                    })
    
                    const startDate = new Date(itineraryDestinationUpdate.start_date)
                    const endDate = new Date(itineraryDestinationUpdate.end_date)

                    const deletedDates = await prismaClient.schedule_Per_Day.deleteMany({
                        where: {
                            itinerary_destination_id: itinerary_destination.id
                        }
                    })

                    const currentDate = startDate
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
                    await 
                    await prismaClient.schedule_Per_Day.createMany({
                        data: datesToInsert,
                    });



                }
                
        
                

                return "Destination Updated"

    }

}