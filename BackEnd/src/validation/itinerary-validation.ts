import { z, ZodType } from "zod";

export class ItineraryValidation{
    static readonly CREATE: ZodType = z.object({
        name: z.string().min(1).max(100),
        user_id: z.number().positive()
    })

    static readonly UPDATE: ZodType = z.object({
        id: z.number().positive(),
        name: z.string().min(1).max(100),
    })
}