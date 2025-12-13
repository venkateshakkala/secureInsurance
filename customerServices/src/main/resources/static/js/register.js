setTimeout(() => {
  const form = document.getElementById("registerForm");
  if (!form) {
    console.error("❌ registerForm not found");
    return;
  }

  console.log("✅ register.js loaded and form found");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    console.log("✅ Form submit captured");

    const customer = {
      firstName: document.getElementById("firstName").value,
      lastName: document.getElementById("lastName").value,
      email: document.getElementById("email").value,
      mobileNumber: document.getElementById("mobileNumber").value,
      address: document.getElementById("address").value
    };

    console.log("📦 Customer data:", customer);

    try {
      const response = await fetch("http://localhost:8090/secureInsurance/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(customer)
      });

      // Parse backend response as JSON
      const data = await response.json();
      console.log("✅ Server response:", data);

      if (response.ok) {
        // ✅ Success response (201 Created)
        alert(data.statusMsg || "Customer created successfully!");
        form.reset();
      } else {
        // ⚠️ Error response (409 Conflict, etc.)
        alert(data.errorMessage || "Something went wrong!");
      }

    } catch (error) {
      console.error("❌ Error during save:", error);
      alert("Error saving customer. Check console for details.");
    }
  })
//  function goHome() {
//      window.location.href = "../home.html"; // or your products page path
//  }
;
}, 100);
