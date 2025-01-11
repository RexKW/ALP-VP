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
exports.BudgetService = void 0;
const database_1 = require("../application/database");
const budget_model_1 = require("../model/budget-model");
class BudgetService {
    static getPlannedBudget(itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const allDays = yield database_1.prismaClient.budget.findMany({
                where: {
                    itinerary_id: itinerary_id
                },
            });
            return (0, budget_model_1.toBudgetResponseList)(allDays);
        });
    }
    static getSpendings(itinenary_id) {
        return __awaiter(this, void 0, void 0, function* () {
        });
    }
    static setBudget() {
        return __awaiter(this, void 0, void 0, function* () {
        });
    }
}
exports.BudgetService = BudgetService;
