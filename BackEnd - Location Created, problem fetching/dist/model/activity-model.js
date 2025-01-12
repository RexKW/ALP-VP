"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toActivityResponse = toActivityResponse;
exports.toActivityResponseList = toActivityResponseList;
function toActivityResponse(activity) {
    return {
        id: activity.id,
        name: activity.name,
        description: activity.description,
        start_time: activity.start_time,
        end_time: activity.end_time,
        type: activity.type,
        cost: activity.cost,
        location_id: activity.location_id
    };
}
function toActivityResponseList(prismaTodo) {
    const result = prismaTodo.map((activity) => {
        return {
            id: activity.id,
            name: activity.name,
            description: activity.description,
            start_time: activity.start_time,
            end_time: activity.end_time,
            type: activity.type,
            cost: activity.cost,
            location_id: activity.location_id
        };
    });
    return result;
}
