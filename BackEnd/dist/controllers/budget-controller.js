"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BudgetController = void 0;
const budget_service_1 = require("../services/budget-service");
class BudgetController {
    static actualBudget(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const id = Number(req.params.itineraryId);
                const response = yield budget_service_1.BudgetService.getSpendings(id);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static plannedBudget(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const id = Number(req.params.itineraryId);
                const response = yield budget_service_1.BudgetService.getPlannedBudget(id);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static createBudget(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const id = Number(req.params.itineraryId);
                const response = yield budget_service_1.BudgetService.setBudget(request, id);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static updateBudget(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const id = Number(req.params.itineraryId);
                const response = yield budget_service_1.BudgetService.updateBudget(request, id);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.BudgetController = BudgetController;
