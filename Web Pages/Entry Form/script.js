document
  .getElementById("session-form")
  .addEventListener("submit", async (e) => {
    e.preventDefault(); // Prevent the form from reloading the page

    // Get input values
    const volName = document.getElementById("volunteer-name").value.trim();
    const title = document.getElementById("title").value.trim();
    const description = document.getElementById("description").value.trim();
    const hours = document.getElementById("hours").value.trim();

    // Fetch and save volunteer object from name
    const formattedName = encodeURIComponent(volName);
    const uri = "http://localhost:3000/volunteers?name=" + formattedName;
    let volId = null;
    try {
      const volResponse = await fetch(uri);

      if (!volResponse.ok) {
        alert("Couldn't Find Volunteer.");
        return;
      }

      const volunteer = await volResponse.json();
      volId = volunteer._id;
    } catch (err) {
      console.error("Error submitting form:", err);
      alert("An error occurred.");
    }

    // Create payload
    const data = {
      volunteer: volId,
      title,
      description,
      hours,
    };

    // Format and send POST request for entry
    try {
      const entryResponse = await fetch("http://localhost:3000/entries", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (entryResponse.ok) {
        alert("Successfully Submitted.");
      } else {
        alert("Submission Failed. Please Try Again.");
      }
    } catch (err) {
      // handle here
      console.error("Error submitting form:", err);
      alert("An error occurred.");
    }
  });
