package com.ucr.ecci.Sheets;

public class Btree {
  private class Node {
    /**Llave del elemento. */
    private int key;
    /**Dato. */
    private String treeData;
    /**Izquierda del nodo. */
    private Node left;
    /**Derecha del nodo. */
    private Node right;

    Node(final int key, final String data) {
      this.treeData = data;
      this.key = key;
      this.left = null;
      this.right = null;
    }

    //Getters
    public int getKey() {
      return key;
    }
    public String getTreeData() {
      return treeData;
    }
    public Node getLeft() {
      return left;
    }
    public Node getRight() {
      return right;
    }

    //Setters
    public void setTreeData(final String treeData) {
      this.treeData = treeData;
    }
    public void setLeft(final Node left) {
      this.left = left;
    }
    public void setRight(final Node right) {
      this.right = right;
    }

    public void printInOrder() {
      if (this.left != null) {
        this.left.printInOrder();
      }
      System.out.println("[" + key + "]" + " : " + treeData);
      if (this.right != null) {
        this.right.printInOrder();
      }
    }
  }
  /**Contador del arbol. */
  private int count;
  /**Raíz del árbol. */
  private Node root;

  /**Constructor de la clase {@code Btree}. */
  public Btree() {
    this.count = 0;
    this.root = null;
  }

  /**
   * Revisar si el árbol está vacío.
   * @return boolean
   */
  public boolean isEmpty() {
    if (this.count == 0) {
      return true;
    }
    return false;
  }

  /**
   * Método para añadir EN ORDEN elementos a un árbol.
   * @param key
   * @param data
   */
  public void add(final int key, final String data) {
    if (isEmpty()) {
      Node node = new Node(key, data);
      this.root = node;
      this.count++;
    } else {
      Node tmp = this.root;
      while (tmp != null) {
        if (key < tmp.getKey()) {
          if (tmp.getLeft() == null) {
            Node node = new Node(key, data);
            tmp.setLeft(node);
            this.count++;
            break;
          } else {
            tmp = tmp.getLeft();
          }
        } else if (key > tmp.getKey()) {
          if (tmp.getRight() == null) {
            Node node = new Node(key, data);
            tmp.setRight(node);
            this.count++;
            break;
          } else {
            tmp = tmp.getRight();
          }
        } else {
          // key es igual a la llave de un nodo.
          break;
        }
      }
    }
  }


  /**
   * Método para encontrar un valor arbitrario del árbol según su llave.
   * @param key
   * @return {@code treeData}
   */
  public String getAt(final int key) {
    String treeData = null;
    if (!isEmpty()) {
      Node tmp = this.root;
      while (tmp != null) {
        if (key < tmp.getKey()) {
          tmp = tmp.getLeft();
        } else if (key > tmp.getKey()) {
          tmp = tmp.getRight();
        } else {
          treeData = tmp.getTreeData();
          break;
        }
      }
    }
    return treeData;
  }

  /**
   * Método para poner un valor arbitrario del árbol según su llave.
   * @param key
   * @param treeData
   */
  public void setAt(final int key, final String treeData) {
    if (!isEmpty()) {
      Node tmp = this.root;
      while (tmp != null) {
        if (key < tmp.getKey()) {
          tmp = tmp.getLeft();
        } else if (key > tmp.getKey()) {
          tmp = tmp.getRight();
        } else if (tmp.getKey() == key) {
          tmp.setTreeData(treeData);
          break;
        }
      }
    }
  }

  /**Impresión de la clase {@code Btree}. */
  public void printInOrder() {
    if (!isEmpty()) {
      this.root.printInOrder();
    }
  }

}
