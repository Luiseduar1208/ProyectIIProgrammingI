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
        int key = parameters.hashCode();
        List list = b.getAt(key);
        if (list != null && celDestination != -1) {
          ConjuntoFracciones sum = new ConjuntoFracciones(0, 1);
          int i = 0;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) break;
            sum = sum.sum(frac);
            i++;
          }
          List resultList = new List();
          resultList.addFront(sum);
          b.add(celDestination, resultList);
        }
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
        int key = parameters.hashCode();
        List list = b.getAt(key);
        if (list != null && celDestination != -1) {
          ConjuntoFracciones product = new ConjuntoFracciones(1, 1);
          int i = 0;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) break;
            product = product.multiply(frac);
            i++;
          }
          List resultList = new List();
          resultList.addFront(product);
          b.add(celDestination, resultList);
        }
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
        int key = parameters.hashCode();
        List list = b.getAt(key);
        if (list != null && celDestination != -1) {
          ConjuntoFracciones sum = new ConjuntoFracciones(0, 1);
          int count = 0;
          int i = 0;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) break;
            sum = sum.sum(frac);
            count++;
            i++;
          }
          ConjuntoFracciones average = sum.divide(new ConjuntoFracciones(count, 1));
          List resultList = new List();
          resultList.addFront(average);
          b.add(celDestination, resultList);
        }
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
        int key = parameters.hashCode();
        List list = b.getAt(key);
        if (list != null && celDestination != -1) {
          ConjuntoFracciones min = list.getAt(0);
          int i = 1;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) break;
            long comp = min.getNumerador() * frac.getDenominador() - frac.getNumerador() * min.getDenominador();
            if (comp > 0) {
              min = frac;
            }
            i++;
          }
          List resultList = new List();
          resultList.addFront(min);
          b.add(celDestination, resultList);
        }
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
        int key = parameters.hashCode();
        List list = b.getAt(key);
        if (list != null && celDestination != -1) {
          ConjuntoFracciones max = list.getAt(0);
          int i = 1;
          while (true) {
            ConjuntoFracciones frac = list.getAt(i);
            if (frac == null) break;
            long comp = max.getNumerador() * frac.getDenominador() - frac.getNumerador() * max.getDenominador();
            if (comp < 0) {
              max = frac;
            }
            i++;
          }
          List resultList = new List();
          resultList.addFront(max);
          b.add(celDestination, resultList);
        }
      }
    } else if (commandName.equals("PRINT")) {
      if (parameters.isEmpty()){
        b.printAsSheet(row, col);          
      } else {
        printConjunto(parameters);
      }

    }

    addUnused();

    return commandName;
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
            if (fraccion == null) break;
            
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
    int num = Integer.parseInt(partes[0]);
    int den = Integer.parseInt(partes[1]);
    return new ConjuntoFracciones(num, den);
}




}
