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
class ActivityService {
    static getAllActivity(day) {
        return __awaiter(this, void 0, void 0, function* () {
            const activity = yield database_1.prismaClient.activity.findMany({
                where: {
                    day_id: day.id,
                },
            });
            return (0, activity_model_1.toActivityResponseList)(activity);
        });
    }
    static getActivity(day, activity_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const todo = yield this.checkActivity(day.id, activity_id);
            return (0, activity_model_1.toActivityResponse)(todo);
        });
    }
    static checkActivity(day_id, activity_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const activity = yield database_1.prismaClient.activity.findUnique({
                where: {
                    id: activity_id,
                    day_id: day_id,
                },
            });
            if (!activity) {
                throw new response_error_1.ResponseError(400, "Activity not found!");
            }
            return activity;
        });
    }
    static createActivity(day, req) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
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
                    day_id: day.id,
                },
            });
            return "Data created successfully!";
        });
    }
    static updateActivity() {
        return __awaiter(this, void 0, void 0, function* () {
        });
    }
}
exports.ActivityService = ActivityService;
