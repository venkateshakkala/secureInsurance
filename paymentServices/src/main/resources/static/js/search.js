function search(){

    const proposal = document.getElementById("proposalNumber").value.trim();
    const mobile = document.getElementById("mobileNumber").value.trim();

    if(proposal){
        // go to proposal details page
        window.location.href = `proposal.html?proposalNumber=${proposal}`;
    }
    else if(mobile){
        // go to proposal list page
        window.location.href = `proposal-list.html?mobileNumber=${mobile}`;
    }
    else{
        alert("Enter Proposal Number OR Mobile Number");
    }
}
