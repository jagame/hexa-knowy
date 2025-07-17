INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (1, 2, 'Introducción al curso',
        '¡Te damos la bienvenida al curso de Java básico! En este curso podrás estudiar pequeñas píldoras conceptuales del apasionante lenguaje de programación que es Java. Java es el medio que estructura muchísimas aplicaciones que utilizas en tu día a día, y para ello te pondremos unos cuantos ejemplos:
Java funda la estructura funcional (el backend) de aplicaciones web como Netflix, Amazon, Spotify y Linkedin.
Aunque en muchos casos migraron o están migrando a lenguaje Kotlin (basado también en Java), estructura la lógica de multitud de clientes Android, desde la aplicación de Spotify hasta la aplicación móvil original de Twitter.
Es el lenguaje original del archiconocido juego Minecraft, y el que le dio su característica de ser fácilmente personalizable (mods).

¡Esperamos que este curso despierte tu interés por seguir aprendiendo! A lo largo de estas lecciones breves pero clave, conocerás los fundamentos que hacen de Java un lenguaje tan versátil y duradero. Ya sea que quieras desarrollar aplicaciones web, móviles o incluso videojuegos, dominar los conceptos básicos de Java te dará una base sólida para avanzar con confianza en el mundo de la programación.
Recuerda que nadie nace sabiendo: aprender a programar es un proceso, y equivocarse forma parte del camino. En un mundo cada vez más digital, conocer al menos un lenguaje de programación no solo es una ventaja, sino una habilidad cada vez más necesaria. ¡Así que ánimo, y manos a la obra!
'),

	   (1, 3, '¿Qué es Java y cómo funciona?', '<p>Java es un <b>lenguaje de programación moderno</b>, robusto y orientado a objetos. Fue creado para permitir que un mismo programa pudiera ejecutarse en distintos sistemas operativos sin necesidad de adaptaciones.</p><br>
	<h2>Características clave:</h2>
	<ul>
		<li><b>Multiplataforma:</b> Gracias a la JVM (Java Virtual Machine), un programa Java puede ejecutarse en Windows, Linux o Mac sin cambiar el código.</li>
		<li><b>Orientado a objetos:</b> Todo se estructura en torno a clases y objetos, lo que permite organizar el código de forma modular y reutilizable.</li>
		<li><b>Fuertemente tipado:</b> el lenguaje obliga a declarar con claridad qué tipo de datos se están utilizando, lo que ayuda a evitar errores.</li>
	</ul>

	<h2>¿Cómo funciona Java?</h2>
	<ol type="1">
		<li>Escribes el código en un archivo <div class="code-example">.java.</div></li>
		<li>Lo compilas con el <b>JDK (Java Development Kit)</b>, que lo convierte en <b>bytecode</b> <div class="code-example">(.class).</div></li>
		<li>Ese bytecode es ejecutado por la <b>JVM (Java Virtual Machine)</b>, que lo interpreta según el sistema donde se encuentre.</li>
	</ol>'),
	   (1, 4, 'Variables. Estructura básica.', '<p>En Java, una <b>variable</b> es un espacio en la memoria en el que guardamos un dato. Para usar una variable, primero <b>debemos indicar qué tipo de dato va a almacenar</b>, darle un <b>nombre</b> y asignarle un <b>valor</b>.</p>

<h2>Estructura básica de una variable</h2>
<ul>
<li>
	<div class="code-example">
		<div style="color:orange; display:inline">tipo</div> nombre =
        <div style="color:blue; display:inline">valor</div>;
	</div>
</li>
</ul>


<em>Por ejemplo:</em>
<br>

<ul>
<li>
<div class="code-example">
	<div style="color:orange; display:inline">int</div> edad =
    <div style="color:blue; display:inline">30</div>;
</div>
</li>
</ul>

Esto le indica al programa:
<ul>
<li>que vas a guardar un número entero
	<div class="code-example" style="color:orange; display:inline">
    (int).
    </div>
</li>
<li>en una variable llamada edad.</li>
<li>y de forma opcional podemos añadir su valor inicial, que en este caso será <div class="code-example" style="color:blue; display:inline">30.</div> </li>
</ul>

'),
	   (1, 5, 'Variables. Tipos primitivos.', '<h2>Tipos primitivos</h2>
	<p>Son los tipos de datos más básicos del lenguaje y que preceden a java. Ocupan poca memoria y no tienen comportamientos asociados (que más tarde llamaremos métodos).<p>

	<table>
		<tr>
			<th>TIPO</th>
			<th>DESCRIPCIÓN</th>
			<th>EJEMPLO</th>
		</tr>
		<tr>
			<td><b>int</b></td>
			<td>Representa a los números reales, es decir, aquellos que no tienen decimales.</td>
			<td>int edad = 30;</td>
		</tr>
		<tr>
			<td><b>float</b></td>
			<td>Representa a los números decimales con poca precisión. A la hora de inicializarla es necesario escribir una f al final de su valor.</td>
			<td>float altura = 1.70f;</td>
		</tr>
		<tr>
			<td><b>double</b></td>
			<td>Representa a los números decimales con mayor precisión.</td>
			<td>double altura = 1.756;</td>
		</tr>
		<tr>
			<td><b>char</b></td>
			<td>Representa un solo carácter Unicode. Se escribe entre comillas simples.</td>
			<td>char letra = ‘A’;</td>
		</tr>
		<tr>
			<td><b>boolean</b></td>
			<td>Representa un valor lógico: true o false. Muy usado en condiciones.</td>
			<td>boolean activo = true;</td>
		</tr>
	</table>



	<p>📝 <b>Nota:</b> Existen otros tipos primitivos como byte, short y long, pero se usan
	con menos frecuencia en proyectos básicos.</p>'),
	   (1, 6, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.'),
	   (1, 7, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.'),
	   (1, 8, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.'),
	   (1, 9, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.');


INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (2, 6, 'Colecciones en Java', 'Descripción detallada de List, Set, Map y sus implementaciones.'),
	   (2, 7, 'Programación Funcional', 'Introducción a lambdas y streams en Java 8 y superior.'),
	   (2, 8, 'Concurrency y Multihilo', 'Manejo de threads y sincronización en Java.'),
	   (2, NULL, 'Buenas Prácticas y Patrones', 'Patrones de diseño comunes y mejores prácticas en Java.');

INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (3, 10, 'Introducción a Servlets', 'Conceptos básicos sobre Servlets y ciclo de vida.'),
	   (3, NULL, 'Frameworks Web', 'Visión general de frameworks como Spring y JSF para desarrollo web.');
