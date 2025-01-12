import express from "express"
import { ItineraryController } from "../controllers/itinerary-controller"
import { ActivityController } from "../controllers/activity-controller"
import { AccomodationController } from "../controllers/accomodation-controller"
import { BudgetController } from "../controllers/budget-controller"
import { authMiddleware } from "../middleware/auth-middleware"
import { DestinationController } from "../controllers/destination-controller"
import { AuthController } from "../controllers/user-controller"

export const apiRouter = express.Router()
apiRouter.use(authMiddleware)
apiRouter.post("/itinerary/create", ItineraryController.createNewItinerary)
apiRouter.get("/itinerary/ownedTrips", ItineraryController.getAllItinerary)
apiRouter.get("/itinerary/invitedTrips", ItineraryController.getAllInvitedItinerary)
apiRouter.put("/itinerary/updateTrip/:itineraryId(\\d+)", ItineraryController.updateItinerary)
apiRouter.delete("/itinerary/deleteTrip/:itineraryId(\\d+)", ItineraryController.deleteItinerary)
apiRouter.get("/itinerary/allJourneys/:itineraryId(\\d+)", ItineraryController.allJourney)
apiRouter.post("/itinerary/createJourney/:destinationId(\\d+)", ItineraryController.selectDestination)
apiRouter.get("/itinerary/explore", ItineraryController.exploreItinerary)
apiRouter.get("/itinerary/journey/:itineraryDestinationId(\\d+)", ItineraryController.getJourney)
apiRouter.put("/itinerary/updateJourney/:itineraryDestinationId(\\d+)", ItineraryController.updateJourney)
apiRouter.delete("/itinerary/deleteJourney/:itineraryDestinationId(\\d+)", ItineraryController.deleteJourney)
apiRouter.post("/itinerary/cloneItinerary/:itineraryId(\\d+)", ItineraryController.cloneItinerary)

apiRouter.get("/destinations/all", DestinationController.getAllDestinations)
apiRouter.get("/destinations/:destinationId(\\d+)", DestinationController.getDestination)


apiRouter.get("/activities/allDays/:itineraryDestinationId(\\d+)", ActivityController.getAllDays)
apiRouter.get("/activities/allActivities/:dayId(\\d+)", ActivityController.getAllActivities)
apiRouter.post("/activities/createActivity/:dayId(\\d+)", ActivityController.createActivity)
apiRouter.get("/activities/getActivity/:activityId(\\d+)", ActivityController.getActivity)
apiRouter.delete("/activities/deleteActivity/:activityId(\\d+)", ActivityController.deleteActivity)
apiRouter.put("/activities/updateActivity/:activityId(\\d+)", ActivityController.updateActivity)

apiRouter.get("/budget/plannedBudget/:itineraryId(\\d+)", BudgetController.plannedBudget)
apiRouter.get("/budget/actualBudget/:itineraryId(\\d+)", BudgetController.actualBudget)
apiRouter.post("/budget/create/:itineraryId(\\d+)", BudgetController.createBudget)
apiRouter.put("/budget/update/:itineraryId(\\d+)", BudgetController.updateBudget)


apiRouter.get("/users/all", AuthController.allUsers)
apiRouter.get("/users/role/:itineraryId(\\d+)", AuthController.userRole)


apiRouter.get("/location/seed")
