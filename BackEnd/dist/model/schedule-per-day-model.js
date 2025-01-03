"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toScheduleResponse = toScheduleResponse;
exports.toScheduleResponseList = toScheduleResponseList;
function toScheduleResponse(schedule) {
    return {
        id: schedule.id,
        itinenary_destination_id: schedule.itinerary_destination_id,
        date: schedule.date,
    };
}
function toScheduleResponseList(prismaTodo) {
    const result = prismaTodo.map((schedule) => {
        return {
            id: schedule.id,
            itinenary_destination_id: schedule.itinerary_destination_id,
            date: schedule.date,
        };
    });
    return result;
}
