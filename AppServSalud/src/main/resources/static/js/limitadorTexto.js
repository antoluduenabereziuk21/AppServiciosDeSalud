document.addEventListener("DOMContentLoaded", function () {
  var cardContainers = document.querySelectorAll(".tarjetas-especialidad");

  cardContainers.forEach(function (container) {
    var textContainer = container.querySelector(".text-container");
    var shortText = textContainer.querySelector(".texto-card-especialidad").textContent;
    var fullText = textContainer.querySelector(".texto-card-especialidad-full").textContent;
    var maxLength = 250; // Define la longitud máxima permitida

    if (shortText.length > maxLength) {
      textContainer.querySelector(".texto-card-especialidad").textContent = shortText.substring(0, maxLength) + "...";
    }

    // Agrega un evento para mostrar el texto completo al pasar el cursor sobre la tarjeta
    container.addEventListener("mouseover", function () {
      textContainer.querySelector(".texto-card-especialidad").textContent = fullText;
    });

    // Agrega un evento para volver a mostrar el texto limitado al quitar el cursor de la tarjeta
    container.addEventListener("mouseout", function () {
      textContainer.querySelector(".texto-card-especialidad").textContent = shortText.substring(0, maxLength) + "...";
    });
  });
});
