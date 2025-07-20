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
	   (1, 7, 'Constantes y buenas prácticas para el manejo de variables', '<h2>Constantes</h2>
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
<h2>Tabla de verdad del operador AND (<code style="color:#de3548;">&&</code>)</h2>
<table>
  <tr>
    <th>A</th>
    <th>B</th>
    <th>A <code style="color:#de3548;">&&</code> B</th>
  </tr>
  <tr>
    <td>true</td>
    <td>true</td>
    <td>true</td>
  </tr>
  <tr>
    <td>true</td>
    <td>false</td>
    <td>false</td>
  </tr>
  <tr>
    <td>false</td>
    <td>true</td>
    <td>false</td>
  </tr>
  <tr>
    <td>false</td>
    <td>false</td>
    <td>false</td>
  </tr>
</table>

<h2>Tabla de verdad del operador OR (<code style="color:#de3548;">||</code>)</h2>
<table>
  <tr>
    <th>A</th>
    <th>B</th>
    <th>A <code style="color:#de3548;">||</code> B</th>
  </tr>
  <tr>
    <td>true</td>
    <td>true</td>
    <td>true</td>
  </tr>
  <tr>
    <td>true</td>
    <td>false</td>
    <td>true</td>
  </tr>
  <tr>
    <td>false</td>
    <td>true</td>
    <td>true</td>
  </tr>
  <tr>
    <td>false</td>
    <td>false</td>
    <td>false</td>
  </tr>
</table>

<h2>Tabla de verdad del operador XOR (<code style="color:#de3548;">^</code>)</h2>
<table>
  <tr>
    <th>A</th>
    <th>B</th>
    <th>A <code style="color:#de3548;">^</code> B</th>
  </tr>
  <tr>
    <td>true</td>
    <td>true</td>
    <td>false</td>
  </tr>
  <tr>
    <td>true</td>
    <td>false</td>
    <td>true</td>
  </tr>
  <tr>
    <td>false</td>
    <td>true</td>
    <td>true</td>
  </tr>
  <tr>
    <td>false</td>
    <td>false</td>
    <td>false</td>
  </tr>
</table>

<h2>Tabla de verdad del operador NOT (<code style="color:#de3548;">!</code>)</h2>
<table>
  <tr>
    <th>A</th>
    <th><code style="color:#de3548;">!A</code></th>
  </tr>
  <tr>
    <td>true</td>
    <td>false</td>
  </tr>
  <tr>
    <td>false</td>
    <td>true</td>
  </tr>
</table>
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

	   (1, 10, 'Conversion de tipos en Java (Casting)', '<p>
En Java, el <b>casting</b> o conversión de tipos permite transformar una variable de un tipo a otro. Existen dos grandes categorías de conversión:
<b>widening</b> (ampliación) y <b>narrowing</b> (reducción o estrechamiento).
</p>

<h2>Widening Casting (Conversión automática o ampliación)</h2>
<p>
El <b>widening casting</b> es una conversión automática que realiza Java cuando pasamos de un tipo de dato más pequeño a uno más grande (por ejemplo, de <code style="color:#ff9900">int</code> a <code style="color:#ff9900">double</code>). En este caso no hay riesgo de pérdida de datos y no se requiere ninguna sintaxis especial.
</p>

<p><b>Ejemplo:</b></p>
<code style="color:#ff9900">int</code> <code>myInt = </code><code style="color:#4a86e8;">9</code>;<br>
<code style="color:#ff9900">double</code> <code>myDouble = </code><code style="color:#ff9900">myInt</code>;<span style="color:#888;"> &nbsp;// Conversión automática: int a double</span><br>
<code>System.out.println(</code><code style="color:#ff9900">myInt</code><code>);</code><span style="color:#888;"> &nbsp;// Imprime 9</span><br>
<code>System.out.println(</code><code style="color:#ff9900">myDouble</code><code>);</code><span style="color:#888;"> &nbsp;// Imprime 9.0</span>

<h2>Narrowing Casting (Conversión explícita o reducción)</h2>
<p>
El <b>narrowing casting</b> es una conversión explícita. Es necesaria cuando convertimos un tipo de dato más grande a uno más pequeño (por ejemplo, de <code style="color:#ff9900">double</code> a <code style="color:#ff9900">int</code>) y <b>puede perderse información</b>. Debe indicarse de forma explícita anteponiendo entre paréntesis el tipo destino.
</p>

<p><b>Ejemplo:</b></p>
<code style="color:#ff9900">double</code> <code>myDouble = </code><code style="color:#4a86e8;">9.78</code>;<br>
<code style="color:#ff9900">int</code> <code>myInt = (int) </code><code style="color:#ff9900">myDouble</code>;<span style="color:#888;"> &nbsp;// Conversión explícita de double a int</span><br>
<code>System.out.println(</code><code style="color:#ff9900">myInt</code><code>);</code><span style="color:#888;"> &nbsp;// Imprime 9</span>

<h2>Resumen gráfico</h2>
<table>
  <tr>
    <th>De</th>
    <th>A</th>
    <th>¿Requiere casting?</th>
    <th>¿Automática?</th>
    <th>¿Puede perder información?</th>
    <th>Ejemplo</th>
  </tr>
  <tr>
    <td><code style="color:#ff9900">int</code></td>
    <td><code style="color:#ff9900">double</code></td>
    <td>No</td>
    <td>Sí (Widening)</td>
    <td>No</td>
    <td>
      <code style="color:#ff9900">int</code> <code>i = </code><code style="color:#4a86e8;">7</code>;<br>
      <code style="color:#ff9900">double</code> <code>d = i;</code>
    </td>
  </tr>
  <tr>
    <td><code style="color:#ff9900">double</code></td>
    <td><code style="color:#ff9900">int</code></td>
    <td>Sí</td>
    <td>No (Narrowing)</td>
    <td>Sí</td>
    <td>
      <code style="color:#ff9900">double</code> <code>d = </code><code style="color:#4a86e8;">7.9</code>;<br>
      <code style="color:#ff9900">int</code> <code>i = (int) d;</code>
    </td>
  </tr>
</table>

<h2>Consideraciones importantes</h2>
<ul>
  <li>
    El <b>widening casting</b> es seguro y automático.
  </li>
  <li>
    El <b>narrowing casting</b> debe indicarse de forma explícita con paréntesis, y puede provocar pérdida de precisión o desbordamientos.
  </li>
  <li>
    El casting también puede aplicarse a objetos relacionados por herencia (upcasting y downcasting), siendo automático el ascendente y explícito el descendente.
  </li>
</ul>

<p>
Usa las conversiones con cuidado, especialmente al reducir el tipo de dato, para evitar errores inesperados o pérdida de información.
</p>
'),

	   (1, NULL, 'Clases y métodos. Introducción a la programación orientada a objetos.', '<p>
Antes de adentrarte en la programación orientada a objetos, es fundamental comprender qué es una <b>clase</b> y para qué sirven los <b>métodos</b> en Java. Estos conceptos te permitirán organizar el código y reutilizarlo fácilmente.
</p>

<h2>¿Qué es una clase?</h2>
<p>
Una <b>clase</b> es como una plantilla o molde que sirve para definir las características y acciones (métodos) comunes de un tipo de dato personalizado.
En una clase puedes declarar:
<ul>
  <li><b>Atributos</b>: Son las variables que almacenan los datos de cada objeto que pertenece a la clase.</li>
  <li><b>Métodos</b>: Son funciones que representan las acciones que esos objetos pueden realizar.</li>
  <li><b>Constructores</b>: Son métodos especiales que se usan para crear nuevos objetos a partir de la clase.</li>
</ul>
</p>

<h2>Estructura básica de una clase en Java</h2>
<pre>
<code style="color:#ff9900">public class</code> <code>NombreDeLaClase</code> {
    <span style="color: #888">// Atributos</span>
    <code style="color:#ff9900">tipo</code> <code>nombreAtributo;</code>
    <span style="color: #888">// Constructor</span>
    <code style="color:#ff9900">public NombreDeLaClase()</code> {
        <span style="color: #888">// Código para iniciar los atributos</span>
    }
    <span style="color: #888">// Métodos</span>
    <code style="color:#ff9900">tipo</code> <code>nombreMetodo()</code> {
        <span style="color: #888">// Acciones del método</span>
    }
}
</code>
</pre>

<h2>Ejemplo sencillo: clase Persona</h2>
<pre>
<code style="color:#ff9900">public class</code> <code>Persona</code> {
    <code style="color:#ff9900">private String</code> <code>nombre;</code>
    <code style="color:#ff9900">private int</code> <code>edad;</code>
    <code style="color:#ff9900">public Persona</code><code>(String nombre, int edad)</code> {
        <code>this.nombre = nombre;</code>
        <code>this.edad = edad;</code>
    }
    <code style="color:#ff9900">public void</code> <code>saludar()</code> {
        <code>System.out.println("Hola, soy "
            + nombre + " y tengo " + edad + " años.");</code>
    }
}
</pre>

<p>
En este ejemplo:
<ul>
  <li><b>Persona</b> es la clase.</li>
  <li><b>nombre</b> y <b>edad</b> son atributos.</li>
  <li>El constructor <code style="color:#ff9900">Persona(String nombre, int edad)</code> se usa para crear objetos y asignar valores iniciales a los atributos.</li>
  <li>El método <code style="color:#ff9900">saludar()</code> muestra un mensaje con los datos de la persona.</li>
</ul>
</p>

<h2>¿Cómo se utiliza una clase?</h2>
<p>
Para poder usar la clase, primero necesitas crear un "objeto" a partir de ella y luego puedes llamar a sus métodos. Ejemplo:
</p>
<pre>
<code style="color:#ff9900">Persona</code> <code>amigo = new Persona("Carlos", 25);</code>
<code>amigo.saludar();</code> <span style="color: #888">// Imprime: Hola, soy Carlos y tengo 25 años.</span>
</pre>

<h2>¿Qué es un método?</h2>
<p>
Un <b>método</b> es una función que pertenece a una clase y define una acción que puedes realizar con los objetos de esa clase. Los métodos pueden tener parámetros y pueden devolver un resultado.
</p>
<pre>
<code style="color:#ff9900">public void</code> <code>saludar()</code> {
    <code>System.out.println("¡Hola!");</code>
}
<code style="color:#ff9900">public int</code> <code>obtenerEdad()</code> {
    <code>return edad;</code>
}
</pre>

<h2>Resumen</h2>
<ul>
  <li>Una clase es una plantilla para crear objetos que tendrán los mismos atributos y métodos.</li>
  <li>Un objeto es una instancia de una clase.</li>
  <li>Los métodos permiten a los objetos realizar acciones o devolver valores.</li>
  <li>Conocer la estructura de una clase es fundamental para empezar con la programación en Java.</li>
</ul>

<p>
Una vez sientas confort con estos conceptos, podrás aprender cómo aprovechar las clases y objetos para organizar mejor tus programas y acercarte a la programación orientada a objetos de forma sencilla.
</p>
');






INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (2, 2, 'Conceptos clave de POO en Java', '<h2>Conceptos básicos de POO en Java</h2>
<p>
Java es un lenguaje basado en la <b>programación orientada a objetos</b> (POO). ¿Por qué importa? Porque te permite organizar y reutilizar el código de una forma lógica y robusta, utilizando clases y objetos que representan elementos del mundo real. Los cuatro <b>pilares</b> básicos de la POO son:
</p>
<ul>
  <li><b>Encapsulación:</b> Oculta los detalles internos de una clase y solo expone una interfaz pública controlada. <br>
    <span style="color:#888">Por ejemplo, al tener atributos <code style="color:#ff9900">private</code> y métodos públicos de acceso (<code style="color:#ff9900">get</code>/<code style="color:#ff9900">set</code>), evitas que los datos se modifiquen sin control.</span>
  </li>
  <li><b>Abstracción:</b> Solo muestra los aspectos esenciales de un objeto, ocultando la complejidad interna. <br>
    <span style="color:#888">Ejemplo: la clase <code style="color:#ff9900">Figura</code> declara el método <code style="color:#ff9900">calcularArea()</code> y deja que cada figura concreta (Círculo, Cuadrado) lo implemente a su manera.</span>
  </li>
  <li><b>Herencia:</b> Permite crear nuevas clases a partir de otras ya existentes, reutilizando código y creando jerarquías. <br>
    <span style="color:#888">Ejemplo: Una clase <code style="color:#ff9900">Empleado</code> puede heredar de <code style="color:#ff9900">Persona</code>.</span>
  </li>
  <li><b>Polimorfismo:</b> Una misma operación puede comportarse de forma diferente según el objeto. <br>
    <span style="color:#888">Ejemplo: el método <code style="color:#ff9900">correr()</code> funciona distinto para un <code style="color:#ff9900">Deportista</code> y para un <code style="color:#ff9900">Ingeniero</code>, aunque sean del tipo <code style="color:#ff9900">Persona</code>.</span>
  </li>
</ul>

<h3>Ejemplo de Encapsulación</h3>
<pre>
<code style="color:#ff9900">public class</code> <code>CuentaBancaria</code> {
    <code style="color:#ff9900">private double</code> <code>saldo;</code>
    <code style="color:#ff9900">public double</code> <code>getSaldo()</code> { <code>return saldo;</code> }
    <code style="color:#ff9900">public void</code> <code>depositar(double monto)</code> {
        <code>if (monto > 0) saldo += monto;</code>
    }
}
</pre>

<h3>Ejemplo de Abstracción</h3>
<pre>
<code style="color:#ff9900">abstract class</code> <code>Figura</code> {
    <code style="color:#ff9900">abstract double</code> <code>calcularArea();</code>
}
<code style="color:#ff9900">class</code> <code>Circulo extends Figura</code> {
    <code style="color:#ff9900">double</code> <code>radio;</code>
    <code style="color:#ff9900">double</code> <code>calcularArea()</code> {
        <code>return Math.PI * radio * radio;</code>
    }
}
</pre>

<h3>Ejemplo de Herencia</h3>
<pre>
<code style="color:#ff9900">public class</code> <code>Persona</code> {
    <code style="color:#ff9900">String</code> <code>nombre;</code>
}
<code style="color:#ff9900">public class</code> <code>Empleado extends Persona</code> {
    <code style="color:#ff9900">double</code> <code>salario;</code>
}
</pre>

<h3>Ejemplo de Polimorfismo</h3>
<pre>
<code style="color:#ff9900">public abstract class</code> <code>Persona</code> {
    <code style="color:#ff9900">public abstract int</code> <code>correr();</code>
}
<code style="color:#ff9900">public class</code> <code>Deportista extends Persona</code> {
    <code style="color:#ff9900">@Override</code>
    <code style="color:#ff9900">public int</code> <code>correr()</code> { <code>return 7;</code> }
}
<code style="color:#ff9900">public class</code> <code>Ingeniero extends Persona</code> {
    <code style="color:#ff9900">@Override</code>
    <code style="color:#ff9900">public int</code> <code>correr()</code> { <code>return 3;</code> }
}
<code>// Uso polimórfico</code>
<code style="color:#ff9900">Persona</code> <code>p1 = new Deportista("Ana");</code>
<code style="color:#ff9900">Persona</code> <code>p2 = new Ingeniero("Luis");</code>
<code>System.out.println(p1.correr()); // 7</code>
<code>System.out.println(p2.correr()); // 3</code>
</pre>'),

	   (2, 3, 'Modificadores de acceso en Java', '
<p>
Los <b>modificadores de acceso</b> definen la <b>visibilidad</b> de clases, atributos y métodos en Java. Estos controlan qué partes del código pueden utilizar o modificar diversos elementos.
</p>
<table>
  <tr>
    <th>Modificador</th>
    <th>¿Dónde se puede acceder?</th>
  </tr>
  <tr>
    <td><code style="color:#ff9900">public</code></td>
    <td>Desde cualquier clase de cualquier paquete.</td>
  </tr>
  <tr>
    <td><code style="color:#ff9900">private</code></td>
    <td>Sólo dentro de la misma clase.</td>
  </tr>
  <tr>
    <td><code style="color:#ff9900">protected</code></td>
    <td>Dentro del mismo paquete y desde subclases, incluso si están en otro paquete.</td>
  </tr>
  <tr>
    <td><i>Sin modificador</i> (default)</td>
    <td>Sólo dentro del mismo paquete.</td>
  </tr>
</table>

<h3>Ejemplo práctico</h3>
<pre>
<code style="color:#ff9900">public class</code> <code>Persona</code> {
    <code style="color:#ff9900">private String</code> <code>nombre;</code>
    <code style="color:#ff9900">public void</code> <code>setNombre(String nombre)</code> {
        <code>this.nombre = nombre;</code>
    }
    <code style="color:#ff9900">protected int</code> <code>edad;</code>
    <code style="color:#ff9900">String</code> <code>pais;</code> <span style="color: #888">// Sin modificador: acceso default</span>
}
</pre>

<ul>
  <li><b>public</b>: Puedes acceder desde cualquier parte de tu programa.</li>
  <li><b>private</b>: Solo visible dentro de la propia clase. Ideal para atributos sensibles.</li>
  <li><b>protected</b>: Usado principalmente cuando hay herencia.</li>
  <li><b>default</b>: Si no se especifica modificador, solo es visible en el mismo paquete.</li>
</ul>
'),
	   (2, 4, 'Diferencia entre == y .equals()', '<h2>Diferencia entre <code style="color:#de3548;">==</code> y <code style="color:#de3548;">.equals()</code> en Java</h2>

<p>
En Java, al comparar objetos puedes usar el operador <code style="color:#de3548;">==</code> o el método <code style="color:#de3548;">.equals()</code>. Aunque ambos parecen similares, trabajan de manera diferente y es muy importante entenderlo para evitar errores comunes.
</p>

<h3>1. Comparación por referencia: <code style="color:#de3548;">==</code></h3>
<p>
El operador <code style="color:#de3548;">==</code> compara si dos variables <b>apuntan exactamente al mismo objeto</b> en la memoria. Es decir, compara la referencia, no el contenido.<br>
<strong>Ejemplo:</strong>
</p>
<pre>
<code style="color:#ff9900">String</code> <code>str1 = new String("hola");</code>
<code style="color:#ff9900">String</code> <code>str2 = new String("hola");</code>

<code>if (str1 == str2) {</code>
    <code>System.out.println("Las referencias son iguales");</code>
<code>} else {</code>
    <code>System.out.println("Las referencias son diferentes");</code>
<code>}</code>
<span style="color: #888">// Imprime: "Las referencias son diferentes"</span>
</pre>
<p>
Aunque ambos <code>String</code> tienen el mismo valor, son objetos distintos en memoria, por lo que <code style="color:#de3548;">==</code> da <b>false</b>.<br>
</p>

<h3>2. Comparación por contenido: <code style="color:#de3548;">.equals()</code></h3>
<p>
El método <code style="color:#de3548;">.equals()</code> compara el <b>contenido</b> de dos objetos, es decir, si sus valores son iguales. Muchas clases, como <code>String</code>, lo sobrescriben para hacer esa comprobación.<br>
<strong>Ejemplo:</strong>
</p>
<pre>
<code style="color:#ff9900">String</code> <code>str1 = new String("hola");</code>
<code style="color:#ff9900">String</code> <code>str2 = new String("hola");</code>

<code>if (str1.equals(str2)) {</code>
    <code>System.out.println("Los valores son iguales");</code>
<code>} else {</code>
    <code>System.out.println("Los valores son diferentes");</code>
<code>}</code>
<span style="color: #888">// Imprime: "Los valores son iguales"</span>
</pre>
<p>
Aquí, aunque las referencias son distintas, <code>.equals()</code> verifica el contenido, y como ambos textos son "hola", la comparación da <b>true</b>.
</p>

<h3>3. Comparando objetos personalizados</h3>
<p>
Si creas tus propias clases y no sobrescribes <code>equals()</code>, la comparación seguirá siendo por referencia (<code style="color:#de3548;">==</code>), aunque los datos internos sean idénticos.<br>
<strong>Ejemplo:</strong>
</p>
<pre>
<code style="color:#ff9900">class</code> <code>Persona</code> {
    <code style="color:#ff9900">String</code> <code>nombre;</code>
    <code style="color:#ff9900">Persona(String nombre) {</code>
        <code>this.nombre = nombre;</code>
    }
}

<code style="color:#ff9900">Persona</code> <code>p1 = new Persona("Ana");</code>
<code style="color:#ff9900">Persona</code> <code>p2 = new Persona("Ana");</code>

<code>System.out.println(p1 == p2);</code>       <span style="color:#888">// false</span>
<code>System.out.println(p1.equals(p2));</code> <span style="color:#888">// false (a menos que sobreescribas equals())</span>
</pre>
<p>
Para comparar el contenido entre objetos personalizados, necesitas sobrescribir <code>equals()</code>.
</p>

<h3>Resumen rápido</h3>
<ul>
  <li><b><code style="color:#de3548;">==</code></b> compara referencias: ¿son el mismo objeto?</li>
  <li><b><code style="color:#de3548;">.equals()</code></b> compara el contenido: ¿son "iguales" según su definición?</li>
  <li>Siempre usa <code>.equals()</code> para comparar contenido de objetos como <code>String</code> y sobreescribe el método en tus propias clases si quieres comparación por valor.</li>
</ul>
'),

	   (2, 5, 'Diferencia entre Sobrecarga (Overloading) y Sobrescritura (Overriding) en Java', '<p>
Java permite utilizar el mismo nombre de método en diferentes situaciones, pero es fundamental comprender la diferencia entre <b>sobrecarga</b> y <b>sobrescritura</b>, ya que tienen propósitos y reglas distintas.
</p>

<h3>¿Qué es la sobrecarga de métodos (<i>Overloading</i>)?</h3>
<ul>
  <li>Consiste en <b>definir varios métodos con el mismo nombre</b> en una misma clase, pero con diferentes listas de parámetros (tipo, orden o cantidad).</li>
  <li>Permite variar la forma en que un método puede ser llamado según los datos que reciba.</li>
  <li>No requiere herencia.</li>
</ul>

<p><b>Ejemplo de sobrecarga:</b></p>
<pre>
<code style="color:#ff9900">public class</code> <code>Calculadora</code> {
    <code style="color:#ff9900">public int</code> <code>sumar(int a, int b)</code> {
        <code>return a + b;</code>
    }
    <code style="color:#ff9900">public double</code> <code>sumar(double a, double b)</code> {
        <code>return a + b;</code>
    }
    <code style="color:#ff9900">public int</code> <code>sumar(int a, int b, int c)</code> {
        <code>return a + b + c;</code>
    }
}
</pre>
<ul>
  <li>El método <code>sumar</code> tiene distintos parámetros.</li>
  <li>El compilador decide cuál método llamar según los argumentos que recibe.</li>
</ul>

<h3>¿Qué es la sobrescritura de métodos (<i>Overriding</i>)?</h3>
<ul>
  <li>Ocurre cuando una subclase <b>hereda</b> un método de su superclase y <b>define su propia versión</b> de ese método.</li>
  <li>La firma del método (nombre, tipo de retorno y parámetros) es exactamente igual a la del método de la superclase.</li>
  <li>Se usa la anotación <code style="color:#de3548;">@Override</code> para mayor claridad y evitar errores.</li>
  <li>Permite cambiar el comportamiento de métodos heredados según el tipo del objeto.</li>
</ul>

<p><b>Ejemplo de sobrescritura:</b></p>
<pre>
<code style="color:#ff9900">public class</code> <code>Vehiculo</code> {
    <code style="color:#ff9900">public String</code> <code>acelerar(long mph)</code> {
        <code>return "El vehículo acelera a: " + mph + " MPH.";</code>
    }
}
<code style="color:#ff9900">public class</code> <code>Coche extends Vehiculo</code> {
    <code style="color:#de3548;">@Override</code>
    <code style="color:#ff9900">public String</code> <code>acelerar(long mph)</code> {
        <code>return "El coche acelera a: " + mph + " MPH.";</code>
    }
}
</pre>
<ul>
  <li><code>Coche</code> <b>hereda</b> de <code>Vehiculo</code> y redefine el método <code>acelerar</code>.</li>
  <li>Cuando se llama a <code>acelerar</code> sobre una instancia <code>Coche</code>, se ejecuta la versión sobrescrita.</li>
</ul>

<h3>Resumen de diferencias clave</h3>
<table>
  <tr>
    <th></th>
    <th style="color:#ff9900">Sobrecarga (<i>Overloading</i>)</th>
    <th style="color:#ff9900">Sobrescritura (<i>Overriding</i>)</th>
  </tr>
  <tr>
    <td><b>Dónde ocurre</b></td>
    <td>En la misma clase</td>
    <td>En clases relacionadas por herencia</td>
  </tr>
  <tr>
    <td><b>Firma del método</b></td>
    <td>Diferente (cambia número/tipo/orden de parámetros)</td>
    <td>Idéntica (nombre, tipo de retorno y parámetros iguales)</td>
  </tr>
  <tr>
    <td><b>Propósito</b></td>
    <td>Permitir distintas formas de usar un método</td>
    <td>Personalizar comportamiento del método heredado</td>
  </tr>
</table>
'),
	   (2, 6, 'Uso de la palabra clave final en Java', '<p>
La palabra clave <code style="color:#de3548;">final</code> en Java es un modificador especial que puede aplicarse a <b>variables</b>, <b>métodos</b> y <b>clases</b>. Su propósito es "fijar" o impedir cambios: lo que marcas como <code>final</code> ya no podrá ser modificado, heredado o sobrescrito, según el caso.<br>
</p>

<h3>1. <code style="color:#de3548;">final</code> en variables</h3>
<ul>
  <li>Cuando una variable se declara como <code style="color:#de3548;">final</code>, se convierte en una <b>constante</b>: solo puedes asignarle un valor UNA vez; después, no puede cambiarse.</li>
  <li>Esto vale tanto para variables locales, atributos de clase o argumentos de métodos.</li>
</ul>

<pre>
<code style="color:#ff9900">final int</code> <code>MAX_EDAD = 100;</code>
<code>MAX_EDAD = 101;</code> <span style="color: #888">// Error: no se puede modificar una variable final.</span>
</pre>
<ul>
  <li>
    Una <b>variable final</b> debe ser inicializada al declararse, en el constructor o en un bloque de inicialización<sup>[1][4][5][6]</sup>.
  </li>
</ul>

<h3>2. <code style="color:#de3548;">final</code> en métodos</h3>
<ul>
  <li>Si marcas un método como <code style="color:#de3548;">final</code>, <b>no puede ser sobrescrito</b> por subclases. Así aseguras que su comportamiento no cambiará en herencias.</li>
</ul>

<pre>
<code style="color:#ff9900">class</code> <code>Padre</code> {
    <code style="color:#de3548;">public final void</code> <code>mostrar()</code> {
        <code>System.out.println(</code><code style="color:#2F9E44;">"Método final en la clase Padre"</code><code>);</code>
    }
}
<code style="color:#ff9900">class</code> <code>Hijo extends Padre</code> {
    <span style="color: #888">// No se puede sobreescribir mostrar(): causaría error</span>
    <span style="color: #888">// public void mostrar() { ... } // Error de compilación</span>
}
</pre>

<h3>3. <code style="color:#de3548;">final</code> en clases</h3>
<ul>
  <li>Si una clase se declara como <code style="color:#de3548;">final</code>, <b>no puede tener subclases</b> (no admite herencia).</li>
  <li>Esto se usa generalmente para crear clases seguras o inmutables, como <code>String</code> en Java.</li>
</ul>

<pre>
<code style="color:#de3548;">public final class</code> <code>Utilidades</code> {
    <code style="color:#ff9900">public static void</code> <code>imprimir()</code> {
        <code>System.out.println(</code><code style="color:#2F9E44;">"Clase final: no puede heredarse."</code><code>);</code>
    }
}
<code>// class MisUtilidades extends Utilidades { } // Error: no puede heredar de una clase final.</code>
</pre>

<h3>4. <code style="color:#de3548;">final</code> en parámetros de métodos</h3>
<ul>
  <li>Puedes declarar argumentos de métodos como <code style="color:#de3548;">final</code>. Así, su valor no puede cambiarse dentro del método.</li>
</ul>

<pre>
<code style="color:#ff9900">public void</code> <code>mostrarEdad(final int edad)</code> {
    <span style="color: #888">// edad = 18; // Error: no se puede reasignar ''edad''</span>
    <code>System.out.println(</code><code style="color:#2F9E44;">"Edad: "</code> <code>+ edad);</code>
}
</pre>

<h3>Resumen</h3>
<ul>
  <li><code style="color:#de3548;">final</code> en variables: se vuelven constantes y no se pueden modificar tras su inicialización.</li>
  <li><code style="color:#de3548;">final</code> en métodos: no pueden ser sobrescritos por subclases.</li>
  <li><code style="color:#de3548;">final</code> en clases: no pueden ser heredadas.</li>
  <li><code style="color:#de3548;">final</code> en parámetros: su valor permanece fijo dentro del método.</li>
</ul>
<p>
La palabra clave <code style="color:#de3548;">final</code> ayuda a crear programas más seguros, robustos y a evitar errores de modificación o herencia no deseada.
</p>
'),
	   (2, 7, 'Manejo de excepciones básicas', '<p>
En Java, el manejo de excepciones permite controlar errores de ejecución y evitar que tu programa termine abruptamente. Utiliza los bloques <b>try</b>, <b>catch</b>, <b>finally</b> y las palabras clave <code style="color:#de3548;">throw</code> y <code style="color:#de3548;">throws</code> para manejar de forma ordenada situaciones excepcionales o inesperadas<span style="color: #888;">[1][2][5][8]</span>.
</p>

<h3>Bloques <code>try</code> y <code>catch</code></h3>
<ul>
  <li><b>try:</b> Contiene el código que <b>puede lanzar una excepción</b> (es decir, que podría fallar en tiempo de ejecución).</li>
  <li><b>catch:</b> “Atrapa” la excepción si ocurre, permitiendo que el programa ejecute código alternativo en vez de detenerse.</li>
</ul>
<pre>
<code style="color:#ff9900">try</code> {
    <code>int resultado = 10 / 0;  // Lanza ArithmeticException</code>
} <code style="color:#ff9900">catch</code> (<code style="color:#ff9900">ArithmeticException</code> e) {
    <code>System.out.println("Error: división por cero.");</code>
}
</pre>
<p>
En este ejemplo, al intentar dividir por cero, se lanza una excepción y se ejecuta el <code>catch</code> para mostrar un mensaje personalizado<span style="color: #888;">[1][2][5]</span>.
</p>

<h3>Bloque <code>finally</code></h3>
<ul>
  <li>El bloque <code>finally</code> es <b>opcional</b> y se ejecuta siempre, ocurra o no una excepción. Es útil para <b>liberar recursos</b> como archivos, conexiones, etc.<span style="color: #888;">[1][3][5][7]</span></li>
</ul>
<pre>
<code style="color:#ff9900">try</code> {
    <code>// Código que puede causar error</code>
} <code style="color:#ff9900">catch</code> (<code style="color:#ff9900">Exception</code> e) {
    <code>System.out.println("Ocurrió una excepción");</code>
} <code style="color:#ff9900">finally</code> {
    <code>System.out.println("Este bloque siempre se ejecuta");</code>
}
</pre>

<h3>Uso de <code style="color:#de3548;">throw</code></h3>
<ul>
  <li>La palabra clave <code style="color:#de3548;">throw</code> permite <b>lanzar explícitamente una excepción</b> desde tu código (por ejemplo, si detectas un dato inválido).</li>
</ul>
<pre>
<code style="color:#ff9900">public void</code> <code>setEdad(int edad)</code> {
    <code style="color:#ff9900">if</code> (edad &lt; 0) {
        <code style="color:#de3548;">throw</code> <code style="color:#ff9900">new IllegalArgumentException</code>(<code style="color:#2F9E44;">"Edad no puede ser negativa"</code>);
    }
    <code>this.edad = edad;</code>
}
</pre>

<h3>Uso de <code style="color:#de3548;">throws</code></h3>
<ul>
  <li>La palabra clave <code style="color:#de3548;">throws</code> se coloca en la declaración de un método para indicar que <b>puede lanzar una excepción</b> y que quien llame al método debe manejarla.</li>
</ul>
<pre>
<code style="color:#ff9900">public void</code> <code>leerArchivo()</code> <code style="color:#de3548;">throws IOException</code> {
    <code>// Código que puede lanzar IOException</code>
}
</pre>

<h3>Resumen</h3>
<ul>
  <li><code style="color:#ff9900">try</code>: Intenta ejecutar el código que puede causar errores.</li>
  <li><code style="color:#ff9900">catch</code>: Maneja la excepción si ocurre.</li>
  <li><code style="color:#ff9900">finally</code>: Se ejecuta siempre, ocurra o no una excepción.</li>
  <li><code style="color:#de3548;">throw</code>: Lanza manualmente una excepción desde tu código.</li>
  <li><code style="color:#de3548;">throws</code>: Señala que un método puede lanzar una excepción que debe ser gestionada por quien lo llama.</li>
</ul>
'),
	   (2, 8, 'Diferencias entre clases abstractas e interfaces', '<p>
En Java, tanto las <b>clases abstractas</b> como las <b>interfaces</b> permiten definir comportamientos que otras clases deben implementar. Sin embargo, tienen diferencias claves en su uso, estructura y propósito.
</p>

<h3>¿Qué es una clase abstracta?</h3>
<ul>
  <li>Una <b>clase abstracta</b> <b>no puede ser instanciada</b> directamente, sirve como plantilla base para otras clases.</li>
  <li>Puedes definir atributos, métodos concretos (con código) y métodos abstractos (sin implementación).</li>
  <li>Sirve para <b>compartir código y estado</b> entre clases relacionadas.</li>
  <li>Las subclases deben extender la clase abstracta e implementar sus métodos abstractos.</li>
</ul>
<pre>
<code style="color:#ff9900">public abstract class</code> <code>Animal</code> {
    <code style="color:#ff9900">private String</code> <code>nombre;</code>
    <code style="color:#ff9900">public void</code> <code>caminar()</code> {
        <code>System.out.println("El animal camina");</code>
    }
    <code style="color:#ff9900">public abstract void</code> <code>hacerSonido();</code>
}
</pre>
<p>
No puedes crear <code>new Animal()</code>, pero sí puedes hacer <code>new Perro()</code> si <code>Perro</code> extiende <code>Animal</code> e implementa <code>hacerSonido()</code>.
</p>

<h3>¿Qué es una interfaz?</h3>
<ul>
  <li>Una <b>interfaz</b> es un <b>contrato</b> de <b>métodos que una clase debe implementar</b>.</li>
  <li>No puede contener estado (atributos normales), solo <b>constantes</b> (<code>public static final</code>).</li>
  <li>Todos los métodos en una interfaz son <b>públicos y abstractos</b> por defecto. Desde Java 8, puede tener métodos <i>default</i> y <i>static</i>.</li>
  <li>Una clase puede implementar varias interfaces a la vez.</li>
</ul>
<pre>
<code style="color:#ff9900">public interface</code> <code>Nadador</code> {
    <code style="color:#ff9900">void</code> <code>nadar();</code>
}
<code style="color:#ff9900">public class</code> <code>Pez implements Nadador</code> {
    <code style="color:#ff9900">public void</code> <code>nadar()</code> {
        <code>System.out.println("El pez nada");</code>
    }
}
</pre>

<h3>Diferencias principales</h3>
<table>
  <tr>
    <th>Característica</th>
    <th>Clase Abstracta</th>
    <th>Interfaz</th>
  </tr>
  <tr>
    <td>Instanciación</td>
    <td>No se puede instanciar</td>
    <td>No se puede instanciar</td>
  </tr>
  <tr>
    <td>Atributos/Estado</td>
    <td>Puedes tener atributos de cualquier tipo/modificador</td>
    <td>Sólo <code>public static final</code> (constantes)</td>
  </tr>
  <tr>
    <td>Métodos</td>
    <td>Abstractos y/o con implementación</td>
    <td>Abstractos (por defecto); desde Java 8 <i>default</i> y <i>static</i></td>
  </tr>
  <tr>
    <td>Constructores</td>
    <td>Puedes tener constructores</td>
    <td>No puede tener constructores</td>
  </tr>
  <tr>
    <td>Herencia / Implementación</td>
    <td>Extiende solo UNA clase</td>
    <td>Una clase puede implementar MUCHAS interfaces</td>
  </tr>
  <tr>
    <td>Propósito</td>
    <td>Plantilla compartida y comportamiento común</td>
    <td>Contrato de qué debe hacer la clase</td>
  </tr>
</table>

<h3>¿Cuándo usar cada una?</h3>
<ul>
  <li><b>Clase abstracta</b>: Cuando quieres compartir código, atributos y comportamientos comunes entre clases relacionadas (<i>herencia</i>).</li>
  <li><b>Interfaz</b>: Cuando quieres definir un contrato de métodos a implementar, sin importar la jerarquía ni compartir implementación.</li>
</ul>

<h3>Resumen visual</h3>
<ul>
  <li>Usa <b>clases abstractas</b> para <b>plantillas con estado y código compartido</b> entre clases de la misma familia.</li>
  <li>Usa <b>interfaces</b> para <b>definir comportamientos comunes</b> que pueden ser implementados por clases no relacionadas.</li>
</ul>
'),
	   (2, NULL, 'Conceptos básicos de Garbage Collection y colecciones', '<h2>Conceptos básicos de Garbage Collection y colecciones en Java</h2>

<h3>¿Qué es el Garbage Collection (GC) en Java?</h3>
<p>
El <b>Garbage Collector</b> es un sistema automático de Java responsable de <b>liberar la memoria ocupada por objetos que ya no se usan en el programa</b>. Esto ayuda a prevenir fugas de memoria y errores al evitar que los desarrolladores tengan que gestionar manualmente la memoria, a diferencia de lenguajes como C o C++[1][4][7].
</p>
<ul>
  <li>Cuando un objeto deja de tener referencias activas en el código, el Garbage Collector lo identifica y lo elimina de la memoria automáticamente[1][2][3].</li>
  <li>Java divide la memoria en áreas como <b>Young Generation</b> (objetos recién creados) y <b>Old Generation</b> (objetos con más tiempo de vida). El colector limpia primero los objetos "jóvenes" y, menos frecuentemente, los "antiguos" para optimizar el rendimiento[1][2][5][6].</li>
  <li>Este sistema permite que las aplicaciones sean más estables y fáciles de mantener, ya que no debes preocuparte por liberar memoria[1][4].</li>
</ul>

<p>
<b>Ejemplo práctico:</b>
Si creas un objeto y dejas de usarlo (es decir, ya no hay ninguna variable que apunte a él), el recolector de basura eventualmente lo eliminará del sistema:
</p>
<pre>
<code style="color:#ff9900">public void crearPersona() {</code>
    <code style="color:#ff9900">Persona</code> <code>p = new Persona("Mario");</code>
    <span style="color: #888">// p deja de existir al finalizar el método, podrá ser limpiado por el GC</span>
<code>}</code>
</pre>

<h3>¿Qué pasa si no hay suficiente memoria?</h3>
<p>
Si el Garbage Collector no logra liberar suficiente memoria y la aplicación necesita más, puede surgir un error <code style="color:#de3548;">OutOfMemoryError</code>[4]. Por este motivo, es importante no mantener referencias innecesarias y aprovechar bien las colecciones.
</p>

<hr>

<h3>Panorama básico del framework de colecciones</h3>
<p>
El <b>Framework de Colecciones de Java</b> es un conjunto de clases e interfaces que <b>facilitan el almacenamiento, manipulación y recorrido de grupos de objetos</b> de forma eficiente y flexible[3]. Los tres grupos principales son:
</p>

<ul>
  <li><b>List</b>: Colección ordenada que permite elementos duplicados y acceso a través de un índice.
    <br><code style="color:#ff9900">ArrayList</code>, <code style="color:#ff9900">LinkedList</code>
  </li>
  <li><b>Set</b>: Colección que <b>no permite elementos duplicados</b> y puede estar desordenada.
    <br><code style="color:#ff9900">HashSet</code>, <code style="color:#ff9900">TreeSet</code>
  </li>
  <li><b>Map</b>: Estructura para almacenar <b>pares clave-valor</b>. No forma parte de la interfaz Collection, pero es fundamental en la plataforma Java.
    <br><code style="color:#ff9900">HashMap</code>, <code style="color:#ff9900">TreeMap</code>
  </li>
</ul>

<table>
  <tr>
    <th>Estructura</th>
    <th>Permite duplicados</th>
    <th>Orden</th>
    <th>Acceso típico</th>
    <th>Ejemplo de clase</th>
  </tr>
  <tr>
    <td>List</td>
    <td>Sí</td>
    <td>Sí (mantiene orden de inserción)</td>
    <td>Por índice</td>
    <td><code style="color:#ff9900">ArrayList</code></td>
  </tr>
  <tr>
    <td>Set</td>
    <td>No</td>
    <td>No necesariamente</td>
    <td>Por elemento</td>
    <td><code style="color:#ff9900">HashSet</code></td>
  </tr>
  <tr>
    <td>Map</td>
    <td>Sólo claves únicas</td>
    <td>No necesariamente</td>
    <td>Por clave</td>
    <td><code style="color:#ff9900">HashMap</code></td>
  </tr>
</table>

<p>
<b>Ejemplo básico de uso de colecciones:</b>
</p>
<pre>
<code style="color:#ff9900">import java.util.*;</code>

<code style="color:#ff9900">List&lt;String&gt;</code> <code>nombres = new ArrayList&lt;&gt;();</code>
<code>nombres.add("Ana");</code>
<code>nombres.add("Luis");</code>
<code>System.out.println(nombres.get(0)); // Imprime: Ana</code>

<code style="color:#ff9900">Set&lt;String&gt;</code> <code>colores = new HashSet&lt;&gt;();</code>
<code>colores.add("rojo");</code>
<code>colores.add("azul");</code>
<code>colores.add("rojo"); // Ignorado porque ya existe</code>
<code>System.out.println(colores);</code>

<code style="color:#ff9900">Map&lt;String, Integer&gt;</code> <code>edades = new HashMap&lt;&gt;();</code>
<code>edades.put("Ana", 25);</code>
<code>edades.put("Luis", 30);</code>
<code>System.out.println(edades.get("Luis")); // Imprime: 30</code>
</pre>

<h3>Resumen</h3>
<ul>
  <li>El <b>Garbage Collector</b> de Java libera la memoria ocupada por objetos no utilizados, agilizando el manejo automático de recursos y evitando errores comunes de memoria[1][4].</li>
  <li>El <b>framework de colecciones</b> ofrece estructuras flexibles (<b>List, Set, Map</b>) para organizar y manipular grupos de datos de manera eficiente y segura en tus programas.</li>
</ul>
'),
	   (2, 10, 'Concurrency y Multihilo', '
<h3>¿Qué es el Garbage Collection (GC) en Java?</h3>
<p>
El <b>Garbage Collector</b> es un sistema automático de Java responsable de <b>liberar la memoria ocupada por objetos que ya no se usan en el programa</b>. Esto ayuda a prevenir fugas de memoria y errores al evitar que los desarrolladores tengan que gestionar manualmente la memoria, a diferencia de lenguajes como C o C++[1][4][7].
</p>
<ul>
  <li>Cuando un objeto deja de tener referencias activas en el código, el Garbage Collector lo identifica y lo elimina de la memoria automáticamente[1][2][3].</li>
  <li>Java divide la memoria en áreas como <b>Young Generation</b> (objetos recién creados) y <b>Old Generation</b> (objetos con más tiempo de vida). El colector limpia primero los objetos "jóvenes" y, menos frecuentemente, los "antiguos" para optimizar el rendimiento[1][2][5][6].</li>
  <li>Este sistema permite que las aplicaciones sean más estables y fáciles de mantener, ya que no debes preocuparte por liberar memoria[1][4].</li>
</ul>

<p>
<b>Ejemplo práctico:</b>
Si creas un objeto y dejas de usarlo (es decir, ya no hay ninguna variable que apunte a él), el recolector de basura eventualmente lo eliminará del sistema:
</p>
<pre>
<code style="color:#ff9900">public void crearPersona() {</code>
    <code style="color:#ff9900">Persona</code> <code>p = new Persona("Mario");</code>
    <span style="color: #888">// p deja de existir al finalizar el método, podrá ser limpiado por el GC</span>
<code>}</code>
</pre>

<h3>¿Qué pasa si no hay suficiente memoria?</h3>
<p>
Si el Garbage Collector no logra liberar suficiente memoria y la aplicación necesita más, puede surgir un error <code style="color:#de3548;">OutOfMemoryError</code>[4]. Por este motivo, es importante no mantener referencias innecesarias y aprovechar bien las colecciones.
</p>

<hr>

<h3>Panorama básico del framework de colecciones</h3>
<p>
El <b>Framework de Colecciones de Java</b> es un conjunto de clases e interfaces que <b>facilitan el almacenamiento, manipulación y recorrido de grupos de objetos</b> de forma eficiente y flexible[3]. Los tres grupos principales son:
</p>

<ul>
  <li><b>List</b>: Colección ordenada que permite elementos duplicados y acceso a través de un índice.
    <br><code style="color:#ff9900">ArrayList</code>, <code style="color:#ff9900">LinkedList</code>
  </li>
  <li><b>Set</b>: Colección que <b>no permite elementos duplicados</b> y puede estar desordenada.
    <br><code style="color:#ff9900">HashSet</code>, <code style="color:#ff9900">TreeSet</code>
  </li>
  <li><b>Map</b>: Estructura para almacenar <b>pares clave-valor</b>. No forma parte de la interfaz Collection, pero es fundamental en la plataforma Java.
    <br><code style="color:#ff9900">HashMap</code>, <code style="color:#ff9900">TreeMap</code>
  </li>
</ul>

<table>
  <tr>
    <th>Estructura</th>
    <th>Permite duplicados</th>
    <th>Orden</th>
    <th>Acceso típico</th>
    <th>Ejemplo de clase</th>
  </tr>
  <tr>
    <td>List</td>
    <td>Sí</td>
    <td>Sí (mantiene orden de inserción)</td>
    <td>Por índice</td>
    <td><code style="color:#ff9900">ArrayList</code></td>
  </tr>
  <tr>
    <td>Set</td>
    <td>No</td>
    <td>No necesariamente</td>
    <td>Por elemento</td>
    <td><code style="color:#ff9900">HashSet</code></td>
  </tr>
  <tr>
    <td>Map</td>
    <td>Sólo claves únicas</td>
    <td>No necesariamente</td>
    <td>Por clave</td>
    <td><code style="color:#ff9900">HashMap</code></td>
  </tr>
</table>

<p>
<b>Ejemplo básico de uso de colecciones:</b>
</p>
<pre>
<code style="color:#ff9900">import java.util.*;</code>

<code style="color:#ff9900">List&lt;String&gt;</code> <code>nombres = new ArrayList&lt;&gt;();</code>
<code>nombres.add("Ana");</code>
<code>nombres.add("Luis");</code>
<code>System.out.println(nombres.get(0)); // Imprime: Ana</code>

<code style="color:#ff9900">Set&lt;String&gt;</code> <code>colores = new HashSet&lt;&gt;();</code>
<code>colores.add("rojo");</code>
<code>colores.add("azul");</code>
<code>colores.add("rojo"); // Ignorado porque ya existe</code>
<code>System.out.println(colores);</code>

<code style="color:#ff9900">Map&lt;String, Integer&gt;</code> <code>edades = new HashMap&lt;&gt;();</code>
<code>edades.put("Ana", 25);</code>
<code>edades.put("Luis", 30);</code>
<code>System.out.println(edades.get("Luis")); // Imprime: 30</code>
</pre>

<h3>Resumen</h3>
<ul>
  <li>El <b>Garbage Collector</b> de Java libera la memoria ocupada por objetos no utilizados, agilizando el manejo automático de recursos y evitando errores comunes de memoria[1][4].</li>
  <li>El <b>framework de colecciones</b> ofrece estructuras flexibles (<b>List, Set, Map</b>) para organizar y manipular grupos de datos de manera eficiente y segura en tus programas.</li>
</ul>
');

INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES (3, 10, 'Introducción a Servlets', 'Conceptos básicos sobre Servlets y ciclo de vida.'),
	   (3, NULL, 'Frameworks Web', 'Visión general de frameworks como Spring y JSF para desarrollo web.');

