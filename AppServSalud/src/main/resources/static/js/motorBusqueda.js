
document.addEventListener("DOMContentLoaded", function() {
    var searchForm = document.getElementById("searchForm");
    var searchInput = document.querySelector("#searchForm input");
    var optionsContainer = document.getElementById("searchOptions");

    // Define los términos de búsqueda y sus respectivas URL de redirección
    var searchTerms = {
        "turnos": "/infoTurnos",
        "especialidades": "/especialidades",
        "obras sociales": "/infoTurnos",
        "profesionales": "/especialidades"
    };

    // Función para mostrar las opciones al usuario
    function showOptions(matchingTerms) {
        // Limpiar las opciones anteriores
        optionsContainer.innerHTML = "";

        if (matchingTerms.length > 0) {
            // Mostrar opciones al usuario
            var optionsList = document.createElement("ul");

            matchingTerms.forEach(function(term) {
                var optionItem = document.createElement("li");
                optionItem.textContent = term;
                optionItem.addEventListener("click", function() {
                    // Redirigir al hacer clic en la opción
                    window.location.href = searchTerms[term];
                });

                optionsList.appendChild(optionItem);
            });

            optionsContainer.appendChild(optionsList);

            // Mostrar el contenedor de opciones
            optionsContainer.style.display = "block";
        } else {
            // Ocultar el contenedor de opciones si no hay coincidencias
            optionsContainer.textContent = "No se encontraron coincidencias";
            optionsContainer.style.display = "none";
        }
    }

    // Evento input para detectar cambios en el campo de búsqueda
    searchInput.addEventListener("input", function() {
        var searchTerm = searchInput.value.toLowerCase();
        var matchingTerms = [];

        // Filtrar los términos de búsqueda que incluyen la cadena escrita
        for (var term in searchTerms) {
            if (term.toLowerCase().includes(searchTerm)) {
                matchingTerms.push(term);
            }
        }

        // Mostrar las opciones al usuario
        showOptions(matchingTerms);
    });

    // Evento submit del formulario (puedes mantener esto si también deseas manejar la búsqueda al enviar el formulario)
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        var searchTerm = searchInput.value.toLowerCase();
        var matchingTerms = [];

        // Filtrar los términos de búsqueda que incluyen la cadena escrita
        for (var term in searchTerms) {
            if (term.toLowerCase().includes(searchTerm)) {
                matchingTerms.push(term);
            }
        }

        // Mostrar las opciones al usuario
        showOptions(matchingTerms);
    });

    // Ocultar el contenedor de opciones al hacer clic en cualquier parte de la página
    document.addEventListener("click", function(event) {
        if (!optionsContainer.contains(event.target)) {
            optionsContainer.style.display = "none";
        }
    });
});

