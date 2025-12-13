// products.js

setTimeout(() => {
  const buttons = document.querySelectorAll(".product-card button");

  if (buttons.length === 0) {
    console.error("❌ No product buttons found");
    return;
  }

  console.log("✅ products.js loaded");

  buttons.forEach((btn) => {
    btn.addEventListener("click", () => {
      const productName = btn.parentElement.querySelector("h3").textContent;
      alert(`You selected ${productName} plan.`);
      console.log(`🟢 ${productName} button clicked`);
    });
  });

  // ✅ Buy Policy button logic
  const buyPolicyBtn = document.getElementById("buyPolicyBtn");
  if (buyPolicyBtn) {
    buyPolicyBtn.addEventListener("click", () => {
      console.log("🟢 Navigating to Buy Policy page...");
      window.location.href = "../buyPolicy.html"; // ✅ Change path if needed
    });
  } else {
    console.warn("⚠️ Buy Policy button not found on page");
  }

}, 100);
