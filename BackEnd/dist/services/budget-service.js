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
const library_1 = require("@prisma/client/runtime/library");
class BudgetService {
    static getPlannedBudget(itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const plannedBudget = yield database_1.prismaClient.budget.findMany({
                where: {
                    itinerary_id: itinerary_id
                },
            });
            return (0, budget_model_1.toBudgetResponseList)(plannedBudget);
        });
    }
    static getSpendings(itinenary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const allItineraryDestination = yield database_1.prismaClient.itinerary_Destinations.findMany({
                where: {
                    itinerary_id: itinenary_id
                }
            });
            let totalAccomodation = new library_1.Decimal(0);
            let totalTransport = new library_1.Decimal(0);
            let totalShoppingEntertainment = new library_1.Decimal(0);
            let totalCulinary = new library_1.Decimal(0);
            let totalSightSeeing = new library_1.Decimal(0);
            let totalHealthcare = new library_1.Decimal(0);
            let totalSport = new library_1.Decimal(0);
            const accommodations = yield Promise.all(allItineraryDestination.map((destination) => __awaiter(this, void 0, void 0, function* () {
                if (destination.accomodation_id === null || destination.accomodation_id === undefined) {
                    return null;
                }
                const accomodation = yield database_1.prismaClient.accomodation.findUnique({
                    where: {
                        id: destination.accomodation_id,
                    },
                });
                if (accomodation && accomodation.cost) {
                    totalAccomodation = totalAccomodation.plus(accomodation.cost);
                }
                return accomodation;
            })));
            const rest = yield Promise.all(allItineraryDestination.map((destinations) => __awaiter(this, void 0, void 0, function* () {
                const days = yield database_1.prismaClient.schedule_Per_Day.findMany({
                    where: {
                        itinerary_destination_id: destinations.id
                    }
                });
                days.map((days) => __awaiter(this, void 0, void 0, function* () {
                    const activities = yield database_1.prismaClient.activity.findMany({
                        where: {
                            day_id: days.id
                        }
                    });
                    activities.map((activity) => __awaiter(this, void 0, void 0, function* () {
                        switch (activity.type) {
                            case "Transport":
                                totalTransport = totalTransport.plus(activity.cost);
                                break;
                            case "Shopping/Entertainment":
                                totalShoppingEntertainment = totalShoppingEntertainment.plus(activity.cost);
                                break;
                            case "Sightseeing":
                                totalSightSeeing = totalSightSeeing.plus(activity.cost);
                                break;
                            case "Food":
                                totalCulinary = totalCulinary.plus(activity.cost);
                                break;
                            case "Healthcare":
                                totalHealthcare = totalHealthcare.plus(activity.cost);
                                break;
                            case "Sport":
                                totalSport = totalSport.plus(activity.cost);
                                break;
                        }
                    }));
                }));
            })));
            const data = {
                totalAccomodation,
                totalTransport,
                totalShoppingEntertainment,
                totalCulinary,
                totalSightSeeing,
                totalHealthcare,
                totalSport
            };
            return data;
        });
    }
    static setBudget(req, itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const accommodationBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Accommodation",
                    actual_budget: 0.0,
                    estimated_budget: req.totalAccommodation
                }
            });
            const transportBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Transport",
                    actual_budget: 0.0,
                    estimated_budget: req.totalTransport
                }
            });
            const shoppingEntertainmentBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Shopping/Entertainment",
                    actual_budget: 0.0,
                    estimated_budget: req.totalTransport
                }
            });
            const sightSeeingBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Sightseeing",
                    actual_budget: 0.0,
                    estimated_budget: req.totalSightSeeing
                }
            });
            const foodBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Food",
                    actual_budget: 0.0,
                    estimated_budget: req.totalSightSeeing
                }
            });
            const healthcareBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Healthcare",
                    actual_budget: 0.0,
                    estimated_budget: req.totalSightSeeing
                }
            });
            const sportBudget = yield database_1.prismaClient.budget.create({
                data: {
                    itinerary_id: itinerary_id,
                    type: "Sport",
                    actual_budget: 0.0,
                    estimated_budget: req.totalSightSeeing
                }
            });
            return "Data Created";
        });
    }
    static updateBudget(req, itinerary_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const allBudget = yield database_1.prismaClient.budget.findMany({
                where: {
                    itinerary_id: itinerary_id
                }
            });
            for (const budget of allBudget) {
                switch (budget.type) {
                    case "Transport":
                        const accommodationBudget = yield database_1.prismaClient.budget.update({
                            where: {
                                id: budget.id
                            },
                            data: {
                                itinerary_id: itinerary_id,
                                type: "Accommodation",
                                actual_budget: 0.0,
                                estimated_budget: req.totalAccommodation
                            }
                        });
                        break;
                    case "Shopping/Entertainment":
                        const shoppingEntertainmentBudget = yield database_1.prismaClient.budget.update({
                            where: {
                                id: budget.id
                            },
                            data: {
                                itinerary_id: itinerary_id,
                                type: "Shopping/Entertainment",
                                actual_budget: 0.0,
                                estimated_budget: req.totalShoppingEntertainment
                            }
                        });
                        break;
                    case "Sightseeing":
                        const sightSeeingBudget = yield database_1.prismaClient.budget.update({
                            where: {
                                id: budget.id
                            },
                            data: {
                                itinerary_id: itinerary_id,
                                type: "Sightseeing",
                                actual_budget: 0.0,
                                estimated_budget: req.totalSightSeeing
                            }
                        });
                        break;
                    case "Food":
                        const foodBudget = yield database_1.prismaClient.budget.update({
                            where: {
                                id: budget.id
                            },
                            data: {
                                itinerary_id: itinerary_id,
                                type: "Food",
                                actual_budget: 0.0,
                                estimated_budget: req.totalCulinary
                            }
                        });
                        break;
                    case "Healthcare":
                        const healthcareBudget = yield database_1.prismaClient.budget.update({
                            where: {
                                id: budget.id
                            },
                            data: {
                                itinerary_id: itinerary_id,
                                type: "Healthcare",
                                actual_budget: 0.0,
                                estimated_budget: req.totalHealthcare
                            }
                        });
                        break;
                    case "Sport":
                        const sportBudget = yield database_1.prismaClient.budget.update({
                            where: {
                                id: budget.id
                            },
                            data: {
                                itinerary_id: itinerary_id,
                                type: "Sport",
                                actual_budget: 0.0,
                                estimated_budget: req.totalSport
                            }
                        });
                        break;
                }
            }
        });
    }
}
exports.BudgetService = BudgetService;
