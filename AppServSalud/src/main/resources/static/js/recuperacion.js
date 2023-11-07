
const token = new URLSearchParams(window.location.search).get("token");

localStorage.setItem("token", token);

const form = document.getElementById("form");

/**
 * funcion para validar password y password2 que sean iguales y que no esten vacios
 * admeas de que la longitud sea mayor a 8 y habilite el boton enviar cuando todo
 * este bien
 */

function validarPassword() {
    const password = document.getElementById("password").value;
    const password2 = document.getElementById("password2").value;
    
    if (password === password2 && password.length > 8) {
        document.getElementById("enviar").disabled = false;
    } else {
        document.getElementById("enviar").disabled = true;
    }
}

document.getElementById("password").addEventListener("keyup", validarPassword);
document.getElementById("password2").addEventListener("keyup", validarPassword);

/**
 * funcion para enviar el formulario de recuperacion de password * 
 * se envia el formulario con el token y el password
 * si todo sale bien se redirige al login
 * si no se muestra un mensaje de error
    */

async function enviar(e) {
    e.preventDefault();

    const password = document.getElementById("password").value;
    const password2 = document.getElementById("password2").value;

    const data = {
        token,
        password,
        password2
    };

    try {
        const response = await fetch("/recuperacion", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (response.status === 200) {
            localStorage.clear();
            window.location.href = "/login";
        } else {
            const { error } = await response.json();
            alert(error);
        }
    } catch (error) {
        console.log(error);
    }
}

form.addEventListener("submit", enviar);