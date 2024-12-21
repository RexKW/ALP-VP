
import express from "express";
import { publicRouter } from "../routes/public-router";
import { errorMiddleware } from "../middlware/error-middleware";

const app = express()
app.use(express.json())
app.use(publicRouter)
app.use(errorMiddleware)

export default app