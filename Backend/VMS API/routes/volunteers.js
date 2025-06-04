const express = require("express");
const router = express.Router();
const Volunteer = require("../models/volunteer");
const volunteer = require("../models/volunteer");

// Getting all
router.get("/", async (req, res) => {
  try {
    // Check for name query
    if (req.query.name) {
      const volunteer = await Volunteer.findOne({ name: req.query.name });
      if (!volunteer) {
        return res.status(404).json({ message: "Volunteer not Found" });
      }
      // Send volunteer based on name
      return res.json(volunteer);
    }
    // Otherwise fetch all volunteers if no name query
    const volunteers = await Volunteer.find();
    res.json(volunteers);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Getting one
router.get("/:id", getVolunteer, (req, res) => {
  res.json(res.volunteer);
});

// Creating one
router.post("/", async (req, res) => {
  const volunteer = new Volunteer({
    name: req.body.name,
    email: req.body.email,
    phone: req.body.phone,
    hours: req.body.hours,
  });
  try {
    const newVolunteer = await volunteer.save();
    res.status(201).json(newVolunteer);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Updating one
router.patch("/:id", getVolunteer, async (req, res) => {
  if (req.body.name != null) {
    res.volunteer.name = req.body.name;
  }
  if (req.body.email != null) {
    res.volunteer.email = req.body.email;
  }
  if (req.body.phone != null) {
    res.volunteer.phone = req.body.phone;
  }
  if (req.body.hours != null) {
    res.volunteer.hours = req.body.hours;
  }
  try {
    const updatedVolunteer = await res.volunteer.save();
    res.json(updatedVolunteer);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Deleting one
router.delete("/:id", getVolunteer, async (req, res) => {
  try {
    await res.volunteer.deleteOne();
    res.json({ message: "Deleted volunteer" });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

async function getVolunteer(req, res, next) {
  let volunteer;
  try {
    volunteer = await Volunteer.findById(req.params.id);
    if (volunteer == null) {
      return res.status(404).json({ message: "Cannot find volunteer" });
    }
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
  res.volunteer = volunteer;
  next();
}

module.exports = router;
