import { Request, Response, NextFunction, RequestHandler } from 'express';
import { UserService } from '../services/auth-service';
import { UserRequest } from '../types/user-request';

export const authMiddleware: RequestHandler = async (req: UserRequest, res: Response, next: NextFunction): Promise<void> => {
    try {
        const token = req.headers.authorization?.split(' ')[1];
        if (!token) {
            res.status(401).json({ message: 'Authentication token is missing' });
            return;
        }

        const user = await UserService.verifyToken(token);
        if (!user) {
            res.status(401).json({ message: 'Invalid token' });
        }

        if (user) {
            req.user = user;
            next();
        } else {
            res.status(401).json({ message: 'Invalid token' });
        }
        next();
    } catch (error) {
        res.status(500).json({ message: 'Internal server error' });
    }
};