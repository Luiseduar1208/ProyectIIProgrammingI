package com.ucr.ecci.Sheets;
import java.util.Scanner;

public class Controller {
  /**Scanner. */
  private Scanner input  = new Scanner(System.in);

  /**Testeo árbol binario. */
  public void testBtree() {
    Btree b = new Btree();
    b.add(8,  "Walter Pate Centeno");
    b.add(10,  "Alonso Pocho Solis");
    b.add(23,  "Paulo La Cobra Wanchope");
    b.add(5,  "Celso La Maleta Borges");
    b.printInOrder();
  }
}
