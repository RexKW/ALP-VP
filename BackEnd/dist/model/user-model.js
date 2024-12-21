"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = toUserResponse;
function toUserResponse(user) {
    var _a;
    return {
        token: (_a = user.token) !== null && _a !== void 0 ? _a : "",
        username: user.username
    };
}
