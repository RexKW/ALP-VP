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
exports.ActivityService = void 0;
const activity_model_1 = require("../model/activity-model");
const validation_1 = require("../validation/validation");
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const activity_validation_1 = require("../validation/activity-validation");
const logging_1 = require("../application/logging");
class ActivityService {
    static getAllActivity(day_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const activity = yield database_1.prismaClient.activity.findMany({
                where: {
                    day_id: day_id,
                },
            });
            return (0, activity_model_1.toActivityResponseList)(activity);
        });
    }
    static getActivity(activity_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const todo = yield this.checkActivity(activity_id);
            return (0, activity_model_1.toActivityResponse)(todo);
        });
    }
    static checkActivity(activity_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const activity = yield database_1.prismaClient.activity.findUnique({
                where: {
                    id: activity_id,
                },
            });
            if (!activity) {
                throw new response_error_1.ResponseError(400, "Activity not found!");
            }
            return activity;
        });
    }
    static createActivity(day_id, req) {
        return __awaiter(this, void 0, void 0, function* () {
            const activityRequest = validation_1.Validation.validate(activity_validation_1.ActivityValidation.CREATE, req);
            const activity = yield database_1.prismaClient.activity.create({
                data: {
                    name: activityRequest.name,
                    description: activityRequest.description,
                    start_time: activityRequest.start_time,
                    end_time: activityRequest.end_time,
                    cost: activityRequest.cost,
                    type: activityRequest.type,
                    location_id: activityRequest.location_id,
                    day_id: day_id,
                },
            });
            return "Data created successfully!";
        });
    }
    static updateActivity(req) {
        return __awaiter(this, void 0, void 0, function* () {
            const activity = validation_1.Validation.validate(activity_validation_1.ActivityValidation.UPDATE, req);
            yield this.checkActivity(activity.id);
            const itineraryUpdate = yield database_1.prismaClient.itinerary.update({
                where: {
                    id: activity.id,
                },
                data: activity,
            });
            logging_1.logger.info("UPDATE RESULT: " + itineraryUpdate);
            return "Data update was successful!";
        });
    }
    static deleteActivity(aId) {
        return __awaiter(this, void 0, void 0, function* () {
            yield this.checkActivity(aId);
            yield database_1.prismaClient.activity.delete({
                where: {
                    id: aId,
                },
            });
            return "Data deletion successful!";
        });
    }
}
exports.ActivityService = ActivityService;
