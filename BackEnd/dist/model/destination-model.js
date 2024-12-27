"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toDestinationResponse = toDestinationResponse;
exports.toDestinationResponseList = toDestinationResponseList;
function toDestinationResponse(destination) {
    return {
        id: destination.id,
        name: destination.name,
        province: destination.province,
    };
}
function toDestinationResponseList(prismaTodo) {
    const result = prismaTodo.map((destination) => {
        return {
            id: destination.id,
            name: destination.name,
            province: destination.province,
        };
    });
    return result;
}
