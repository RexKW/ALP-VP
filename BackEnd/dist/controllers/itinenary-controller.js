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
exports.ItineraryController = void 0;
const itinerary_service_1 = require("../services/itinerary-service");
class ItineraryController {
    static getItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.getItinerary(Number(req.params.todoId));
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getAllItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.getAllItinerary(req.user);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static createNewItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield itinerary_service_1.ItineraryService.createItinerary(request, req.user);
                res.status(201).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static deleteItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield itinerary_service_1.ItineraryService.deleteItinerary(Number(req.params.todoId));
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static updateItinerary(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                request.id = Number(req.params.itineraryId);
                const response = yield itinerary_service_1.ItineraryService.updateItinerary(request);
                res.status(201).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.ItineraryController = ItineraryController;
