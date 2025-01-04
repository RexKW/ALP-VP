"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = void 0;


export interface User {
    id: string;
    username: string;
    email: string;
    password: string; // Add other properties as necessary
    token?: string;
}
// Tipe data untuk request registrasi
export interface RegisterRequest {
    username: string;
    password: string;
    email: string;
}

// Tipe data untuk request login
export interface LoginRequest {
    username: string;
    password: string;
}

// Tipe data untuk response registrasi
export interface RegisterResponse {
    id: string;
    username: string;
    email: string;
}

// Tipe data untuk response login
export interface LoginResponse {
    token: string;
}

// Tipe data untuk response user
export interface UserResponse {
    id: string;
    username: string;
    email: string;
    password: string; // Add this property if it is required
    token?: string;
}

// Fungsi untuk mengubah objek user menjadi response yang sesuai
export function toUserResponse(user: any): UserResponse {

    return {

        id: user.id,

        username: user.username,

        email: user.email,

        password: user.password, // Add this line

        token: user.token,

    };

}
exports.toUserResponse = toUserResponse;