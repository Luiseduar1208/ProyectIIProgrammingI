package com.ucr.ecci.Sheets;
import java.lang.reflect.Parameter;
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

/**
 * Procesa el comando.
 * Verifica la linea de comando.
 */
public void comandProcessor() {
  String line = input.nextLine();
  commandRun(line);
}
/**
 * Corre el comando.
 */
private void commandRun(final String command) {
  String commandWhioutEqual = command.substring(1);
  int openParen = commandWhioutEqual.indexOf('(');
  int closeParen = commandWhioutEqual.lastIndexOf(')');


  String commandName = commandWhioutEqual.substring(0, openParen);
  String parameters = commandWhioutEqual.substring(openParen + 1, closeParen)

  if (commandName.equals("CEL")){
      //CEL(parameters);
  } else if (commandName.equals("SET")){
      //SET(parameters);
  } else if (commandName.equals("SUM")){
      //SUM(parameters);
  } else if (commandName.equals("MUL")){
      //MUL(parameters);
  } else if (commandName.equals("AVR")){
      String[] rango = parameters.split(":");
      start = rango[0];
      end = rango[1];
      ConjuntoFracciones resultado = new ConjuntoFracciones(0, 1).average(this);
      System.out.println(resultado);
  } else if (commandName.equals("MDN")){
      String[] rango = parameters.split(":");
      start = rango[0];
      end = rango[1];
      ConjuntoFracciones resultado = new ConjuntoFracciones(0, 1).median(this);
      System.out.println(resultado);
  } else if (commandName.equals("MIN")){
      String[] rango = parameters.split(":");
      start = rango[0];
      end = rango[1];
      ConjuntoFracciones resultado = new ConjuntoFracciones(0, 1).minimum(this);
      System.out.println(resultado);
  } else if (commandName.equals("MAX")){
      String[] rango = parameters.split(":");
      start = rango[0];
      end = rango[1];
      ConjuntoFracciones resultado = new ConjuntoFracciones(0, 1).maximum(this);
      System.out.println(resultado);
  } else if (commandName.equals("PRINT")){
      //PRINT(parameters);
  }

}
}
