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
exports.AccomodationService = void 0;
const database_1 = require("../application/database");
class AccomodationService {
    static getOrCreateAccomodation(data) {
        return __awaiter(this, void 0, void 0, function* () {
            // Check if accommodation exists by place_id
            let accomodation = yield database_1.prismaClient.accomodation.findUnique({
                where: { place_id: data.place_id },
            });
            if (!accomodation) {
                // If accommodation doesn't exist, create a new one
                accomodation = yield database_1.prismaClient.accomodation.create({
                    data: {
                        place_id: data.place_id,
                        name: data.name,
                        address: data.address,
                        cost: data.cost,
                        people: data.people,
                        location_image: data.location_image || "default_image_url", // Default image
                        place_api: data.place_api,
                        categories: data.categories,
                        opening_hours: data.opening_hours || null,
                        website: data.website || null,
                        phone: data.phone || null,
                    },
                });
            }
            else {
                // If it already exists, update cost and people if needed
                accomodation = yield database_1.prismaClient.accomodation.update({
                    where: { id: accomodation.id },
                    data: {
                        cost: data.cost || accomodation.cost,
                        people: data.people || accomodation.people,
                    },
                });
            }
            return accomodation;
        });
    }
}
exports.AccomodationService = AccomodationService;
