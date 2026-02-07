/* ============================================================
   PAGE INIT
============================================================ */
document.addEventListener("DOMContentLoaded", () => {

  loadCovers();

  const riskList = JSON.parse(sessionStorage.getItem("riskDto") || "[]");
  const coverList = JSON.parse(sessionStorage.getItem("coverDto") || "[]");

  populateRiskDetails(riskList);
  populateCoverDetails(coverList);
});

/* ============================================================
   HOME
============================================================ */
document.getElementById("btnHome")?.addEventListener("click", () => {
  window.location.href = "../home.html";
});

/* ============================================================
   LOAD COVERS
============================================================ */
async function loadCovers() {
  try {
    const res = await fetch("http://localhost:8092/proposal/covers");
    if (!res.ok) throw new Error();

    const covers = await res.json();
    const dropdown = document.getElementById("cName");

    dropdown.innerHTML = `<option value="">-- Select Cover --</option>`;

    covers.forEach(c => {
      const opt = document.createElement("option");
      opt.value = c.coverCode;
      opt.textContent = c.coverName;
      dropdown.appendChild(opt);
    });

  } catch {
    alert("❌ Unable to load covers");
  }
}

/* ============================================================
   RISK MODAL
============================================================ */
const riskModal = document.getElementById("riskModal");

document.getElementById("btnAddRisk").onclick = () => {
  riskModal.style.display = "block";
};

document.getElementById("closeRisk").onclick = () => {
  riskModal.style.display = "none";
};

document.getElementById("saveRisk").onclick = () => {

  const name = document.getElementById("rName").value.trim();
  const age = Number(document.getElementById("rAge").value);
  const gender = document.getElementById("rGender").value;
  const relation = document.getElementById("rRelation").value;
  const occupation = document.getElementById("rOccupation").value;

  if (!name || age <= 0 || !gender) {
    alert("Please enter valid risk details");
    return;
  }

  const row = document.querySelector("#riskTable tbody").insertRow();
  row.innerHTML = `
    <td>${name}</td>
    <td>${age}</td>
    <td>${gender}</td>
    <td>${relation || "-"}</td>
    <td>${occupation || "-"}</td>
    <td><button onclick="deleteRow(this)">🗑️</button></td>
  `;

  riskModal.style.display = "none";

  document.getElementById("rName").value = "";
  document.getElementById("rAge").value = "";
  document.getElementById("rGender").value = "";
  document.getElementById("rRelation").value = "";
  document.getElementById("rOccupation").value = "";
};

/* ============================================================
   COVER MODAL
============================================================ */
const coverModal = document.getElementById("coverModal");

document.getElementById("btnAddCover").onclick = () => {
  coverModal.style.display = "block";
};

document.getElementById("closeCover").onclick = () => {
  coverModal.style.display = "none";
};

document.getElementById("cName").addEventListener("change", () => {
  document.getElementById("cCode").value =
    document.getElementById("cName").value;
  calculatePremium();
});

document.getElementById("cSumInsured")
  .addEventListener("input", calculatePremium);

function calculatePremium() {
  const si = Number(document.getElementById("cSumInsured").value);
  document.getElementById("cPremium").value =
    si > 0 ? (si * 0.10).toFixed(2) : "";
}

document.getElementById("saveCover").onclick = () => {

  const code = document.getElementById("cCode").value;
  const name = document.getElementById("cName").selectedOptions[0]?.text;
  const si = Number(document.getElementById("cSumInsured").value);
  const premium = Number(document.getElementById("cPremium").value);

  if (!code || si <= 0) {
    alert("Invalid cover details");
    return;
  }

  const existingCodes = [...document.querySelectorAll("#coverTable tbody tr")]
    .filter(r => r.cells.length >= 4)
    .map(r => r.cells[0].innerText);

  if (existingCodes.includes(code)) {
    alert("Cover already added");
    return;
  }

  const row = document.querySelector("#coverTable tbody").insertRow();
  row.innerHTML = `
    <td>${code}</td>
    <td>${name}</td>
    <td>${si}</td>
    <td>${premium.toFixed(2)}</td>
    <td><button onclick="deleteRow(this)">🗑️</button></td>
  `;

  coverModal.style.display = "none";

  document.getElementById("cCode").value = "";
  document.getElementById("cSumInsured").value = "";
  document.getElementById("cPremium").value = "";
  document.getElementById("cName").value = "";
};

/* ============================================================
   VIEW PREMIUM (FIXED)
============================================================ */
document.getElementById("btnViewPremium").onclick = () => {

  const rows = document.querySelectorAll("#coverTable tbody tr");

  let totalSI = 0;
  let netPremium = 0;

  rows.forEach(r => {
    if (r.cells.length < 4) return; // ✅ IMPORTANT FIX

    totalSI += Number(r.cells[2].innerText);
    netPremium += Number(r.cells[3].innerText);
  });

  if (netPremium === 0) {
    alert("❌ Please add at least one cover");
    return;
  }

  const gst = +(netPremium * 0.18).toFixed(2);
  const totalPremium = +(netPremium + gst).toFixed(2);

  alert(
    `💰 PREMIUM SUMMARY\n\n` +
    `Total Sum Insured : ₹${totalSI}\n` +
    `Net Premium       : ₹${netPremium.toFixed(2)}\n` +
    `GST (18%)         : ₹${gst}\n` +
    `--------------------------\n` +
    `TOTAL PREMIUM     : ₹${totalPremium}`
  );
};

/* ============================================================
   SAVE PROPOSAL (FIXED)
============================================================ */
document.getElementById("btnSaveProposal").onclick = async () => {

  const mobileNumber = sessionStorage.getItem("customerMobile");
  if (!mobileNumber) {
    alert("Customer mobile missing");
    return;
  }

  const proposalData = {
    mobileNumber,
    departmentCode: sessionStorage.getItem("departmentCode"),
    productCode: sessionStorage.getItem("productCode"),
    policyStartDate: sessionStorage.getItem("policyStartDate"),
    policyEndDate: sessionStorage.getItem("policyEndDate"),
    policyTenure: Number(sessionStorage.getItem("policyTenure")),
    remarks: sessionStorage.getItem("remarks"),
    risks: [],
    covers: []
  };

  document.querySelectorAll("#riskTable tbody tr").forEach(r => {
    if (r.cells.length < 5) return;

    proposalData.risks.push({
      insuredName: r.cells[0].innerText,
      age: Number(r.cells[1].innerText),
      gender: r.cells[2].innerText,
      relation: r.cells[3].innerText,
      occupation: r.cells[4].innerText
    });
  });

  let totalSI = 0;
  let netPremium = 0;

  document.querySelectorAll("#coverTable tbody tr").forEach(r => {
    if (r.cells.length < 4) return; // ✅ IMPORTANT FIX

    const si = Number(r.cells[2].innerText);
    const pr = Number(r.cells[3].innerText);

    totalSI += si;
    netPremium += pr;

    proposalData.covers.push({
      coverCode: r.cells[0].innerText,
      coverName: r.cells[1].innerText,
      sumInsured: si,
      premium: pr
    });
  });

  if (proposalData.covers.length === 0) {
    alert("❌ Please add at least one cover");
    return;
  }

  proposalData.totalSumInsured = totalSI;
  proposalData.netPremium = +netPremium.toFixed(2);
  proposalData.gst = +(netPremium * 0.18).toFixed(2);
  proposalData.totalPremium =
    +(proposalData.netPremium + proposalData.gst).toFixed(2);

  try {
    const res = await fetch("http://localhost:8092/proposal/save", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(proposalData)
    });

    const data = await res.json(); // ✅ read response first

    if (!res.ok) {
      throw new Error(data.statusMsg || "Save failed");
    }

    const proposalNumber = data.statusMsg.split(" ").pop();

    navigator.clipboard.writeText(proposalNumber);


    alert(`✅ Proposal Saved Successfully\nProposal No : ${proposalNumber}`);
    window.location.href = "../buyPolicy.html";

  } catch (err) {
    alert("❌ " + err.message); // ⭐ shows REAL backend error
  }
};

/* ============================================================
   DELETE ROW
============================================================ */
function deleteRow(btn) {
  btn.closest("tr").remove();
}

/* ============================================================
   POPULATE RISK DETAILS
============================================================ */
function populateRiskDetails(riskList) {
  const tbody = document.querySelector("#riskTable tbody");
  tbody.innerHTML = "";

  if (!riskList.length) {
    tbody.innerHTML =
      `<tr><td colspan="6" style="text-align:center;">
        No Risk Details Found</td></tr>`;
    return;
  }

  riskList.forEach(risk => {
    const row = tbody.insertRow();
    row.innerHTML = `
      <td>${risk.insuredName}</td>
      <td>${risk.age}</td>
      <td>${risk.gender}</td>
      <td>${risk.relation || "-"}</td>
      <td>${risk.occupation || "-"}</td>
      <td><button onclick="deleteRow(this)">🗑️</button></td>
    `;
  });
}

/* ============================================================
   POPULATE COVER DETAILS
============================================================ */
function populateCoverDetails(coverList) {
  const tbody = document.querySelector("#coverTable tbody");
  tbody.innerHTML = "";

  if (!coverList.length) {
    tbody.innerHTML =
      `<tr><td colspan="5" style="text-align:center;">
        No Cover Details Found</td></tr>`;
    return;
  }

  coverList.forEach(c => {
    const row = tbody.insertRow();
    row.innerHTML = `
      <td>${c.coverCode}</td>
      <td>${c.coverName}</td>
      <td>${c.sumInsured}</td>
      <td>${c.premium}</td>
      <td><button onclick="deleteRow(this)">🗑️</button></td>
    `;
  });
}
