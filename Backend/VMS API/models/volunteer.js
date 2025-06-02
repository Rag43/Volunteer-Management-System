const mongoose = require("mongoose");

const volunteerSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  phone: {
    type: String,
  },
  hours: {
    type: Number,
    default: 0,
  },
});

module.exports = mongoose.model("Volunteer", volunteerSchema);
