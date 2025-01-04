import { UserResponse } from '../model/user-model'; // Adjust the path according to your project structure

declare global {
    namespace Express {
        interface Request {
            user?: UserResponse; // Using UserResponse type from user-model.ts
        }
    }
}