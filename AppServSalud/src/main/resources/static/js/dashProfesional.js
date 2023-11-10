function showFragment(fragmentName) {
    const fragments = ['principalFragment', 'pacientesFragment', 'turnosFragment', 'misDatosFragment' , 'detallePacienteFragment'];
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
                    console.log("datos",data);
                    document.getElementById('pacienteNombre').innerText = paciente.nombre;
                    document.getElementById('pacienteDni').innerText = paciente.dni;


                })
                .catch(error => console.error('Error:', error));
        });
    });
});
