const url = 'http://localhost:8080/project-1/';
document.getElementById("emp-body").onload = function() {loadData()};
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

        document.getElementById("emp-table-info-body").appendChild(row);
    }

}

document.getElementById("logout").addEventListener("click", function() {
    let c = confirm("Do you want to logout?");
    if(c==true) {
        location.href="user.html";
    } 
});


var select = document.getElementById("emp-select");
var intSelect = select.value;
var menu = document.getElementById("menu");
var reqForm = document.getElementById("request-form");
var viewRibs = document.getElementById("view-reimbs");
let amountHelper = document.getElementById("amountHelp");

select.addEventListener("change", menuSelect);


function menuSelect() {
    let newSelect = select.value;   
    if(newSelect!=intSelect) {
        if(newSelect=='request') {
            reqForm.hidden = false;
            menu.hidden = true;
            viewRibs.hidden = true;
            amountHelper.innerHTML = "The amount of reimbursment must be a number that greater than 0";
            amountHelper.style.color = "black";               
        } else if(newSelect=='menu') {
            // select.value = "menu";
            reqForm.hidden = true;
            menu.hidden = false;
            viewRibs.hidden = true;
        } else {
            reqForm.hidden = true;
            menu.hidden = true;
            viewRibs.hidden = false;
            getReimbursements();
        }
        intSelect = newSelect;
    }
}



document.getElementById("amount").addEventListener("keyup", ()=> {
    if(document.getElementById("amount").value.length > 0) {
        document.getElementById('submit').disabled = false;
    } else {
        document.getElementById('submit').disabled = true;
    }
});

document.getElementById('submit').addEventListener('click', ersSubmit);


async function ersSubmit() {      
    let c = confirm("Do you want to submit?");
    if (c === true) {
        let ersAmountVal = document.getElementById("amount").value;
        let ersOptValue = document.getElementById("rb-type").value;
        let ersDesVal = document.getElementById("description").value;   
        if (isNaN(ersAmountVal)) {
            amountHelper.innerHTML = 'The enter amount is not a number';
            amountHelper.style.color = 'red';
        } else if (ersAmountVal <= 0) {
            amountHelper.innerHTML = 'The amount must greater than 0.0';
            amountHelper.style.color = 'red';
        } else {
            let rib = {
                amount: ersAmountVal,
                description: ersDesVal,
                author: user,
                status: {
                    statusId: 100,
                    status: ""
                },
                type: {
                    typeId: ersOptValue,
                    type: ""
                }
            };

            select.value = "menu";
            intSelect = "menu";
            reqForm.hidden = true;
            viewRibs.hidden = true;
            menu.innerHTML = "Reimbursement submitting.....";
            menu.style.color = "black";
            menu.hidden = false;

            try {
                let resp = await fetch(url + 'emp/submit', {
                    method: "POST",
                    body: JSON.stringify(rib),
                    credentials: 'include',
                });  
                if (resp.status === 200) {
                    menu.innerHTML="";
                    menu.innerHTML = "Your reimbursement has submitted successfully";
                    menu.style.color = "green";
                } else {
                    let message = await resp.text();
                    menu.innerHTML="";
                    menu.innerHTML =  "Submitted reimbursement failed.\n" + message;
                    nume.style.color = "red";
                }
            } catch (error) {
                menu.innerHTML="";
                menu.innerHTML = error.message + "\nSorry! Can't reach to server. Please login again";
                menu.style.color = "red";
                setTimeout(()=>{
                    let c = confirm("Do you want to logout?");
                    if(c==true) {
                            location.href="user.html";
                    } 
                },2000);
                
            }   
        }
    }          
}
let msg = document.getElementById("view-reimbs-message");

async function getReimbursements() {
    msg.innerHTML = "";  
    try {
        let resp = await fetch(url+"emp/view-all", {credentials: 'include'});
        if (resp.status === 200) {
            
            let data = await resp.json();
            document.getElementById("emp-table-view-reimbs").hidden=false;
            document.getElementById("emp-table-view-reimbs-body").innerHTML="";
            console.log(data);
            for(let rimb of data) {
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

                let resol = document.createElement("td");
                if(rimb.resolved!=null) {
                    let r = new Date(rimb.resolved);
                    resol.innerHTML = r.toDateString();
                }
                
                row.appendChild(resol);

                document.getElementById("emp-table-view-reimbs-body").appendChild(row);
            }
            
        } else {
            let message = await resp.text();
            msg.innerHTML = message;
            msg.style.color = "red";
        }
    } catch (error) {
        msg.innerHTML = error.message + "\nSorry! Can't reach to server. Please login again";
        msg.style.color = "red";
        setTimeout(()=>{
            let c = confirm("Do you want to logout?");
            if(c==true) {
                    location.href="user.html";
            } 
        },2000);       
    } 
     
}