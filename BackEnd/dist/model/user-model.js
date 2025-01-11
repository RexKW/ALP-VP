"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = toUserResponse;
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = void 0;
// Fungsi untuk mengubah objek user menjadi response yang sesuai
function toUserResponse(user) {
    return {
        id: user.id,
        token: (_a = user.token) !== null && _a !== void 0 ? _a : "",
        email: user.email,
        username: user.username,
        email: user.email,
        password: user.password, // Add this line
        token: user.token,
    };
}
exports.toUserResponse = toUserResponse;
