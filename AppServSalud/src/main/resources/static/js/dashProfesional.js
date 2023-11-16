function showFragment(fragmentName) {
    const fragments = ['principalFragment', 'pacientesFragment', 'detallePacienteFragment'];
    fragments.forEach(fragmentId => {
        const fragment = document.getElementById(fragmentId);
        if (fragmentId === `${fragmentName}Fragment`) {
            fragment.classList.remove("d-none");
        } else {
            if (!fragment.classList.contains("d-none")) {

                fragment.classList.add("d-none");
            }
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const detallePacienteFragment = document.querySelector('#detallePacienteFragment');
    var detalleBtns = document.querySelectorAll('.detalle-btn');
    detalleBtns.forEach(function (btn) {
        btn.addEventListener('click', function () {
            // var pacienteId = btn.dataset.id;
            var detallesUrl = btn.dataset.url;

            fetch(detallesUrl)
                .then(response => response.json())
                .then(data => {
                    let paciente = data.paciente;
                    // manejar los datos del paciente para mostrarlos en la vista del fragment
                    console.log("datos", data);
                    document.getElementById('pacienteNombre').innerText = paciente.nombre;
                    document.getElementById('pacienteDni').innerText = paciente.dni;


                })
                .catch(error => console.error('Error:', error));
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    $("#fotoPerfil").change(function () {
        previsualizarImagen(this);
    });

    function previsualizarImagen(input) {
        var archivo = input.files[0];

        if (archivo) {
            var lector = new FileReader();

            lector.onload = function (e) {
                $("#imagenPrevia").attr("src", e.target.result);
                $("#imagenPrevia").removeClass("d-none");
                $("#imagenPerfilFormulario").addClass("d-none");
            };

            lector.readAsDataURL(archivo);
        }
    }
});
