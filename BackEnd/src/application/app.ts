
import express from "express";
import { publicRouter } from "../routes/public-router";
import { errorMiddleware } from "../middleware/error-middleware";
import { apiRouter } from "../routes/api";

const app = express()
app.use(express.json())
app.use(publicRouter)
app.use(apiRouter)
app.use(errorMiddleware)

export default app