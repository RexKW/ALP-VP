import express from "express"
import { ItineraryController } from "../controllers/itinenary-controller"
import { ActivityController } from "../controllers/activity-controller"
import { AccomodationController } from "../controllers/accomodation-controller"
import { BudgetController } from "../controllers/budget-controller"
import { authMiddleware } from "../middlware/auth-middleware"

export const apiRouter = express.Router()
apiRouter.use(authMiddleware)
apiRouter.post("/itinerary/create", ItineraryController.createNewItinerary)
apiRouter.get("/itinerary/ownedTrips", ItineraryController.getAllItinerary)