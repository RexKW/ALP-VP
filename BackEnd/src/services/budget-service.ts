import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day , Destination} from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { ActualBudgetResponse, BudgetResponse, toBudgetResponse, toBudgetResponseList } from "../model/budget-model";
import { Decimal } from "@prisma/client/runtime/library";



export class BudgetService{
    static async getPlannedBudget(itinerary_id: number): Promise<BudgetResponse[]>{
        const plannedBudget = await prismaClient.budget.findMany({
            where: {
                itinerary_id: itinerary_id
            },
        })


        return toBudgetResponseList(plannedBudget)
    }

    static async getSpendings(itinenary_id: number): Promise<ActualBudgetResponse>{
        const allItineraryDestination = await prismaClient.itinerary_Destinations.findMany({
            where:{
                itinerary_id : itinenary_id
            }
        })

        let totalAccomodation = new Decimal(0);
        let totalTransport = new Decimal(0);
        let totalShoppingEntertainment = new Decimal(0);
        let totalCulinary = new Decimal(0);
        let totalSightSeeing = new Decimal(0);
        let totalHealthcare = new Decimal(0);
        let totalSport = new Decimal(0);

        const accommodations = await Promise.all(
            allItineraryDestination.map(async (destination) => {
                if (destination.accomodation_id === null || destination.accomodation_id === undefined) {
                    return null; 
                }

                const accomodation = await prismaClient.accomodation.findUnique({
                    where: {
                        id: destination.accomodation_id,
                    },
                });
        
                if (accomodation && accomodation.cost) {
                    totalAccomodation = totalAccomodation.plus(accomodation.cost);
                }
        
                return accomodation;
            })
        );

        const rest = await Promise.all(
            allItineraryDestination.map(async (destinations) =>{
                const days = await prismaClient.schedule_Per_Day.findMany({
                    where:{
                        itinerary_destination_id: destinations.id
                    }
                })

                days.map(async (days) =>{
                    const activities = await prismaClient.activity.findMany({
                        where:{
                            day_id: days.id
                        }
                    })

                    activities.map( async (activity)=>{
                        switch(activity.type){
                            case "Transport":
                                totalTransport = totalTransport.plus(activity.cost)
                                break;
                            case "Shopping/Entertainment":
                                totalShoppingEntertainment = totalShoppingEntertainment.plus(activity.cost)
                                break;
                            case "Sightseeing":
                                totalSightSeeing = totalSightSeeing.plus(activity.cost)
                                break;
                            case "Food":
                                totalCulinary = totalCulinary.plus(activity.cost)
                                break;
                            case "Healthcare":
                                totalHealthcare = totalHealthcare.plus(activity.cost)
                                break;
                            case "Sport":
                                totalSport = totalSport.plus(activity.cost)
                                break;
                        }
                    })
                })
            })
        );
        

        const data = {
            totalAccomodation,
            totalTransport,
            totalShoppingEntertainment,
            totalCulinary,
            totalSightSeeing,
            totalHealthcare,
            totalSport
        }

        return data

    }

    static async setBudget(){

    }
}