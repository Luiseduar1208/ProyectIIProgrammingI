package com.ucr.ecci.Sheets;
/**
 * Proyecto II CI-0112. Organización fracciones en hoja de cálculo.
 * @author Luis Eduardo Hernández Rojas
 * @author César Pérez Mendoza
 * @version 2.1
 */
public final class Main {
  /**
   * Método main.
   * @param args
   */
  public static void main(final String[] args) {
    Controller c = new Controller();
    c.loadMtx();

    // Primero ejecutar todos los comandos excepto PRINT
    while (true) {
        String command = c.commandProcessor();

        if (command.equals("PRINT") || command.isEmpty()) {
            break;
        }
    }

    c.addUnused();

    while (true) {
        String command = c.commandProcessor();

        if (command.isEmpty()) {
            break;
        }
    }
  }
  private Main() { }
}
