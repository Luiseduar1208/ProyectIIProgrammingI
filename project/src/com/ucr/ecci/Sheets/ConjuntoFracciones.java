package com.ucr.ecci.Sheets;

public class ConjuntoFracciones {

  /** Numerador de la fracción. */
  private long numerador;
  /** Denominador de la fracción. Nunca puede ser 0. */
  private long denominador;

  /**
   * Constructor de la clase.
   * @param numerador Numerador de la fracción.
   * @param denominador Denominador de la fracción.
   */
  public ConjuntoFracciones(final long numerador, final long denominador) {

    this.numerador = numerador;

    if (denominador == 0) {
      this.denominador = 1;
    } else {
      this.denominador = denominador;
    }

    if (this.denominador < 0) {
      this.numerador *= -1;
      this.denominador *= -1;
    }

    simplificar();

  }

  /** Simplifica la fracción al dividir ambos valores entre su MCD. */
  private void simplificar() {
    long mcd = maximoComunDivisor(
      Math.abs(this.numerador), Math.abs(this.denominador));
    if (mcd != 0) {
      this.numerador /= mcd;
      this.denominador /= mcd;
    }
  }

  /**
   * Calcula el máximo común divisor.
   * @param a Primer número.
   * @param b Segundo número.
   * @return El MCD.
   */
  private long maximoComunDivisor(final long a, final long b) {
    long tmpA = a;
    long tmpB = b;

    while (tmpB != 0) {
      long tmp = tmpB;
      tmpB = tmpA % tmpB;
      tmpA = tmp;
    }

    return tmpA;
  }

  /**
   * Suma fracciones.
   * @param other fracción a sumar.
   * @return resultado de la suma.
   */
  public ConjuntoFracciones sum(final ConjuntoFracciones other) {
    long newNumerador =
        (this.numerador * other.denominador)
            + (other.numerador * this.denominador);

    long newDenominador = this.denominador * other.denominador;

    return new ConjuntoFracciones(newNumerador, newDenominador);
  }
  /**
   * Suma fracciones en un rango.
   * @param controller controlador con la matriz.
   */
  public void sumRange(final Controller controller) {
    ConjuntoFracciones[] fractions = loadAndSort(controller);
    
    ConjuntoFracciones result = new ConjuntoFracciones(1, 1);
    
    for (int i = 0; i < fractions.length; i++) {
        result = result.sum(fractions[i]);
    }
    
    int destination = controller.getCelDestination();
    List l = new List();
    l.addFront(result);
    controller.getB().add(destination, l);
  }
  /**
   * Multiplica fracciones.
   * @param other fracción a multiplicar.
   * @return resultado de la multiplicar.
   */
  public ConjuntoFracciones multiply(final ConjuntoFracciones other) {
    long newNumerador = this.numerador * other.numerador;
    long newDenominador = this.denominador * other.denominador;

    return new ConjuntoFracciones(newNumerador, newDenominador);
  }
  /**
   * Multiplica fracciones en un rango.
   * @param controller controlador con la matriz.
   */
  public void multiplyRange(final Controller controller) {
    ConjuntoFracciones[] fractions = loadAndSort(controller);
    
    ConjuntoFracciones result = new ConjuntoFracciones(1, 1);
    
    for (int i = 0; i < fractions.length; i++) {
        result = result.multiply(fractions[i]);
    }
    
    int destination = controller.getCelDestination();
    List l = new List();
    l.addFront(result);
    controller.getB().add(destination, l);
  }
  /**
   * Divide fracciones.
   * @param divider Fracción dividida.
   * @return resultado de la división.
   */
  public ConjuntoFracciones divide(final ConjuntoFracciones divider) {

    if (divider.numerador == 0) {
      throw new ArithmeticException("No se puede dividir entre cero");
    }

    long newNumerador = this.numerador * divider.denominador;
    long newDenominador = this.denominador * divider.numerador;

    return new ConjuntoFracciones(newNumerador, newDenominador);
  }

  /**
   * Carga del rango de celdas, parsea fracciones y las ordena.
   * @param controller controlador con la matriz.
   * @return un arreglo ordenado ascendentemente por valor decimal.
   */
  private ConjuntoFracciones[] loadAndSort(final Controller controller) {

    String[][] mtx = controller.getiMtx();
    int col = controller.getCol();

    int calcStart = controller.startI() * col + controller.startJ();
    int calcEnd = controller.endI() * col + controller.endJ();
    int count = calcEnd - calcStart + 1;

    ConjuntoFracciones[] arr = new ConjuntoFracciones[count];
    double[] decimal = new double[count];

    int index = 0;

    for (int idx = calcStart; idx <= calcEnd; idx++) {

      int row = idx / col;
      int column = idx % col;

      String[] parts = mtx[row][column].split("/");

      long num = Long.parseLong(parts[0]);
      long den = Long.parseLong(parts[1]);

      ConjuntoFracciones f = new ConjuntoFracciones(num, den);

      arr[index] = f;
      decimal[index] = (double) num / den;

      index++;
    }

    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
      boolean swapped = false;

      for (int j = 0; j < n - 1 - i; j++) {
        if (decimal[j] > decimal[j + 1]) {

          double tmpD = decimal[j];
          decimal[j] = decimal[j + 1];
          decimal[j + 1] = tmpD;

          ConjuntoFracciones tmp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = tmp;

          swapped = true;
        }
      }
      if (!swapped) {
        break;
      }
    }

    return arr;
  }

  /**
   * Calcula el promedio del conjunto.
   *
   * @param controller controlador con la matriz.
   */
  public void average(final Controller controller) {

    ConjuntoFracciones[] arr = loadAndSort(controller);

    ConjuntoFracciones acumulado = new ConjuntoFracciones(0, 1);

    for (int i = 0; i < arr.length; i++) {
      ConjuntoFracciones f = arr[i];
      acumulado = acumulado.sum(f);
    }

    ConjuntoFracciones divisor = new ConjuntoFracciones(arr.length, 1);

    ConjuntoFracciones average = acumulado.divide(divisor);
    int destine = controller.getCelDestination();

    List l = new List();
    l.addFront(average);
    controller.getB().add(destine, l);
  }

  /**
   * Calcula la mediana.
   * @param controller controlador con la matriz.
   */
  public void median(final Controller controller) {

    ConjuntoFracciones[] arr = loadAndSort(controller);
    int n = arr.length;
    ConjuntoFracciones median = new ConjuntoFracciones(0, 1);
    if (n % 2 == 0) {

      ConjuntoFracciones a = arr[(n - 1) / 2];
      ConjuntoFracciones b = arr[n / 2];

      ConjuntoFracciones two = new ConjuntoFracciones(2, 1);
      median = a.sum(b).divide(two);

    } else {
      median = arr[n / 2];
    }

    int destine = controller.getCelDestination();

    List l = new List();
    l.addFront(median);
    controller.getB().add(destine, l);
  }

  /**
   * Calcula el valor máximo del conjunto.
   * @param controller controlador con la matriz.
   */
  public void maximum(final Controller controller) {

    ConjuntoFracciones[] arr = loadAndSort(controller);
    ConjuntoFracciones max = arr[arr.length - 1];

    int destine = controller.getCelDestination();

    List l = new List();
    l.addFront(max);
    controller.getB().add(destine, l);

  }

  /**
   * Calcula el valor mínimo del conjunto.
   * @param controller controlador con la matriz.
   */
  public void minimum(final Controller controller) {

    ConjuntoFracciones[] arr = loadAndSort(controller);
    ConjuntoFracciones min = arr[0];

    int destine = controller.getCelDestination();

    List l = new List();
    l.addFront(min);
    controller.getB().add(destine, l);

  }
  /**
   * Imprime la Hoja.
   * @param controller controlador con la matriz.
   */
  public void printSheet(final Controller controller){
    String[][] mtx = controller.getiMtx();
    int rows = controller.getRow();
    int cols = controller.getCol();
    
    int maxWidth = 4;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (mtx[i][j].length() > maxWidth) {
                maxWidth = mtx[i][j].length();
            }
        }
    }
    
    System.out.print("   |");
    for (int j = 0; j < cols; j++) {
        char colLetter = (char) ('A' + j);
        System.out.printf(" %" + maxWidth + "s", colLetter);
    }
    System.out.println();
    
    System.out.print("---+");
    for (int j = 0; j < cols; j++) {
        for (int k = 0; k < maxWidth; k++) {
            System.out.print("-");
        }
        System.out.print(" ");
    }
    System.out.println();
    
    for (int i = 0; i < rows; i++) {
        System.out.printf("%2d |", i + 1);
        for (int j = 0; j < cols; j++) {
            System.out.printf(" %" + maxWidth + "s", mtx[i][j]);
        }
        System.out.println();
    }
  }
  /** Representación en formato texto. */
  @Override
  public String toString() {
    return this.numerador + "/" + this.denominador;
  }
}
