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
exports.LocationService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
class LocationService {
    /**
     * Get or create a location in the database
     * @param locationRequest - Location data to check or create
     */
    static getOrCreateLocation(locationRequest) {
        return __awaiter(this, void 0, void 0, function* () {
            // Check if a location with the same place_id exists
            let location = yield database_1.prismaClient.location.findUnique({
                where: {
                    place_id: locationRequest.place_id,
                },
            });
            if (!location) {
                // If not found, create a new location
                location = yield database_1.prismaClient.location.create({
                    data: {
                        place_id: locationRequest.place_id,
                        place_api: locationRequest.place_api, // Add the place_api field here
                        name: locationRequest.name,
                        address: locationRequest.address,
                        categories: locationRequest.categories,
                        opening_hours: locationRequest.opening_hours || null,
                        website: locationRequest.website || null,
                        phone: locationRequest.phone || null,
                        location_image: locationRequest.location_image || "default_image_url",
                        description: locationRequest.description || "Default description",
                    },
                });
            }
            return location;
        });
    }
    /**
     * Fetch location details from an external API
     * @param place_id - The unique identifier for the location
     * @param categories - The categories to filter the location (e.g., "entertainment,commercial")
     */
    static fetchLocationFromExternalAPI(place_id, categories) {
        return __awaiter(this, void 0, void 0, function* () {
            const axios = require("axios");
            const apiKey = "c9b67c73febc4f71baad197651185143"; // Replace with your actual API key
            const url = `https://api.geoapify.com/v2/places?categories=${categories}&filter=place:${place_id}&limit=30&apiKey=${apiKey}`;
            try {
                console.log(`Calling external API with URL: ${url}`); // Log the URL
                const response = yield axios.get(url);
                return response.data;
            }
            catch (error) {
                // Simplified error handling with a fixed HTTP status
                console.error(`Error fetching location details for place_id ${place_id}:`, error);
                throw new response_error_1.ResponseError(400, `Failed to fetch location details for place_id ${place_id}.`);
            }
        });
    }
    /**
     * Get all days for an itinerary destination
     * @param itinerary_destination_id - The ID of the itinerary destination
     */
    static getAllDays(itinerary_destination_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const allDays = yield database_1.prismaClient.schedule_Per_Day.findMany({
                where: {
                    itinerary_destination_id: itinerary_destination_id,
                },
            });
            return allDays;
        });
    }
    /**
   * Get all locations from the database
   * @returns A list of all locations
   */
    static getAllLocations() {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const locations = yield database_1.prismaClient.location.findMany();
                return locations;
            }
            catch (error) {
                console.error("Error fetching all locations:", error);
                throw new response_error_1.ResponseError(500, "Failed to fetch all locations.");
            }
        });
    }
}
exports.LocationService = LocationService;
