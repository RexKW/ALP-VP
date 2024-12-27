import express from "express"
import { AuthController } from "../controllers/user-controller"
import { DestinationController } from "../controllers/destination-controller"

export const publicRouter = express.Router()

publicRouter.post("/api/register", AuthController.register)
publicRouter.post("/api/login", AuthController.login)
publicRouter.get("/destinations/all", DestinationController.getAllDestinations)