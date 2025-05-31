const mongoose = require("mongoose");

const entrySchema = new mongoose.Schema({
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  hours: {
    type: Number,
    required: true,
  },
});

module.exports = mongoose.model("Entry", entrySchema);
