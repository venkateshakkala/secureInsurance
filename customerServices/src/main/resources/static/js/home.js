document.addEventListener("DOMContentLoaded", () => {
  const btnHome = document.getElementById("btnHome");
  const btnRegister = document.getElementById("btnRegister");
  const btnProducts = document.getElementById("btnProducts");
  const downSection = document.querySelector(".down");

  // 🏠 Home Button → Reload full home page
  btnHome.addEventListener("click", () => {
    window.location.href = "../home.html";
  });

  // 🧾 Register Button → Load registration page
  btnRegister.addEventListener("click", () => {
    loadPage("/secureInsurance/register", "../js/register.js", "registration form");
  });

  // 🛍️ Products Button → Load products page
  btnProducts.addEventListener("click", () => {
    loadPage("/secureInsurance/products", "../js/products.js", "products page");
  });

  // 🔧 Common function to fetch HTML and attach JS
  function loadPage(url, scriptPath, label) {
    fetch(url)
      .then(res => {
        if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
        return res.text();
      })
      .then(html => {
        downSection.innerHTML = html;

        // Load JS dynamically
        const script = document.createElement("script");
        script.src = scriptPath;
        script.defer = true;
        document.body.appendChild(script);

        console.log(`✅ Loaded ${label} successfully`);
      })
      .catch(err => {
        downSection.innerHTML = `<p style='color:red;'>Failed to load ${label}.</p>`;
        console.error(`❌ Error loading ${label}:`, err);
      });
  }
});
