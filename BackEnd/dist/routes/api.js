"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.apiRouter = void 0;
const express_1 = __importDefault(require("express"));
const itinenary_controller_1 = require("../controllers/itinenary-controller");
const destination_controller_1 = require("../controllers/destination-controller");
const auth_middleware_1 = require("../middleware/auth-middleware"); // Ensure this path is correct
exports.apiRouter = express_1.default.Router();
// Apply authMiddleware to all routes below
exports.apiRouter.use(auth_middleware_1.authMiddleware);
// Routes for itinerary
exports.apiRouter.post("/itinerary/create", itinenary_controller_1.ItineraryController.createNewItinerary);
exports.apiRouter.get("/itinerary/ownedTrips", itinenary_controller_1.ItineraryController.getAllItinerary);
exports.apiRouter.put("/itinerary/updateTrip/:itineraryId(\\d+)", itinenary_controller_1.ItineraryController.updateItinerary);
exports.apiRouter.delete("/itinerary/deleteTrip/:itineraryId(\\d+)", itinenary_controller_1.ItineraryController.deleteItinerary);
// Routes for destinations
exports.apiRouter.get("/destinations/all", destination_controller_1.DestinationController.getAllDestinations);
exports.apiRouter.post("/destinations/selectDestination/:destinationId(\\d+)", destination_controller_1.DestinationController.selectDestination);
exports.apiRouter.put("/destinations/switchDestination/:destinationId(\\d+)", itinenary_controller_1.ItineraryController.updateItinerary);
