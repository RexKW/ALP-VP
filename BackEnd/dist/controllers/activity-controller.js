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
exports.ActivityController = void 0;
const activity_service_1 = require("../services/activity-service");
const day_service_1 = require("../services/day-service");
class ActivityController {
    static getAllDays(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const itineraryDestinationId = Number(req.params.itineraryDestinationId);
                const response = yield day_service_1.DayService.getAllDays(itineraryDestinationId);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getAllActivities(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const dayId = Number(req.params.dayId);
                const response = yield activity_service_1.ActivityService.getAllActivity(dayId);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getActivity(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const activityId = Number(req.params.activityId);
                const response = yield activity_service_1.ActivityService.getActivity(activityId);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static createActivity(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const dayId = Number(req.params.dayId);
                const timeRequest = Object.assign(Object.assign({}, request), { start_time: new Date(request.start_time), end_time: new Date(request.end_time) });
                const response = yield activity_service_1.ActivityService.createActivity(dayId, timeRequest);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.ActivityController = ActivityController;
