package com.ucr.ecci.Sheets;
import java.util.Scanner;

public class Controller {
  /**Scanner. */
  private Scanner input  = new Scanner(System.in);
  /**Matriz de entrada. */
  private String[][] iMtx;
  /**Cantidad de lineas. */
  private int row;
  /**Cantidad de colums. */
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
   * Getter de la colum.
   * @return col
   */
  public int getCol() {
    return col;
  }
  /**
   * Getter de la row.
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
   * @return StartI (row)
   */
  public int startI() {
      int rowStart = Integer.parseInt(start.substring(1)) - 1;
      return rowStart;
  }

  /**
   * Pasar las coords a números.
   * @return StartJ (colum)
   */
  public int startJ() {
      int colStart = start.charAt(0) - 'A';
      return colStart;
  }

  /**
   * Pasar las coords a números.
   * @return EndI (row)
   */
  public int endI() {
      int rowEnd = Integer.parseInt(end.substring(1)) - 1;
      return rowEnd;
  }

  /**
   * Pasar las coords a números.
   * @return EndJ (colum)
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
    String letter = parameters.substring(0, 1);
      String number = parameters.substring(1);

      int column = letter.charAt(0) - 'A';
      int row = Integer.parseInt(number) - 1;

      int position = row * col + column;

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
      String[] range = parameters.split(":");
      start = range[0];
      end = range[1];

      ConjuntoFracciones c = new ConjuntoFracciones(1, 1);
      c.sumRange(this); 

    } else if (commandName.equals("MUL")) {
      String[] range = parameters.split(":");
      start = range[0];
      end = range[1];

      ConjuntoFracciones c = new ConjuntoFracciones(1, 1);
      c.multiplyRange(this);
      }

    } else if (commandName.equals("AVR")) {
        String[] range = parameters.split(":");
        start = range[0];
        end = range[1];

        ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
        c.average(this);


    } else if (commandName.equals("MDN")) {
        String[] range = parameters.split(":");
        start = range[0];
        end = range[1];

        ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
        c.median(this);

    } else if (commandName.equals("MIN")) {
        String[] range = parameters.split(":");
        start = range[0];
        end = range[1];

        ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
        c.minimum(this);

    } else if (commandName.equals("MAX")) {
        String[] range = parameters.split(":");
        start = range[0];
        end = range[1];

        ConjuntoFracciones c = new ConjuntoFracciones(0, 1);
        c.maximum(this);

    } else if (commandName.equals("PRINT")) {
        new ConjuntoFracciones(0, 1).printSheet(this);
    }

  }
}
