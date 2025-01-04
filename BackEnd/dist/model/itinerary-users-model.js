"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryUserResponseList = toItineraryUserResponseList;
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryUserResponse = toItineraryUserResponseList;
exports.toItineraryUserResponseList = toItineraryUserResponseList;
"use strict";
function toItineraryUserResponseList(prismaItineraryUsers) {
    return prismaItineraryUsers.map(user => ({
        id: user.id.toString(),
        user_id: user.user_id.toString(),
        itinerary_id: user.itinerary_id.toString(),
        role: user.role
    }));
}
