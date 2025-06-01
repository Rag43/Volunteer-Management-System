require("dotenv").config();

const express = require("express");
const app = express();
const mongoose = require("mongoose");

mongoose.connect(process.env.CLOUD_DB_URL, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});
const db = mongoose.connection;
db.on("error", (error) => {
  console.error(error);
});
db.once("open", () => console.log("Connected to Database"));

app.use(express.json());

const volunteersRouter = require("./routes/volunteers.js");
app.use("/volunteers", volunteersRouter);

const entriesRouter = require("./routes/entries.js");
app.use("/entries", entriesRouter);

app.listen(3000, () => {
  console.log("Server has started");
});
