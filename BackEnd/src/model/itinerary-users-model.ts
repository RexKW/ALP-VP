"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItineraryUserResponse = toItineraryUserResponseList;
exports.toItineraryUserResponseList = toItineraryUserResponseList;
"use strict";

interface ItineraryUserResponse {
    id: string;
    user_id: string;
    itinerary_id: string;
    role: string;
}

function toItineraryUserResponseList(prismaItineraryUsers: any[]): ItineraryUserResponse[] {
    return prismaItineraryUsers.map(user => ({
        id: user.id.toString(),
        user_id: user.user_id.toString(),
        itinerary_id: user.itinerary_id.toString(),
        role: user.role
    }));
}

export { toItineraryUserResponseList, ItineraryUserResponse };