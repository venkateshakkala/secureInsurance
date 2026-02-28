document.addEventListener("DOMContentLoaded", function () {

    const params = new URLSearchParams(window.location.search);
    const mobileNumber = params.get("mobileNumber");

    console.log("Mobile from URL:", mobileNumber);

    if (!mobileNumber) {
        document.getElementById("msg").innerText = "Mobile number missing.";
        return;
    }

    fetch(`http://localhost:8093/payments/search?mobileNumber=${mobileNumber}`)
        .then(res => res.json())
        .then(data => {

            if (!data || data.length === 0) {
                document.getElementById("msg").innerText = "No proposals found.";
                return;
            }

            const tbody = document.getElementById("proposalBody");

            data.forEach(p => {

                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>
                        <span class="proposal-link" onclick="openProposal('${p.proposalNumber}')">
                            ${p.proposalNumber}
                        </span>
                    </td>
                    <td>${p.status || '-'}</td>
                `;

                tbody.appendChild(row);
            });
        })
        .catch(err => {
            console.error(err);
            document.getElementById("msg").innerText = "Error loading proposals.";
        });
});

function openProposal(proposalNumber) {
    window.location.href = `proposal.html?proposalNumber=${proposalNumber}`;
}
