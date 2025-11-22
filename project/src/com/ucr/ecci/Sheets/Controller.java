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


  /**Cargar la matriz y tomar row y col. */
  public void loadMtx() {
    row = input.nextInt();
    col = input.nextInt();
    input.nextLine();
    iMtx = new String[row][col];

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        iMtx[i][j] = input.next();
      }
    }

    used = new boolean[row][col];

  }

  /** Posición destino para guardar resultados de CEL(). */
  private int celDestination = -1;
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
      // Leer la siguiente línea no vacía (ignorar líneas en blanco)
      while (input.hasNextLine()) {
          String line = input.nextLine().trim();
          if (line.isEmpty()) {
              continue; // saltar líneas vacías
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

    used[fila][columna] = true;
  }



  /**
   * Procesa el comando SET para crear conjuntos de fracciones.
   * @param parameters nombre del conjunto y lista de celdas
   */
  public void setConjunto(final String parameters) {
    String[] parts = parameters.split(",");
    String nombre = parts[0];
    List listaFracciones = new List();

    for (int i = 1; i < parts.length; i++) {
      String celda = parts[i].trim();
      int posicion = locate(celda);
      List listaCelda = b.getAt(posicion);
      if (listaCelda == null) {
        int fila = posicion / col;
        int columna = posicion % col;
        String valor = iMtx[fila][columna];
        if (valor != null) {
          ConjuntoFracciones fraccion = parseFraccion(valor);
          listaFracciones.addBack(fraccion);
        }
      } else {
          ConjuntoFracciones fraccion = listaCelda.getAt(0);
          if (fraccion != null) {
            listaFracciones.addBack(fraccion);
          }
      }
    }

    int key = nombre.hashCode();
    b.add(key, listaFracciones);
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
          setConjunto(parameters);

      } else if (commandName.equals("SUM")) {
          String[] rango = parameters.split(":");
          start = rango[0];
          end = rango[1];

          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.sumRange(this);

      } else if (commandName.equals("MUL")) {
          String[] rango = parameters.split(":");
          start = rango[0];
          end = rango[1];

          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.multiplyRange(this);

      } else if (commandName.equals("AVR")) {
          String[] rango = parameters.split(":");
          start = rango[0];
          end = rango[1];

          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.average(this);

      } else if (commandName.equals("MDN")) {
          String[] rango = parameters.split(":");
          start = rango[0];
          end = rango[1];

          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.median(this);

      } else if (commandName.equals("MIN")) {
          String[] rango = parameters.split(":");
          start = rango[0];
          end = rango[1];

          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.minimum(this);

      } else if (commandName.equals("MAX")) {
          String[] rango = parameters.split(":");
          start = rango[0];
          end = rango[1];

          ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
          c.maximum(this);

      } else if (commandName.equals("PRINT")) {
          b.printAsSheet(row, col);
      }

    addUnused();

    return commandName;
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
    int num = Integer.parseInt(partes[0]);
    int den = Integer.parseInt(partes[1]);
    return new ConjuntoFracciones(num, den);
}




}
