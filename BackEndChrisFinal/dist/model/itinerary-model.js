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
exports.toItineraryResponse = toItineraryResponse;
exports.toItineraryExploreResponseList = toItineraryExploreResponseList;
exports.toItineraryResponseList = toItineraryResponseList;
const database_1 = require("../application/database");
function toItineraryResponse(itinerary, travellerNumber, start_date, end_date) {
    return {
        id: itinerary.id,
        name: itinerary.name,
        travellers: travellerNumber,
        from: start_date,
        to: end_date
    };
}
function toItineraryExploreResponseList(prismaItinerary) {
    return __awaiter(this, void 0, void 0, function* () {
        const result = [];
        for (const itinerary of prismaItinerary) {
            const destinations = yield database_1.prismaClient.itinerary_Destinations.count({
                where: {
                    itinerary_id: itinerary.id
                }
            });
            result.push({
                id: itinerary.id,
                name: itinerary.name,
                destinations: destinations
            });
        }
        return result;
    });
}
function toItineraryResponseList(prismaitinerary) {
    return __awaiter(this, void 0, void 0, function* () {
        var _a, _b;
        const result = [];
        for (const itinerary of prismaitinerary) {
            const travellerNumber = yield database_1.prismaClient.itinerary_Users.count({
                where: {
                    itinerary_id: itinerary.id,
                },
            });
            const dateRange = yield database_1.prismaClient.itinerary_Destinations.aggregate({
                where: {
                    itinerary_id: itinerary.id,
                },
                _min: {
                    start_date: true,
                },
                _max: {
                    end_date: true,
                },
            });
            const start_date = (_a = dateRange._min.start_date) !== null && _a !== void 0 ? _a : new Date(0);
            const end_date = (_b = dateRange._max.end_date) !== null && _b !== void 0 ? _b : new Date(0);
            result.push({
                id: itinerary.id,
                name: itinerary.name,
                travellers: travellerNumber,
                from: start_date,
                to: end_date
            });
        }
        return result;
    });
}
