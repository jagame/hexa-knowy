TRUNCATE TABLE public.option RESTART IDENTITY CASCADE;

-- JAVA BÁSICO

-- LECCIÓN 1: INTRODUCCIÓN AL CURSO

-- Ejercicio 1: ¿Qué lenguajes tienen influencia o relación directa con Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(1, 'C y C++', true),
	(1, 'Python y Ruby', false),
	(1, 'HTML y CSS', false),
	(1, 'Pascal y Basic', false);

-- Ejercicio 2: ¿Qué ejemplos de aplicaciones se mencionan que utilizan Java en su backend?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(2, 'Netflix, Amazon, Spotify y LinkedIn', true),
	(2, 'Facebook, Twitter, TikTok y YouTube', false),
	(2, 'WhatsApp, Telegram y Signal', false),
	(2, 'Google Maps y Gmail', false);

-- Ejercicio 3: ¿Qué empresa usó Java completamente para su versión original del juego Minecraft?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(3, 'Mojang', true),
	(3, 'Microsoft', false),
	(3, 'Google', false),
	(3, 'Oracle', false);

-- Ejercicio 4: ¿Cuál es uno de los objetivos principales de este curso de Java básico?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(4, 'Aprender robótica aplicada en Java', false),
	(4, 'Estudiar fundamentos esenciales del lenguaje Java', true),
	(4, 'Dominar frameworks complejos como Spring', false),
	(4, 'Aprender HTML desde cero', false);

-- Ejercicio 5: ¿Qué mensaje transmite el curso sobre cometer errores mientras aprendes a programar?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(5, 'Que debes evitarlos a toda costa', false),
	(5, 'Que son una parte natural del aprendizaje', true),
	(5, 'Que se deben corregir con inteligencia artificial', false),
	(5, 'Que indican que la programación no es lo tuyo', false);

-- Ejercicio 6: ¿Por qué puede considerarse útil aprender al menos un lenguaje de programación?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(6, 'Porque es obligatorio para todos los trabajos', false),
	(6, 'Porque mejora la memoria a corto plazo', false),
	(6, 'Porque es cada vez una habilidad más necesaria en el mundo digital', true),
	(6, 'Porque sustituye a los idiomas en el currículum', false);

-- Ejercicio 7: ¿Qué ventaja se menciona de Java para el desarrollo de videojuegos como Minecraft?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(7, 'Puede ejecutarse sin límite de memoria', false),
	(7, 'Soporta VR de forma nativa', false),
	(7, 'Permite personalización a través de mods', true),
	(7, 'Utiliza HTML para crear gráficos 3D', false);

-- Ejercicio 8: Verdadero o falso: Aprender a programar significa que debes evitar errores a toda costa.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(8, 'Verdadero', false),
	(8, 'Falso', true);

-- Ejercicio 9: ¿Qué áreas del desarrollo de software se mencionan como posibles aplicaciones de Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(9, 'Sitios web estáticos', false),
	(9, 'Aplicaciones web, móviles y videojuegos', true),
	(9, 'Desarrollo de hojas de cálculo', false),
	(9, 'Diseño gráfico 2D', false);

-- Ejercicio 10: ¿Qué actitud recomienda el curso adoptar al estudiar programación por primera vez?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(10, 'Rendirse rápido ante la dificultad', false),
	(10, 'Estudiar solo sin ayuda ni recursos', false),
	(10, 'Tener paciencia y aceptar los errores como parte del proceso', true),
	(10, 'Evitar los lenguajes difíciles como Java', false);

-- LECCIÓN 3: Variables. Estructura básica.

-- Ejercicio 1 (ID 21): ¿Qué es una variable en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(21, 'Una constante que nunca cambia', false),
	(21, 'Una caja donde puedes guardar y modificar un dato', true),
	(21, 'Una función del sistema operativo', false),
	(21, 'Una copia del código Java', false);

-- Ejercicio 2 (ID 22): ¿Qué se necesita declarar al crear una variable en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(22, 'Solo el nombre', false),
	(22, 'El tipo de dato y el nombre', true),
	(22, 'El valor y el nombre, sin tipo', false),
	(22, 'Solo el valor', false);

-- Ejercicio 3 (ID 23): Verdadero o falso: Una variable puede cambiar su valor a lo largo del tiempo.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(23, 'Verdadero', true),
	(23, 'Falso', false);

-- Ejercicio 4 (ID 24): ¿Cuál es el tipo adecuado para almacenar números enteros en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(24, 'String', false),
	(24, 'int', true),
	(24, 'float', false),
	(24, 'char', false);

-- Ejercicio 5 (ID 25): ¿Qué significa que Java sea fuertemente tipado?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(25, 'Se pueden cambiar los tipos sin error', false),
	(25, 'Las variables deben ser declaradas con un tipo específico y no permiten cambios de tipo sin conversión', true),
	(25, 'Java detecta virus en los tipos', false),
	(25, 'Solo se admiten tipos primitivos', false);

-- Ejercicio 6 (ID 26): ¿Cuál de estas opciones representa correctamente una declaración de variable?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(26, 'edad int = 30;', false),
	(26, 'int edad = 30;', true),
	(26, 'int = edad 30;', false),
	(26, 'variable edad = 30;', false);

-- Ejercicio 7 (ID 27): Verdadero o falso: Se puede declarar una variable sin indicar su tipo de dato.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(27, 'Verdadero', false),
	(27, 'Falso', true);

-- Ejercicio 8 (ID 28): ¿Qué representa el nombre de la variable?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(28, 'El tipo de dato', false),
	(28, 'El valor almacenado', false),
	(28, 'El identificador que se usa para referirse a su valor', true),
	(28, 'El contenido de la memoria RAM', false);

-- Ejercicio 9 (ID 29): ¿Qué parte de esta línea es el valor?: `int edad = 30;`
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(29, 'int', false),
	(29, 'edad', false),
	(29, '30', true),
	(29, '=', false);

-- Ejercicio 10 (ID 30): ¿Qué ocurre si no inicializas una variable en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(30, 'Su valor es 0 automáticamente', false),
	(30, 'El programa la ignora por completo', false),
	(30, 'Puede provocar un error de compilación si se intenta usar sin asignarle valor', true),
	(30, 'Será automáticamente igual a null, siempre', false);

-- LECCIÓN 4: Variables. Tipos primitivos.

-- Ejercicio 1 (ID 31): ¿Qué son los tipos primitivos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(31, 'Clases que heredan de Object', false),
	(31, 'Tipos de datos básicos como int, float, boolean, etc.', true),
	(31, 'Interfaces predefinidas por el programador', false),
	(31, 'Funciones de bajo nivel', false);

-- Ejercicio 2 (ID 32): ¿Cuál de los siguientes tipos se utiliza para representar números enteros?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(32, 'String', false),
	(32, 'int', true),
	(32, 'float', false),
	(32, 'boolean', false);

-- Ejercicio 3 (ID 33): ¿Qué tipo usarías para representar un número con decimales y mayor precisión?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(33, 'float', false),
	(33, 'double', true),
	(33, 'int', false),
	(33, 'char', false);

-- Ejercicio 4 (ID 34): ¿Cómo se representa un carácter en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(34, 'Usando comillas dobles (")', false),
	(34, 'Usando comillas simples ('')', true),
    (34, 'Con doble barra invertida (\\)', false),
    (34, 'Con el símbolo #', false);

-- Ejercicio 5 (ID 35): ¿Cuál es el tipo primitivo para almacenar valores booleanos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
    (35, 'bool', false),
    (35, 'Boolean', false),
    (35, 'boolean', true),
    (35, 'bit', false);

-- Ejercicio 6 (ID 36): ¿Qué valor es correcto para inicializar un `float` en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
    (36, '3.14', false),
    (36, '3.14f', true),
    (36, '"3.14"', false),
    (36, 'float(3.14)', false);

-- Ejercicio 7 (ID 37): Verdadero o falso: Los tipos primitivos en Java tienen métodos incorporados.
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
    (37, 'Verdadero', false),
    (37, 'Falso', true);

-- Ejercicio 8 (ID 38): ¿Qué ocurre si no añades una "f" al final de un número decimal al declararlo como `float`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
    (38, 'Se convierte automáticamente a float', false),
    (38, 'El compilador lanzará un error por incompatibilidad de tipos', true),
    (38, 'El valor se redondea a entero', false),
    (38, 'No ocurre nada', false);

-- Ejercicio 9 (ID 39): ¿Qué valor devuelve un `boolean` cuando una condición se cumple?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
    (39, 'false', false),
    (39, 'null', false),
    (39, 'true', true),
    (39, '1', false);

-- Ejercicio 10 (ID 40): ¿Cuál de estos tipos primitivos representa el menor nivel de precisión para números decimales?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
    (40, 'double', false),
    (40, 'int', false),
    (40, 'float', true),
    (40, 'String', false);

-- LECCIÓN 5: Variables. Tipos no primitivos

-- Ejercicio 1 (ID 41): ¿Qué son los tipos no primitivos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(41, 'Tipos básicos como int, double, char', false),
	(41, 'Clases u objetos que no son tipos incorporados del lenguaje', true),
	(41, 'Errores lógicos en tiempo de compilación', false),
	(41, 'Funciones booleanas', false);

-- Ejercicio 2 (ID 42): ¿Cuál de los siguientes ejemplos representa un tipo no primitivo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(42, 'int', false),
	(42, 'String', true),
	(42, 'boolean', false),
	(42, 'char', false);

-- Ejercicio 3 (ID 43): ¿Qué característica diferencia a los tipos no primitivos de los primitivos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(43, 'Son más lentos', false),
	(43, 'Tienen métodos y propiedades asociados', true),
	(43, 'No pueden ser utilizados en estructuras de control', false),
	(43, 'Se almacenan fuera de la memoria', false);

-- Ejercicio 4 (ID 44): ¿Cuál de estas afirmaciones sobre los tipos no primitivos es correcta?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(44, 'No pueden almacenar datos', false),
	(44, 'Pueden tener métodos que los manipulan', true),
	(44, 'Se usan solo en bases de datos', false),
	(44, 'Solo funcionan con enteros', false);

-- Ejercicio 5 (ID 45): ¿Qué tipo se usa comúnmente en Java para representar textos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(45, 'char', false),
	(45, 'String', true),
	(45, 'text', false),
	(45, 'List', false);

-- Ejercicio 6 (ID 46): ¿Qué diferencia principal existe entre un tipo primitivo y uno no primitivo en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(46, 'Solo los primitivos pueden tener métodos', false),
	(46, 'Los no primitivos pueden tener propiedades y métodos', true),
	(46, 'Los primitivos son más grandes', false),
	(46, 'Los primitivos se declaran con mayúscula', false);

-- Ejercicio 7 (ID 47): ¿Qué es una propiedad en un tipo no primitivo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(47, 'Una característica o atributo de un objeto', true),
	(47, 'Una constante lógica', false),
	(47, 'Un operador matemático', false),
	(47, 'Una función anónima', false);

-- Ejercicio 8 (ID 48): ¿Qué se puede hacer con un tipo no primitivo como `String`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(48, 'Suma y resta de números', false),
	(48, 'Utilizar métodos como .length() o .toUpperCase()', true),
	(48, 'Declarar constantes lógicas', false),
	(48, 'Ejecutar instrucciones del sistema', false);

-- Ejercicio 9 (ID 49): ¿Cuál es la ventaja principal de los tipos no primitivos frente a los primitivos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(49, 'No se almacenan en memoria', false),
	(49, 'Permiten funcionalidades adicionales a través de métodos', true),
	(49, 'Son más rápidos que los tipos primitivos', false),
	(49, 'Solo existen en Java 17', false);

-- Ejercicio 10 (ID 50): ¿Cuál de los siguientes NO es un tipo no primitivo en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(50, 'List', false),
	(50, 'String', false),
	(50, 'int', true),
	(50, 'Array', false);
-- LECCIÓN 6: Constantes y buenas prácticas en el manejo de variables

-- Ejercicio 1 (ID 51): ¿Qué palabra clave se utiliza en Java para declarar una constante?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(51, 'final', true),
	(51, 'const', false),
	(51, 'static', false),
	(51, 'let', false);

-- Ejercicio 2 (ID 52): ¿Qué sucede si intentas modificar una constante en Java después de haberla declarado?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(52, 'Se modifica sin problema', false),
	(52, 'La constante se convierte en variable', false),
	(52, 'El programa lanza un error en tiempo de compilación', true),
	(52, 'Se ignora el nuevo valor', false);

-- Ejercicio 3 (ID 53): ¿Cuál de los siguientes nombres sigue la convención para constantes en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(53, 'MAX_VALUE', true),
	(53, 'maxValue', false),
	(53, 'MaxValue', false),
	(53, 'MAXvalue', false);

-- Ejercicio 4 (ID 54): ¿Cuál es una razón para usar constantes en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(54, 'Para mejorar el rendimiento gráfico', false),
	(54, 'Para evitar duplicar valores literales y facilitar el mantenimiento', true),
	(54, 'Para declarar funciones', false),
	(54, 'Para convertir variables en objetos', false);

-- Ejercicio 5 (ID 55): ¿Qué convención se utiliza en Java para nombrar variables?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(55, 'kebab-case', false),
	(55, 'lowerCamelCase', true),
	(55, 'UPPERCASE', false),
	(55, 'snake_case', false);

-- Ejercicio 6 (ID 56): ¿Cuál de los siguientes nombres de variable sigue la convención lowerCamelCase?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(56, 'EdadUsuario', false),
	(56, 'edad_usuario', false),
	(56, 'edadUsuario', true),
	(56, 'EDADUSUARIO', false);

-- Ejercicio 7 (ID 57): ¿Qué tipo de valores se recomienda almacenar como constantes?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(57, 'Valores que pueden cambiar durante la ejecución', false),
	(57, 'Cadenas que no se usan', false),
	(57, 'Valores fijos que no deben modificarse', true),
	(57, 'Solo números negativos', false);

-- Ejercicio 8 (ID 58): ¿Cuál es un beneficio de usar nombres claros y representativos en las variables?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(58, 'Hace que el código sea más entendible y mantenible', true),
	(58, 'Hace que el programa corra más rápido', false),
	(58, 'Reduce el uso de RAM', false),
	(58, 'Evita que el código se compile', false);

-- Ejercicio 9 (ID 59): ¿Qué valor por defecto tiene una variable de tipo `boolean` en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(59, 'true', false),
	(59, '0', false),
	(59, 'false', true),
	(59, 'null', false);

-- Ejercicio 10 (ID 60): ¿Cuál de estas opciones representa correctamente la declaración de una constante?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(60, 'constant int NUM = 5;', false),
	(60, 'final int NUMERO_MAXIMO = 100;', true),
	(60, 'let final numero = 10;', false),
	(60, 'const int VALOR;', false);
-- LECCIÓN 7: Operadores aritméticos

-- Ejercicio 1 (ID 61): ¿Qué operador se utiliza para obtener el resto de una división en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(61, '/', false),
	(61, '%', true),
	(61, '*', false),
	(61, '//', false);

-- Ejercicio 2 (ID 62): ¿Cuál de los siguientes operadores se utiliza para comparar si dos valores son iguales?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(62, '==', true),
	(62, '=', false),
	(62, '!=', false),
	(62, '>=', false);

-- Ejercicio 3 (ID 63): ¿Cuál es el resultado de la operación `10 / 3` en Java si ambas variables son enteras?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(63, '3', true),
	(63, '3.333', false),
	(63, '10', false),
	(63, '0', false);

-- Ejercicio 4 (ID 64): ¿Qué operador lógico se utiliza para representar “y”?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(64, '&&', true),
	(64, '||', false),
	(64, '!', false),
	(64, '&', false);

-- Ejercicio 5 (ID 65): ¿Qué valor devuelve la expresión `5 != 3`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(65, 'true', true),
	(65, 'false', false),
	(65, '5', false),
	(65, '3', false);

-- Ejercicio 6 (ID 66): ¿Cuál es el resultado de `10 % 4`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(66, '0', false),
	(66, '2', true),
	(66, '4', false),
	(66, '10', false);

-- Ejercicio 7 (ID 67): ¿Cuál de las siguientes expresiones lógicas devuelve `true`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(67, '(5 > 3)', true),
	(67, '(2 > 7)', false),
	(67, '(0 == 1)', false),
	(67, '(4 < 2)', false);

-- Ejercicio 8 (ID 68): ¿Qué hace el operador `!` en una expresión lógica?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(68, 'Multiplica el valor', false),
	(68, 'Niega el valor booleano/invierte el resultado', true),
	(68, 'Convierte a entero', false),
	(68, 'Hace una comparación', false);

-- Ejercicio 9 (ID 69): ¿Qué operador se usa para concatenar cadenas en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(69, '+', true),
	(69, '&', false),
	(69, '.', false),
	(69, '-', false);

-- Ejercicio 10 (ID 70): ¿Cuál es el resultado de esta expresión: `(5 > 3) && (2 < 4)`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(70, 'false', false),
	(70, 'true', true),
	(70, '2', false),
	(70, '5', false);

-- LECCIÓN 8: Concatenación de cadenas

-- Ejercicio 1 (ID 71): ¿Qué operador se usa en Java para concatenar cadenas?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(71, '+', true),
	(71, '&', false),
	(71, '-', false),
	(71, '.', false);

-- Ejercicio 2 (ID 72): ¿Qué resultado produce esta operación? `"Hola" + ", " + "Ana"`
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(72, 'Hola', false),
	(72, '"Hola Ana"', false),
	(72, 'Hola, Ana', true),
	(72, 'HolaAna', false);

-- Ejercicio 3 (ID 73): ¿Qué tipo de dato permite realizar concatenación con el operador `+`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(73, 'boolean', false),
	(73, 'String', true),
	(73, 'int', false),
	(73, 'char', false);

-- Ejercicio 4 (ID 74): ¿Qué sucede si se concatena un `String` con un número?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(74, 'Java lanza un error', false),
	(74, 'El número se convierte a cadena automáticamente', true),
	(74, 'Se ignora el número', false),
	(74, 'Solo se concatena si el número es entero', false);

-- Ejercicio 5 (ID 75): ¿Cuál de las siguientes afirmaciones es correcta sobre la concatenación en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(75, 'Solo se puede concatenar usando variables', false),
	(75, 'Solo String admite concatenación', false),
	(75, 'Puedes concatenar cadenas y valores numéricos sin error', true),
	(75, 'La concatenación requiere importar una librería', false);

-- Ejercicio 6 (ID 76): El operador `+` también puede sumar números. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(76, 'Verdadero', true),
	(76, 'Falso', false);

-- Ejercicio 7 (ID 77): Java lanza un error si se concatena un número con una cadena. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(77, 'Verdadero', false),
	(77, 'Falso', true);

-- Ejercicio 8 (ID 78): Es posible usar variables dentro de una concatenación. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(78, 'Verdadero', true),
	(78, 'Falso', false);

-- Ejercicio 9 (ID 79): La concatenación puede incluir resultados de operaciones matemáticas. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(79, 'Verdadero', true),
	(79, 'Falso', false);

-- Ejercicio 10 (ID 80): La concatenación es útil para construir mensajes dinámicos. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(80, 'Verdadero', true),
	(80, 'Falso', false);

-- LECCIÓN 9: Conversión de tipos en Java (Casting)

-- Ejercicio 1 (ID 81): ¿Qué tipo de conversión en Java es automática y no requiere casting?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(81, 'Narrowing (ajuste estrecho)', false),
	(81, 'Widening (ajuste amplio)', true),
	(81, 'Casting booleano', false),
	(81, 'Conversión a String', false);

-- Ejercicio 2 (ID 82): ¿Cuál de las siguientes afirmaciones describe correctamente el narrowing casting?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(82, 'Es automático y sin pérdida de datos', false),
	(82, 'Convierte un tipo más grande a uno más pequeño y puede causar pérdida de datos', true),
	(82, 'Solo funciona con booleanos', false),
	(82, 'Solo se aplica a objetos', false);

-- Ejercicio 3 (ID 83): ¿Qué palabra describe la conversión de un tipo más pequeño a uno más grande?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(83, 'Narrowing', false),
	(83, 'Widening', true),
	(83, 'Parsing', false),
	(83, 'Stringify', false);

-- Ejercicio 4 (ID 84): ¿Qué sucede cuando se realiza un narrowing casting de `double` a `int`?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(84, 'El valor se redondea automáticamente', false),
	(84, 'Se produce un error en tiempo de ejecución', false),
	(84, 'Se trunca la parte decimal del número', true),
	(84, 'La variable se convierte en float', false);

-- Ejercicio 5 (ID 85): ¿Cuál es la sintaxis correcta para realizar un narrowing casting en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(85, '(int) 7.89', true),
	(85, 'int(7.89)', false),
	(85, '7.89 as int', false),
	(85, 'cast int = 7.89', false);

-- Ejercicio 6 (ID 86): El widening casting puede provocar pérdida de información. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(86, 'Verdadero', false),
	(86, 'Falso', true);

-- Ejercicio 7 (ID 87): El narrowing casting se realiza automáticamente. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(87, 'Verdadero', false),
	(87, 'Falso', true);

-- Ejercicio 8 (ID 88): El casting se puede aplicar también a objetos relacionados por herencia. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(88, 'Verdadero', true),
	(88, 'Falso', false);

-- Ejercicio 9 (ID 89): El upcasting en objetos es un tipo de conversión explícita. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(89, 'Verdadero', false),
	(89, 'Falso', true);

-- Ejercicio 10 (ID 90): El narrowing casting requiere usar paréntesis con el tipo de destino. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(90, 'Verdadero', true),
	(90, 'Falso', false);
-- LECCIÓN 10: Clases y métodos en Java

-- Ejercicio 1 (ID 91): ¿Qué representa una clase en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(91, 'Una función matemática', false),
	(91, 'Un archivo ejecutable', false),
	(91, 'Una plantilla para crear objetos con atributos y comportamientos', true),
	(91, 'Un componente visual de una interfaz', false);

-- Ejercicio 2 (ID 92): ¿Cuál de los siguientes elementos NO forma parte de una clase en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(92, 'Atributos', false),
	(92, 'Constructores', false),
	(92, 'Métodos', false),
	(92, 'Estilos CSS', true);

-- Ejercicio 3 (ID 93): ¿Cuál es la finalidad de un constructor en una clase Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(93, 'Renderizar interfaces gráficas', false),
	(93, 'Inicializar objetos con valores predeterminados o personalizados', true),
	(93, 'Eliminar objetos de memoria', false),
	(93, 'Optimizar el rendimiento del IDE', false);

-- Ejercicio 4 (ID 94): ¿Cuál es el tipo de acceso adecuado para los atributos si se quiere aplicar encapsulamiento?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(94, 'public', false),
	(94, 'private', true),
	(94, 'static', false),
	(94, 'protected', false);

-- Ejercicio 5 (ID 95): ¿Qué instrucción crea un nuevo objeto de la clase Persona con nombre "Ana" y edad 30?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(95, 'Persona.create("Ana", 30);', false),
	(95, 'new Persona("Ana", 30);', true),
	(95, 'Persona = ("Ana", 30);', false),
	(95, 'Persona("Ana", 30);', false);

-- Ejercicio 6 (ID 96): Un objeto es una instancia de una clase. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(96, 'Verdadero', true),
	(96, 'Falso', false);

-- Ejercicio 7 (ID 97): Los métodos pueden devolver valores o no devolver nada. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(97, 'Verdadero', true),
	(97, 'Falso', false);

-- Ejercicio 8 (ID 98): Los atributos de una clase siempre deben ser públicos. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(98, 'Verdadero', false),
	(98, 'Falso', true);

-- Ejercicio 9 (ID 99): El constructor debe tener el mismo nombre que la clase. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(99, 'Verdadero', true),
	(99, 'Falso', false);

-- Ejercicio 10 (ID 100): Un método puede acceder directamente a los atributos de la clase. ¿Verdadero o falso?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(100, 'Verdadero', true),
	(100, 'Falso', false);

-- LECCIÓN 11

-- Ejercicio 1 (ID 101): ¿Qué es la concurrencia en Java y para qué sirve?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(101, 'Es un sistema gráfico para manejar interfaces', false),
	(101, 'Es la capacidad de ejecutar múltiples tareas al mismo tiempo para mejorar el rendimiento', true),
	(101, 'Es una API para conectarse con bases de datos', false),
	(101, 'Es una librería para gestión de errores', false);

-- LECCIÓN 12

-- Ejercicio 1 (ID 102): ¿Cuáles son algunas buenas prácticas de programación en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(102, 'Escribir código sin comentarios y sin nombres claros', false),
	(102, 'Evitar usar clases y dividir en un solo archivo', false),
	(102, 'Usar nombres descriptivos, aplicar principios SOLID y mantener código limpio', true),
	(102, 'Ignorar convenciones de estilo de código', false);

-- LECCIÓN 13

-- Ejercicio 1 (ID 103): ¿Qué necesitas para empezar a desarrollar aplicaciones web en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(103, 'Solo necesitas un navegador web', false),
	(103, 'Un IDE, un servidor y conocimientos de Java web (como Servlets o frameworks)', true),
	(103, 'Un archivo HTML y conexión a internet', false),
	(103, 'Una cuenta en redes sociales', false);

-- LECCIÓN 14

-- Ejercicio 1 (ID 104): ¿Qué es un framework en Java y cuál es su utilidad principal?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(104, 'Un compilador de código en Java', false),
	(104, 'Una plantilla de presentaciones', false),
	(104, 'Un conjunto de herramientas que facilita el desarrollo de aplicaciones', true),
	(104, 'Un traductor automático de código', false);
-- LECCIÓN 15

-- Ejercicio 1 (ID 105): ¿Qué pilares forman parte de la Programación Orientada a Objetos en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(105, 'Abstracción, herencia, encapsulamiento, polimorfismo', true),
	(105, 'Estilización, optimización, virtualización, modularidad', false),
	(105, 'Funciones, arreglos, bucles, excepciones', false),
	(105, 'Interfaces, variables, ejecutables, integraciones', false);
-- LECCIÓN 16

-- Ejercicio 1 (ID 106): ¿Qué ventaja principal ofrece el uso de polimorfismo en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(106, 'Permite que múltiples clases diferentes respondan a un mismo mensaje', true),
	(106, 'Permite ejecutar varias aplicaciones a la vez', false),
	(106, 'Evita el uso de clases abstractas', false),
	(106, 'Convierte métodos en propiedades', false);
-- LECCIÓN 17

-- Ejercicio 1 (ID 107): ¿Qué estructura de datos usarías para almacenar un conjunto sin elementos repetidos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(107, 'ArrayList', false),
	(107, 'HashSet', true),
	(107, 'Queue', false),
	(107, 'Map', false);
-- LECCIÓN 18

-- Ejercicio 1 (ID 108): ¿Qué diferencia hay entre una lista y un mapa en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(108, 'Ambas estructuras son idénticas', false),
	(108, 'Una lista guarda valores en orden; un mapa usa claves para almacenar pares clave-valor', true),
	(108, 'Un mapa no puede contener números', false),
	(108, 'Una lista solo admite texto y un mapa solo números', false);
-- LECCIÓN 19

-- Ejercicio 1 (ID 109): ¿Qué es Spring y cuál es su principal objetivo?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(109, 'Un editor de texto para código Java', false),
	(109, 'Un servidor de bases de datos', false),
	(109, 'Un framework para facilitar el desarrollo de aplicaciones Java empresariales', true),
	(109, 'Un navegador web hecho en Java', false);
-- LECCIÓN 20

-- Ejercicio 1 (ID 110): ¿Qué es la inyección de dependencias en el contexto de Spring?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(110, 'Una forma de enviar datos por formularios', false),
	(110, 'Un mecanismo que permite a Spring proporcionar automáticamente las instancias requeridas por una clase', true),
	(110, 'Una técnica para importar librerías', false),
	(110, 'Una función exclusiva para testeo', false);
-- LECCIÓN 21

-- Ejercicio 1 (ID 111): ¿Qué herramienta ofrece Spring Boot para ejecutar rápidamente una aplicación?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(111, 'Spring Builder', false),
	(111, 'Spring Runner', false),
	(111, 'SpringApplication.run()', true),
	(111, 'Java Executor', false);
-- LECCIÓN 22

-- Ejercicio 1 (ID 112): ¿Qué anotación en Spring Boot se usa para declarar un servicio REST?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(112, '@SpringBootApp', false),
	(112, '@Service', false),
	(112, '@RestController', true),
	(112, '@WebService', false);
-- LECCIÓN 23

-- Ejercicio 1 (ID 113): ¿Cuál es la finalidad principal de un Servlet en Java EE?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(113, 'Cifrar claves de usuario', false),
	(113, 'Enviar correos desde Java', false),
	(113, 'Procesar solicitudes HTTP y generar respuestas dinámicas en aplicaciones web', true),
	(113, 'Diseñar interfaces gráficas', false);
-- LECCIÓN 24

-- Ejercicio 1 (ID 114): ¿Qué servidor embebido suele usarse para desplegar aplicaciones Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(114, 'Apache Server', false),
	(114, 'Node.js', false),
	(114, 'Tomcat', true),
	(114, 'GlassFish', false);
-- LECCIÓN 25

-- Ejercicio 1 (ID 115): ¿Qué funcionalidad brinda un controlador REST en una API?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(115, 'Controla la interfaz gráfica en tiempo real', false),
	(115, 'Permite realizar operaciones CRUD a través de peticiones HTTP', true),
	(115, 'Genera archivos JavaScript automáticamente', false),
	(115, 'Sirve para crear apps móviles en Android', false);
-- LECCIÓN 26

-- Ejercicio 1 (ID 116): ¿Cómo puede un cliente JavaScript consumir una API REST?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(116, 'Usando comandos de terminal', false),
	(116, 'Usando fetch() o librerías como axios', true),
	(116, 'Llamando directamente a bases de datos', false),
	(116, 'Ejecutando funciones Java directamente', false);
-- LECCIÓN 27

-- Ejercicio 1 (ID 117): ¿Para qué se usa la API JPA en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(117, 'Para diseñar interfaces gráficas', false),
	(117, 'Para consultar APIs externas en formato JSON', false),
	(117, 'Para mapear objetos Java a tablas en base de datos relacional', true),
	(117, 'Para imprimir objetos por consola', false);
-- LECCIÓN 28

-- Ejercicio 1 (ID 118): ¿Cuál es la diferencia entre FetchType.EAGER y FetchType.LAZY en Hibernate?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(118, 'EAGER carga relaciones de manera inmediata y LAZY lo hace bajo demanda', true),
	(118, 'EAGER se usa con booleanos, LAZY con enteros', false),
	(118, 'LAZY es más rápido que EAGER en todos los casos', false),
	(118, 'No hay ninguna diferencia entre ambos', false);
-- LECCIÓN 29

-- Ejercicio 1 (ID 119): ¿Qué librería se utiliza mayormente para pruebas unitarias en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(119, 'Mockito', false),
	(119, 'JUnit', true),
	(119, 'TestNG', false),
	(119, 'QTestPro', false);
-- LECCIÓN 30

-- Ejercicio 1 (ID 120): ¿Qué beneficios ofrece el uso de Mockito en pruebas unitarias?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(120, 'Permite integrar APIs externas en tiempo real', false),
	(120, 'Permite simular el comportamiento de objetos para aislar dependencias', true),
	(120, 'Evita la necesidad de clases en Java', false),
	(120, 'Mejora la velocidad de red', false);
-- LECCIÓN 31

-- Ejercicio 1 (ID 121): ¿Cuál es el primer paso para construir una app en JavaFX?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(121, 'Escribir una clase Main que extienda Application', true),
	(121, 'Crear un archivo HTML inicial', false),
	(121, 'Definir una API REST', false),
	(121, 'Conectar con MySQL', false);
-- LECCIÓN 32

-- Ejercicio 1 (ID 122): ¿Qué componentes permiten interacción con el usuario en JavaFX?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(122, 'Labels y VBox', false),
	(122, 'TextField, Button y CheckBox', true),
	(122, 'Thread y Runnable', false),
	(122, 'PrintStream y Scanner', false);
-- LECCIÓN 33

-- Ejercicio 1 (ID 123): ¿Qué instrucción imprime texto en la consola en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(123, 'console.log()', false),
	(123, 'System.out.println()', true),
	(123, 'Imprimir()', false),
	(123, 'printf()', false);
-- LECCIÓN 34

-- Ejercicio 1 (ID 124): ¿Qué tipo de dato usarías para almacenar el nombre de una persona?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(124, 'int', false),
	(124, 'boolean', false),
	(124, 'String', true),
	(124, 'char[]', false);
-- LECCIÓN 35

-- Ejercicio 1 (ID 125): ¿Qué diferencia hay entre `let` y `const` en JavaScript?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(125, '`let` define variables modificables, `const` define constantes que no pueden reasignarse', true),
	(125, '`const` permite cambiar valores numéricos', false),
	(125, '`let` es global y `const` es local', false),
	(125, 'No hay diferencia entre ambas', false);
-- LECCIÓN 36

-- Ejercicio 1 (ID 126): ¿Qué es una función flecha en JavaScript?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(126, 'Una función que solo se puede usar con bucles', false),
	(126, 'Una forma más corta de declarar funciones utilizando `=>`', true),
	(126, 'Una función para lanzar errores personalizados', false),
	(126, 'Una etiqueta HTML que ejecuta funciones', false);
-- LECCIÓN 37

-- Ejercicio 1 (ID 127): ¿Qué problema resuelve el uso de clases en JavaScript moderno?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(127, 'El acceso a hojas de estilo desde script', false),
	(127, 'Facilita la creación de objetos reutilizables con una sintaxis clara y estructurada', true),
	(127, 'Evita que HTML y JS se mezclen', false),
	(127, 'Hace que las funciones se ejecuten más rápido', false);
-- LECCIÓN 38

-- Ejercicio 1 (ID 128): ¿Qué palabra clave permite utilizar *promesas* más fácilmente en JS?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(128, 'await', true),
	(128, 'sync', false),
	(128, 'thread', false),
	(128, 'promise', false);
-- LECCIÓN 39

-- Ejercicio 1 (ID 129): ¿Qué objeto especial permite interactuar con el DOM en JavaScript?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(129, 'document', true),
	(129, 'window', false),
	(129, 'html', false),
	(129, 'main', false);
-- LECCIÓN 40

-- Ejercicio 1 (ID 130): ¿Qué método se usa para hacer peticiones HTTP en JavaScript moderno?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(130, 'connect()', false),
	(130, 'fetch()', true),
	(130, 'openRequest()', false),
	(130, 'readFile()', false);
-- LECCIÓN 41

-- Ejercicio 1 (ID 131): ¿Qué palabra clave define una clase en Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(131, 'function', false),
	(131, 'define', false),
	(131, 'class', true),
	(131, 'object', false);
-- LECCIÓN 42

-- Ejercicio 1 (ID 132): ¿Qué diferencia hay entre una interface y una clase abstracta?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(132, 'Una interface puede tener atributos privados', false),
	(132, 'Una clase abstracta no puede tener ningún método implementado', false),
	(132, 'Una interface define un contrato sin implementar lógica; la clase abstracta puede tener lógica parcial', true),
	(132, 'No hay ninguna diferencia práctica', false);
-- LECCIÓN 43

-- Ejercicio 1 (ID 133): ¿Qué es una aplicación de múltiples capas (multicapa)?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(133, 'Una aplicación dividida en frontend y backend únicamente', false),
	(133, 'Una estructura que separa las responsabilidades en capas como presentación, servicio y datos', true),
	(133, 'Una app que necesita múltiples frameworks', false),
	(133, 'Un sistema con muchas carpetas', false);
-- LECCIÓN 44

-- Ejercicio 1 (ID 134): ¿Qué permite JDBC en el desarrollo Java?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(134, 'Conectar Java con hojas de estilo CSS', false),
	(134, 'Conectar aplicaciones Java con bases de datos relacionales', true),
	(134, 'Traducir Java a JavaScript', false),
	(134, 'Subir archivos al servidor', false);
-- LECCIÓN 45

-- Ejercicio 1 (ID 135): ¿Qué etiqueta define la estructura básica de una página HTML?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(135, '<html>', true),
	(135, '<head>', false),
	(135, '<body>', false),
	(135, '<meta>', false);
-- LECCIÓN 46

-- Ejercicio 1 (ID 136): ¿Qué elemento se usa para insertar un video en HTML5?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(136, '<video>', true),
	(136, '<media>', false),
	(136, '<embed>', false),
	(136, '<iframe>', false);
-- LECCIÓN 47

-- Ejercicio 1 (ID 137): ¿Qué son las etiquetas <code>ARIA</code> y para qué se usan?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(137, 'Etiquetas para estilizar en CSS', false),
	(137, 'Atributos que mejoran la accesibilidad web para personas con discapacidad', true),
	(137, 'Etiquetas para insertar contenido multimedia', false),
	(137, 'Sintaxis especial de JavaScript', false);
-- LECCIÓN 48

-- Ejercicio 1 (ID 138): ¿Qué elementos ayudan al posicionamiento SEO en HTML semántico?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(138, '<section>, <article>, <header> y <footer>', true),
	(138, '<div>, <br>, <span>', false),
	(138, '<style>, <script>', false),
	(138, '<audio>, <video>', false);
-- LECCIÓN 49

-- Ejercicio 1 (ID 139): ¿Qué selector CSS se usa para seleccionar todos los elementos <code>&lt;p&gt;</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(139, 'p', true),
	(139, '.p', false),
	(139, '#p', false),
	(139, '*p', false);
-- LECCIÓN 50

-- Ejercicio 1 (ID 140): ¿Qué propiedad CSS permite centrar un bloque con <code>margin: auto;</code>?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(140, 'float', false),
	(140, 'margin', true),
	(140, 'padding', false),
	(140, 'text-align', false);
-- LECCIÓN 51

-- Ejercicio 1 (ID 141): ¿Qué ventajas ofrece Flexbox sobre el modelo de caja tradicional?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(141, 'Permite crear animaciones 3D rápidamente', false),
	(141, 'Organiza elementos de manera flexible tanto en filas como columnas', true),
	(141, 'Sirve solo para posicionamiento absoluto', false),
	(141, 'Elimina el uso de etiquetas HTML', false);
-- LECCIÓN 52

-- Ejercicio 1 (ID 142): ¿Qué propiedad CSS permite una transición suave entre estilos?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(142, 'transition', true),
	(142, 'opacity', false),
	(142, 'transform', false),
	(142, 'hover', false);
-- LECCIÓN 53

-- Ejercicio 1 (ID 143): ¿Qué función se usa para imprimir texto en Python?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(143, 'print()', true),
	(143, 'echo()', false),
	(143, 'System.out.println()', false),
	(143, 'console.log()', false);
-- LECCIÓN 54

-- Ejercicio 1 (ID 144): ¿Qué palabra clave se usa en Python para escribir una estructura condicional?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(144, 'if', true),
	(144, 'when', false),
	(144, 'case', false),
	(144, 'switch', false);
-- LECCIÓN 55

-- Ejercicio 1 (ID 145): ¿Qué función de Pandas se utiliza para cargar un archivo CSV?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(145, 'pd.read()', false),
	(145, 'pd.open_csv()', false),
	(145, 'pd.read_csv()', true),
	(145, 'csv.load()', false);
-- LECCIÓN 56

-- Ejercicio 1 (ID 146): ¿Qué biblioteca gráfica se usa comúnmente junto con Pandas para visualizaciones?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(146, 'Seaborn', false),
	(146, 'Matplotlib', true),
	(146, 'Numpy', false),
	(146, 'Requests', false);
-- LECCIÓN 57

-- Ejercicio 1 (ID 147): ¿Qué función de PHP se usa para imprimir en pantalla?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(147, 'console.log()', false),
	(147, 'System.out.println()', false),
	(147, 'echo', true),
	(147, 'print()', false);
-- LECCIÓN 58

-- Ejercicio 1 (ID 148): ¿Qué superglobal de PHP se utiliza para obtener los datos de un formulario enviado por POST?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(148, '$_GET', false),
	(148, '$_FORMDATA', false),
	(148, '$_POST', true),
	(148, '$_REQUEST', false);
-- LECCIÓN 59

-- Ejercicio 1 (ID 149): ¿Qué función de PHP permite establecer conexión con una base de datos MySQL?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(149, 'mysql_connect()', false),
	(149, 'mysqli_connect()', true),
	(149, 'connect_to_db()', false),
	(149, 'dbLink()', false);
-- LECCIÓN 60

-- Ejercicio 1 (ID 150): ¿Qué significa el acrónimo CRUD?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(150, 'Create, Run, Undo, Display', false),
	(150, 'Copy, Read, Update, Delete', false),
	(150, 'Create, Read, Update, Delete', true),
	(150, 'Code, Render, Upload, Deploy', false);
-- LECCIÓN 61

-- Ejercicio 1 (ID 151): ¿Cuál es la función principal del método <code>Main()</code> en C#?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(151, 'Compilar el programa', false),
	(151, 'Ejecutar el código principal de la aplicación', true),
	(151, 'Declarar las variables de clase', false),
	(151, 'Almacenar constantes globales', false);
-- LECCIÓN 62

-- Ejercicio 1 (ID 152): ¿Qué es un objeto en C# y cómo se declara?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(152, 'Una variable de tipo string', false),
	(152, 'Una instancia de una clase, usando la palabra clave <code>new</code>', true),
	(152, 'Un número entero declarado en una función', false),
	(152, 'Una hoja de estilos cargada en tiempo de ejecución', false);
-- LECCIÓN 63

-- Ejercicio 1 (ID 153): ¿Qué permite hacer LINQ en colecciones como listas?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(153, 'Establecer conexiones con bases de datos externas automáticamente', false),
	(153, 'Agregar estilos CSS desde código C#', false),
	(153, 'Realizar consultas y filtros de forma declarativa sobre colecciones', true),
	(153, 'Dividir clases en múltiples archivos', false);
-- LECCIÓN 64

-- Ejercicio 1 (ID 154): ¿Qué tipo de aplicación permite crear Windows Forms?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(154, 'Aplicaciones web', false),
	(154, 'Aplicaciones de consola', false),
	(154, 'Aplicaciones de escritorio con interfaz gráfica en Windows', true),
	(154, 'Aplicaciones móviles para Android', false);
-- LECCIÓN 65

-- Ejercicio 1 (ID 155): ¿Qué palabra clave define un tipo en TypeScript?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(155, 'define', false),
	(155, 'type', true),
	(155, 'struct', false),
	(155, 'declareType', false);

-- LECCIÓN 66: ¿Para qué se utiliza una interfaz (interface) en TypeScript?

INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(156, 'Para definir la estructura que deben seguir los objetos, asegurando tipo y contratos', true),
	(156, 'Para aplicar estilos CSS a los elementos HTML', false),
	(156, 'Para manejar peticiones HTTP en Angular', false),
	(156, 'Para ejecutar código en tiempo de compilación', false);

-- Lección 67: ¿Qué archivo contiene el metadato y configuración de un proyecto Angular?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(157, 'package.json', false),
	(157, 'angular.json', true),
	(157, 'index.html', false),
	(157, 'main.ts', false);

-- Lección 68: ¿Qué servicio Angular permite manejar peticiones HTTP?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(158, 'RouterModule', false),
	(158, 'HttpClient', true),
	(158, 'FormsModule', false),
	(158, 'HttpServiceProvider', false);

--  Lección 69: ¿Qué línea imprime texto en Ruby?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(159, 'System.out.println("Hola")', false),
	(159, 'echo "Hola"', false),
	(159, 'print("Hola")', false),
	(159, 'puts "Hola"', true);

-- Lección 70: ¿Qué línea imprime texto en Ruby?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(160, 'Una estructura basada en funciones globales', false),
	(160, 'Una arquitectura basada en capas de red', false),
	(160, 'Modelo-Vista-Controlador (MVC)', true),
	(160, 'Framework sin estructura definida', false);


-- Lección 70: ¿Qué estructura proporciona Ruby on Rails para organizar una web MVC?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(160, 'Una estructura basada en funciones globales', false),
	(160, 'Una arquitectura basada en capas de red', false),
	(160, 'Modelo-Vista-Controlador (MVC)', true),
	(160, 'Framework sin estructura definida', false);

-- Lección 71: ¿Qué palabra clave se usa para declarar una constante en Swift?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(161, 'let', true),
	(161, 'final', false),
	(161, 'const', false),
	(161, 'static', false);


-- Lección 72: Lección 72: ¿Qué herramienta de desarrollo visual ofrece Apple para diseñar interfaces en iOS?
INSERT INTO public.option (id_exercise, option_text, is_correct)
VALUES
	(162, 'Storyboard', true),
	(162, 'UIKit Viewer', false),
	(162, 'Material UI', false),
	(162, 'Xcode Runner', false);
