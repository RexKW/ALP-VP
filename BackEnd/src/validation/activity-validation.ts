import { z, ZodType } from "zod";

export class ActivityValidation{
    static readonly CREATE: ZodType = z.object({
        name: z.string().min(1).max(100),
        description:z.string(),
        type:z.string().min(1).max(20),
        startTime: z.date(),
        endTime: z.date(),
        cost: z.number().nonnegative(),
        location_id: z.number().int()
    })
}