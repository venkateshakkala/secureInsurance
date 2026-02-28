// ✅ Navigation Buttons
document.getElementById("homeBtn").addEventListener("click", () => {
    window.location.href = "../home.html";
});

document.getElementById("quotationBtn").addEventListener("click", () => {
    window.location.href = "proposal.html";
});

document.getElementById("paymentBtn").addEventListener("click", () => {
    window.location.href = "http://localhost:8093/search.html";
});

document.getElementById("generatePolicyBtn").addEventListener("click", () => {
    window.location.href = "../generatePolicy/generatePolicy.html";
});
