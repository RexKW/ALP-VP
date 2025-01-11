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
exports.DestinationController = void 0;
const auth_service_1 = require("../services/auth-service");
const destination_service_1 = require("../services/destination-service");
class DestinationController {
    static getAllDestinations(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield destination_service_1.DestinationService.getAllDestination();
                res.status(200).json({
                    data: JSON.parse(JSON.stringify(response))
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getDestination(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                console.log(req.params);
                const destinationId = Number(req.params.destinationId);
                const response = yield destination_service_1.DestinationService.getDestinationDBbyID(destinationId);
                res.status(200).json({
                    data: JSON.parse(JSON.stringify(response))
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static deleteItinenerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield auth_service_1.UserService.login(request);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static updateItinenerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield auth_service_1.UserService.login(request);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.DestinationController = DestinationController;
