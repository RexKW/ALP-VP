import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day , Destination} from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { BudgetResponse, toBudgetResponse, toBudgetResponseList } from "../model/budget-model";

export class BudgetService{
    static async getPlannedBudget(itinerary_id: number): Promise<BudgetResponse[]>{
        const allDays = await prismaClient.budget.findMany({
            where: {
                itinerary_id: itinerary_id
            },
        })


        return toBudgetResponseList(allDays)
    }

    static async getSpendings(itinenary_id: number){
        
    }

    static async setBudget(){

    }
}