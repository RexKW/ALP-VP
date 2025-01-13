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
exports.AccomodationController = void 0;
const accomodation_service_1 = require("../services/accomodation-service");
const library_1 = require("@prisma/client/runtime/library");
class AccomodationController {
    static getOrCreateAccomodation(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const { place_id, name, address, location_image, place_api, categories, opening_hours, website, phone, cost, people, } = req.query;
                // Validate required fields
                if (!place_id || !name || !address || !place_api || !categories || cost === undefined || people === undefined) {
                    throw new Error("Missing required fields: place_id, name, address, place_api, categories, cost, or people.");
                }
                // Normalize categories
                const normalizedCategories = categories.split(",");
                // Pass the data to AccomodationService
                const accomodation = yield accomodation_service_1.AccomodationService.getOrCreateAccomodation({
                    place_id,
                    name,
                    address,
                    location_image: location_image || "default_image_url", // Default image
                    place_api,
                    categories: normalizedCategories,
                    opening_hours: opening_hours || undefined, // Replace null with undefined
                    website: website || undefined, // Replace null with undefined
                    phone: phone || undefined, // Replace null with undefined
                    cost: new library_1.Decimal(cost), // Convert to number
                    people: parseInt(people), // Convert to number
                });
                // Respond with the accommodation data
                res.status(200).json({ data: accomodation });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.AccomodationController = AccomodationController;
