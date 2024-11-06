package org.example;

public class Node {
    private Operation data;
    private Node next;
    private Node prev;
    public Node(Operation data) {
        this.data = data;

    }


    public Operation getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }
    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
    public void setData(Operation data) {
        this.data = data;
    }




}
