package org.example;

import java.util.ArrayList;
import java.util.List;

public class DoubleLinearList {
    private Node head;


    public void add(Operation operation){
        Node node = new Node(operation);
        if(head == null){
            head = node;
            return;
        }
        Node last = head.getNext();
        if(last == null){
            head.setNext(node);
            node.setPrev(head);
        }
        else {
            while(last.getNext() != null){
                last = last.getNext();
            }
            last.setNext(node);
            node.setPrev(last);
        }
    }
    public  List <Operation> findAllByCipherBook(String cipherBook) {
        if (head == null) throw new NullPointerException("Список пуст!");
        Node current = head;
        List <Operation> operations = new ArrayList();
        do {
            if (current.getData().getCipherBook().equals(cipherBook)) {
                operations.add(current.getData());
            }
            current = current.getNext();
        }
        while (current.getNext() != null);
        return operations;
    }
    public Operation find(String cipherBook) {
        if (head == null) throw new NullPointerException("Список пуст!");
        Node current = head;
        do {
            if (current.getData().getCipherBook().equals(cipherBook)) {
                return current.getData();
            }
            current = current.getNext();
        }
        while (current.getNext() != null);
        throw new IllegalArgumentException("Операция с данным шифром отсутствует");
    }
    public Operation find(String cipherBook, String readerTicketNumber) {
        if (head == null) throw new NullPointerException("Список пуст!");
        Node current = head;
        do {
            if (current.getData().getCipherBook().equals(cipherBook) && current.getData().getNumbOfReadTicket().equals(readerTicketNumber)) {
                return current.getData();
            }
            current = current.getNext();
        }
        while (current != null && current.getNext() != null);
        throw new IllegalArgumentException("Операция с данным шифром отсутствует");
    }
    public List<Operation> findAll(String ticketNumber){
        if (head == null) throw new NullPointerException("Список пуст!");
        Node current = head;
        ArrayList<Operation> operationsList = new ArrayList<>();
        do {
            if (current.getData().getNumbOfReadTicket().equals(ticketNumber)) {
              operationsList.add(current.getData());
            }
            current = current.getNext();
        }
        while (current != null);
       return operationsList;
    }
    public boolean remove(String cipherBook) {
        if (head == null) throw new NullPointerException("Список пуст!");
        Node current = head;
        do {
            if (current.getData().getCipherBook().equals(cipherBook)) {
               current.getPrev().setNext(current.getNext());
               current.getNext().setPrev(current.getPrev());
            }
            current = current.getNext();
        }
        while (current.getNext() != null);
        throw new IllegalArgumentException("Операция с данным шифром отсутствует");
    }

    public void sort (){
        Node current = head;
        while (current != null){
            Node min = current;
            Node next = current.getNext();
            while (next != null){
                if(min.getData().getCipherBook().compareTo(next.getData().getCipherBook()) > 0){
                    min = next;
                }
                next = next.getNext();
            }
            if(min != current){
                Operation temp = current.getData();
                current.setData(min.getData());
                min.setData(temp);
            } //equals на будущее лучше
            current = current.getNext();
        }
    }
    public void print() {
        if (head == null) throw new NullPointerException("Список пуст!");
        Node current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }

    }
}
