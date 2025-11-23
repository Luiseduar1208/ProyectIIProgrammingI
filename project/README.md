# Proyecto 2 - Hoja de Cálculo con Fracciones

## Descripción del Problema

ACME S.A. necesita una solución para manejar cálculos financieros con precisión absoluta. Las hojas de cálculo tradicionales usan números de punto flotante que causan errores de redondeo en operaciones contables. Nuestra extensión resuelve este problema utilizando fracciones para garantizar cálculos exactos en todas las operaciones.

## Características Principales

* **Operaciones con fracciones**: Suma, multiplicación, promedio, mediana, máximo y mínimo
* **Manejo de rangos**: Operaciones sobre filas y columnas específicas
* **Conjuntos personalizados**: Agrupe celdas en conjuntos con nombres
* **Resultados precisos**: Sin errores de redondeo gracias al uso de fracciones
* **Interfaz de texto**: Fácil integración con sistemas existentes

## Comandos Disponibles

| Comando | Descripción |
|---------|-------------|
| `=CEL(celda)` | Prepara una celda para recibir resultados |
| `=SET(nombre,celdas)` | Crea un conjunto con celdas |
| `=SUM(rango)` | Suma un rango o conjunto |
| `=MUL(rango)` | Multiplica un rango o conjunto |
| `=AVR(rango)` | Calcula el promedio |
| `=MDN(rango)` | Calcula la mediana y lo ordena en la hoja de cálculo final |
| `=MIN(rango)` | Encuentra el valor mínimo |
| `=MAX(rango)` | Encuentra el valor máximo |
| `=PRINT()` | Muestra toda la hoja |
| `=PRINT(conjunto)` | Muestra un conjunto |

## Cómo Usar

### Creacion de archivos

```bash
mkdir bin build
```

### Compilación

```bash
cd project
javac -d bin src/com/ucr/ecci/Sheets/*.java
```

### Ejecución

```bash
java -cp bin com.ucr.ecci.Sheets.Main < tests/0001.txt
```

Se puso a comodidad del usuario una serie de casos de prueba en la carpeta **tests**

### Crear Ejecutable JAR

```bash
jar cfe planilla.jar com.ucr.ecci.Sheets.Main -C bin .
java -jar planilla.jar < tests/input-001.txt
```

## Estructura del Proyecto

* **src/**: Código fuente Java organizado en paquetes
* **tests/**: Casos de prueba para validar funcionalidades
* **bin/**: Archivos compilados (no versionado)
* **design/**: Documentación de diseño y diagramas

## Clases Principales

* **Controller**: Gestiona lógica principal y ejecución de comandos
* **ConjuntoFracciones**: Maneja operaciones con fracciones
* **List**: Implementa listas doblemente enlazadas
* **Btree**: Árbol binario para gestión de conjuntos
* **Main**: Punto de entrada de la aplicación

## Créditos

**Desarrollado por:**

* Luis Eduardo Hernández - luisedurdo.hernandez@ucr.ac.cr
* César Pérez - cesar.perezmendoza@ucr.ac.cr


**Contribuciones Técnicas:**

* **Profesor Alberto Rojas**: Implementación de métodos fundamentales en la clase ConjuntoFracciones

**Curso:** CI-0112 - Programación 1

**Universidad de Costa Rica**
**Segundo Semestre 2025**
