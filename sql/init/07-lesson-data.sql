TRUNCATE TABLE public.lesson RESTART IDENTITY CASCADE;
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

	(1, 4, 'Variables. Estructura básica.', '<p><p>
En Java, una <b>variable</b> es un espacio en la memoria en el que guardamos un dato.
Para usar una variable, primero <b>debemos indicar qué tipo de dato va a almacenar</b>,
darle un <b>nombre</b> y asignarle un <b>valor</b>.
</p>

<h2>Estructura básica de una variable</h2>
<ul>
  <li>
    <code style="color:#ff9900">tipo</code> <code>nombre = </code><code style="color:#4a86e8;">valor</code>;
  </li>
</ul>

<em>Por ejemplo:</em>
<br>

<ul>
  <li>
    <code style="color:#ff9900">int</code> <code>edad = </code><code style="color:#4a86e8;">30</code>;
  </li>
</ul>

<p>Esto le indica al programa:</p>
<ul>
  <li>
    que vas a guardar un número entero
    <code style="color:#ff9900">(int)</code>.
  </li>
  <li>
    en una variable llamada <b>edad</b>.
  </li>
  <li>
    y de forma opcional podemos añadir su valor inicial, que en este caso será
    <code style="color:#4a86e8;">30</code>.
  </li>
</ul>
'),

	(1, 5, 'Variables. Tipos primitivos.', '<h2>Tipos primitivos</h2>
<p>
    Los <b>tipos primitivos</b> en Java son los datos más básicos del lenguaje y tienen un tamaño y rango de valores bien definidos. Se almacenan directamente en la memoria y <b>no disponen de métodos asociados</b>, a diferencia de otros tipos más avanzados.
</p>

<table>
    <tr>
        <th>TIPO</th>
        <th>DESCRIPCIÓN</th>
        <th>EJEMPLO</th>
    </tr>
    <tr>
        <td><code style="color:#ff9900">int</code></td>
        <td>Representa números enteros, es decir, valores que no tienen decimales.</td>
        <td>
            <code style="color:#ff9900">int</code> <code>edad = </code><code style="color:#4a86e8;">30</code>;
        </td>
    </tr>
    <tr>
        <td><code style="color:#ff9900">float</code></td>
        <td>Representa números decimales con precisión simple. Al inicializar una variable de este tipo debes añadir una <b>f</b> al final del número.</td>
        <td>
            <code style="color:#ff9900">float</code> <code>altura = </code><code style="color:#4a86e8;">1.70f</code>;
        </td>
    </tr>
    <tr>
        <td><code style="color:#ff9900">double</code></td>
        <td>Representa números decimales con doble precisión.</td>
        <td>
            <code style="color:#ff9900">double</code> <code>altura = </code><code style="color:#4a86e8;">1.756</code>;
        </td>
    </tr>
    <tr>
        <td><code style="color:#ff9900">char</code></td>
        <td>Representa un único carácter Unicode. Se escribe entre comillas simples.</td>
        <td>
            <code style="color:#ff9900">char</code> <code>letra = </code><code style="color:#2F9E44;">''A''</code>;
        </td>
    </tr>
    <tr>
        <td><code style="color:#ff9900">boolean</code></td>
        <td>Representa un valor lógico: <em>true</em> o <em>false</em>.</td>
        <td>
            <code style="color:#ff9900">boolean</code> <code>activo = </code><code style="color:#4a86e8;">true</code>;
        </td>
    </tr>
</table>
<br>
<p>
    Estos tipos primitivos son fundamentales para trabajar con datos en Java. Cada uno tiene su propio rango y características
'),

	(1, 6, 'Variables. Tipos no primitivos.', '<p>Los <b>tipos no primitivos</b> en Java son diferentes de los tipos básicos (como
		<code style="color:#ff9900">int</code>,
		<code style="color:#ff9900">double</code>,
		o <code style="color:#ff9900">char</code>).
		Estos tipos no están integrados directamente en el lenguaje, sino que se construyen a partir de estructuras más complejas que ofrece Java.</p>
	<ul>
		<li>
			Los tipos no primitivos pueden almacenar más información que un valor simple. Por ejemplo, pueden guardar varios datos a la vez y realizar operaciones con ellos.
		</li>
		<li>
			Pueden tener <b>propiedades</b> (también llamadas atributos o campos), que son datos que describen alguna característica de ese tipo.
			Por ejemplo, un texto (
			<code style="color:#ff9900">String</code>
			) es un tipo no primitivo que puede guardar una frase, palabras, o cualquier secuencia de caracteres.
		</li>
		<li>
			También pueden tener <b>métodos</b>, que son acciones u operaciones que puedes realizar con ese tipo de dato.
			Por ejemplo, con una cadena de texto (
			<code style="color:#ff9900">String</code>
			), puedes contar cuántos caracteres tiene o convertirla a mayúsculas.
		</li>
	</ul>
	<p>
		Lo más importante por ahora es saber que el tipo no primitivo más común al empezar a programar en Java es el
		<code style="color:#ff9900">String</code> (texto), que se utiliza para trabajar con palabras y frases.
	</p>
	<p>
		En resumen, los <b>tipos no primitivos</b> nos permiten trabajar con datos más complejos y realizar más operaciones con ellos que con los tipos básicos.
		Más adelante, aprenderemos exactamente cómo funcionan y qué significa todo esto en detalle.
	</p>
'),
	   (1, 7, 'Constantes y últimos ejemplos', '<h2>Constantes</h2>
<p>
En ocasiones, necesitamos almacenar valores que no deben cambiar durante la ejecución del programa. Para ello, Java permite definir constantes utilizando la palabra clave
<code style="color:#ff9900">final</code>. Una vez asignado, el valor de una constante no puede ser modificado.
</p>

<p><b>Ejemplo</b></p>
<code style="color:#ff9900">final</code> <code style="color:#ff9900">double</code> <code>PI = </code><code style="color:#4a86e8;">3.14</code>;

<p>
Aquí, <code>PI</code> es una constante. <b>Por convención, los identificadores de constantes se escriben en mayúsculas y, si incluyen varias palabras, se separan con guiones bajos</b> (<code>MAX_USUARIOS</code>, <code>PI</code>, etc.) para diferenciarlas fácilmente.
</p>

<h3>¿Para qué se emplean?</h3>
<p>
Las constantes se usan para valores fijos, como el valor de <code>PI</code>, límites de un rango, configuraciones que no deben cambiar, mensajes de error, etc. Esto mejora la legibilidad y reduce el riesgo de errores, ya que el valor de una constante no puede modificarse accidentalmente.
</p>

<h3>Buenas prácticas: nombrar variables</h3>
<p>
En Java, los nombres de las variables deben ser claros y representativos de lo que almacenan. Así, el código es más fácil de entender y mantener.
</p>

<h4>Convención lowerCamelCase</h4>
<p>
Java utiliza la convención <b>lowerCamelCase</b> para nombrar variables, que consiste en:
</p>
<ul>
    <li>Empezar el nombre con letra minúscula.</li>
    <li>
        Si el nombre tiene varias palabras, cada palabra adicional comienza con mayúscula
        (sin espacios ni guiones).
    </li>
</ul>

<p><b>Ejemplos</b></p>
<code style="color:#ff9900">String</code> <code>nombreCompleto = </code><code style="color:#2F9E44;">"Alonso Pérez Pérez"</code>;
<br>
<code style="color:#ff9900">boolean</code> <code>estaActivo;</code> <span style="color: #888">(su valor por defecto es false)</span>

<p>
Esta forma de nombrar es una norma no obligatoria, pero muy recomendada porque facilita la lectura y el mantenimiento del código.
</p>'),

	   (1, 8, 'Operadores', '<p>
Los operadores son símbolos que permiten hacer operaciones matemáticas, comparar valores o combinar condiciones. Java permite realizar operaciones matemáticas, lógicas y concatenaciones de cadenas, lo que es fundamental en cualquier aplicación que realice cálculos.
</p>

<h2>Operadores comunes:</h2>
<ul>
  <li><b>Aritméticos:</b> <code style="color:#de3548;">+</code>, <code style="color:#de3548;">-</code>, <code style="color:#de3548;">*</code>, <code style="color:#de3548;">/</code>, <code style="color:#de3548;">%</code> (resto de división).</li>
  <li><b>Comparación:</b> <code style="color:#de3548;">==</code>, <code style="color:#de3548;">!=</code>, <code style="color:#de3548;">&lt;</code>, <code style="color:#de3548;">&gt;</code>, <code style="color:#de3548;">&lt;=</code>, <code style="color:#de3548;">&gt;=</code>.</li>
  <li><b>Lógicos:</b> <code style="color:#de3548;">&&</code> (y), <code style="color:#de3548;">||</code> (o), <code style="color:#de3548;">!</code> (no).</li>
</ul>

<h2>Operadores Aritméticos:</h2>
<ul>
  <li><b>Suma (<code style="color:#de3548;">+</code>):</b><br>
    Se utiliza para agregar dos números.<br>
    Ejemplo: Si <code style="color:#ff9900;">a</code> = <code style="color:#4a86e8;">10</code> y <code style="color:#ff9900;">b</code> = <code style="color:#4a86e8;">5</code>, entonces <code style="color:#de3548;">a + b</code> es <code style="color:#4a86e8;">15</code>.<br>
    Uso en la vida real: Sumar cantidades en una factura o acumular puntajes.
  </li>

  <li><b>Resta (<code style="color:#de3548;">-</code>):</b><br>
    Resta el valor de una variable de otra.<br>
    Ejemplo: <code style="color:#4a86e8;">10 - 5</code> da <code style="color:#4a86e8;">5</code>.
  </li>

  <li><b>Multiplicación (<code style="color:#de3548;">*</code>):</b><br>
    Multiplica dos números.<br>
    Ejemplo: <code style="color:#4a86e8;">10 * 5</code> da <code style="color:#4a86e8;">50</code>.
  </li>

  <li><b>División (<code style="color:#de3548;">/</code>):</b><br>
    Divide un número entre otro. Es importante distinguir la división entera de la división real (decimal).<br>
    Ejemplo: <code style="color:#4a86e8;">10 / 3</code> en enteros da <code style="color:#4a86e8;">3</code> (se descarta la parte decimal); mientras que <code style="color:#4a86e8;">10.0 / 3.0</code> da aproximadamente <code style="color:#4a86e8;">3.33</code>.
  </li>

  <li><b>Módulo (<code style="color:#de3548;">%</code>):</b><br>
    Calcula el resto de una división.<br>
    Ejemplo: <code style="color:#4a86e8;">10 % 3</code> es <code style="color:#4a86e8;">1</code>, ya que 10 dividido entre 3 da 3 de cociente y 1 de resto.
  </li>
</ul>

<h2>Operadores de comparación:</h2>
<ul>
  <li><b>Igualdad (<code style="color:#de3548;">==</code>):</b> Compara si dos valores son iguales.<br>
    Ejemplo: <code style="color:#4a86e8;">5 == 5</code> devuelve <code style="color:#4a86e8;">true</code>.
  </li>
  <li><b>Desigualdad (<code style="color:#de3548;">!=</code>):</b> Verifica si dos valores son diferentes.</li>
  <li><b>Mayor/Menor que (<code style="color:#de3548;">&gt;</code>, <code style="color:#de3548;">&lt;</code>, <code style="color:#de3548;">&gt;=</code>, <code style="color:#de3548;">&lt;=</code>):</b> Permiten comparar magnitudes.</li>
</ul>

<h2>Operadores lógicos:</h2>
<ul>
  <li><b>AND (<code style="color:#de3548;">&&</code>):</b> Devuelve <code style="color:#4a86e8;">true</code> solo si ambas condiciones son verdaderas.<br>
    Ejemplo: (<code style="color:#ff9900;">edad</code> &gt; <code style="color:#4a86e8;">18</code>) <code style="color:#de3548;">&&</code> (<code style="color:#ff9900;">edad</code> &lt; <code style="color:#4a86e8;">65</code>) es <code style="color:#4a86e8;">true</code> solo si la edad está en ese rango.
  </li>
  <li><b>OR (<code style="color:#de3548;">||</code>):</b> Devuelve <code style="color:#4a86e8;">true</code> si al menos una de las condiciones es verdadera.</li>
  <li><b>Negación (<code style="color:#de3548;">!</code>):</b> Invierte el valor lógico de una expresión.</li>
</ul>
'),
	    (1, 9, 'Concatenación de cadenas', '<p>
La <b>concatenación</b> es el proceso de unir dos o más cadenas de texto (<code style="color:#ff9900">String</code>). En Java, el operador <code style="color:#de3548;">+</code> se utiliza para este propósito. Esto permite construir mensajes dinámicos y formar textos a partir de datos variables.
</p>

<h2>Ejemplo y explicación</h2>
<code style="color:#ff9900">String</code> <code>saludo = </code><code style="color:#2F9E44;">"Hola"</code>;<br>
<code style="color:#ff9900">String</code> <code>nombre = </code><code style="color:#2F9E44;">"Carlos"</code>;<br>
<code style="color:#ff9900">String</code> <code>mensaje = </code>
<code style="color:#2F9E44;">saludo</code> <code style="color:#de3548;">+ </code><code style="color:#2F9E44;">", "</code> <code style="color:#de3548;">+ </code><code style="color:#2F9E44;">nombre</code> <code style="color:#de3548;">+ </code><code style="color:#2F9E44;">"!"</code>;

<p>
<b>¿Qué ocurre aquí?</b><br>
Se combinan las cadenas para formar el mensaje <code style="color:#2F9E44;">"Hola, Carlos!"</code>.
</p>

<h2>Aplicación práctica</h2>
<p>
La concatenación es muy útil para generar informes, mostrar resultados al usuario o construir mensajes de error. También puedes combinar cadenas con valores numéricos, ya que Java convierte automáticamente los números a texto al usar el operador <code style="color:#de3548;">+</code> con <code style="color:#ff9900">String</code>.
</p>
'),

	   (1, 10, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.'),

	   (1, 11, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.'),

	   (1, NULL, 'Manejo de Excepciones', 'Cómo usar try, catch y finalmente para controlar errores.');




INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (2, 6, 'Colecciones en Java', 'Descripción detallada de List, Set, Map y sus implementaciones.'),
	   (2, 7, 'Programación Funcional', 'Introducción a lambdas y streams en Java 8 y superior.'),
	   (2, 8, 'Concurrency y Multihilo', 'Manejo de threads y sincronización en Java.'),
	   (2, NULL, 'Buenas Prácticas y Patrones', 'Patrones de diseño comunes y mejores prácticas en Java.');

INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (3, 10, 'Introducción a Servlets', 'Conceptos básicos sobre Servlets y ciclo de vida.'),
	   (3, NULL, 'Frameworks Web', 'Visión general de frameworks como Spring y JSF para desarrollo web.');

