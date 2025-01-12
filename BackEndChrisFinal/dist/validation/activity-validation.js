"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ActivityValidation = void 0;
const zod_1 = require("zod");
class ActivityValidation {
}
exports.ActivityValidation = ActivityValidation;
ActivityValidation.CREATE = zod_1.z.object({
    name: zod_1.z.string().min(1).max(100),
    description: zod_1.z.string(),
    type: zod_1.z.string().min(1).max(100),
    start_time: zod_1.z.date(),
    end_time: zod_1.z.date(),
    cost: zod_1.z.number().nonnegative(),
    location_id: zod_1.z.number().int()
});
ActivityValidation.UPDATE = zod_1.z.object({
    name: zod_1.z.string().min(1).max(100),
    day_id: zod_1.z.number().positive(),
    description: zod_1.z.string(),
    type: zod_1.z.string().min(1).max(100),
    start_time: zod_1.z.date(),
    end_time: zod_1.z.date(),
    cost: zod_1.z.number().nonnegative(),
    location_id: zod_1.z.number().int()
});
