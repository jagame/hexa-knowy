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


-- CURSO 2: ENTRECISTA TÉCNICA JAVA
-- Lección 1: Conceptos clave de POO en Java
INSERT INTO public.exercise (id_lesson, question)
VALUES
	(1, '¿Cuál de los siguientes es uno de los pilares fundamentales de la programación orientada a objetos?'),
	(1, '¿Qué principio de la POO se refiere a ocultar detalles internos y mostrar solo lo necesario?'),
	(1, '¿Qué palabra clave en Java se utiliza para declarar una clase abstracta?'),
	(1, '¿Qué sucede cuando una clase hereda de otra en Java?'),
	(1, '¿Cuál es una ventaja del polimorfismo en la programación orientada a objetos?'),
	(1, 'Encapsulación permite ocultar los atributos internos de una clase. ¿Verdadero o falso?'),
	(1, 'La herencia impide la reutilización de código en Java. ¿Verdadero o falso?'),
	(1, 'En Java, una clase abstracta puede tener métodos sin cuerpo. ¿Verdadero o falso?'),
	(1, 'Polimorfismo significa que un mismo método puede tener comportamientos distintos. ¿Verdadero o falso?'),
	(1, 'La abstracción muestra todos los detalles de una clase al usuario. ¿Verdadero o falso?');
