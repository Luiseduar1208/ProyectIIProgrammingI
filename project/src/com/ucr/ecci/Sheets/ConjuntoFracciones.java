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
   * Transforma la fracción a una representación en formato String.
   * @autor Alberto Rojas (implementación original)
   */
  @Override
  public String toString() {
    return this.numerador + "/" + this.denominador;
  }
}
