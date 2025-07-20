TRUNCATE TABLE public.option RESTART IDENTITY CASCADE;
-- JAVA BASICO
-- LECCION 2
-- Ejercicio 1: ¿Qué significa que Java sea "multiplataforma"?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (1, 'Que solo se puede usar en Windows', false),
	   (1, 'Que puede ejecutarse en distintos sistemas sin cambiar el código', true),
	   (1, 'Que necesita diferentes versiones para cada sistema operativo', false),
	   (1, 'Que no necesita ser compilado', false);

-- Ejercicio 2: ¿Cuál es el nombre del componente que permite ejecutar el bytecode en cualquier sistema operativo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (2, 'JDK', false),
	   (2, 'JVM', true),
	   (2, 'JRE', false),
	   (2, 'IDE', false);

-- Ejercicio 3: Verdadero o falso: En Java, puedes ejecutar directamente el archivo .java sin compilarlo antes.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (3, 'Verdadero', true),
	   (3, 'Falso', false);

-- Ejercicio 4: ¿Qué papel cumple el JDK (Java Development Kit)?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (4, 'Ejecuta los programas Java', false),
	   (4, 'Controla los errores en tiempo de ejecución', false),
	   (4, 'Compila el código fuente en bytecode', true),
	   (4, 'Es un editor de texto para programadores', false);

-- Ejercicio 5: ¿Qué archivo se genera tras compilar un programa Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (5, '.exe', false),
	   (5, '.class', true),
	   (5, '.txt', false),
	   (5, '.jar', false);

-- Ejercicio 6: Verdadero o falso: Java es un lenguaje débilmente tipado, por eso no es necesario declarar el tipo de las variables.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (6, 'Verdadero', false),
	   (6, 'Falso', true);

-- Ejercicio 7: ¿Qué ventaja principal ofrece la orientación a objetos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (7, 'Hace que el programa sea más rápido', false),
	   (7, 'Permite usar emojis en el código', false),
	   (7, 'Organiza el código de forma modular y reutilizable', true),
	   (7, 'Elimina la necesidad de usar funciones', false);

-- Ejercicio 8: Verdadero o falso: La JVM traduce el código fuente de Java directamente al lenguaje máquina.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (8, 'Verdadero', false),
	   (8, 'Falso', true);

-- Ejercicio 9: ¿Qué extensión tiene un archivo que contiene código fuente en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (9, '.class', false),
	   (9, '.xml', false),
	   (9, '.java', true),
	   (9, '.js', false);

-- Ejercicio 10: Verdadero o falso: Para programar en Java solo necesitas tener instalada la JVM.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES (10, 'Falso', true),
	   (10, 'Verdadero', false);


-- LECCION 3 - VARIABLES. ESTRUCTURA BASICA.
-- Ejercicio 1: ¿Qué es una variable en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(11, 'Un espacio de memoria donde se guarda un dato', true),
	(11, 'Un tipo de error', false),
	(11, 'Un comando del sistema', false),
	(11, 'Una función que imprime mensajes', false);

-- Ejercicio 2: ¿Qué se necesita declarar al crear una variable en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(12, 'Solo el nombre', false),
	(12, 'Solo el valor', false),
	(12, 'El tipo, nombre y valor (opcionalmente)', true),
	(12, 'Solo el tipo', false);

-- Ejercicio 3: Verdadero o falso: Una variable puede cambiar su valor a lo largo del tiempo.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(13, 'Verdadero', true),
	(13, 'Falso', false);

-- Ejercicio 4: ¿Cuál es el tipo adecuado para almacenar números enteros en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(14, 'int', true),
	(14, 'float', false),
	(14, 'String', false),
	(14, 'boolean', false);

-- Ejercicio 5: ¿Qué significa que Java sea fuertemente tipado?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(15, 'Que el compilador permite omitir los tipos', false),
	(15, 'Que las variables no tienen tipo', false),
	(15, 'Que se debe declarar el tipo de cada variable de forma clara', true),
	(15, 'Que los tipos se asignan en tiempo de ejecución', false);

-- Ejercicio 6: ¿Cuál de estas opciones representa correctamente una declaración de variable?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(16, 'int edad = 30;', true),
	(16, 'edad int = 30;', false),
	(16, '30 = int edad;', false),
	(16, 'variable edad = int;', false);

-- Ejercicio 7: Verdadero o falso: Se puede declarar una variable sin indicar su tipo de dato.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(17, 'Verdadero', false),
	(17, 'Falso', true);

-- Ejercicio 8: ¿Qué representa el nombre de la variable?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(18, 'El tipo de dato que almacena', false),
	(18, 'El valor que se le asigna', false),
	(18, 'El identificador que usamos para referirnos al dato', true),
	(18, 'Una palabra reservada de Java', false);

-- Ejercicio 9: ¿Qué parte de esta línea es el valor?: <code>int edad = 30;</code>
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(19, 'int', false),
	(19, 'edad', false),
	(19, '30', true),
	(19, '=', false);

-- Ejercicio 10: ¿Qué ocurre si no inicializas una variable en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(20, 'Java le asigna automáticamente un valor', false),
	(20, 'El programa puede fallar o mostrar un error de compilación', true),
	(20, 'Se interpreta como una constante', false),
	(20, 'No hay diferencia, es opcional inicializar', false);

--LECCION 4: VARIABLES. TIPOS PRIMITIVOS.
-- Ejercicio 1: ¿Qué son los tipos primitivos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(21, 'Datos básicos que se almacenan directamente en memoria', true),
	(21, 'Objetos con métodos asociados', false),
	(21, 'Archivos de configuración de Java', false),
	(21, 'Librerías externas de funciones matemáticas', false);

-- Ejercicio 2: ¿Cuál de los siguientes tipos se utiliza para representar números enteros?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(22, 'float', false),
	(22, 'char', false),
	(22, 'int', true),
	(22, 'double', false);

-- Ejercicio 3: ¿Qué tipo usarías para representar un número con decimales y mayor precisión?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(23, 'int', false),
	(23, 'float', false),
	(23, 'boolean', false),
	(23, 'double', true);

-- Ejercicio 4: ¿Cómo se representa un carácter en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(24, 'Con comillas dobles', false),
	(24, 'Con una barra invertida', false),
	(24, 'Con comillas simples', true),
	(24, 'Con paréntesis', false);

-- Ejercicio 5: ¿Cuál es el tipo primitivo para almacenar valores booleanos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(25, 'int', false),
	(25, 'truefalse', false),
	(25, 'boolean', true),
	(25, 'char', false);

-- Ejercicio 6: ¿Qué valor es correcto para inicializar un float en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(26, 'float x = 1.75;', false),
	(26, 'float x = 1.75f;', true),
	(26, 'float x = "1.75";', false),
	(26, 'float x = f1.75;', false);

-- Ejercicio 7: Verdadero o falso: Los tipos primitivos en Java tienen métodos incorporados.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(27, 'Verdadero', false),
	(27, 'Falso', true);

-- Ejercicio 8: ¿Qué ocurre si no añades una "f" al final de un número decimal al declararlo como float?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(28, 'El programa compila normalmente', false),
	(28, 'El número se interpreta como int', false),
	(28, 'Se genera un error de compilación', true),
	(28, 'El número se convierte automáticamente en String', false);

-- Ejercicio 9: ¿Qué valor devuelve un boolean cuando una condición se cumple?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(29, 'true', true),
	(29, 'yes', false),
	(29, '1', false),
	(29, 'correcto', false);

-- Ejercicio 10: ¿Cuál de estos tipos primitivos representa el menor nivel de precisión para números decimales?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(30, 'int', false),
	(30, 'double', false),
	(30, 'float', true),
	(30, 'boolean', false);

-- LECCION 5: VARIABLES. TIPOS NO PRIMITIVOS.
-- Ejercicio 1: ¿Qué son los tipos no primitivos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(31, 'Tipos de datos complejos que no están integrados directamente en el lenguaje', true),
	(31, 'Variables que solo pueden contener números', false),
	(31, 'Métodos que transforman datos primitivos', false),
	(31, 'Tipos especiales para bases de datos', false);

-- Ejercicio 2: ¿Cuál de los siguientes ejemplos representa un tipo no primitivo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(32, '<code>int</code>', false),
	(32, '<code>char</code>', false),
	(32, '<code>String</code>', true),
	(32, '<code>double</code>', false);

-- Ejercicio 3: ¿Qué característica diferencia a los tipos no primitivos de los primitivos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(33, 'Tienen nombres más largos', false),
	(33, 'No se pueden utilizar en operaciones matemáticas', false),
	(33, 'Tienen propiedades y métodos asociados', true),
	(33, 'Solo funcionan en sistemas operativos de 64 bits', false);

-- Ejercicio 4: ¿Cuál de estas afirmaciones sobre los tipos no primitivos es correcta?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(34, 'No se pueden modificar una vez creados', false),
	(34, 'Permiten almacenar y manipular información compleja', true),
	(34, 'Solo funcionan con números enteros', false),
	(34, 'Se comportan igual que los tipos primitivos', false);

-- Ejercicio 5: ¿Qué tipo se usa comúnmente en Java para representar textos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(35, 'int', false),
	(35, 'float', false),
	(35, 'boolean', false),
	(35, 'String', true);

-- Ejercicio 6: ¿Qué es un método en el contexto de los tipos no primitivos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(36, 'Un número que puede ser dividido', false),
	(36, 'Una acción que puede realizar un objeto', true),
	(36, 'Una parte de un número decimal', false),
	(36, 'Una dirección de memoria', false);

-- Ejercicio 7: ¿Qué es una propiedad en un tipo no primitivo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(37, 'Un número aleatorio asociado al tipo', false),
	(37, 'Un valor de tipo booleano que se puede modificar', false),
	(37, 'Un dato que describe una característica del objeto', true),
	(37, 'Un tipo de error común en programación', false);

-- Ejercicio 8: ¿Qué se puede hacer con un tipo no primitivo como <code>String</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(38, 'Convertirlo a mayúsculas', true),
	(38, 'Dividirlo como si fuera un número', false),
	(38, 'Sumarlo directamente a un float', false),
	(38, 'Transformarlo a tipo boolean', false);

-- Ejercicio 9: ¿Cuál es la ventaja principal de los tipos no primitivos frente a los primitivos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(39, 'Permiten ahorrar memoria', false),
	(39, 'Son más rápidos que los primitivos', false),
	(39, 'Permiten trabajar con estructuras de datos complejas', true),
	(39, 'Solo se usan para diseño gráfico', false);

-- Ejercicio 10: ¿Cuál de los siguientes NO es un tipo no primitivo en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(40, 'String', false),
	(40, 'Array', false),
	(40, 'int', true),
	(40, 'BigDecimal', false);

-- LECCIÓN 6: CONSTANTES Y BUENAS PRÁCTICAS EN EL MANEJO DE VARIABLES
-- Ejercicio 51: ¿Qué palabra clave se utiliza en Java para declarar una constante?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (51, '<code>const</code>', false),
	 (51, '<code>let</code>', false),
	 (51, '<code>final</code>', true),
	 (51, '<code>static</code>', false);

-- Ejercicio 52: ¿Qué sucede si intentas modificar una constante en Java después de haberla declarado?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (52, 'Se elimina la constante automáticamente', false),
	 (52, 'El programa se comporta de manera impredecible', false),
	 (52, 'Se lanza un error de compilación', true),
	 (52, 'Se crea una nueva constante con ese valor', false);

-- Ejercicio 53: ¿Cuál de los siguientes nombres sigue la convención para constantes en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (53, 'pi', false),
	 (53, 'valorMaximo', false),
	 (53, 'MAX_USUARIOS', true),
	 (53, 'nombre_completo', false);

-- Ejercicio 54: ¿Cuál es una razón para usar constantes en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (54, 'Mejoran el rendimiento de la CPU', false),
	 (54, 'Permiten modificar variables fácilmente', false),
	 (54, 'Evitan errores al mantener valores fijos inalterables', true),
	 (54, 'Hacen que el código sea más corto', false);

-- Ejercicio 55: ¿Qué convención se utiliza en Java para nombrar variables?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (55, 'snake_case', false),
	 (55, 'UPPER_CASE', false),
	 (55, 'lowerCamelCase', true),
	 (55, 'kebab-case', false);

-- Ejercicio 56: ¿Cuál de los siguientes nombres de variable sigue la convención lowerCamelCase?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (56, 'NombreCompleto', false),
	 (56, 'nombre_completo', false),
	 (56, 'nombreCompleto', true),
	 (56, 'nombre-completo', false);

-- Ejercicio 57: ¿Qué tipo de valores se recomienda almacenar como constantes?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (57, 'Solo nombres de personas', false),
	 (57, 'Cualquier valor que cambie constantemente', false),
	 (57, 'Valores fijos como PI, límites o mensajes de error', true),
	 (57, 'Variables que se usan temporalmente', false);

-- Ejercicio 58: ¿Cuál es un beneficio de usar nombres claros y representativos en las variables?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (58, 'Ocultan la funcionalidad del programa', false),
	 (58, 'Hacen que el código sea más difícil de leer', false),
	 (58, 'Facilitan la comprensión y el mantenimiento del código', true),
	 (58, 'Permiten ejecutar el código más rápido', false);

-- Ejercicio 59: ¿Qué valor por defecto tiene una variable de tipo <code>boolean</code> en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (59, '<code>true</code>', false),
	 (59, '<code>1</code>', false),
	 (59, '<code>false</code>', true),
	 (59, '<code>null</code>', false);

-- Ejercicio 60: ¿Cuál de estas opciones representa correctamente la declaración de una constante?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	 (60, '<code>final double PI = 3.14;</code>', true),
	 (60, '<code>const double PI = 3.14;</code>', false),
	 (60, '<code>double final PI = 3.14;</code>', false),
	 (60, '<code>double PI = 3.14 final;</code>', false);


-- LECCIÓN 7: OPERADORES ARITMÉTICOS Y LÓGICOS

-- Ejercicio 1: ¿Qué operador se utiliza para obtener el resto de una división?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(61, '<code>/</code>', false),
	(61, '<code>%</code>', true),
	(61, '<code>*</code>', false),
	(61, '<code>#</code>', false);

-- Ejercicio 2: ¿Qué operador se usa en Java para comparar si dos valores son iguales?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(62, '<code>=</code>', false),
	(62, '<code>!=</code>', false),
	(62, '<code>==</code>', true),
	(62, '<code>===</code>', false);

-- Ejercicio 3: ¿Cuál es el resultado de la operación <code>10 / 3</code> en enteros?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(63, '<code>3.33</code>', false),
	(63, '<code>3</code>', true),
	(63, '<code>3.0</code>', false),
	(63, '<code>Error</code>', false);

-- Ejercicio 4: ¿Qué operador lógico devuelve <code>true</code> solo si ambas condiciones son verdaderas?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(64, '<code>&&</code>', true),
	(64, '<code>||</code>', false),
	(64, '<code>==</code>', false),
	(64, '<code>!</code>', false);

-- Ejercicio 5: ¿Cuál es el resultado de la expresión: <code>(5 > 3) && (4 != 2)</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(65, '<code>true</code>', true),
	(65, '<code>false</code>', false),
	(65, '<code>undefined</code>', false),
	(65, '<code>null</code>', false);

-- Ejercicio 6: ¿Cuál es el resultado de la operación <code>10 % 4</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(66, '<code>2</code>', true),
	(66, '<code>0</code>', false),
	(66, '<code>1</code>', false),
	(66, '<code>4</code>', false);

-- Ejercicio 7: ¿Cuál de estas expresiones devuelve <code>true</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(67, '<code>(5 > 2) && (1 > 3)</code>', false),
	(67, '<code>(2 == 2) || (3 == 4)</code>', true),
	(67, '<code>(3 < 1) && (4 < 5)</code>', false),
	(67, '<code>!(true)</code>', false);

-- Ejercicio 8: ¿Qué hace el operador lógico <code>!</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(68, 'Convierte el resultado a entero', false),
	(68, 'Invierte el valor lógico (true a false y viceversa)', true),
	(68, 'Realiza una comparación', false),
	(68, 'Concatena dos condiciones', false);

-- Ejercicio 9: ¿Qué operador se utiliza para sumar dos números en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(69, '<code>+</code>', true),
	(69, '<code>&</code>', false),
	(69, '<code>#</code>', false),
	(69, '<code>@</code>', false);

-- Ejercicio 10: ¿Cuál es el resultado de: <code>(3 > 1) || (2 < 0)</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(70, '<code>false</code>', false),
	(70, '<code>true</code>', true),
	(70, '<code>null</code>', false),
	(70, '<code>undefined</code>', false);

-- LECCIÓN 7: CONCATENACIÓN DE CADENAS

-- Ejercicio 81: ¿Qué operador se usa en Java para concatenar cadenas?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(81, '<code>+</code>', true),
	(81, '<code>&</code>', false),
	(81, '<code>*</code>', false),
	(81, '<code>concat()</code>', false);

-- Ejercicio 82: ¿Qué resultado produce esta operación? "Hola" + ", " + "Ana"
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(82, 'Hola, Ana', true),
	(82, 'Hola Ana', false),
	(82, 'Hola+Ana', false),
	(82, 'Error', false);

-- Ejercicio 83: ¿Qué tipo de dato permite realizar concatenación con el operador +?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(83, 'String', true),
	(83, 'int', false),
	(83, 'boolean', false),
	(83, 'char', false);

-- Ejercicio 84: ¿Qué sucede si se concatena un String con un número?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(84, 'El número se convierte automáticamente en texto', true),
	(84, 'Java muestra un error de compilación', false),
	(84, 'Se ignora el número', false),
	(84, 'Se ejecuta una suma matemática', false);

-- Ejercicio 85: ¿Cuál afirmación es correcta sobre la concatenación?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(85, 'Permite unir varias cadenas usando el operador +', true),
	(85, 'Solo se puede usar con dos cadenas a la vez', false),
	(85, 'No funciona con variables, solo con literales', false),
	(85, 'Requiere el uso obligatorio del método concat()', false);

-- Ejercicio 86: El operador + también puede sumar números. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(86, 'Verdadero', true),
	(86, 'Falso', false);

-- Ejercicio 87: Java lanza un error si se concatena un número con una cadena. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(87, 'Verdadero', false),
	(87, 'Falso', true);

-- Ejercicio 88: Es posible usar variables dentro de una concatenación. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(88, 'Verdadero', true),
	(88, 'Falso', false);

-- Ejercicio 89: La concatenación puede incluir resultados de operaciones matemáticas. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(89, 'Verdadero', true),
	(89, 'Falso', false);

-- Ejercicio 90: La concatenación es útil para construir mensajes dinámicos. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(90, 'Verdadero', true),
	(90, 'Falso', false);

-- LECCIÓN 9: CONVERSIÓN DE TIPOS EN JAVA (CASTING)

-- Ejercicio 91: ¿Qué tipo de conversión en Java es automática y no requiere casting?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(91, 'Widening casting', true),
	(91, 'Narrowing casting', false),
	(91, 'Downcasting', false),
	(91, 'Upcasting', false);

-- Ejercicio 92: ¿Cuál de las siguientes afirmaciones describe correctamente el narrowing casting?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(92, 'Es una conversión explícita de un tipo más grande a uno más pequeño', true),
	(92, 'Es una conversión automática de un tipo más pequeño a uno más grande', false),
	(92, 'Solo se usa entre tipos booleanos y enteros', false),
	(92, 'No puede provocar pérdida de datos', false);

-- Ejercicio 93: ¿Qué palabra describe la conversión de un tipo más pequeño a uno más grande?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(93, 'Widening', true),
	(93, 'Narrowing', false),
	(93, 'Shrinking', false),
	(93, 'Reducing', false);

-- Ejercicio 94: ¿Qué sucede cuando se realiza un narrowing casting de <code>double</code> a <code>int</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(94, 'Se pierde la parte decimal', true),
	(94, 'Se redondea automáticamente al número más cercano', false),
	(94, 'Java lanza un error de compilación', false),
	(94, 'El valor se convierte en un String', false);

-- Ejercicio 95: ¿Cuál es la sintaxis correcta para realizar un narrowing casting en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(95, '<code>int x = (int) myDouble;</code>', true),
	(95, '<code>int x = myDouble as int;</code>', false),
	(95, '<code>int x = cast(int) myDouble;</code>', false),
	(95, '<code>int x = myDouble.toInt();</code>', false);

-- Ejercicio 96: El widening casting puede provocar pérdida de información. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(96, 'Verdadero', false),
	(96, 'Falso', true);

-- Ejercicio 97: El narrowing casting se realiza automáticamente. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(97, 'Verdadero', false),
	(97, 'Falso', true);

-- Ejercicio 98: El casting se puede aplicar también a objetos relacionados por herencia. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(98, 'Verdadero', true),
	(98, 'Falso', false);

-- Ejercicio 99: El upcasting en objetos es un tipo de conversión explícita. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(99, 'Verdadero', false),
	(99, 'Falso', true);

-- Ejercicio 100: El narrowing casting requiere usar paréntesis con el tipo de destino. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(100, 'Verdadero', true),
	(100, 'Falso', false);

-- LECCIÓN 10: CLASES Y MÉTODOS EN JAVA

-- Ejercicio 101: ¿Qué representa una clase en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(101, 'Una plantilla para definir atributos y métodos de un objeto', true),
	(101, 'Un archivo de texto que guarda datos', false),
	(101, 'Una variable especial para hacer cálculos', false),
	(101, 'Un contenedor para instrucciones condicionales', false);

-- Ejercicio 102: ¿Cuál de los siguientes elementos NO forma parte de una clase en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(102, 'Sentencias <code>if</code> sueltas', true),
	(102, 'Constructores', false),
	(102, 'Atributos', false),
	(102, 'Métodos', false);

-- Ejercicio 103: ¿Cuál es la finalidad de un constructor en una clase Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(103, 'Inicializar objetos de esa clase', true),
	(103, 'Eliminar objetos antiguos', false),
	(103, 'Realizar operaciones matemáticas', false),
	(103, 'Escribir datos en consola', false);

-- Ejercicio 104: ¿Cuál es el tipo de acceso adecuado para los atributos si se quiere aplicar encapsulamiento?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(104, '<code>private</code>', true),
	(104, '<code>public</code>', false),
	(104, '<code>static</code>', false),
	(104, '<code>protected</code>', false);

-- Ejercicio 105: ¿Qué instrucción crea un nuevo objeto de la clase Persona con nombre "Ana" y edad 30?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(105, '<code>Persona ana = new Persona("Ana", 30);</code>', true),
	(105, '<code>Persona.ana("Ana", 30);</code>', false),
	(105, '<code>new Persona("Ana", 30) = ana;</code>', false),
	(105, '<code>Persona = Persona("Ana", 30);</code>', false);

-- Ejercicio 106: Un objeto es una instancia de una clase. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(106, 'Verdadero', true),
	(106, 'Falso', false);

-- Ejercicio 107: Los métodos pueden devolver valores o no devolver nada. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(107, 'Verdadero', true),
	(107, 'Falso', false);

-- Ejercicio 108: Los atributos de una clase siempre deben ser públicos. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(108, 'Verdadero', false),
	(108, 'Falso', true);

-- Ejercicio 109: El constructor debe tener el mismo nombre que la clase. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(109, 'Verdadero', true),
	(109, 'Falso', false);

-- Ejercicio 110: Un método puede acceder directamente a los atributos de la clase. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(110, 'Verdadero', true),
	(110, 'Falso', false);
