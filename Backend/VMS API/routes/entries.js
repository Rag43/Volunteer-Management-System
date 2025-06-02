const express = require("express");
const router = express.Router();
const Entry = require("../models/entry");

// Getting all
router.get("/", async (req, res) => {
  try {
    const entries = await Entry.find();
    res.json(entries);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Getting one
router.get("/:id", getEntry, (req, res) => {
  res.json(res.entry);
});

// Creating one
router.post("/", async (req, res) => {
  const entry = new Entry({
    volunteer: req.body.volunteer,
    title: req.body.title,
    description: req.body.description,
    hours: req.body.hours,
    approved: req.body.approved,
  });
  try {
    const newEntry = await entry.save();
    res.status(201).json(newEntry);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Updating one
router.patch("/:id", getEntry, async (req, res) => {
  if (req.body.volunteer != null) {
    res.entry.volunteer = req.body.volunteer;
  }
  if (req.body.title != null) {
    res.entry.title = req.body.title;
  }
  if (req.body.description != null) {
    res.entry.description = req.body.description;
  }
  if (req.body.hours != null) {
    res.entry.hours = req.body.hours;
  }
  if (req.body.approved != null) {
    res.entry.approved = req.body.approved;
  }
  try {
    const updatedEntry = await res.entry.save();
    res.json(updatedEntry);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Deleting one
router.delete("/:id", getEntry, async (req, res) => {
  try {
    await res.entry.deleteOne();
    res.json({ message: "Deleted entry" });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

async function getEntry(req, res, next) {
  let entry;
  try {
    entry = await Entry.findById(req.params.id);
    if (entry == null) {
      return res.status(404).json({ message: "Cannot find entry" });
    }
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
  res.entry = entry;
  next();
}

module.exports = router;
