# App Servicios De Salud
Proyecto Web Final de Egg 2023 Full Stack Java

### DESCRIPCIÓN DEL PROYECTO
Somos un colectivo de profesionales de la salud auto-organizados que deseamos
tener nuestra propia plataforma de provisión de servicios para pacientes.
Deseamos administrarla nosotros con un delegado para ese rol, pero que cada
profesional que se quiera sumar lo pueda hacer. Y que nos deje: su especialidad, su
disponibilidad horaria, que los pacientes puedan agendar un turno con el/la
profesional.

### PROBLEMA
Dificultad para conseguir turnos, pérdida de la historia clínica del paciente.

### SOLUCIÓN
Los usuarios profesionales deben poder acceder a su perfil y crear fichas de cada
paciente que ha tenido una consulta con ellos, en la que puedan volcar notas de la
visita (fecha, prepaga, etc).
Los pacientes deben poder acceder y buscar profesionales por especialidad, agendar
un turno y dejar asentado sus datos (de contacto, de obra social y de intención de
consulta). Creemos que vamos a empezar con las especialidades más clásicas
(pediatría, ginecología, clínica y cardiología) y ver cómo nos va. Sería muy bueno si
podemos ver la reputación de cada profesional.

### CASOS DE USO
- Cada profesional de la salud registrará su especialización, horarios disponibles
y valor de la consulta. Al ver a un paciente registrará esa actualización en una
historia clínica (La misma será estándar para todos los profesionales con
Proyecto Final
Integrador
campos obligatorios). Los pacientes no tienen acceso a su historia clínica pero
el profesional sí puede ver la historia clínica del paciente que haya reservado
un turno.
- Cada paciente podrá buscar profesionales de acuerdo a su especialización y
ordenarlos por precio de consulta y calificación. Una vez seleccionado podrá
reservar un turno.
- El administrador otorgará roles.

#

## Diseño de la Api

- ### Estructura:
  Diseñada con el patrón MVC y sus correspondientes mapeos de verbos HTTP para obtener, modificar o eliminar información.

- #### Seguridad:
  Se implementan mecanismos de autenticación y autorización para proteger la API y restringir el acceso no autorizado.
  Mediante Spring security y el acceso por usuario y contraseña, con control de sesion.

#

## Estructura del proyecto

- com.GrupoD.AppServSalud
    - configuraciones
    - controladores
    - dominio
      - entidades
      - repositorios
      - services
    - excepciones
    - utils
        - filterclass
    - **main class**

#

## Colaboradores

-  [Lucas Ivan Molina](https://github.com/LucasIvan)
-  [Mauricio Maldonado](https://github.com/J0k3r-rg)
-  [Antonio Ludueña Biereziuk](https://github.com/antoluduenabereziuk21)
-  [Ayelen Griselda Ruiz Cuenca](https://github.com/ayeeruuiz)
-  [Ramiro Lopez](https://github.com/razier31)
-  [Maria Bean Penna](https://github.com/Macebe)
-  [Araceli Rojas](https://github.com/Aru972021)
-  [Leandro Suarez]()

#

### Tecnologias y Herramientas:

- Java 8
- Spring boot 2.7.17
- Maven
- MySQL
- Thymeleaf
- Netbeans
- VScode
