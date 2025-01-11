import { Budget } from "@prisma/client";
import { Decimal } from "@prisma/client/runtime/library";

export interface CreateBudgetRequest {
  itinenary_id: number;
  estimated_budget: number;
  actual_budget: number;
  type: string;
}

export interface BudgetResponse {
  id: number;
  estimated_budget: Decimal;
  actual_budget: Decimal;
  type: string;
}

export function toBudgetResponse(budget: Budget): BudgetResponse {
  return {
    id: budget.id,
    estimated_budget: budget.estimated_budget,
    actual_budget: budget.actual_budget,
    type: budget.type,
  };
}