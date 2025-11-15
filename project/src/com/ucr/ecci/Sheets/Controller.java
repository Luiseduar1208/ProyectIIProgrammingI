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

  /**Cargar la matriz y tomar row y col. */
  public void loadMtx() {
    row = input.nextInt();
    col = input.nextInt();
    iMtx = new String[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        iMtx[i][j] = input.next();
      }
    }
  }
  /**
   * Columna.
   * @return col
   */
  public int getCol() {
    return col;
  }
  /**
   * Fila.
   * @return row
   */
  public int getRow() {
    return row;
  }
  /**
   * Matriz.
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


}
