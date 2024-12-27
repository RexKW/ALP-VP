"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.publicRouter = void 0;
const express_1 = __importDefault(require("express"));
const user_controller_1 = require("../controllers/user-controller");
const destination_controller_1 = require("../controllers/destination-controller");
exports.publicRouter = express_1.default.Router();
exports.publicRouter.post("/api/register", user_controller_1.AuthController.register);
exports.publicRouter.post("/api/login", user_controller_1.AuthController.login);
exports.publicRouter.get("/destinations/all", destination_controller_1.DestinationController.getAllDestinations);
