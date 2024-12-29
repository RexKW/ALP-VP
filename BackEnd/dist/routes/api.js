"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.apiRouter = void 0;
const express_1 = __importDefault(require("express"));
const itinenary_controller_1 = require("../controllers/itinenary-controller");
const auth_middleware_1 = require("../middlware/auth-middleware");
exports.apiRouter = express_1.default.Router();
exports.apiRouter.use(auth_middleware_1.authMiddleware);
exports.apiRouter.post("/itinerary/create", itinenary_controller_1.ItineraryController.createNewItinerary);
exports.apiRouter.get("/itinerary/ownedTrips", itinenary_controller_1.ItineraryController.getAllItinerary);
