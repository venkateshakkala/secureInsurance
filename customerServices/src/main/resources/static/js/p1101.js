/* ============================================================
   PAGE INIT
============================================================ */
document.addEventListener("DOMContentLoaded", () => {
  loadCovers();
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
  const age = document.getElementById("rAge").value;
  const gender = document.getElementById("rGender").value;
  const relation = document.getElementById("rRelation").value;
  const occupation = document.getElementById("rOccupation").value;

  if (!name || !age || !gender) {
    alert("Please fill mandatory fields");
    return;
  }

  const row = document.querySelector("#riskTable tbody").insertRow();
  row.innerHTML = `
    <td>${name}</td>
    <td>${age}</td>
    <td>${gender}</td>
    <td>${relation}</td>
    <td>${occupation}</td>
    <td><button onclick="deleteRow(this)">🗑️</button></td>
  `;

  riskModal.style.display = "none";
  document.getElementById("rName").value = "";
  document.getElementById("rAge").value = "";
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

document.getElementById("cSumInsured").addEventListener("input", calculatePremium);

function calculatePremium() {
  const si = Number(document.getElementById("cSumInsured").value);
  document.getElementById("cPremium").value =
    si > 0 ? (si * 0.10).toFixed(2) : "";
}

document.getElementById("saveCover").onclick = () => {

  const code = document.getElementById("cCode").value;
  const name = document.getElementById("cName").selectedOptions[0]?.text;
  const si = document.getElementById("cSumInsured").value;
  const premium = document.getElementById("cPremium").value;

  if (!code || !si) {
    alert("Cover details missing");
    return;
  }

  const row = document.querySelector("#coverTable tbody").insertRow();
  row.innerHTML = `
    <td>${code}</td>
    <td>${name}</td>
    <td>${si}</td>
    <td>${premium}</td>
    <td><button onclick="deleteRow(this)">🗑️</button></td>
  `;

  coverModal.style.display = "none";
  document.getElementById("cCode").value = "";
  document.getElementById("cSumInsured").value = "";
  document.getElementById("cPremium").value = "";
  document.getElementById("cName").value = "";
};

/* ============================================================
   VIEW PREMIUM  ✅ WORKING
============================================================ */
document.getElementById("btnViewPremium").onclick = () => {

  const rows = document.querySelectorAll("#coverTable tbody tr");
  if (rows.length === 0) {
    alert("❌ Please add at least one cover");
    return;
  }

  let totalSI = 0;
  let netPremium = 0;

  rows.forEach(r => {
    totalSI += Number(r.cells[2].innerText);
    netPremium += Number(r.cells[3].innerText);
  });

  const gst = netPremium * 0.18;
  const totalPremium = netPremium + gst;

  alert(
    `💰 PREMIUM SUMMARY\n\n` +
    `Total Sum Insured : ₹${totalSI}\n` +
    `Net Premium       : ₹${netPremium.toFixed(2)}\n` +
    `GST (18%)         : ₹${gst.toFixed(2)}\n` +
    `--------------------------\n` +
    `TOTAL PREMIUM     : ₹${totalPremium.toFixed(2)}`
  );
};

/* ============================================================
   SAVE PROPOSAL
============================================================ */
document.getElementById("btnSaveProposal").onclick = async () => {

  const mobileNumber = sessionStorage.getItem("customerMobile");
  if (!mobileNumber) {
    alert("Customer mobile missing");
    return;
  }

  const proposalData = {
    mobileNumber: mobileNumber,
    departmentCode: sessionStorage.getItem("departmentCode"),
    productCode: sessionStorage.getItem("productCode"),
    policyStartDate: sessionStorage.getItem("policyStartDate"),
    policyEndDate: sessionStorage.getItem("policyEndDate"),
    policyTenure: parseInt(sessionStorage.getItem("policyTenure")),
    remarks: sessionStorage.getItem("remarks"),
    risks: [],
    covers: []
  };

  document.querySelectorAll("#riskTable tbody tr").forEach(r => {
    proposalData.risks.push({
      insuredName: r.cells[0].innerText,
      age: r.cells[1].innerText,
      gender: r.cells[2].innerText,
      relation: r.cells[3].innerText,
      occupation: r.cells[4].innerText
    });
  });

  let totalSI = 0;
  let netPremium = 0;

  document.querySelectorAll("#coverTable tbody tr").forEach(r => {
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

  proposalData.totalSumInsured = totalSI;
  proposalData.netPremium = netPremium;
  proposalData.gst = Math.round(netPremium * 0.18);
  proposalData.totalPremium = proposalData.netPremium + proposalData.gst;

  const res = await fetch("http://localhost:8092/proposal/save", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(proposalData)
  });

  if (res.ok) {
    alert("✅ Proposal Saved Successfully");
    window.location.href = "../buyPolicy.html";
  } else {
    alert("❌ Save failed");
  }
};

/* ============================================================
   DELETE ROW
============================================================ */
function deleteRow(btn) {
  btn.closest("tr").remove();
}
