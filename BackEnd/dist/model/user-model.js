"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = toUserResponse;
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = void 0;
// Fungsi untuk mengubah objek user menjadi response yang sesuai
function toUserResponse(user) {
    return {
        id: user.id,
        username: user.username,
        email: user.email,
        password: user.password, // Add this line
        token: user.token,
    };
}
exports.toUserResponse = toUserResponse;
