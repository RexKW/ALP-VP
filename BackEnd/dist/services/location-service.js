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
const schedule_per_day_model_1 = require("../model/schedule-per-day-model");
class LocationService {
    static getAllDays(itinerary_destination_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const allDays = yield database_1.prismaClient.schedule_Per_Day.findMany({
                where: {
                    itinerary_destination_id: itinerary_destination_id
                },
            });
            return (0, schedule_per_day_model_1.toScheduleResponseList)(allDays);
        });
    }
}
exports.LocationService = LocationService;
