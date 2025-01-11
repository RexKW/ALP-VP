"use strict";
import { prismaClient } from "../application/database";
import { UserResponse } from "../model/user-model";
import { ResponseError } from "../error/response-error";
import { toUserResponse } from "../model/user-model";
import { UserValidation } from "../validation/user-validation";
import { Validation } from "../validation/validation";
import { v4 as uuidv4 } from "uuid";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import {v4 as uuid} from "uuid"
import bcrypt from "bcrypt"
import { PrismaClient } from "@prisma/client";

export class UserService{
    static async register(req: RegisterUserRequest): Promise<UserResponse>{
        const registerReq = Validation.validate(
            UserValidation.REGISTER,req
        )


class UserService {
    static async register(req: any): Promise<UserResponse> {
        const registerReq = Validation.validate(UserValidation.REGISTER, req);
        const email = await prismaClient.user.findFirst({
            where: {
                email: registerReq.email
            },
        });
        if (email) {
            throw new ResponseError(400, "Email already exists!");
        } else {
            registerReq.password = await bcrypt.hash(registerReq.password, 10);
        }
        const user = await prismaClient.user.create({
            data: {
                username: registerReq.username,
                email: registerReq.email,
                password: registerReq.password,
                token: uuidv4()
            }
        });
        return toUserResponse(user);
    }

    static async login(request: any): Promise<UserResponse> {
        const loginRequest = Validation.validate(UserValidation.LOGIN, request);
        let user = await prismaClient.user.findFirst({
            where: {
                email: loginRequest.email,
            },
        });
        if (!user) {
            throw new ResponseError(400, "Invalid email or password!");
        }
        const passwordIsValid = await bcrypt.compare(loginRequest.password, user.password);
        if (!passwordIsValid) {
            throw new ResponseError(400, "Invalid email or password!");
        }
        user = await prismaClient.user.update({
            where: {
                id: user.id,
            },
            data: {
                token: uuidv4(),
            },
        });
        const response = toUserResponse(user);
        return response;
    }

    static async verifyToken(token: string): Promise<UserResponse | null> {
        try {
            const decoded: any = jwt.verify(token, process.env.JWT_SECRET!);
            const user = await prismaClient.user.findUnique({
                where: {
                    id: decoded.id,
                },
            });
            if (!user) {
                return null;
            }
            return toUserResponse(user);
        } catch (error) {
            return null;
        }
    }
}

export { UserService };

    static async getAllUsers(){
        const userList = await prismaClient.user.findMany()


        return userList


    }
}
