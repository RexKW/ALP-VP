"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toScheduleResponse = toScheduleResponse;
function toScheduleResponse(schedule) {
    return {
        id: schedule.id,
        date: schedule.date,
    };
}
