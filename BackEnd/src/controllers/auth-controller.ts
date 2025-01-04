import { Request, Response, NextFunction } from 'express';
import { UserService } from "../services/auth-service";

interface RegisterRequest {
    username: string;
    password: string;
    email: string;
}

interface RegisterResponse {
    id: string;
    username: string;
    email: string;
}

interface LoginRequest {
    username: string;
    password: string;
}

interface LoginResponse {
    token: string;
}

export class AuthController {
    static async register(req: Request, res: Response, next: NextFunction): Promise<void> {
        try {
            const request: RegisterRequest = req.body;
            const response: RegisterResponse = await UserService.register(request);

            res.status(201).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }

    static async login(req: Request, res: Response, next: NextFunction): Promise<void> {
        try {
            const request: LoginRequest = req.body;
            const userResponse = await UserService.login(request);

            if (!userResponse.token) {
                throw new Error("Token is undefined");
            }

            const response: LoginResponse = { token: userResponse.token };
            res.status(200).json({
                data: response,
            });
        } catch (error) {
            next(error);
        }
    }
}