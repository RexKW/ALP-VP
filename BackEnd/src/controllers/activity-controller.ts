import { NextFunction, Request, Response } from "express";
import { LoginRequest, RegisterRequest, UserResponse } from "../model/user-model";
import { UserService } from "../services/auth-service";

export class ActivityController{
    static async register(req: Request, res: Response, next: NextFunction){
        try{
            const request: RegisterRequest = req.body as RegisterRequest
            const response: UserResponse = await UserService.register(request)

            res.status(200).json({
                data: response
            })
        } catch (error){
            next(error)
        }
    }

    static async login(req: Request, res: Response, next: NextFunction) {
        try {
            const request = req.body as LoginRequest
            const response = await UserService.login(request)

            res.status(200).json({
                data: response,
            })
        } catch (error) {
            next(error)
        }
    }
}