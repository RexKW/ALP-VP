import { z, ZodType } from "zod";

export class ActivityValidation{
    static readonly CREATE: ZodType = z.object({
        name: z.string().min(1).max(100),
        description:z.string(),
        type:z.string().min(1).max(20),
        start_time: z.date(),
        end_time: z.date(),
        cost: z.number().nonnegative(),
        location_id: z.number().int()
    })

    static readonly UPDATE: ZodType = z.object({
        id: z.number().positive(),
        name: z.string().min(1).max(100),
        description:z.string(),
        type:z.string().min(1).max(20),
        startTime: z.date(),
        endTime: z.date(),
        cost: z.number().nonnegative(),
        location_id: z.number().int()
    })
}