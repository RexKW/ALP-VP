"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.LocationController = void 0;
const location_service_1 = require("../services/location-service");
const database_1 = require("../application/database");
class LocationController {
    /**
     * Get or create a location
     * @param req - Express Request object
     * @param res - Express Response object
     * @param next - Express NextFunction
     */
    static getOrCreateLocation(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                // Extract parameters from query
                const { place_id, categories, name, address, openingHours, website, phone } = req.query;
                if (!place_id || !categories) {
                    throw new Error("Missing required query parameters: place_id or categories.");
                }
                // Normalize categories to a string array
                const normalizedCategories = Array.isArray(categories)
                    ? categories.map((category) => String(category))
                    : [String(categories)];
                // Pass parameters to LocationService
                const location = yield location_service_1.LocationService.getOrCreateLocation({
                    place_id: place_id,
                    place_api: "geoapify", // Example source, adjust as needed
                    name: name,
                    address: address,
                    categories: normalizedCategories,
                    opening_hours: openingHours ? String(openingHours) : null,
                    website: website ? String(website) : null,
                    phone: phone ? String(phone) : null,
                    location_image: "default_image_url", // Example, replace as needed
                    description: "Default description", // Example, replace as needed
                });
                // Respond with the location data
                res.status(200).json({ data: location });
            }
            catch (error) {
                next(error);
            }
        });
    }
    /**
     * Get all locations
     * @param req - Express Request object
     * @param res - Express Response object
     * @param next - Express NextFunction
     */
    static getAllLocations(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const locations = yield location_service_1.LocationService.getAllLocations();
                res.status(200).json({
                    data: locations,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getLocationById(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                console.log("Request received to get location by ID:", req.params.locationId);
                const locationId = parseInt(req.params.locationId);
                if (isNaN(locationId)) {
                    console.error("Invalid location ID");
                    return res.status(400).json({ error: "Invalid location ID" });
                }
                const location = yield database_1.prismaClient.location.findUnique({
                    where: { id: locationId },
                });
                if (!location) {
                    console.warn("Location not found for ID:", locationId);
                    return res.status(404).json({ error: "Location not found" });
                }
                console.log("Location found:", location);
                res.status(200).json({ data: location });
            }
            catch (error) {
                console.error("Error in getLocationById:", error);
                next(error);
            }
        });
    }
}
exports.LocationController = LocationController;
