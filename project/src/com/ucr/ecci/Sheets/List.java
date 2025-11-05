package com.ucr.ecci.Sheets;

public class List {
  private class Node {
    /**Dato que contiene la lista. */
    private String listData;
    /**Enlace siguiente de la lista. */
    private Node next;
    /**Enlace previo de la lista. */
    private Node previous;

    Node(final String data) {
      this.listData = data;
      this.next = null;
      this.previous = null;
    }
    //Getters
    public String getListData() {
      return listData;
    }
    public Node getNext() {
      return next;
    }
    public Node getPrevious() {
      return previous;
    }

    //Setters
    public void setListData(final String data) {
      this.listData = data;
    }
    public void setNext(final Node next) {
      this.next = next;
    }
    public void setPrevious(final Node previous) {
      this.previous = previous;
    }
  }
  /**Tamaño de la lista. */
  private int size;
  /**Cabeza o parte frontal de la lista. */
  private Node head;
  /**Cola o elemento trasero de la lista. */
  private Node tail;

  /**Constructor de la clase lista. */
  public List() {
    this.size = 0;
    this.head = null;
    this.tail = null;
  }

  /**
   * Método revisión si la lista está vacia {@code isEmpty()}.
   * @return boolean
   */
  public boolean isEmpty() {
    if (this.size == 0) {
      return true;
    }
    return false;
  }
  /**
   * Método encargado de añadir elementos al principio de la lista.
   * @param data
   */
  public void addFront(final String data) {
    Node n = new Node(data);
    if (isEmpty()) {
      this.head = n;
      this.tail = n;
    } else {
      this.head.setPrevious(n);
      n.setNext(this.head);
      this.head = n;
    }
    this.size++;
  }

  /**
   * Método encargado de añadir elementos al final de la lista.
   * @param data
   */
  public void addBack(final String data) {
    Node n = new Node(data);
    if (isEmpty()) {
      this.head = n;
      this.tail = n;
    } else {
      this.tail.setNext(n);
      n.setPrevious(this.tail);
      this.tail = n;
    }
    this.size++;
  }

  /**
   * Método para remover un valor arbitrario en una lista según su índice.
   * @param index
   */
  public void remove(final int index) {
    if (0 <= index && index <= this.size) {
      if (index == 0) {
        this.head = this.head.getNext();
        this.head.setPrevious(null);
      } else if (index == this.size - 1) {
        this.tail = this.tail.getPrevious();
        this.tail.setNext(null);
      } else {
        Node tmp = this.head;
        for (int i = 0; i < this.size; i++) {
          if (i == index) {
            tmp.getPrevious().setNext(tmp.getNext());
            tmp.getNext().setPrevious(tmp.getPrevious());
          }
          tmp = tmp.getNext();
        }
      }
      this.size--;
    }
  }

  /**
   * Método para encontrar un valor dentro de la lista según su índice.
   * @param index
   * @return data
   */
  public String getAt(final int index) {
    String data = null;
    if (0 < index && index < this.size) {
      Node tmp = this.head;
      for (int i = 0; i < this.size; i++) {
        if (i == index) {
          data = tmp.getListData();
          break;
        }
        tmp = tmp.getNext();
      }
    }
    return data;
  }

  /**
   * Método para poner un dato en un lugar de la lista según su índice.
   * @param index
   * @param data
   */
  public void setAt(final int index, final String data) {
    if (0 < index && index < this.size) {
      Node tmp = this.head;
      for (int i = 0; i < this.size; i++) {
        if (i == index) {
          tmp.setListData(data);
          break;
        }
        tmp = tmp.getNext();
      }
    }
  }

    /**
   * Método encargado de ordenar los elementos de la lista de forma ascendente.
   */
  public void sort() {
    if (this.head == null || this.head.getNext() == null) {
      return; // no hay nada que ordenar
    }
    boolean swapped = true;
    while (swapped) {
      swapped = false;
      Node tmp = this.head;
      while (tmp.getNext() != null) {
        // Comparar los datos de tipo String
        if (tmp.getListData().compareTo(tmp.getNext().getListData()) > 0) {
          // Intercambiar los valores (no los nodos)
          String aux = tmp.getListData();
          tmp.setListData(tmp.getNext().getListData());
          tmp.getNext().setListData(aux);
          swapped = true;
        }
        tmp = tmp.getNext();
      }
    }
  }

  /**Impresión básica o prueba de la lista. */
  public void print() {
    Node tmp = this.head;
    for (int i = 0; i < this.size; i++) {
      System.out.println(tmp + "->");
      tmp = tmp.getNext();
    }
    System.out.println();
  }


}
