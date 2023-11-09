document.addEventListener("DOMContentLoaded", function() {
  var paragraph = document.getElementById("myParagraph");
  var textContainer = document.querySelector(".text-container");
  var maxLength = 100; // Define la longitud máxima permitida

  if (paragraph.textContent.length > maxLength) {
    paragraph.textContent = paragraph.textContent.substring(0, maxLength) + "...";
  }

  // Agrega un evento para mostrar el texto completo al pasar el cursor sobre la card
  textContainer.addEventListener("mouseover", function() {
    textContainer.style.maxHeight = "100%"; // Establece una altura alta para mostrar todo el texto
  });

  // Agrega un evento para volver a mostrar el texto limitado al quitar el cursor de la card
  textContainer.addEventListener("mouseout", function() {
    textContainer.style.maxHeight = "150px"; // Restablece la altura máxima
  });
});
