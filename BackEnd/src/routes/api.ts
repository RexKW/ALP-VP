import express from "express"
import { ItineraryController } from "../controllers/itinenary-controller"
import { ActivityController } from "../controllers/activity-controller"
import { AccomodationController } from "../controllers/accomodation-controller"
import { BudgetController } from "../controllers/budget-controller"
import { authMiddleware } from "../middlware/auth-middleware"
import { DestinationController } from "../controllers/destination-controller"

export const apiRouter = express.Router()
apiRouter.use(authMiddleware)
apiRouter.post("/itinerary/create", ItineraryController.createNewItinerary)
apiRouter.get("/itinerary/ownedTrips", ItineraryController.getAllItinerary)
apiRouter.put("/itinerary/updateTrip/:itineraryId(\\d+)", ItineraryController.updateItinerary)
apiRouter.delete("/itinerary/deleteTrip/:itineraryId(\\d+)", ItineraryController.deleteItinerary)

apiRouter.get("/destinations/all", DestinationController.getAllDestinations)
apiRouter.post("/destinations/selectDestination/:destinationId(\\d+)", ItineraryController.selectDestination)
apiRouter.put("/destinations/switchDestination/:destinationId(\\d+)", ItineraryController.updateItinerary)