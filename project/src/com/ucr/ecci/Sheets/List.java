package com.ucr.ecci.Sheets;

public class List {
  private class Node {
    /**Dato que contiene la lista. */
    private ConjuntoFracciones listData;
    /**Enlace siguiente de la lista. */
    private Node next;
    /**Enlace previo de la lista. */
    private Node previous;

    Node(final ConjuntoFracciones data) {
      this.listData = data;
      this.next = null;
      this.previous = null;
    }
    //Getters
    public ConjuntoFracciones getListData() {
      return listData;
    }
    public Node getNext() {
      return next;
    }
    public Node getPrevious() {
      return previous;
    }

    //Setters
    public void setListData(final ConjuntoFracciones data) {
      this.listData = data;
    }
    public void setNext(final Node next) {
      this.next = next;
    }
    public void setPrevious(final Node previous) {
      this.previous = previous;
    }
    @Override
    public String toString() {
      return String.valueOf(listData);
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
  public void addFront(final ConjuntoFracciones data) {
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
  public void addBack(final ConjuntoFracciones data) {
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
  public ConjuntoFracciones getAt(final int index) {
    ConjuntoFracciones data = null;
    if (0 <= index && index < this.size) {
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
  public void setAt(final int index, final ConjuntoFracciones data) {
    if (0 <= index && index < this.size) {
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
}
