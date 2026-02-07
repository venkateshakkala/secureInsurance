/* ============================================================
   PAGE INIT
============================================================ */
document.addEventListener("DOMContentLoaded", () => {

  loadDepartments();

  document.getElementById("btnHome")
    ?.addEventListener("click", () => {
      window.location.href = "../home.html";
    });

  document.getElementById("btnFetchCustomer")
    ?.addEventListener("click", fetchCustomer);

  document.getElementById("btnFetchProposal")
    ?.addEventListener("click", fetchProposal);

  document.getElementById("btnNext")
    ?.addEventListener("click", goToRiskPage);
});


/* ============================================================
   FETCH CUSTOMER
============================================================ */
async function fetchCustomer() {

  const mobileNumber = document
    .getElementById("mobileNumber")
    .value
    .trim();

  if (!mobileNumber) {
    alert("Please enter mobile number");
    return;
  }

  try {
    const res = await fetch(
      `http://localhost:8090/secureInsurance/fetch?mobileNumber=${encodeURIComponent(mobileNumber)}`
    );

    if (!res.ok) {
      alert("Customer not found");
      return;
    }

    const customer = await res.json();

    // Show customer section
    document.getElementById("customerDataSection").style.display = "block";
    document.getElementById("customerId").innerText = customer.customerId;
    document.getElementById("customerName").innerText =
      `${customer.firstName} ${customer.lastName}`;
    document.getElementById("customerEmail").innerText =
      customer.email || "-";
    document.getElementById("customerAddress").innerText =
      customer.address || "-";

    // ✅ CRITICAL: store for backend usage
    sessionStorage.setItem("customerId", customer.customerId);
    sessionStorage.setItem("customerMobile", mobileNumber);

  } catch (err) {
    console.error(err);
    alert("Error while fetching customer");
  }
}


async function fetchProposal() {

  const proposalNumber = document
    .getElementById("proposalNumber")
    .value
    .trim();

  if (!proposalNumber) {
    alert("Enter proposal number");
    return;
  }

  try {
    const res = await fetch(
      `http://localhost:8092/proposal/fetch/${proposalNumber}`
    );

    if (!res.ok) {
      alert("Proposal not found");
      return;
    }

    const proposal = await res.json();

    populateProposal(proposal);

    // ✅ STORE FOR p1101.js
    sessionStorage.setItem(
      "riskDto",
      JSON.stringify(proposal.riskDto || [])
    );

    sessionStorage.setItem(
      "coverDto",
      JSON.stringify(proposal.coverDto || [])
    );

  } catch (err) {
    console.error(err);
    alert("Error while fetching proposal");
  }
}



function populateProposal(response) {

  const proposal = response.proposalDto;
  const customer = response.customerDto;
  const department = response.departmentDto;
  const product = response.productDto;

  document.getElementById("customerDataSection").style.display = "block";

  // ---- CUSTOMER ----
  document.getElementById("customerId").innerText = customer.customerId;
  document.getElementById("customerName").innerText =
    `${customer.firstName} ${customer.lastName}`;

  document.getElementById("customerEmail").innerText = customer.email;
  document.getElementById("customerAddress").innerText = customer.address;
  document.getElementById("mobileNumber").value = customer.mobileNumber;

  // ---- POLICY ----
  document.getElementById("startDate").value = proposal.policyStartDate;
  document.getElementById("endDate").value = proposal.policyEndDate;
  document.getElementById("tenure").value = proposal.policyTenure;
  // ---- DEPARTMENT & PRODUCT ----
  document.getElementById("department").value = department.departmentCode;

  loadProducts(department.departmentCode, () => {
    document.getElementById("product").value = product.productCode;
  });

  document.getElementById("remarks").value = proposal.remarks || "";

  // ---- SESSION STORAGE ----
  sessionStorage.setItem("customerId", customer.customerId);
  sessionStorage.setItem("customerMobile", customer.mobileNumber);

}



/* ============================================================
   LOAD DEPARTMENTS
============================================================ */
async function loadDepartments() {

  try {
    const res = await fetch(
      "http://localhost:8090/secureInsurance/department"
    );
    const departments = await res.json();

    const deptDropdown = document.getElementById("department");
    deptDropdown.innerHTML =
      `<option value="">-- Select Department --</option>`;

    departments.forEach(d => {
      const opt = document.createElement("option");
      opt.value = d.departmentCode;
      opt.textContent = d.departmentName;
      deptDropdown.appendChild(opt);
    });

    deptDropdown.addEventListener("change", () => {
      loadProducts(deptDropdown.value);
    });

  } catch (err) {
    console.error(err);
    alert("Unable to load departments");
  }
}


/* ============================================================
   LOAD PRODUCTS
============================================================ */
async function loadProducts(departmentCode, callback) {

  const productDropdown = document.getElementById("product");
  productDropdown.innerHTML =
    `<option value="">-- Select Product --</option>`;

  if (!departmentCode) return;

  try {
    const res = await fetch(
      `http://localhost:8090/secureInsurance/product/${departmentCode}`
    );
    const products = await res.json();

    products.forEach(p => {
      const opt = document.createElement("option");
      opt.value = p.productCode;
      opt.textContent = p.productName;
      productDropdown.appendChild(opt);
    });

    if (callback) callback();

  } catch (err) {
    console.error(err);
    alert("Unable to load products");
  }
}


/* ============================================================
   UPDATE END DATE
============================================================ */
function updateEndDate() {

  const startDate =
    document.getElementById("startDate").value;
  const tenure =
    parseInt(document.getElementById("tenure").value);

  if (!startDate || !tenure) return;

  const d = new Date(startDate);
  d.setFullYear(d.getFullYear() + tenure);

  document.getElementById("endDate").value =
    d.toISOString().split("T")[0];
}


/* ============================================================
   NEXT → RISK PAGE
============================================================ */
function goToRiskPage() {

  const customerId = sessionStorage.getItem("customerId");
  const mobileNumber = sessionStorage.getItem("customerMobile");

  if (!customerId || !mobileNumber) {
    alert("Please fetch customer first");
    return;
  }

  const departmentCode =
    document.getElementById("department").value;
  const productCode =
    document.getElementById("product").value;

  if (!departmentCode || !productCode) {
    alert("Select department & product");
    return;
  }

  const departmentName =
    document.getElementById("department").selectedOptions[0].text;
  const productName =
    document.getElementById("product").selectedOptions[0].text;

  // ✅ Store everything needed for next pages & backend
  sessionStorage.setItem("customerMobile", mobileNumber);
  sessionStorage.setItem("departmentCode", departmentCode);
  sessionStorage.setItem("departmentName", departmentName);
  sessionStorage.setItem("productCode", productCode);
  sessionStorage.setItem("productName", productName);
  sessionStorage.setItem(
    "policyStartDate",
    document.getElementById("startDate").value
  );
  sessionStorage.setItem(
    "policyEndDate",
    document.getElementById("endDate").value
  );
  sessionStorage.setItem(
    "policyTenure",
    document.getElementById("tenure").value
  );
  sessionStorage.setItem(
    "remarks",
    document.getElementById("remarks").value
  );

  // Navigate to product-specific risk page
  window.location.href = `p${productCode}.html`;
}
