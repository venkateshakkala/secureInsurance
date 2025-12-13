// 🏠 Go back to Proposal Page
function goBack() {
  window.location.href = "../proposal/proposal.html";
}

// ✅ Submit Illness Health Risk
async function submitRisk1102() {
  const data = {
    insuredName: document.getElementById("insuredName").value.trim(),
    illnessType: document.getElementById("illnessType").value.trim(),
    treatmentStatus: document.getElementById("treatmentStatus").value,
    hospitalization: document.getElementById("hospitalization").value
  };

  // ✅ Basic Validation
  if (!data.insuredName || !data.illnessType || !data.treatmentStatus || !data.hospitalization) {
    alert("⚠️ Please fill all required fields.");
    return;
  }

  console.log("Illness Health Risk Data:", data);

  // 🔹 In real scenario:
  // await fetch("http://localhost:8090/secureInsurance/saveIllnessRisk", {
  //   method: "POST",
  //   headers: { "Content-Type": "application/json" },
  //   body: JSON.stringify(data)
  // });

  alert("✅ Illness Health Risk submitted successfully!");
  window.location.href = "../proposal/quotation.html";
}
