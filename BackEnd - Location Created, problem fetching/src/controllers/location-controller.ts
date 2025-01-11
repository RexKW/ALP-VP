import { NextFunction, Request, Response } from "express";
import { CreateLocationRequest, LocationResponse } from "../model/location-model";
import { LocationService } from "../services/location-service";

export class LocationController {
  /**
   * Get or create a location
   * @param req - Express Request object
   * @param res - Express Response object
   * @param next - Express NextFunction
   */
  
  static async getOrCreateLocation(req: Request, res: Response, next: NextFunction) {
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
        const location = await LocationService.getOrCreateLocation({
            place_id: place_id as string,
            place_api: "geoapify", // Example source, adjust as needed
            name: name as string,
            address: address as string,
            categories: normalizedCategories,
            opening_hours: openingHours ? String(openingHours) : null,
            website: website ? String(website) : null,
            phone: phone ? String(phone) : null,
            location_image: "default_image_url", // Example, replace as needed
            description: "Default description", // Example, replace as needed
        });

        // Respond with the location data
        res.status(200).json({ data: location });
    } catch (error) {
        next(error);
    }
}
//   static async getOrCreateLocation(req: Request, res: Response, next: NextFunction) {
//     try {
//       // Extract place_id and categories from query params
//       const { place_id, categories } = req.query;

//       if (!place_id || !categories) {
//         throw new Error("Missing required query parameters: place_id or categories.");
//       }

//       // Normalize categories to a string array
//       const normalizedCategories = Array.isArray(categories)
//         ? categories.map((category) => String(category)) // Ensure all values are strings
//         : [String(categories)];

//       // Fetch or create the location using the LocationService
//       const location = await LocationService.getOrCreateLocation({
//         place_id: place_id as string,
//         place_api: "geoapify", // Example value for place_api
//         name: "Example Location Name", // Replace with actual value if available
//         address: "Example Address", // Replace with actual value if available
//         categories: normalizedCategories,
//         opening_hours: null, // Replace with actual value if available
//         website: null, // Replace with actual value if available
//         phone: null, // Replace with actual value if available
//         location_image: "default_image_url", // Replace with actual value if available
//         description: "Default description", // Replace with actual value if available
//       });

//       // res.status(200).json({
//       //   data: location,
//       // });
//       res.status(200).json({
//         id: location.id, // Include the ID for the frontend
//         place_id: location.place_id,
//         name: location.name,
//         address: location.address,
//         categories: location.categories,
//         opening_hours: location.opening_hours,
//         website: location.website,
//         phone: location.phone,
//         location_image: location.location_image,
//         description: location.description,
//       });
//     } catch (error) {
//       next(error);
//     }
// }

  /**
   * Get all locations
   * @param req - Express Request object
   * @param res - Express Response object
   * @param next - Express NextFunction
   */
  static async getAllLocations(req: Request, res: Response, next: NextFunction) {
    try {
      const locations = await LocationService.getAllLocations();
      res.status(200).json({
        data: locations,
      });
    } catch (error) {
      next(error);
    }
  }
  
}
