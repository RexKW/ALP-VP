"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.apiRouter = void 0;
const express_1 = __importDefault(require("express"));
const itinerary_controller_1 = require("../controllers/itinerary-controller");
const activity_controller_1 = require("../controllers/activity-controller");
const accomodation_controller_1 = require("../controllers/accomodation-controller");
const budget_controller_1 = require("../controllers/budget-controller");
const auth_middleware_1 = require("../middleware/auth-middleware");
const destination_controller_1 = require("../controllers/destination-controller");
const user_controller_1 = require("../controllers/user-controller");
const location_controller_1 = require("../controllers/location-controller");
exports.apiRouter = express_1.default.Router();
exports.apiRouter.use(auth_middleware_1.authMiddleware);
exports.apiRouter.post("/itinerary/create", itinerary_controller_1.ItineraryController.createNewItinerary);
exports.apiRouter.get("/itinerary/ownedTrips", itinerary_controller_1.ItineraryController.getAllItinerary);
exports.apiRouter.get("/itinerary/invitedTrips", itinerary_controller_1.ItineraryController.getAllInvitedItinerary);
exports.apiRouter.put("/itinerary/updateTrip/:itineraryId(\\d+)", itinerary_controller_1.ItineraryController.updateItinerary);
exports.apiRouter.delete("/itinerary/deleteTrip/:itineraryId(\\d+)", itinerary_controller_1.ItineraryController.deleteItinerary);
exports.apiRouter.get("/itinerary/allJourneys/:itineraryId(\\d+)", itinerary_controller_1.ItineraryController.allJourney);
exports.apiRouter.post("/itinerary/createJourney/:destinationId(\\d+)", itinerary_controller_1.ItineraryController.selectDestination);
exports.apiRouter.get("/itinerary/explore", itinerary_controller_1.ItineraryController.exploreItinerary);
exports.apiRouter.get("/itinerary/journey/:itineraryDestinationId(\\d+)", itinerary_controller_1.ItineraryController.getJourney);
exports.apiRouter.put("/itinerary/updateJourney/:itineraryDestinationId(\\d+)", itinerary_controller_1.ItineraryController.updateJourney);
exports.apiRouter.delete("/itinerary/deleteJourney/:itineraryDestinationId(\\d+)", itinerary_controller_1.ItineraryController.deleteJourney);
exports.apiRouter.post("/itinerary/cloneItinerary/:itineraryId(\\d+)", itinerary_controller_1.ItineraryController.cloneItinerary);
exports.apiRouter.get("/destinations/all", destination_controller_1.DestinationController.getAllDestinations);
exports.apiRouter.get("/destinations/:destinationId(\\d+)", destination_controller_1.DestinationController.getDestination);
// Add AccomodationController routes
exports.apiRouter.get("/accommodations/getOrCreate", accomodation_controller_1.AccomodationController.getOrCreateAccomodation); // Fetch or create accommodation
exports.apiRouter.put("/itinerary/updateJourneyAccommodation/:itineraryDestinationId(\\d+)", itinerary_controller_1.ItineraryController.updateAccomodation); // Update the accommodation for a specific itinerary destination
exports.apiRouter.get("/itinerary/checkAccommodation/:itineraryDestinationId", itinerary_controller_1.ItineraryController.checkAccommodation);
exports.apiRouter.get("/accommodations/:accommodationId", itinerary_controller_1.ItineraryController.getAccommodationDetails);
exports.apiRouter.get("/activities/allDays/:itineraryDestinationId(\\d+)", activity_controller_1.ActivityController.getAllDays);
exports.apiRouter.get("/activities/allActivities/:dayId(\\d+)", activity_controller_1.ActivityController.getAllActivities);
exports.apiRouter.post("/activities/createActivity/:dayId(\\d+)", activity_controller_1.ActivityController.createActivity);
exports.apiRouter.get("/activities/getActivity/:activityId(\\d+)", activity_controller_1.ActivityController.getActivity);
exports.apiRouter.delete("/activities/deleteActivity/:activityId(\\d+)", activity_controller_1.ActivityController.deleteActivity);
exports.apiRouter.put("/activities/updateActivity/:activityId(\\d+)", activity_controller_1.ActivityController.updateActivity);
exports.apiRouter.get("/budget/plannedBudget/:itineraryId(\\d+)", budget_controller_1.BudgetController.plannedBudget);
exports.apiRouter.get("/budget/actualBudget/:itineraryId(\\d+)", budget_controller_1.BudgetController.actualBudget);
exports.apiRouter.post("/budget/create/:itineraryId(\\d+)", budget_controller_1.BudgetController.createBudget);
exports.apiRouter.put("/budget/update/:itineraryId(\\d+)", budget_controller_1.BudgetController.updateBudget);
exports.apiRouter.get("/users/all", user_controller_1.AuthController.allUsers);
exports.apiRouter.get("/users/role/:itineraryId(\\d+)", user_controller_1.AuthController.userRole);
// Add LocationController routes
exports.apiRouter.get("/locations", location_controller_1.LocationController.getAllLocations); // Fetch all locations
//apiRouter.post("/locations", LocationController.createLocation); // Create a location
exports.apiRouter.get("/locations/getOrCreate", location_controller_1.LocationController.getOrCreateLocation); // Get or create a location by place_id and categories
exports.apiRouter.get("/locations/:locationId(\\d+)", location_controller_1.LocationController.getLocationById);
exports.apiRouter.get("/location/seed");
