"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.DestinationService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const destination_model_1 = require("../model/destination-model");
class DestinationService {
    static getAllProvinces() {
        return __awaiter(this, void 0, void 0, function* () {
            const axios = require('axios');
            const destinations = yield axios.get('https://rexkw.github.io/api-wilayah-indonesia/api/provinces.json');
            return destinations.data;
        });
    }
    static getAllCitiesInProvince(provinceId) {
        return __awaiter(this, void 0, void 0, function* () {
            const axios = require('axios');
            const response = yield axios.get(`https://emsifa.github.io/api-wilayah-indonesia/api/regencies/${provinceId}.json`);
            return response.data;
        });
    }
    static getAllDestination() {
        return __awaiter(this, void 0, void 0, function* () {
            const provinces = yield this.getAllProvinces();
            const allCities = [];
            for (const province of provinces) {
                const regencies = yield this.getAllCitiesInProvince(province.id);
                const cityWithProvince = regencies.map(({ id, name }) => ({
                    id,
                    name,
                    province: province.name
                }));
                allCities.push(...cityWithProvince);
            }
            return allCities;
        });
    }
    static getDestinationbyID(destinationId) {
        return __awaiter(this, void 0, void 0, function* () {
            const axios = require('axios');
            const destination = yield axios.get(`https://emsifa.github.io/api-wilayah-indonesia/api/regency/${destinationId}.json`);
            return destination;
        });
    }
    static getDestinationbyName(name) {
        return __awaiter(this, void 0, void 0, function* () {
            const destination = yield this.checkDestinationName(name);
            return (0, destination_model_1.toDestinationResponseList)(destination);
        });
    }
    static checkDestinationID(destination_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const destination = yield database_1.prismaClient.destination.findUnique({
                where: {
                    id: destination_id
                },
            });
            if (!destination) {
                throw new response_error_1.ResponseError(400, "Destination not found!");
            }
            return destination;
        });
    }
    static checkDestinationName(name) {
        return __awaiter(this, void 0, void 0, function* () {
            const destination = yield database_1.prismaClient.destination.findMany({
                where: {
                    name: name
                }
            });
            if (!destination) {
                throw new response_error_1.ResponseError(400, "Destination not found!");
            }
            return destination;
        });
    }
}
exports.DestinationService = DestinationService;
