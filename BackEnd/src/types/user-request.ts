import { Request } from "express";
import { User } from "../model/user-model"; // Adjust the path as necessary

export interface UserRequest extends Request {
    user?: User;
}