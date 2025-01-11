import { NextFunction, Request, Response } from "express";
import { LoginUserRequest, RegisterUserRequest, UserResponse } from "../model/user-model";
import { UserService } from "../services/auth-service";
import { UserRequest } from "../type/user-request";
import { ActualBudgetResponse } from "../model/budget-model";
import { BudgetService } from "../services/budget-service";

export class BudgetController{
    static async actualBudget(req: UserRequest, res: Response, next: NextFunction){
        try{
            const id = Number(req.params.itineraryId)
            const response: ActualBudgetResponse = await BudgetService.getSpendings(id)

            res.status(200).json({
                data: response
            })
        } catch (error){
            next(error)
        }
    }

    static async login(req: Request, res: Response, next: NextFunction) {
        try {
            const request = req.body as LoginUserRequest
            const response = await UserService.login(request)

            res.status(200).json({
                data: response,
            })
        } catch (error) {
            next(error)
        }
    }
}