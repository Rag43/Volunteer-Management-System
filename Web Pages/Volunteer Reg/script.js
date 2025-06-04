document
  .getElementById("volunteer-form")
  .addEventListener("submit", async (e) => {
    e.preventDefault(); // Prevent the form from reloading the page

    // Get input values
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();

    // Create payload
    const data = { name, email, phone };

    // Format and send PUT request to API
    try {
      const response = await fetch("http://localhost:3000/volunteers", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        alert("Successfully Submitted.");
      } else {
        alert("Submission Failed. Please Try Again.");
      }
    } catch (err) {
      // handle here
      console.error("Error submitting form:", error);
      alert("An error occurred.");
    }
  });
