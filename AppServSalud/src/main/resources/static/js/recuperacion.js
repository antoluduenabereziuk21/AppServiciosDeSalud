
const token = new URLSearchParams(window.location.search).get("token");
document.getElementById("enviar").disabled = true;

localStorage.setItem("token", token);

document.getElementById("token").value = localStorage.getItem("token");

function validarPassword() {
    const password = document.getElementById("password").value;
    const password2 = document.getElementById("password2").value;
    
    if (password === password2) {
        document.getElementById("enviar").disabled = false;
    } else {
        document.getElementById("enviar").disabled = true;
    }
}

document.getElementById("password").addEventListener("keyup", validarPassword);
document.getElementById("password2").addEventListener("keyup", validarPassword);