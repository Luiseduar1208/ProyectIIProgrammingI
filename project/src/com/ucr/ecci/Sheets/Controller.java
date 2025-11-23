package com.ucr.ecci.Sheets;
import java.util.Scanner;

public class Controller {
  /**Scanner. */
  private Scanner input  = new Scanner(System.in);
  /**Matriz de entrada. */
  private String[][] iMtx;
  /**Cantidad de lineas. */
  private int row;
  /**Cantidad de columnas. */
  private int col;
  /**Coordenadas de inicio. */
  private String start;
  /**Coordenadas de final. */
  private String end;
  /**Árbol binario. */
  private Btree b = new Btree();
  /**Elementos usados. */
  private boolean[][] used;
  /**Fracción mas grande en la matriz. */
  private int maxWidth = 3;
  /** Posición destino para guardar resultados de CEL(). */
  private int celDestination = -1;


  /**Cargar la matriz y tomar row y col. */
  public void loadMtx() { 
    row = input.nextInt();
    col = input.nextInt();
    input.nextLine();
    iMtx = new String[row][col];

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        iMtx[i][j] = input.next();
        if (iMtx[i][j].length() > maxWidth) {
          maxWidth = iMtx[i][j].length();
        }
      }
    }

    used = new boolean[row][col];

  }

  /**
   * Getter ancho maximo de una fraccion.
   * @return el ancho maximo
   */
  public int getMaxWidth() {
    return maxWidth;
  }

  /**
   * Setter del destino.
   * @param pos
   */
  public void setCelDestination(final int pos) {
      this.celDestination = pos;
  }
  /**
   * Getter del destino.
   * @return el destino.
   */
  public int getCelDestination() {
      return this.celDestination;
  }

  /**
   * Getter árbol.
   * @return el árbol binario
   */
  public Btree getB() {
    return b;
  }

  /**
   * Getter de la Columna.
   * @return col
   */
  public int getCol() {
    return col;
  }
  /**
   * Getter de la Fila.
   * @return row
   */
  public int getRow() {
    return row;
  }
  /**
   * Getter de la Matriz.
   * @return iMtx
   */
  public String[][] getiMtx() {
    return iMtx;
  }

  /**
   * Pasar las coords a números.
   * @return StartI (fila)
   */
  public int startI() {
      int rowStart = Integer.parseInt(start.substring(1)) - 1;
      return rowStart;
  }

  /**
   * Pasar las coords a números.
   * @return StartJ (columna)
   */
  public int startJ() {
      int colStart = start.charAt(0) - 'A';
      return colStart;
  }

  /**
   * Pasar las coords a números.
   * @return EndI (fila)
   */
  public int endI() {
      int rowEnd = Integer.parseInt(end.substring(1)) - 1;
      return rowEnd;
  }

  /**
   * Pasar las coords a números.
   * @return EndJ (columna)
   */
  public int endJ() {
      int colEnd = end.charAt(0) - 'A';
      return colEnd;
  }


    /**
   * Procesa el comando.
   * Verifica la linea de comando.
   * @return el comando a correr (nombre) o "" si no hay más líneas
   */
  public String commandProcessor() {
      while (input.hasNextLine()) {
          String line = input.nextLine().trim();
          if (line.isEmpty()) {
              continue;
          }
          return commandRun(line);
      }
      return "";
  }


  /**
   * Metodo para localizar el lugar.
   * @param parameters
   * @return el punto en la matriz.
   */
  public int locate(final String parameters) {
    String letra = parameters.substring(0, 1);
    String numero = parameters.substring(1);

    int columna = letra.charAt(0) - 'A';
    int fila = Integer.parseInt(numero) - 1;

    int position = fila * col + columna;

    return position;
  }

  /**
   * Borrar la fraccion de la matriz.
   * @param parameters
   */
  public void erase(final String parameters) {
    String letra = parameters.substring(0, 1);
    String numero = parameters.substring(1);

    int columna = letra.charAt(0) - 'A';
    int fila = Integer.parseInt(numero) - 1;
    if (fila >= 0 && fila < row && columna >= 0 && columna < col) {
      used[fila][columna] = true;
    }
  }


  /**
   * Procesa el comando SET para crear conjuntos de fracciones.
   * @param parameters nombre del conjunto y list de celdas
   */
  public void setConjunto(final String parameters) {
    String[] parts = parameters.split(",");
    String nombre = parts[0];
    List listFracciones = new List();

    for (int i = 1; i < parts.length; i++) {
      String celda = parts[i].trim();
      int posicion = locate(celda);
      List listCelda = b.getAt(posicion);
      if (listCelda == null) {
        int fila = posicion / col;
        int columna = posicion % col;
        String valor = iMtx[fila][columna];
        if (valor != null) {
          ConjuntoFracciones fraccion = parseFraccion(valor);
          listFracciones.addBack(fraccion);
        }
      } else {
          ConjuntoFracciones fraccion = listCelda.getAt(0);
          if (fraccion != null) {
            listFracciones.addBack(fraccion);
          }
      }
    }

    int key = nombre.hashCode();
    b.add(key, listFracciones);
  }

  private void handleSet(final String opp, final String parameters) {
    int key = parameters.hashCode();
    List list = b.getAt(key);
      if (list != null && celDestination != -1) {
        ConjuntoFracciones result = null;

        if (opp.equals("SUM")) {
          ConjuntoFracciones sum = new ConjuntoFracciones(0, 1);
          int i = 0;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) {
              break;
            }
            sum = sum.sum(frac);
            i++;
          }
          result = sum;
        } else if (opp.equals("MUL")) {
            ConjuntoFracciones product = new ConjuntoFracciones(1, 1);
            int i = 0;
            while (true) {
              ConjuntoFracciones frac = list.getAt(i);
              if (frac == null) {
                break;
              }
              product = product.multiply(frac);
              i++;
            }
            result = product;
          } else if (opp.equals("AVR")) {
            ConjuntoFracciones sum = new ConjuntoFracciones(0, 1);
            int count = 0;
            int i = 0;
            while (true) {
              ConjuntoFracciones frac = list.getAt(i);
              if (frac == null) {
                break;
              }
              sum = sum.sum(frac);
              count++;
              i++;
            }
            ConjuntoFracciones average =
              sum.divide(new ConjuntoFracciones(count, 1));
              result = average;
          } else if (opp.equals("MDN")) {
            int count = 0;
            int i = 0;
            while (list.getAt(i) != null) {
              count++;
              i++;
            }

            ConjuntoFracciones[] arr = new ConjuntoFracciones[count];
            double[] decimals = new double[count];

            for (int j = 0; j < count; j++) {
              arr[j] = list.getAt(j);
              decimals[j] =
                (double) arr[j].getNumerador() / arr[j].getDenominador();
            }

            for (int a = 0; a < count - 1; a++) {
              for (int b = 0; b < count - 1 - a; b++) {
                if (decimals[b] > decimals[b + 1]) {
                  double tmpD = decimals[b];
                  decimals[b] = decimals[b + 1];
                  decimals[b + 1] = tmpD;

                  ConjuntoFracciones tmpF = arr[b];
                  arr[b] = arr[b + 1];
                arr[b + 1] = tmpF;
                }
              }
            }

            ConjuntoFracciones median;
            if (count % 2 == 0) {
              ConjuntoFracciones a = arr[(count - 1) / 2];
              ConjuntoFracciones b = arr[count / 2];
              ConjuntoFracciones two = new ConjuntoFracciones(2, 1);
              median = a.sum(b).divide(two);
            } else {
              median = arr[count / 2];
            }
            result = median;
        } else if (opp.equals("MIN")) {
          ConjuntoFracciones min = list.getAt(0);
          int i = 1;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) {
              break;
            }
            long comp = min.getNumerador() * frac.getDenominador()
              - frac.getNumerador() * min.getDenominador();
            if (comp > 0) {
              min = frac;
            }
            i++;
          }
          result = min;

        } else if (opp.equals("MAX")) {
          ConjuntoFracciones max = list.getAt(0);
          int i = 1;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) {
              break;
            }
            long comp = max.getNumerador() * frac.getDenominador()
              - frac.getNumerador() * max.getDenominador();
            if (comp < 0) {
              max = frac;
            }
            i++;
          }
          result = max;
        }
        List resultList = new List();
        resultList.addFront(result);
        b.add(celDestination, resultList);

        int fila = celDestination / col;
        int columna = celDestination % col;
        iMtx[fila][columna] = result.toString();
      }
  }


  /**
   * Corre el comando.
   * @param command
   * @return el nombre del comando
   */
  private String commandRun(final String command) {
    if (command == null) {
        return "";
    }
    String line = command.trim();
    if (line.isEmpty()) {
        return "";
    }
    String commandWithoutEqual =
    line.startsWith("=") ? line.substring(1) : line;
    int openParen = commandWithoutEqual.indexOf('(');
    int closeParen = commandWithoutEqual.lastIndexOf(')');
    String commandName;
    String parameters = "";
    if (openParen == -1 || closeParen == -1 || closeParen < openParen) {
      commandName = commandWithoutEqual.trim();
    } else {
      commandName = commandWithoutEqual.substring(0, openParen).trim();
      parameters =
      commandWithoutEqual.substring(openParen + 1, closeParen).trim();
    }
    if (commandName.isEmpty()) {
      return "";
    }
    if (commandName.equals("CEL")) {
      int pos = locate(parameters);
      setCelDestination(pos);
      erase(parameters);
    } else if (commandName.equals("SET")) {
      String[] parts = parameters.split(",");
      if (parts.length > 0) {
        String setName = parts[0].trim();
        List fractionList = new List();
        for (int i = 1; i < parts.length; i++) {
          String cell = parts[i].trim();
          int pos = locate(cell);
          List cellList = b.getAt(pos);
          if (cellList != null) {
            ConjuntoFracciones frac = cellList.getAt(0);
            if (frac != null) {
              fractionList.addBack(frac);
            }
          } else {
            int fila = pos / col;
            int columna = pos % col;
            String valor = iMtx[fila][columna];
            if (valor != null) {
              ConjuntoFracciones fraccion = parseFraccion(valor);
              fractionList.addBack(fraccion);
            }
          }
        }
        int key = setName.hashCode();
        b.add(key, fractionList);
      }
    } else if (commandName.equals("SUM")) {
      if (parameters.contains(":")) {
        String[] range = parameters.split(":");
        if (range.length == 2) {
          start = range[0];
          end = range[1];
          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.sumRange(this);
        }
      } else {
        handleSet("SUM", parameters);
      }
    } else if (commandName.equals("MUL")) {
        if (parameters.contains(":")) {
          String[] range = parameters.split(":");
          if (range.length == 2) {
            start = range[0];
            end = range[1];
            ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
            c.multiplyRange(this);
          }
        } else {
          handleSet("MUL", parameters);
        }
  } else if (commandName.equals("AVR")) {
      if (parameters.contains(":")) {
        String[] range = parameters.split(":");
        if (range.length == 2) {
          start = range[0];
          end = range[1];
            ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
            c.average(this);
        }
      } else {
        handleSet("AVR", parameters);
      }
    } else if (commandName.equals("MDN")) {
        if (parameters.contains(":")) {
          String[] range = parameters.split(":");
          if (range.length == 2) {
            start = range[0];
            end = range[1];
            ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
            c.median(this);
          }
        } else {
          handleSet("MDN", parameters);
        }
    } else if (commandName.equals("MIN")) {
      if (parameters.contains(":")) {
        String[] range = parameters.split(":");
        if (range.length == 2) {
          start = range[0];
          end = range[1];
          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.minimum(this);
        }
      } else {
        handleSet("MIN", parameters);
      }
    } else if (commandName.equals("MAX")) {
      if (parameters.contains(":")) {
        String[] rango = parameters.split(":");
        if (rango.length == 2) {
          start = rango[0];
          end = rango[1];
          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.maximum(this);
        }
      } else {
        handleSet("MAX", parameters);
      }
    } else if (commandName.equals("PRINT")) {
      if (parameters.isEmpty()) {
        printMatrix();
      } else {
        printConjunto(parameters);
      }

    }

    return commandName;
  }

  /**
   * Imprime la matriz usando los valores del árbol.
   */
  public void printMatrix() {
    maxWidth = 0;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (iMtx[i][j] != null) {
          int len = iMtx[i][j].length();
          if (len > maxWidth) {
            maxWidth = len;
          }
        }
      }
    }
    
    // Encabezado
    System.out.print("   |");
    for (int i = 0; i < col; i++) {
        char colLetter = (char) ('A' + i);
        System.out.printf("%" + (maxWidth + 1) + "c ", colLetter);
    }
    System.out.println();

    // Línea separadora
    System.out.print("---+");
    for (int i = 0; i < col; i++) {
        System.out.print("-".repeat(maxWidth + 1) + " ");
    }
    System.out.println();

    // Contenido - IMPRIMIR DESDE iMtx
    for (int i = 0; i < row; i++) {
      System.out.printf("%3d|", i + 1);
      for (int j = 0; j < col; j++) {
        String valor = iMtx[i][j].replace(",", "");
        String[] parts = valor.split("/");
        long num = Long.parseLong(parts[0]);
        long den = Long.parseLong(parts[1]);
        ConjuntoFracciones cf = new ConjuntoFracciones(num, den);
        System.out.printf("%" + (maxWidth + 1) + "s ", cf.toString());
      }
      System.out.println();
    }
    System.out.println();
  }
  /**
   * Imprime un conjunto de fracciones separadas por comas.
   * @param nombreConjunto nombre del conjunto a imprimir
   */
  public void printConjunto(final String nombreConjunto) {
    int key = nombreConjunto.hashCode();
    List lista = b.getAt(key);
    if (lista != null) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            ConjuntoFracciones fraccion = lista.getAt(i);
            if (fraccion == null) {
              break;
            }

            if (i > 0) {
                sb.append(", ");
            }
            sb.append(fraccion.toString());
            i++;
        }
        System.out.println(sb.toString());
    }
  }
  /**Añadir al arbol las matrices que no fueron usadas. */
  public void addUnused() {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
          if (used[i][j]) {
            continue;
          }
          int pos = i * col + j;

          if (b.getAt(pos) == null) {
              String valor = iMtx[i][j];
              ConjuntoFracciones frac = parseFraccion(valor.replace(",", ""));
              List l = new List();
              l.addFront(frac);
              b.add(pos, l);
          }
      }
    }
}



  /**
   * Hacer un parseo de un elemento formado por strings a una fraccion.
   * @param s
   * @return la nueva fraccion
   */
  private ConjuntoFracciones parseFraccion(final String s) {
    String clean = s.replace(",", "");
    String[] partes = clean.split("/");
    long num = Long.parseLong(partes[0]);
    long den = Long.parseLong(partes[1]);
    return new ConjuntoFracciones(num, den);
  }

}
