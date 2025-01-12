import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day , Destination} from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { ActualBudgetResponse, BudgetResponse, CreateBudgetRequest, toBudgetResponse, toBudgetResponseList } from "../model/budget-model";
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

    static async setBudget(req: CreateBudgetRequest, itinerary_id: number): Promise<String>{
        const accommodationBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Accommodation",
                actual_budget: 0.0,
                estimated_budget: req.totalAccommodation

            }
        })
        const transportBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Transport",
                actual_budget: 0.0,
                estimated_budget: req.totalTransport

            }
        })
        const shoppingEntertainmentBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Shopping/Entertainment",
                actual_budget: 0.0,
                estimated_budget: req.totalTransport

            }
        })
        const sightSeeingBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Sightseeing",
                actual_budget: 0.0,
                estimated_budget: req.totalSightSeeing
            }
        })
        const foodBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Food",
                actual_budget: 0.0,
                estimated_budget: req.totalSightSeeing
            }
        })
        const healthcareBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Healthcare",
                actual_budget: 0.0,
                estimated_budget: req.totalSightSeeing
            }
        })
        const sportBudget = await prismaClient.budget.create({
            data:{
                itinerary_id: itinerary_id,
                type: "Sport",
                actual_budget: 0.0,
                estimated_budget: req.totalSightSeeing
            }
        })
        return "Data Created"
    }

    static async updateBudget(req: CreateBudgetRequest, itinerary_id: number){
        const allBudget = await prismaClient.budget.findMany({
            where:{
                itinerary_id: itinerary_id
            }
        })

        for(const budget of allBudget){
            switch(budget.type){
                case "Transport":
                    const accommodationBudget = await prismaClient.budget.update({
                        where:{
                            id: budget.id
                        },
                        data:{
                            itinerary_id: itinerary_id,
                            type: "Accommodation",
                            actual_budget: 0.0,
                            estimated_budget: req.totalAccommodation
            
                        }
                    })
                    break;
                case "Shopping/Entertainment":
                    const shoppingEntertainmentBudget = await prismaClient.budget.update({
                        where:{
                            id: budget.id
                        },
                        data:{
                            itinerary_id: itinerary_id,
                            type: "Shopping/Entertainment",
                            actual_budget: 0.0,
                            estimated_budget: req.totalShoppingEntertainment
            
                        }
                    })
                    
                    break;
                case "Sightseeing":
                    const sightSeeingBudget = await prismaClient.budget.update({
                        where:{
                            id: budget.id
                        },
                        data:{
                            itinerary_id: itinerary_id,
                            type: "Sightseeing",
                            actual_budget: 0.0,
                            estimated_budget: req.totalSightSeeing
            
                        }
                    })
                    
                    break;
                case "Food":
                    const foodBudget = await prismaClient.budget.update({
                        where:{
                            id: budget.id
                        },
                        data:{
                            itinerary_id: itinerary_id,
                            type: "Food",
                            actual_budget: 0.0,
                            estimated_budget: req.totalCulinary
            
                        }
                    })
                    
                    break;
                case "Healthcare":
                    const healthcareBudget = await prismaClient.budget.update({
                        where:{
                            id: budget.id
                        },
                        data:{
                            itinerary_id: itinerary_id,
                            type: "Healthcare",
                            actual_budget: 0.0,
                            estimated_budget: req.totalHealthcare
            
                        }
                    })
                    
                    break;
                case "Sport":
                    const sportBudget = await prismaClient.budget.update({
                        where:{
                            id: budget.id
                        },
                        data:{
                            itinerary_id: itinerary_id,
                            type: "Sport",
                            actual_budget: 0.0,
                            estimated_budget: req.totalSport
            
                        }
                    })
                    
                    break;
            }
        }
    }
}