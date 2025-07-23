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

-- Lecciones para los cursos del 2 al 32 con id_next_lesson secuencial empezando en 12
INSERT INTO public.lesson (id_course, id_next_lesson, title, explanation)
VALUES
-- Curso 2
(2, 12, 'Introducción a conceptos avanzados de Java', 'Resumen introductorio sobre concurrencia y colecciones.'),
(2, NULL, 'Conceptos Avanzados de Java', 'Explora temas avanzados como concurrencia y colecciones.'),

-- Curso 3
(3, 14, 'Introducción al Desarrollo Web con Java', 'Introducción general sobre desarrollo web con Java.'),
(3, NULL, 'Introducción al Desarrollo Web', 'Fundamentos básicos para crear aplicaciones web con Java.'),

-- Curso 4
(4, 16, 'Introducción a la Programación Orientada a Objetos', 'Conceptos fundamentales de POO.'),
(4, NULL, 'Fundamentos de POO', 'Conoce la programación orientada a objetos y sus pilares.'),

-- Curso 5
(5, 18, 'Introducción a Estructuras de Datos en Java', 'Presentación básica sobre listas, pilas, y colas.'),
(5, NULL, 'Listas y Arrays', 'Aprende a trabajar con listas, arrays y sus usos prácticos.'),

-- Curso 6
(6, 20, 'Introducción a Spring', 'Visión general del ecosistema Spring Framework.'),
(6, NULL, '¿Qué es Spring?', 'Curso de introducción al ecosistema Spring.'),

-- Curso 7
(7, 22, 'Introducción a Spring Boot', 'Fundamentos para crear aplicaciones modernas con Spring Boot.'),
(7, NULL, 'Introducción a Spring Boot', 'Crea una app básica usando Spring Boot.'),

-- Curso 8
(8, 24, 'Introducción a Servlets y JSP', 'Conceptos iniciales para desarrollo web con Java EE.'),
(8, NULL, 'Servlets y JSP', 'Primeros pasos en desarrollo web con Java EE.'),

-- Curso 9
(9, 26, 'Introducción a APIs RESTful con Spring', 'Principios para diseñar APIs profesionales.'),
(9, NULL, 'Controladores REST', 'Aprende a crear servicios REST usando Spring MVC.'),

-- Curso 10
(10, 28, 'Introducción a JPA e Hibernate', 'Conceptos básicos de mapeo objeto-relacional.'),
(10, NULL, 'Introducción a JPA', 'Mapeo objeto-relacional con anotaciones básicas.'),

-- Curso 11
(11, 30, 'Introducción a Testing en Java', 'Por qué y cómo hacer pruebas unitarias e integración.'),
(11, NULL, 'JUnit desde Cero', 'Pruebas unitarias con JUnit 5 en Java.'),

-- Curso 12
(12, 32, 'Introducción a JavaFX', 'Introducción a la creación de interfaces gráficas.'),
(12, NULL, 'Primeros pasos con JavaFX', 'Configura y crea tu primera ventana.'),

-- Curso 13
(13, 34, 'Introducción a Java para principiantes', 'Primeros pasos en Java, filosofía y conceptos.'),
(13, NULL, 'Hola Mundo en Java', 'Primera aplicación simple en consola.'),

-- Curso 14
(14, 36, 'Introducción a JavaScript', 'Fundamentos y características esenciales de JavaScript.'),
(14, NULL, 'Sintaxis y Variables', 'Uso de let, const y operadores.'),

-- Curso 15
(15, 38, 'Introducción a la herencia y prototipos en JavaScript', 'Conceptos básicos para entender herencia.'),
(15, NULL, 'Prototipos y Clases', 'Profundiza en la herencia prototípica y clases.'),

-- Curso 16
(16, 40, 'Introducción al DOM y Eventos en JavaScript', 'Manipulación de la página web con JavaScript.'),
(16, NULL, 'DOM y Eventos', 'Manipula la estructura de una página web con JS.'),

-- Curso 17
(17, 42, 'Introducción a POO en Java', 'Conceptos básicos y utilidad.'),
(17, NULL, 'POO en Java', 'Creación de clases, objetos y encapsulamiento.'),

-- Curso 18
(18, 44, 'Introducción a Arquitectura Web en Java', 'Visión general de capas y componentes.'),
(18, NULL, 'Arquitectura Web en Java', 'Componentes principales de una app web Java.'),

-- Curso 19
(19, 46, 'Introducción a HTML', 'Estructura y elementos esenciales.'),
(19, NULL, 'Estructura HTML', 'Etiquetas básicas para construir una página.'),

-- Curso 20
(20, 48, 'Introducción a la accesibilidad web', 'Importancia y conceptos básicos.'),
(20, NULL, 'Accesibilidad en la Web', 'Buenas prácticas y etiquetas ARIA.'),

-- Curso 21
(21, 50, 'Introducción a CSS Selectores y Propiedades', 'Conceptos esenciales para estilos.'),
(21, NULL, 'Selectores y Propiedades', 'Cómo aplicar estilos a elementos HTML.'),

-- Curso 22
(22, 52, 'Introducción a flexbox y grid', 'Diseños modernos en CSS.'),
(22, NULL, 'Flexbox y Grid', 'Diseños modernos y responsivos.'),

-- Curso 23
(23, 54, 'Introducción a Python básico', 'Variables y estructuras de datos.'),
(23, NULL, 'Variables y Tipos', 'Primeros programas en Python.'),

-- Curso 24
(24, 56, 'Introducción a Pandas para análisis de datos', 'Manipulación de datos básica.'),
(24, NULL, 'Pandas Básico', 'Lectura y manipulación de datos con Pandas.'),

-- Curso 25
(25, 58, 'Introducción a PHP básico', 'Bases para desarrollo web.'),
(25, NULL, 'PHP y HTML', 'Incrustar PHP en HTML para crear páginas dinámicas.'),

-- Curso 26
(26, 60, 'Introducción a MySQL con PHP', 'Conexión y operaciones básicas.'),
(26, NULL, 'Conexión a MySQL', 'Establece una conexión segura a bases de datos.'),

-- Curso 27
(27, 62, 'Introducción a C# y sintaxis', 'Lenguaje y estructura básica.'),
(27, NULL, 'Sintaxis de C#', 'Fundamentos del lenguaje y estructura de un programa.'),

-- Curso 28
(28, 64, 'Introducción a LINQ y delegados', 'Manipulación de datos en C#.'),
(28, NULL, 'LINQ y Delegados', 'Técnicas para manipular colecciones y eventos.'),

-- Curso 29
(29, 66, 'Introducción a TypeScript', 'Tipos estáticos y funciones.'),
(29, NULL, 'Tipos y Funciones', 'Declara tipos estáticos y estructura funciones.'),

-- Curso 30
(30, 68, 'Introducción a Angular y TypeScript', 'Conceptos básicos y componentes.'),
(30, NULL, 'Componentes Angular', 'Crea componentes reutilizables con TS y Angular.'),

-- Curso 31
(31, 70, 'Introducción a Ruby', 'Fundamentos del lenguaje.'),
(31, NULL, 'Sintaxis de Ruby', 'Cómo escribir tu primer programa en Ruby.'),

-- Curso 32
(32, 72, 'Introducción a Swift', 'Conceptos básicos para principiantes.'),
(32, NULL, 'Swift para Principiantes', 'Explora variables, constantes y control de flujo.');
