import express from "express"
import { ItineraryController } from "../controllers/itinerary-controller"
import { ActivityController } from "../controllers/activity-controller"
import { AccomodationController } from "../controllers/accomodation-controller"
import { BudgetController } from "../controllers/budget-controller"
import { authMiddleware } from "../middlware/auth-middleware"
import { DestinationController } from "../controllers/destination-controller"
import { AuthController } from "../controllers/user-controller"

export const apiRouter = express.Router()
apiRouter.use(authMiddleware)
apiRouter.post("/itinerary/create", ItineraryController.createNewItinerary)
apiRouter.get("/itinerary/ownedTrips", ItineraryController.getAllItinerary)
apiRouter.put("/itinerary/updateTrip/:itineraryId(\\d+)", ItineraryController.updateItinerary)
apiRouter.delete("/itinerary/deleteTrip/:itineraryId(\\d+)", ItineraryController.deleteItinerary)
apiRouter.get("/itinerary/allJourneys/:itineraryId(\\d+)", ItineraryController.allJourney)
apiRouter.post("/itinerary/createJourney/:destinationId(\\d+)", ItineraryController.selectDestination)
apiRouter.get("/itinerary/explore", ItineraryController.exploreItinerary)

apiRouter.get("/destinations/all", DestinationController.getAllDestinations)
apiRouter.get("/destinations/:destinationId(\\d+)", DestinationController.getDestination)
apiRouter.put("/destinations/updateDestination/:destinationId(\\d+)", ItineraryController.updateItinerary)

apiRouter.get("/activities/allDays/:itineraryDestinationId(\\d+)", ActivityController.getAllDays)
apiRouter.get("/activities/allActivities/:dayId(\\d+)", ActivityController.getAllActivities)
apiRouter.post("/activities/createActivity/:dayId(\\d+)", ActivityController.createActivity)
apiRouter.get("/activities/getActivity/:activityId(\\d+)", ActivityController.getActivity)


apiRouter.get("/users/all", AuthController.allUsers)


apiRouter.get("/location/seed")