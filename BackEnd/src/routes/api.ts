import express from "express";
import { ItineraryController } from "../controllers/itinenary-controller";
import { DestinationController } from "../controllers/destination-controller";
import { authMiddleware } from "../middleware/auth-middleware"; // Ensure this path is correct

export const apiRouter = express.Router();

// Apply authMiddleware to all routes below
apiRouter.use(authMiddleware);

// Routes for itinerary
apiRouter.post("/itinerary/create", ItineraryController.createNewItinerary);
apiRouter.get("/itinerary/ownedTrips", ItineraryController.getAllItinerary);
apiRouter.put("/itinerary/updateTrip/:itineraryId(\\d+)", ItineraryController.updateItinerary);
apiRouter.delete("/itinerary/deleteTrip/:itineraryId(\\d+)", ItineraryController.deleteItinerary);

// Routes for destinations
apiRouter.get("/destinations/all", DestinationController.getAllDestinations);
apiRouter.post("/destinations/selectDestination/:destinationId(\\d+)", DestinationController.selectDestination);
apiRouter.put("/destinations/switchDestination/:destinationId(\\d+)", ItineraryController.updateItinerary);