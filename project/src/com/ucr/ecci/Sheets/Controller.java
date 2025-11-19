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

    input.nextLine();
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
   */
  public void commandProcessor() {
    String line = input.nextLine();
    commandRun(line);
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
   * Corre el comando.
   * @param command
   */
  private void commandRun(final String command) {
    String commandWithoutEqual = command.substring(1);
    int openParen = commandWithoutEqual.indexOf('(');
    int closeParen = commandWithoutEqual.lastIndexOf(')');


    String commandName = commandWithoutEqual.substring(0, openParen);
    String
      parameters = commandWithoutEqual.substring(openParen + 1, closeParen);

    if (commandName.equals("CEL")) {
      int pos = locate(parameters);
      setCelDestination(pos);
      return;

    } else if (commandName.equals("SET")) {
      return;
        //SET(parameters);
    } else if (commandName.equals("SUM")) {
      return;
        //SUM(parameters);
    } else if (commandName.equals("MUL")) {
      return;
        //MUL(parameters);
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
        //PRINT(parameters);
        return;
    }

  }
}
