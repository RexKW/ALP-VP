"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ItineraryValidation = void 0;
const zod_1 = require("zod");
class ItineraryValidation {
}
exports.ItineraryValidation = ItineraryValidation;
ItineraryValidation.CREATE = zod_1.z.object({
    name: zod_1.z.string().min(1).max(100),
    user_id: zod_1.z.number().positive()
});
ItineraryValidation.UPDATE = zod_1.z.object({
    id: zod_1.z.number().positive(),
    name: zod_1.z.string().min(1).max(100),
});
