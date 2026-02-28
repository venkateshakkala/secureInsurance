const params = new URLSearchParams(window.location.search);
const proposalNumber = params.get("proposalNumber");

const detailsDiv = document.getElementById("details");
const payBtn = document.getElementById("payBtn");

if(!proposalNumber){
    detailsDiv.innerHTML = "<p class='error'>Proposal number missing</p>";
}
else{

    fetch(`http://localhost:8093/payments/search/${proposalNumber}`)
        .then(res => {
            if(!res.ok){
                throw new Error("Proposal not found");
            }
            return res.json();
        })
        .then(data => {

            detailsDiv.innerHTML = `
                <table>
                    <tr><td>Proposal Number</td><td>${data.proposalNumber}</td></tr>
                    <tr><td>Customer Name</td><td>${data.customerName}</td></tr>
                    <tr><td>Mobile</td><td>${data.mobileNumber}</td></tr>
                    <tr><td>Net Premium</td><td>₹ ${data.netPremium}</td></tr>
                    <tr><td>GST</td><td>₹ ${data.gst}</td></tr>
                    <tr><td>Total Premium</td><td><b>₹ ${data.totalPremium}</b></td></tr>
                    <tr><td>Status</td><td>${data.status}</td></tr>
                </table>
            `;

            // Show pay button only if pending
            if(data.status === "PENDING"){
                payBtn.classList.remove("hidden");

                payBtn.onclick = () => {
                    window.location.href =
                        `payment.html?proposalNumber=${data.proposalNumber}&amount=${data.totalPremium}`;
                };
            }

        })
        .catch(err => {
            detailsDiv.innerHTML = `<p class='error'>${err.message}</p>`;
        });
}
