package es.usantatecla.paradigms.mutable.recursive.object.inyection;

public class List<T> {

  private Node<T> head;

  public List() {
    this.head = null;
  }

  public List(Node<T> head) {
    this.head = head;
  }

  public void add(T element) {
    Node<T> node = new Node<T>(element);
    if (this.isEmpty()) {
      this.setHead(node);
    } else {
      Node<T> head = this.head;
      if (head.isLast()){
        head.setNext(node);
      } else {
        this.getTail().add(element);
      }
    }
  }
  
  public void filter(Predicate<T> predicate) {
    Node<T> head = this.getHead();
    if (head != null) {
      List<T> tail = this.getTail();
      tail.filter(predicate);
      T headInterval = head.getElement();
      if (predicate.test(headInterval)){
        this.head = tail.head;    
      } else {
        head.setNext(tail.getHead());
      }
    }
  }

  public void map(UnaryOperator<T> unaryOperator) {
    Node<T> head = this.getHead();
    if (head != null) {
      T headElement = head.getElement();
      head.setElement(unaryOperator.apply(headElement));
      this.getTail().map(unaryOperator);
    }    
  }

  public double doubleReduce(ToDoubleFunction<T> toDoubleFunction) {
    Node<T> head = this.getHead();
    if (head == null) {
      return 0.0;
    } else {
      return toDoubleFunction.applyAsDouble(head.getElement()) + this.getTail().doubleReduce(toDoubleFunction);
    } 
  }

  public List<T> getTail() {
    return new List<T>(head.getNext());
  }

  public boolean isEmpty() {
    return this.getHead() == null;
  }

  protected Node<T> getHead() {
    return this.head;
  }

  protected void setHead(Node<T> head) {
    this.head = head;
  }

  public String toString() {
    String result = "[";
    Node<T> head = this.head;
    while (head != null) {
      result += head.getElement().toString() + (head.isLast()? "" : ", ");
      head = head.getNext();
    }
    return result + "]";
  }

}