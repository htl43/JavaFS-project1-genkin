const url = 'http://18.191.207.214:8090/project-1/';
document.getElementById("man-body").onload = function() {loadData()};
var user;

function loadData() {
    let data = localStorage.getItem("userData");
    user = JSON.parse(data);

    if(user) {
        let row = document.createElement("tr");

        let id = document.createElement("td");
        id.innerHTML = user.userId;
        row.appendChild(id);

        let firstname = document.createElement("td");
        firstname.innerHTML = user.firstname;
        row.appendChild(firstname);

        let lastname = document.createElement("td");
        lastname.innerHTML = user.lastname;
        row.appendChild(lastname);

        let email = document.createElement("td");
        email.innerHTML = user.email;
        row.appendChild(email);

        let role = document.createElement("td");
        role.innerHTML = user.role.userRole;
        row.appendChild(role);

        document.getElementById("man-table-info-body").appendChild(row);
    }

}

document.getElementById("logout").addEventListener("click", function() {
    let c = confirm("Do you want to logout?");
    if(c==true) {
        location.href="user.html";
    } 
});

var select = document.getElementById("man-select");
var menu = document.getElementById("menu");
var viewRibs = document.getElementById("view-reimbs");

var intSelect = select.value;

select.addEventListener("change", menuSelect);
function menuSelect() {
    let newSelect = select.value;   
    if(newSelect!=intSelect) {
        if(newSelect=='menu') {
            menu.hidden = false;
            viewRibs.hidden = true;          
        } else {
            menu.hidden = true;
            viewRibs.hidden = false;
            getAllRibs(newSelect, viewAllRibs);
            
        } 
        intSelect = newSelect;
    }
}

let msg = document.getElementById("view-reimbs-message");
let viewTableRibs = document.getElementById("man-table-view-reimbs-body");

function viewAllRibs(dataSelect) { 
    console.log(dataSelect);
    if(dataSelect.length) {
        for(let rimb of dataSelect) {
            let row = document.createElement("tr");

            let id = document.createElement("td");
            id.innerHTML = rimb.reimbId;
            row.appendChild(id);

            let submit = document.createElement("td");
            let s = new Date(rimb.submitted);
            submit.innerHTML = s.toDateString();
            row.appendChild(submit);

            let amount = document.createElement("td");
            amount.innerHTML = "$" + rimb.amount;
            row.appendChild(amount);

            let authorId = document.createElement("td");
            authorId.innerHTML = rimb.author.userId;
            row.appendChild(authorId);

            let type = document.createElement("td");
            type.innerHTML = rimb.type.type;
            row.appendChild(type);

            let des = document.createElement("td");
            des.innerHTML = rimb.description;
            row.appendChild(des);

            let status = document.createElement("td");
            status.innerHTML = rimb.status.status;
            if(rimb.status.statusId==101) {
                status.setAttribute('class', 'bg-success text-white');
            }
            else if(rimb.status.statusId==102){
                status.setAttribute('class', 'bg-danger text-white');
            } else {
                status.setAttribute('class', 'bg-light text-dark');
            }
            row.appendChild(status);
            
            
            let resolverId = document.createElement("td");
            if(rimb.resolver!=null) {
                resolverId.innerHTML = rimb.resolver.userId;
            }  
            row.appendChild(resolverId);

            let resol = document.createElement("td");
            if(rimb.resolved!=null) {
                let r = new Date(rimb.resolved);
                resol.innerHTML = r.toDateString();
            }
            
            row.appendChild(resol);
        
            viewTableRibs.appendChild(row);
        }       
    } else {
        console.log("data is null");
        msg.innerHTML = "No Reibursement Found";
        msg.style.color = "blue";
        document.getElementById("man-table-view-reimbs").hidden = true;
    }
}

async function getAllRibs(newSelect, displayRibs) {
    msg.innerHTML = "";
    let pc = document.getElementById('pending-change');
    let reimbIdSelect = document.getElementById('reimbId-select'); 
    try {
        let resp = await fetch(url+"man/view-all", {credentials: 'include'});
        if (resp.status === 200) {
                viewTableRibs.hidden = false;
                let data = await resp.json();
                document.getElementById("man-table-view-reimbs").hidden=false;
                document.getElementById("man-table-view-reimbs-body").innerHTML="";
                let dataSelect = [];
                if(newSelect=="viewAll") {
                    pc.hidden = true;
                    dataSelect = data;
                } else if (newSelect=="viewPast") {
                    pc.hidden = true;
                    for(let rimb of data) {
                        if(rimb.status.statusId!=100) {
                            dataSelect.push(rimb);
                        }
                    }
                } else {
                    pc.hidden = false;
                    reimbIdSelect.innerHTML = "";
                    for(let rimb of data) {
                        if(rimb.status.statusId==100) {
                            let reimbIdOpt = document.createElement('option');
                            reimbIdOpt.value = rimb.reimbId;
                            reimbIdOpt.innerHTML =  rimb.reimbId;
                            reimbIdSelect.appendChild(reimbIdOpt);
                            dataSelect.push(rimb);
                        }
                    }
                }
                displayRibs(dataSelect);
                
        } else {
            viewTableRibs.hidden = true;
            let message = await resp.text();
            msg.innerHTML = message;
            msg.style.color = "red";           
        }
    } catch (error) {
        viewTableRibs.hidden =  true;
        msg.innerHTML = error.message + "<br/>Sorry! Can't reach to server. Please try to login again";
        msg.style.color = "red";
        setTimeout(()=>{
            let c = confirm("Do you want to logout?");
            if(c==true) {
                    location.href="user.html";
            } 
        },2000); 
    }
}
let ribId = document.getElementById('reimbId-select');
let ribStatus = document.getElementById('reimbStatus-select');
let submitButton = document.getElementById('reimbs-change-submit');

ribStatus.addEventListener("change", ()=> {
    submitButton.disabled = false;
    if(ribStatus.value==101) {
        ribStatus.className = 'bg-success text-white';
    } else if (ribStatus.value==102) {
        ribStatus.className = 'bg-danger text-white';
    } else {
        ribStatus.className = 'bg-light text-dark';
        submitButton.disabled = true;
    }
});


submitButton.addEventListener('click', submitRibStatusChange);

async function submitRibStatusChange() {   
    if(ribStatus.value!=100) {
        msg.innerHTML="";
        let rib = {
            reimbId : ribId.value,
            status : {
                statusId: ribStatus.value
            },
            resolver : {
                userId : user.userId
            }
        }
        try {
            let resp = await fetch(url + 'man/change-status', {
                method: "POST",
                body: JSON.stringify(rib),
                credentials: 'include',
            });  
            if (resp.status === 200) {               
                getAllRibs(intSelect, viewAllRibs);
                msg.innerHTML = "Updated Reimbursement Status successfully!";
                msg.style.color = "green";
            } else {
                let message = await resp.text();
                msg.innerHTML =  "Submitted reimbursement failed.\n" + message;
                msg.style.color = "red";
            }
        } catch (error) {   
            msg.innerHTML = error.message + "\nSorry! Can't reach to server. Please try to login again";
            msg.style.color = "red";
            setTimeout(()=>{
                let c = confirm("Do you want to logout?");
                if(c==true) {
                        location.href="user.html";
                } 
            },2000);
            
        }
    }
    
}