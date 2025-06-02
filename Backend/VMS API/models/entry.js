const mongoose = require("mongoose");

const entrySchema = new mongoose.Schema({
  volunteer: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Volunteer",
    required: true,
  },
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
  approved: {
    type: String,
    default: "pending",
  },
});

module.exports = mongoose.model("Entry", entrySchema);
