package com.ucr.ecci.Sheets;

public class ConjuntoFracciones {

  // Definimos los atributos de una fracción.

  /** Numerador de la fracción. */
  private long numerador;
  /** Denominador de la fracción. */
  private long denominador;
  /**
   * Constructor de la clase.
   * @param numerador  Numerador de la fracción.
   * @param denominador Denominador de la fracción y nunca puede ser 0.
   * @autor Alberto Rojas (implementación original)
   */
  public ConjuntoFracciones(final long numerador, final long denominador) {
    // Asignación del valor del numerador.
    this.numerador = numerador;

    // Asignación del valor del denominador.
    if (denominador == 0) {
      this.denominador = 1;
    } else {
      this.denominador = denominador;
    }

    // Si el denominador es negativo.
    if (this.denominador < 0) {
      this.numerador *= -1;
      this.denominador *= -1;
    }

    // Se simplifica la fracción.
    simplificar();
  }
  /**
   * Saca el maximo común divisor.
   * @param a Primer num para calcular el MCD, no puede ser negativo.
   * @param b Segundo num para calcular el MCD, no puede ser negativo.
   * @return El maximo común divisor de los dos números.
   * @autor Alberto Rojas (implementación original)
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
   * Simplifica fracciones.
   * @autor Alberto Rojas (implementación original)
   */
  private void simplificar() {
    long mcd = maximoComunDivisor(this.numerador, this.denominador);
    if (mcd != 0) {
      this.numerador /= mcd;
      this.denominador /= mcd;
    }
  }

  /**
   * Suma fracciones.
   * @param other Fracción a sumar.
   * @return La suma del valor de la fracción.
   * @autor Alberto Rojas (implementación original)
   * @autor Cesar Perez (optimización)
   */
  public ConjuntoFracciones sum(final ConjuntoFracciones other) {
    long newNumerador = (this.numerador * other.denominador)
      + (other.numerador * this.denominador);
    long newDenominador = this.denominador * other.denominador;
    return new ConjuntoFracciones(newNumerador, newDenominador);
  }
  /**
   * Multiplica fracciones.
   * @param other Fracción a multiplicar.
   * @return La multiplicación del valor de la fracción.
   */
  public ConjuntoFracciones multiplication(final ConjuntoFracciones other) {
    long newNumerador = this.numerador * other.numerador;
    long newDenominador = this.denominador * other.denominador;
    return new ConjuntoFracciones(newNumerador, newDenominador);
  }
  /**
   * Método de division.
   * @param divider
   * @return la división de las fracciones.
   */
  public ConjuntoFracciones divide(final ConjuntoFracciones divider) {
    long newNumerador = (this.numerador * divider.denominador);
    long newDenominator = (this.denominador * divider.numerador);
    return new ConjuntoFracciones(newNumerador, newDenominator);
  }

  /**
   * Función capaz de calcular la fracción más grande.
   * @param controller
   * @return la fracción más grande entre dos puntos arbitrarios
   */
  public ConjuntoFracciones maximum(final Controller controller) {

    String[][] mtx = controller.getiMtx();
    int col = controller.getCol();

    int calcStart = controller.startI() * col + controller.startJ();
    int calcEnd   = controller.endI()   * col + controller.endJ();
    int count = (calcEnd - calcStart) + 1;

    ConjuntoFracciones[] fracciones = new ConjuntoFracciones[count];
    double[] decimalValue = new double[count];

    int index = 0;
    for (int idx = calcStart; idx <= calcEnd; idx++) {

        int fila = idx / col;
        int columna = idx % col;

        String valor = mtx[fila][columna];
        String[] partes = valor.split("/");

        long num = Long.parseLong(partes[0]);
        long den = Long.parseLong(partes[1]);

        double decimalV = (double) num / den;

        decimalValue[index] = decimalV;

        ConjuntoFracciones f = new ConjuntoFracciones(num, den);
        fracciones[index] = f;

        index++;
    }

    int maxIndex = 0;
    double maxValor = decimalValue[0];
    for (int i = 1; i < count; i++) {
      if (decimalValue[i] > maxValor) {
        maxValor = decimalValue[i];
        maxIndex = i;
      }
    }
    return fracciones[maxIndex];
  }
  /**
   * Función capaz de calcular la fracción más pequeña.
   * @param controller
   * @return la fracción más pequeña entre dos puntos arbitrarios
   */
  public ConjuntoFracciones minimum(final Controller controller) {

    String[][] mtx = controller.getiMtx();
    int col = controller.getCol();

    int calcStart = controller.startI() * col + controller.startJ();
    int calcEnd   = controller.endI()   * col + controller.endJ();
    int count = (calcEnd - calcStart) + 1;

    ConjuntoFracciones[] fracciones = new ConjuntoFracciones[count];
    double[] decimalValue = new double[count];

    int index = 0;
    for (int idx = calcStart; idx <= calcEnd; idx++) {

        int fila = idx / col;
        int columna = idx % col;

        String valor = mtx[fila][columna];
        String[] partes = valor.split("/");

        long num = Long.parseLong(partes[0]);
        long den = Long.parseLong(partes[1]);

        double decimalV = (double) num / den;

        decimalValue[index] = decimalV;

        ConjuntoFracciones f = new ConjuntoFracciones(num, den);
        fracciones[index] = f;

        index++;
    }

    int minIndex = 0;
    double minValor = decimalValue[0];
    for (int i = 1; i < count; i++) {
      if (decimalValue[i] < minValor) {
        minValor = decimalValue[i];
        minIndex = i;
      }
    }
    return fracciones[minIndex];
  }
  /**
   * Clase que calcula el promedio.
   * @param controller
   * @return devuelve el promedio
   */
  public ConjuntoFracciones average(final Controller controller) {

    String[][] mtx = controller.getiMtx();
    int col = controller.getCol();

    int calcStart = controller.startI() * col + controller.startJ();
    int calcEnd   = controller.endI()   * col + controller.endJ();

    ConjuntoFracciones acumulado = new ConjuntoFracciones(0, 1);

    for (int idx = calcStart; idx <= calcEnd; idx++) {

      int fila = idx / col;
      int columna = idx % col;
      String valor = mtx[fila][columna];

      String[] partes = valor.split("/");
      long num = Long.parseLong(partes[0]);
      long den = Long.parseLong(partes[1]);
      ConjuntoFracciones f = new ConjuntoFracciones(num, den);
      acumulado = acumulado.sum(f);
    }
    int count = (calcEnd - calcStart + 1);
    ConjuntoFracciones avg = new ConjuntoFracciones(count, 1);
    avg = acumulado.divide(avg);
    return avg;
  }
  /**
   * Función capaz de calcular la mediana.
   * @param controller
   * @return la mediana entre dos puntos arbitrarios
   */
  public ConjuntoFracciones median(final Controller controller) {

    String[][] mtx = controller.getiMtx();
    int col = controller.getCol();

    int calcStart = controller.startI() * col + controller.startJ();
    int calcEnd   = controller.endI()   * col + controller.endJ();
    int count = (calcEnd - calcStart) + 1;

    ConjuntoFracciones[] medianCalc = new ConjuntoFracciones[count];
    double[] decimalValue = new double[count];

    int index = 0;
    for (int idx = calcStart; idx <= calcEnd; idx++) {

        int fila = idx / col;
        int columna = idx % col;

        String valor = mtx[fila][columna];
        String[] partes = valor.split("/");

        long num = Long.parseLong(partes[0]);
        long den = Long.parseLong(partes[1]);

        double decimalV = (double) num / den;

        decimalValue[index] = decimalV;

        ConjuntoFracciones f = new ConjuntoFracciones(num, den);
        medianCalc[index] = f;

        index++;
    }

  int n = medianCalc.length;
  for (int i = 0; i < n - 1; i++) {
      boolean swapped = false;
      for (int j = 0; j < n - 1 - i; j++) {
          if (decimalValue[j] > decimalValue[j + 1]) {
              double tmpD = decimalValue[j];
              decimalValue[j] = decimalValue[j + 1];
              decimalValue[j + 1] = tmpD;

              ConjuntoFracciones tmp = medianCalc[j];
              medianCalc[j] = medianCalc[j + 1];
              medianCalc[j + 1] = tmp;

              swapped = true;
          }
      }
      if (!swapped) {
          break;
      }
  }

  ConjuntoFracciones median;

  if (n % 2 == 0) {
      ConjuntoFracciones upper = medianCalc[n / 2];
      ConjuntoFracciones lower = medianCalc[(n - 1) / 2];
      ConjuntoFracciones two = new ConjuntoFracciones(2, 1);
      median = lower.sum(upper).divide(two);
  } else {
      median = medianCalc[n / 2];
  }
  return median;
}
  /**
   * Transforma la fracción a una representación en formato String.
   * @autor Alberto Rojas (implementación original)
   */
  @Override
  public String toString() {
    return this.numerador + "/" + this.denominador;
  }
}
