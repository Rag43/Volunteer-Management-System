require("dotenv").config();

const express = require("express");
const app = express();
const mongoose = require("mongoose");
const cors = require("cors");

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

app.use(cors());

const volunteersRouter = require("./routes/volunteers.js");
app.use("/volunteers", volunteersRouter);

const entriesRouter = require("./routes/entries.js");
app.use("/entries", entriesRouter);

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
  console.log("Server has started on port ${PORT}");
});
