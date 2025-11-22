package com.ucr.ecci.Sheets;

public class Btree {
  private class Node {
    /**Llave del elemento. */
    private int key;
    /**Dato. */
    private List treeData;
    /**Izquierda del nodo. */
    private Node left;
    /**Derecha del nodo. */
    private Node right;

    Node(final int key, final List data) {
      this.treeData = data;
      this.key = key;
      this.left = null;
      this.right = null;
    }

    //Getters
    public int getKey() {
      return key;
    }
    public List getTreeData() {
      return treeData;
    }
    public Node getLeft() {
      return left;
    }
    public Node getRight() {
      return right;
    }

    //Setters
    public void setTreeData(final List treeData) {
      this.treeData = treeData;
    }
    public void setLeft(final Node left) {
      this.left = left;
    }
    public void setRight(final Node right) {
      this.right = right;
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
  public void add(final int key, final List data) {
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
  public List getAt(final int key) {
    List treeData = null;
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
  public void setAt(final int key, final List treeData) {
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

  /**
   * Poner el arbol como un arreglo para una impresion mas facil.
   * @return el arreglo
   */
  public List[] toArrayInOrder() {
    List[] arr = new List[this.count];
    Index idx = new Index();
    fillInOrder(this.root, arr, idx);
    return arr;
  }

  private void fillInOrder(final Node n, final List[] arr, final Index idx) {
      if (n == null) {
        return;
      }
      fillInOrder(n.getLeft(), arr, idx);
      arr[idx.value++] = n.getTreeData();
      fillInOrder(n.getRight(), arr, idx);
  }

  /**Índice para los datos. */
  private class Index {
    /**Valor. */
      private int value = 0;
  }

  /**
   * Impresion del árbol en estilo de matriz.
   * @param col
   * @param row
   */
  public void printAsSheet(final int row, final int col) {
      List[] array = toArrayInOrder();

      // Encabezado
      System.out.print("   |");
      for (int i = 0; i < col; i++) {
          char colLetter = (char) ('A' + i);
          System.out.printf("%4c ", colLetter);
      }
      System.out.println();

      // Línea separadora
      System.out.print("---+");
      for (int i = 0; i < col; i++) {
          System.out.print("---- ");
      }
      System.out.println();

      // Contenido
      int count = 1;
      System.out.printf("%3d|", count);

      for (int i = 0; i < array.length; i++) {
          ConjuntoFracciones cf = array[i].getAt(0);
          System.out.printf("%4s ", cf.toString());

          if ((i + 1) % col == 0 && i < array.length - 1) {
              System.out.println();
              count++;
              System.out.printf("%3d|", count);
          }
      }
      System.out.println();
  }

}
