"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toLocationResponse = toLocationResponse;
function toLocationResponse(location) {
    return {
        id: location.id,
        name: location.name,
        description: location.description,
    };
}
