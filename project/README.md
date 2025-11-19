# Proyecto 2 - Hoja de Cálculo

## Enunciado

## Descripción del Problema

Las hojas de cálculo como Excel, LibreOffice Calc o Google Sheets, son los programas más utilizado para llevar la contabilidad de muchos negocios pequeños (y algunos no tan pequeños). Debido a problemas de redondeo en su software de hojas de cálculo, la empresa ***ACME S.A.*** lo contrató para desarrollar una pequeña extensión.

La extensión solicitada debe extender las funcionalidades de la hoja de cálculo y permitir el uso de fracciones y operaciones con fracciones. Según los encargados de la contabilidad de la empresa, las fracciones almacenarían mejor los datos de sus bitácoras financieras que su equivalente en punto flotante.

Como parte del proyecto, usted debe presentar un prototipo de la extensión con las operaciones básicas solicitadas antes de proceder con la implementación de la versión final. Dicho prototipo no ocupa interfaz gráfica. Por lo tanto, todos los datos se recibiran por la *entrada estándar* y se los resultados se mostraran por la *salida estándar*. El prototipo debe de pasar todos los casos de prueba suministrados por el equipo financiero de la empresa de ***ACME S.A.***.

Las operaciones solicitadas son las siguientes:

1. Sumar y multiplicar filas y columnas desde una celda inicial especificada hasta una celda final especificada.
1. Obtener el valor máximo y mínimo de una fila o columna desde una celda inicial hasta una celda final.
1. Obtener el promedio y la mediana de una fila o columna desde una celda inicial hasta una celda final.
1. Crear subconjuntos formados por celdas de la hoja de cálculo.
1. Sumar y multiplicar subconjuntos.
1. Calcular promedio y mediana de subconjuntos.

El prototipo deberá leer los casos de prueba desde la entrada estándar como se muestran en el siguiente ejemplo:

```txt
4 8 

10/02, 02/02, 03/02, 04/02, 05/02, 06/02, 07/02, 00/01
01/05, 02/05, 03/05, 04/05, 05/05, 06/05, 07/05, 00/01
07/07, 06/07, 03/07, 04/07, 05/07, 02/07, 01/07, 00/01
00/01, 00/01, 00/01, 00/01, 00/01, 00/01, 00/01, 00/01

=CEL(H1)
=SUM(B1:G1)
=CEL(H2)
=AVR(A2:G2)
=CEL(H3)
=MDN(A3:G3)
=CEL(B4)
=MUL(B1:B3)
=CEL(G4)
=SET(valores,A1,B2,C3,C4)
=SUMA(valores)
=PRINT()
```

Además de los datos (matriz) que debe cargar la hoja de cálculo, su programa debe leer una serie de instrucciones. La instrucción `=CEL(xx)` indican la posición donde se guardará el resultado de las siguientes operaciones. Si se desea guardar el valor resultante de una operación en otra celda se debe volver a ejecutar la instrucción `=CEL(xx)`. La hoja de cálculo debe de actualizarse cada vez que se ejecutar una instrucción. Si no se indica una celda donde guardar el resultado, la hoja de cálculo no deberá actualizarse.

Las **columnas** de la hoja de cálculo se identifican utilizando **letras mayúsculas** y las **filas** con **números**. Las operaciones que se deben implementar se presentan más adelante.

Los subconjuntos deben de implementarse utilizando **listas doblemente** enlazadas. Note que una hoja de cálculo puede tener más de un conjunto y cada conjunto debe estar asociado a un nombre. Para manejar los conjuntos, se recomienda implementar un **árbol binario de búsqueda**. Las clases lista y árbol binario deben implementarse desde cero. **No** se pueden utilizar estructuras de datos de las bibliotecas estándar de Java (*e.g.*, ArrayList, List, etc).

La instrucción `=PRINT()` mostrará en la *salida estándar* en estado de la matriz en ese momento. El siguiente cuadro muestra la salida esperada de la entrada anterior.

```txt
   |   A    B    C    D    E    F    G    H 
---+---- ---- ---- ---- ---- ---- ---- ---- 
  1| 5/1  1/1  3/2  2/1  5/2  3/1  7/2 27/2
  2| 1/5  2/5  3/5  4/5  1/1  6/5  7/5  4/5
  3| 1/7  2/7  3/7  4/7  5/7  6/7  1/1  4/7 
  4| 0/1 4/35  0/1  0/1  0/1  0/1 32/2  0/1
```

Note que las columnas de la tabla anterior tienen un ancho de 4 dígitos debido a que las fracciones que poseen más digitos tiene 4 (*e.g.*, `27/2`). Si existiera una fracción con más dígitos, las columnas se deben ajustar a ese número de dígitos. Además, entre columnas hay un espacio que las separa.

La siguiente tabla muestra la lista de comandos válidos.

| Comando | Descripción |
| --- | --- |
| `=CEL(celda)` | Se posiciona en una celda. |
| `=SET(nombre, celda1,…,celda_n)` | Crea un conjunto con un nombre específico y agrega las celdas que se le indican en el argumento. |
| `=SUM(celda1:celda2)` | Suma una fila o columna desde la `celda1` hasta la `celda2`. |
| `=SUM(nombreConjunto)` | Suma los valores de un conjunto. |
| `=MUL(celda1:celda2)` | Multiplica una fila o columna desde la `celda1` hasta la `celda2`.  |
| `=MUL(nombreConjunto)` | Multiplica los valores de un conjunto. |
| `=AVR(celda1:celda2)` | Calcula el promedio de una fila o columna desde la `celda1` hasta la `celda2`. |
| `=AVR(nombreConjunto)` | Calcula el promedio de un conjunto. |
| `=MDN(celda1:celda2)` | Calcula la mediana de una fila o columna desde la `celda1` hasta la `celda2`. |
| `=MDN(nombreConjunto)` | Calcula la mediana de un conjunto. |
| `=MIN(celda1:celda2)` | Calcula la fracción con el valor más pequeño de una fila o columna desde la `celda1` hasta la `celda2`. |
| `=MAX(celda1:celda2)` | Calcula la fracción con el valor más grande de una fila o columna desde la `celda1` hasta la `celda2`. |
| `=PRINT()` | Imprime toda la hoja de cálculo. |
| `=PRINT(nombreConjunto)` | Imprime el conjunto de fracciones separadas por comas (`,`). |

## Control de Versiones

El proyecto será realizado en parejas en un repositorio de control de versiones. Ambos participantes deben trabajar todas las fases del proceso (análisis, diseño, programación, documentación, pruebas) y deben dejar evidencia en el historial de `commits`. La nota de un participante será proporcional a la cantidad y calidad de los commits que realizó.

Haga buen uso del repositorio de control de versiones. Cada `commit` debe tener un cambio y un mensaje significativo, y "no romper el build". No haga "megacommits" que implementan muchas funcionalidades distintas y acumuladas en varios días de desarrollo.

Por ninguna razón deben agregar archivos binarios a control de cambios o que hayan sido generados a través de un proceso automatizado, por ejemplo, los archivos de la carpeta `build`, ejecutables `.jar`,  documentación generada por Javadoc (carpeta `doc`). En su lugar, deben tener un archivo `.gitignore` adecuado. Deben agregar al profesor del curso como `maintainer` a su repositorio de control de versiones.

Las carpetas y archivos que se deben agregar al control de versiones son los siguientes:

| Recurso  | Descripción | Versionado |
| --- | --- | --- |
| `bin/`| Carpeta de archivos binarios y `.jar`. | **NO** |
| `build/`| Carpeta con los archivos intermedios necesarios para construir el proyecto y los archivos ejecutables | **NO** |
| `design/`| Contiene el pseudocódigo y diagramas UML de sus procedimientos y clases.  | **SI** |
| `doc/`| Contiene su documentación generada por Javadoc. | **NO** |
| `src/`| Archivos con su código (archivos fuente). | **SI** |
| `tests/`| Carpeta con los archivos de los casos de prueba facilitados por el profesor y los creados por la persona estudiante. | **SI** |
| `readme.md`| Contiene la descripción y análisis del proyecto. | **SI** |
| `.gitignore`| Indica cuales archivos y carpetas no deben ser incluidos al control de versiones. Este archivo puede estar en la raíz del repositorio | **SI** |

## Análisis

En un archivo `readme.md` plasme el resultado de la fase de análisis. Si no conoce el formato **Markdown**, asegúrese de seguir un manual o buscar un tutorial, ya que la buena calidad del código también será evaluada. Por ejemplo, un párrafo en estas notaciones se forma al separar por dos cambios de línea y no por uno. Su documento de análisis debe incluir:

1. Una **descripción** del problema a resolver. Puede adaptar partes de este enunciado, pero no copiarlas exactamente igual, ya que el enunciado va dirigido al estudiantado, mientras que el README va dirigido a cualquier persona que visite el proyecto y quiera entender el problema que la solución resuelve.
1. Incluir una **guía de usuario** que indique como **compilar** el archivo `.jar` y como ejecutar los casos de prueba.
1. La **estructura del proyecto** describiendo muy brevemente (una o dos oraciones) cada sección del proyecto.
1. Una sección de **créditos**, donde indica su nombre e información de contacto (correo institucional). Si utiliza recursos de terceros, como código fuente (por ejemplo, si utilizó código de StackOverflow), dé el respectivo crédito.

## Diseño

Su proyecto debe traer una carpeta llamada `design` donde debe incluir el pseudocódigo de su proyecto. Durante la etapa de diseño debe enforcarse en la solución general, no es necesario enforcarse en los detalles del lenguaje Java. Utilice las 8 instrucciones básicas del paradigma procedimental más la instrucción `return`.  

Su diseño debe de utilizar el paradigma de *Programación Orientada Objetos*. Por esta razón, su solución debe tener un archivo por cada clase. Su proyecto debe tener como mínimo 5 clases: `Controlador`, `HojaDeCalculo`, `ConjuntoFrancciones` (opcional), `ListaDeFracciones`, `ArbolDeListas` y `Fraccion`. Los nombres anteriores son sugerencias y pueden cambiar.  

1. La clase controladora es la que se encarga de manejar la lógica del programa. Esta clase posee el método `main` y `run`. También puede encargarse de la lectura de datos e impresión de los datos.  
1. La clase `HojaDeCalculo` debe de poseer un atributo para almacenar los valores (matriz de fracciones) y métodos para manipular los atributos.
1. La clase `HojaDeCalculo` necesita un atributo de tipo árbol binario que asocie nombres con listas de fracciones.
1. La clase `ListaDeFracciones` debe poder almacenar nodos con fracciones y debe poseer métodos para poder manipular los datos (p. ej., agregar nodo, eliminar nodo, ordenar lista, `toString`, etc.).

Debe incluir una imagen de los diagrama de clases UML en la carpeta `design`.

## Documentación

Su código debe trae documentación interna siguiendo la convención de Javadoc vista en clase. Las clases, atributos, procedimientos públicos y privados deben venir documentados. El siguiente ejemplo corto muestra cómo se espera la documentación de un procedimiento:

```java
  /** 
   * El método {@code calcularMediana} calcula la mediana de un arreglo de números reales. 
   * @param numElementos Es un número de tipo {@code int} que indica el tamaño del arreglo. 
   * @param arrayElementos Arreglo de {@code doubles} al que hay que sacar la mediana. 
   * @return Se retorna un {@code double} con el resultado de la mediana 
   *  estadística de arreglo. 
   */ 
  public double calcularMediana(final int numElementos, final double[] arrayElementos) { … } 
```

Además, debe incluir documentación dentro del cuerpo de los métodos para explicar que hacen las instrucciones más significativas de su procedimiento.

## Estilo y Pruebas

Su código debe de apegarse a la convención de estilo de *SUN* modificado usando (disponible en Mediación Virtual) la herremienta *CheckStyle*. Además, debe utilizar nombres significativos para los procedimientos y las variables.  

Asegúrese de verificar el funcionamiento de su programa con los casos de prueba (caja negra) que se adjuntan. Tiene que generar al menos **tres pruebas** de tamaño y dificultad significativa.

## Evaluación

Por definir.
