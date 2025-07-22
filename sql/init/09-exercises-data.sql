TRUNCATE TABLE public.exercise RESTART IDENTITY CASCADE;
-- Lección 2: ¿Qué es Java y cómo funciona?
INSERT INTO public.exercise (id_lesson, question)
VALUES (2, '¿Qué significa que Java sea "multiplataforma"?'),
	   (2, '¿Cuál es el nombre del componente que permite ejecutar el bytecode en cualquier sistema operativo?'),
	   (2, 'Verdadero o falso: En Java, puedes ejecutar directamente el archivo .java sin compilarlo antes.'),
	   (2, '¿Qué papel cumple el JDK (Java Development Kit)?'),
	   (2, '¿Qué archivo se genera tras compilar un programa Java?'),
	   (2, 'Verdadero o falso: Java es un lenguaje débilmente tipado, por eso no es necesario declarar el tipo de las variables.'),
	   (2, '¿Qué ventaja principal ofrece la orientación a objetos en Java?'),
	   (2, 'Verdadero o falso: La JVM traduce el código fuente de Java directamente al lenguaje máquina.'),
	   (2, '¿Qué extensión tiene un archivo que contiene código fuente en Java?'),
	   (2, 'Verdadero o falso: Para programar en Java solo necesitas tener instalada la JVM.');

-- Lección 3: Variables. Estructura básica.
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(3, '¿Qué es una variable en Java?'),
	(3, '¿Qué se necesita declarar al crear una variable en Java?'),
	(3, 'Verdadero o falso: Una variable puede cambiar su valor a lo largo del tiempo.'),
	(3, '¿Cuál es el tipo adecuado para almacenar números enteros en Java?'),
	(3, '¿Qué significa que Java sea fuertemente tipado?'),
	(3, '¿Cuál de estas opciones representa correctamente una declaración de variable?'),
	(3, 'Verdadero o falso: Se puede declarar una variable sin indicar su tipo de dato.'),
	(3, '¿Qué representa el nombre de la variable?'),
	(3, '¿Qué parte de esta línea es el valor?: <code>int edad = 30;</code>'),
	(3, '¿Qué ocurre si no inicializas una variable en Java?');


-- Lección 4: Variables. Tipos primitivos.
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(4, '¿Qué son los tipos primitivos en Java?'),
	(4, '¿Cuál de los siguientes tipos se utiliza para representar números enteros?'),
	(4, '¿Qué tipo usarías para representar un número con decimales y mayor precisión?'),
	(4, '¿Cómo se representa un carácter en Java?'),
	(4, '¿Cuál es el tipo primitivo para almacenar valores booleanos?'),
	(4, '¿Qué valor es correcto para inicializar un <code>float</code> en Java?'),
	(4, 'Verdadero o falso: Los tipos primitivos en Java tienen métodos incorporados.'),
	(4, '¿Qué ocurre si no añades una "f" al final de un número decimal al declararlo como <code>float</code>?'),
	(4, '¿Qué valor devuelve un <code>boolean</code> cuando una condición se cumple?'),
	(4, '¿Cuál de estos tipos primitivos representa el menor nivel de precisión para números decimales?');


-- Lección 5: Variables. Tipos no primitivos.
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(5, '¿Qué son los tipos no primitivos en Java?'),
	(5, '¿Cuál de los siguientes ejemplos representa un tipo no primitivo?'),
	(5, '¿Qué característica diferencia a los tipos no primitivos de los primitivos?'),
	(5, '¿Cuál de estas afirmaciones sobre los tipos no primitivos es correcta?'),
	(5, '¿Qué tipo se usa comúnmente en Java para representar textos?'),
	(5, '¿Qué diferencia principal existe entre un tipo primitivo y uno no primitivo en Java?'),
	(5, '¿Qué es una propiedad en un tipo no primitivo?'),
	(5, '¿Qué se puede hacer con un tipo no primitivo como <code>String</code>?'),
	(5, '¿Cuál es la ventaja principal de los tipos no primitivos frente a los primitivos?'),
	(5, '¿Cuál de los siguientes NO es un tipo no primitivo en Java?');

-- Lección 6: Constantes y buenas prácticas en el manejo de variables.
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(6, '¿Qué palabra clave se utiliza en Java para declarar una constante?'),
	(6, '¿Qué sucede si intentas modificar una constante en Java después de haberla declarado?'),
	(6, '¿Cuál de los siguientes nombres sigue la convención para constantes en Java?'),
	(6, '¿Cuál es una razón para usar constantes en Java?'),
	(6, '¿Qué convención se utiliza en Java para nombrar variables?'),
	(6, '¿Cuál de los siguientes nombres de variable sigue la convención lowerCamelCase?'),
	(6, '¿Qué tipo de valores se recomienda almacenar como constantes?'),
	(6, '¿Cuál es un beneficio de usar nombres claros y representativos en las variables?'),
	(6, '¿Qué valor por defecto tiene una variable de tipo <code>boolean</code> en Java?'),
	(6, '¿Cuál de estas opciones representa correctamente la declaración de una constante?');

-- Lección 7: Operadores aritméticos.
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(7, '¿Qué operador se utiliza para obtener el resto de una división en Java?'),
	(7, '¿Cuál de los siguientes operadores se utiliza para comparar si dos valores son iguales?'),
	(7, '¿Cuál es el resultado de la operación <code>10 / 3</code> en Java si ambas variables son enteras?'),
	(7, '¿Qué operador lógico se utiliza para representar “y”?'),
	(7, '¿Qué valor devuelve la expresión <code>5 != 3</code>?'),
	(7, '¿Cuál es el resultado de <code>10 % 4</code>?'),
	(7, '¿Cuál de las siguientes expresiones lógicas devuelve <code>true</code>?'),
	(7, '¿Qué hace el operador <code>!</code> en una expresión lógica?'),
	(7, '¿Qué operador se usa para concatenar cadenas en Java?'),
	(7, '¿Cuál es el resultado de esta expresión: <code>(5 > 3) && (2 < 4)</code>?');

-- Lección 9: Concatenación de cadenas
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(8, '¿Qué operador se usa en Java para concatenar cadenas?'),
	(8, '¿Qué resultado produce esta operación? <code>"Hola" + ", " + "Ana"</code>'),
	(8, '¿Qué tipo de dato permite realizar concatenación con el operador <code>+</code>?'),
	(8, '¿Qué sucede si se concatena un <code>String</code> con un número?'),
	(8, '¿Cuál de las siguientes afirmaciones es correcta sobre la concatenación en Java?'),
	(8, 'El operador <code>+</code> también puede sumar números. ¿Verdadero o falso?'),
	(8, 'Java lanza un error si se concatena un número con una cadena. ¿Verdadero o falso?'),
	(8, 'Es posible usar variables dentro de una concatenación. ¿Verdadero o falso?'),
	(8, 'La concatenación puede incluir resultados de operaciones matemáticas. ¿Verdadero o falso?'),
	(8, 'La concatenación es útil para construir mensajes dinámicos. ¿Verdadero o falso?');

-- Lección 9: Conversión de tipos en Java (Casting)
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(9, '¿Qué tipo de conversión en Java es automática y no requiere casting?'),
	(9, '¿Cuál de las siguientes afirmaciones describe correctamente el narrowing casting?'),
	(9, '¿Qué palabra describe la conversión de un tipo más pequeño a uno más grande?'),
	(9, '¿Qué sucede cuando se realiza un narrowing casting de <code>double</code> a <code>int</code>?'),
	(9, '¿Cuál es la sintaxis correcta para realizar un narrowing casting en Java?'),
	(9, 'El widening casting puede provocar pérdida de información. ¿Verdadero o falso?'),
	(9, 'El narrowing casting se realiza automáticamente. ¿Verdadero o falso?'),
	(9, 'El casting se puede aplicar también a objetos relacionados por herencia. ¿Verdadero o falso?'),
	(9, 'El upcasting en objetos es un tipo de conversión explícita. ¿Verdadero o falso?'),
	(9, 'El narrowing casting requiere usar paréntesis con el tipo de destino. ¿Verdadero o falso?');

-- Lección 10: Clases y métodos en Java
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(10, '¿Qué representa una clase en Java?'),
	(10, '¿Cuál de los siguientes elementos NO forma parte de una clase en Java?'),
	(10, '¿Cuál es la finalidad de un constructor en una clase Java?'),
	(10, '¿Cuál es el tipo de acceso adecuado para los atributos si se quiere aplicar encapsulamiento?'),
	(10, '¿Qué instrucción crea un nuevo objeto de la clase Persona con nombre "Ana" y edad 30?'),
	(10, 'Un objeto es una instancia de una clase. ¿Verdadero o falso?'),
	(10, 'Los métodos pueden devolver valores o no devolver nada. ¿Verdadero o falso?'),
	(10, 'Los atributos de una clase siempre deben ser públicos. ¿Verdadero o falso?'),
	(10, 'El constructor debe tener el mismo nombre que la clase. ¿Verdadero o falso?'),
	(10, 'Un método puede acceder directamente a los atributos de la clase. ¿Verdadero o falso?');

-- Lección 11
INSERT INTO public.exercise (id_lesson, question)
VALUES (11, '¿Qué es la concurrencia en Java y para qué sirve?');

-- Lección 12
INSERT INTO public.exercise (id_lesson, question)
VALUES (12, '¿Cuáles son algunas buenas prácticas de programación en Java?');

-- Lección 13
INSERT INTO public.exercise (id_lesson, question)
VALUES (13, '¿Qué necesitas para empezar a desarrollar aplicaciones web en Java?');

-- Lección 14
INSERT INTO public.exercise (id_lesson, question)
VALUES (14, '¿Qué es un framework en Java y cuál es su utilidad principal?');

-- Lección 15
INSERT INTO public.exercise (id_lesson, question)
VALUES (15, '¿Qué pilares forman parte de la Programación Orientada a Objetos en Java?');

-- Lección 16
INSERT INTO public.exercise (id_lesson, question)
VALUES (16, '¿Qué ventaja principal ofrece el uso de polimorfismo en Java?');

-- Lección 17
INSERT INTO public.exercise (id_lesson, question)
VALUES (17, '¿Qué estructura de datos usarías para almacenar un conjunto sin elementos repetidos?');

-- Lección 18
INSERT INTO public.exercise (id_lesson, question)
VALUES (18, '¿Qué diferencia hay entre una lista y un mapa en Java?');

-- Lección 19
INSERT INTO public.exercise (id_lesson, question)
VALUES (19, '¿Qué es Spring y cuál es su principal objetivo?');

-- Lección 20
INSERT INTO public.exercise (id_lesson, question)
VALUES (20, '¿Qué es la inyección de dependencias en el contexto de Spring?');

-- Lección 21
INSERT INTO public.exercise (id_lesson, question)
VALUES (21, '¿Qué herramienta ofrece Spring Boot para ejecutar rápidamente una aplicación?');

-- Lección 22
INSERT INTO public.exercise (id_lesson, question)
VALUES (22, '¿Qué anotación en Spring Boot se usa para declarar un servicio REST?');

-- Lección 23
INSERT INTO public.exercise (id_lesson, question)
VALUES (23, '¿Cuál es la finalidad principal de un Servlet en Java EE?');

-- Lección 24
INSERT INTO public.exercise (id_lesson, question)
VALUES (24, '¿Qué servidor embebido suele usarse para desplegar aplicaciones Java?');

-- Lección 25
INSERT INTO public.exercise (id_lesson, question)
VALUES (25, '¿Qué funcionalidad brinda un controlador REST en una API?');

-- Lección 26
INSERT INTO public.exercise (id_lesson, question)
VALUES (26, '¿Cómo puede un cliente JavaScript consumir una API REST?');

-- Lección 27
INSERT INTO public.exercise (id_lesson, question)
VALUES (27, '¿Para qué se usa la API JPA en Java?');

-- Lección 28
INSERT INTO public.exercise (id_lesson, question)
VALUES (28, '¿Cuál es la diferencia entre FetchType.EAGER y FetchType.LAZY en Hibernate?');

-- Lección 29
INSERT INTO public.exercise (id_lesson, question)
VALUES (29, '¿Qué librería se utiliza mayormente para pruebas unitarias en Java?');

-- Lección 30
INSERT INTO public.exercise (id_lesson, question)
VALUES (30, '¿Qué beneficios ofrece el uso de Mockito en pruebas unitarias?');

-- Lección 31
INSERT INTO public.exercise (id_lesson, question)
VALUES (31, '¿Cuál es el primer paso para construir una app en JavaFX?');

-- Lección 32
INSERT INTO public.exercise (id_lesson, question)
VALUES (32, '¿Qué componentes permiten interacción con el usuario en JavaFX?');

-- Lección 33
INSERT INTO public.exercise (id_lesson, question)
VALUES (33, '¿Qué instrucción imprime texto en la consola en Java?');

-- Lección 34
INSERT INTO public.exercise (id_lesson, question)
VALUES (34, '¿Qué tipo de dato usarías para almacenar el nombre de una persona?');

-- Lección 35
INSERT INTO public.exercise (id_lesson, question)
VALUES (35, '¿Qué diferencia hay entre <code>let</code> y <code>const</code> en JavaScript?');

-- Lección 36
INSERT INTO public.exercise (id_lesson, question)
VALUES (36, '¿Qué es una función flecha en JavaScript?');

-- Lección 37
INSERT INTO public.exercise (id_lesson, question)
VALUES (37, '¿Qué problema resuelve el uso de clases en JavaScript moderno?');

-- Lección 38
INSERT INTO public.exercise (id_lesson, question)
VALUES (38, '¿Qué palabra clave permite utilizar <em>promesas</em> más fácilmente en JS?');

-- Lección 39
INSERT INTO public.exercise (id_lesson, question)
VALUES (39, '¿Qué objeto especial permite interactuar con el DOM en JavaScript?');

-- Lección 40
INSERT INTO public.exercise (id_lesson, question)
VALUES (40, '¿Qué método se usa para hacer peticiones HTTP en JavaScript moderno?');

-- Lección 41
INSERT INTO public.exercise (id_lesson, question)
VALUES (41, '¿Qué palabra clave define una clase en Java?');

-- Lección 42
INSERT INTO public.exercise (id_lesson, question)
VALUES (42, '¿Qué diferencia hay entre una interface y una clase abstracta?');

-- Lección 43
INSERT INTO public.exercise (id_lesson, question)
VALUES (43, '¿Qué es una aplicación de múltiples capas (multicapa)?');

-- Lección 44
INSERT INTO public.exercise (id_lesson, question)
VALUES (44, '¿Qué permite JDBC en el desarrollo Java?');

-- Lección 45
INSERT INTO public.exercise (id_lesson, question)
VALUES (45, '¿Qué etiqueta define la estructura básica de una página HTML?');

-- Lección 46
INSERT INTO public.exercise (id_lesson, question)
VALUES (46, '¿Qué elemento se usa para insertar un video en HTML5?');

-- Lección 47
INSERT INTO public.exercise (id_lesson, question)
VALUES (47, '¿Qué son las etiquetas <code>ARIA</code> y para qué se usan?');

-- Lección 48
INSERT INTO public.exercise (id_lesson, question)
VALUES (48, '¿Qué elementos ayuda al posicionamiento SEO en HTML semántico?');

-- Lección 49
INSERT INTO public.exercise (id_lesson, question)
VALUES (49, '¿Qué selector CSS se usa para seleccionar todos los elementos <code>&lt;p&gt;</code>?');

-- Lección 50
INSERT INTO public.exercise (id_lesson, question)
VALUES (50, '¿Qué propiedad CSS permite centrar un bloque con <code>margin: auto;</code>?');

-- Lección 51
INSERT INTO public.exercise (id_lesson, question)
VALUES (51, '¿Qué ventajas ofrece Flexbox sobre el modelo de caja tradicional?');

-- Lección 52
INSERT INTO public.exercise (id_lesson, question)
VALUES (52, '¿Qué propiedad CSS permite una transición suave entre estilos?');

-- Lección 53
INSERT INTO public.exercise (id_lesson, question)
VALUES (53, '¿Qué función se usa para imprimir texto en Python?');

-- Lección 54
INSERT INTO public.exercise (id_lesson, question)
VALUES (54, '¿Qué palabra clave se usa en Python para escribir una estructura condicional?');

-- Lección 55
INSERT INTO public.exercise (id_lesson, question)
VALUES (55, '¿Qué función de Pandas se utiliza para cargar un archivo CSV?');

-- Lección 56
INSERT INTO public.exercise (id_lesson, question)
VALUES (56, '¿Qué biblioteca gráfica se usa comúnmente junto con Pandas para visualizaciones?');

-- Lección 57
INSERT INTO public.exercise (id_lesson, question)
VALUES (57, '¿Qué función de PHP se usa para imprimir en pantalla?');

-- Lección 58
INSERT INTO public.exercise (id_lesson, question)
VALUES (58, '¿Qué superglobal de PHP se utiliza para obtener los datos de un formulario enviado por POST?');

-- Lección 59
INSERT INTO public.exercise (id_lesson, question)
VALUES (59, '¿Qué función de PHP permite establecer conexión con una base de datos MySQL?');

-- Lección 60
INSERT INTO public.exercise (id_lesson, question)
VALUES (60, '¿Qué significa el acrónimo CRUD?');

-- Lección 61
INSERT INTO public.exercise (id_lesson, question)
VALUES (61, '¿Cuál es la función principal del método <code>Main()</code> en C#?');

-- Lección 62
INSERT INTO public.exercise (id_lesson, question)
VALUES (62, '¿Qué es un objeto en C# y cómo se declara?');

-- Lección 63
INSERT INTO public.exercise (id_lesson, question)
VALUES (63, '¿Qué permite hacer LINQ en colecciones como listas?');

-- Lección 64
INSERT INTO public.exercise (id_lesson, question)
VALUES (64, '¿Qué tipo de aplicación permite crear Windows Forms?');

-- Lección 65
INSERT INTO public.exercise (id_lesson, question)
VALUES (65, '¿Qué palabra clave define un tipo en TypeScript?');

-- Lección 66
INSERT INTO public.exercise (id_lesson, question)
VALUES (66, '¿Para qué se utiliza una interfaz (<code>interface</code>) en TypeScript?');

-- Lección 67
INSERT INTO public.exercise (id_lesson, question)
VALUES (67, '¿Qué archivo contiene el metadato y configuración de un proyecto Angular?');

-- Lección 68
INSERT INTO public.exercise (id_lesson, question)
VALUES (68, '¿Qué servicio Angular permite manejar peticiones HTTP?');

-- Lección 69
INSERT INTO public.exercise (id_lesson, question)
VALUES (69, '¿Qué línea imprime texto en Ruby?');

-- Lección 70
INSERT INTO public.exercise (id_lesson, question)
VALUES (70, '¿Qué estructura proporciona Ruby on Rails para organizar una web MVC?');

-- Lección 71
INSERT INTO public.exercise (id_lesson, question)
VALUES (71, '¿Qué palabra clave se usa para declarar una constante en Swift?');

-- Lección 72
INSERT INTO public.exercise (id_lesson, question)
VALUES (72, '¿Qué herramienta de desarrollo visual ofrece Apple para diseñar interfaces en iOS?');

