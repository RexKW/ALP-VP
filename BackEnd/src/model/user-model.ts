import { User } from "@prisma/client";

export interface RegisterUserRequest{
    username: string
    email: string
    password:string
}

export interface UserResponse{
    token?: string
    username: string

}

export interface LoginUserRequest {
    email: string
    password: string
}

export function toUserResponse (user: User):UserResponse {
    return{
        token: user.token ?? "",
        username: user.username
    }
}