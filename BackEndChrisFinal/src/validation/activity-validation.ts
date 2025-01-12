import { z, ZodType } from "zod";

export class ActivityValidation{
    static readonly CREATE: ZodType = z.object({
        name: z.string().min(1).max(100),
        description:z.string(),
        type:z.string().min(1).max(100),
        start_time: z.date(),
        end_time: z.date(),
        cost: z.number().nonnegative(),
        location_id: z.number().int()
    })

    static readonly UPDATE: ZodType = z.object({
        name: z.string().min(1).max(100),
        day_id: z.number().positive(),
        description:z.string(),
        type:z.string().min(1).max(100),
        start_time: z.date(),
        end_time: z.date(),
        cost: z.number().nonnegative(),
        location_id: z.number().int()
    })
}