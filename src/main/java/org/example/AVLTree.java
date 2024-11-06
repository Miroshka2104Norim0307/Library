package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class AVLTree {
    private AVLNode root;
    class AVLNode {
      Book book;
        AVLNode left, right;
        int height;
        private AVLNode(Book book) {
            this.book = book;
            this.height = 1;
        }
    }

    public AVLTree() {
        this.root = null;
    }
    // Метод для вставки нового клиента в дерево
    public void insert(Book book) {
        root = insert(root, book);
    }
    private AVLNode insert(AVLNode node, Book book)
    {
        if (Objects.isNull(node)) {
            return new AVLNode(book);
        }
        if(book.getCipher().compareTo(node.book.getCipher()) < 0){
            node.left = insert(node.left, book);
        } else if
        (book.getCipher().compareTo(node.book.getCipher()) > 0) {
            node.right = insert(node.right, book);
        } else {
            // Ключи должны быть уникальными, дубликаты не добавляем
            return node;
        }
        // Обновление высоты предка текущего узла
        node.height = 1 + Math.max(height(node.left),
                height(node.right));
        // Балансировка дерева
        return balance(node, book.getCipher());
    }
    private AVLNode balance(AVLNode node, String key) {
        int balance = getBalance(node);
        // Левый Левый Случай
        if (balance > 1 &&
                key.compareTo(node.left.book.getCipher()) < 0)
        {
            return rightRotate(node);
        }
        // Правый Правый Случай
        if (balance < -1 &&
                key.compareTo(node.right.book.getCipher()) >
                        0) {
            return leftRotate(node);
        }
        // Левый Правый Случай
        if (balance > 1 &&
                key.compareTo(node.left.book.getCipher()) > 0)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Правый Левый Случай
        // Правый Левый Случай
        if (balance < -1 &&
                key.compareTo(node.right.book.getCipher()) <
                        0) {
            node.right = rightRotate(node.right); // Левый поворот для правого дочернего узла
            return leftRotate(node); // Правый поворот для node
        }
        return node; // Возвращаем узел без изменений, если балансировка не требуется
    }
    private int getBalance(AVLNode N) {
        if (N == null) {
            return 0;
        }
        return height(N.left) - height(N.right);
    }
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        // Выполнение поворота
        x.right = y;
        y.left = T2;
        // Обновление высот
        y.height = Math.max(height(y.left), height(y.right))
                + 1;
        x.height = Math.max(height(x.left), height(x.right))
                + 1;
        // Возвращаем новый корень
        return x;
    }
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        // Выполнение поворота
        y.left = x;
        x.right = T2;
        // Обновление высот
        x.height = Math.max(height(x.left), height(x.right))
                + 1;
        y.height = Math.max(height(y.left), height(y.right))
                + 1;
        // Возвращаем новый корень
        return y;
    }
    private int height(AVLNode N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }
    // Метод для получения всех клиентов в иде списка
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        getAllBooks(root, books);
        return books;
    }
    private void getAllBooks(AVLNode node, List<Book>
            books) {
        if (Objects.nonNull(node)) {
            getAllBooks(node.left, books);
            books.add(node.book);
            getAllBooks(node.right, books);
        }
    }
    // Метод для удаления клиента из дерево
    public void deleteBook(String cipher) {
        root = deleteBook(root, cipher);
    }
    private AVLNode deleteBook(AVLNode node, String
            cipher) {
        if (Objects.isNull(node)) {
            return null;
        }
        int compareResult =
                cipher.compareTo(node.book.getCipher());
        // если клиент не был найден - номер паспорта меньше, чем текущий
        if (compareResult < 0) {
            // поиск идет по левому элементу
            node.left = deleteBook(node.left,
                    cipher);
            // если клиент не был найден - номер паспорта больше, чем текущий
        } else if (compareResult > 0) {
            node.right = deleteBook(node.right,
                    cipher);
        } else if (Objects.nonNull(node.left) &&
                Objects.nonNull(node.right)) {
            // если у найденной ноды есть соседи
            node.book =
                    Objects.requireNonNull(findMin(node.right)).book;
            node.right = deleteBook(node.right,
                    node.book.getCipher());
        } else {
            // если соседей нет
            node = (Objects.nonNull(node.left)) ? node.left :
                    node.right;
        }
        return balanceAfterDeletion(node);
    }
    private AVLNode findMin(AVLNode node) {
        if (Objects.isNull(node)) {
            return null;
        } else if (Objects.isNull(node.left)) {
            return node;
        }
        return findMin(node.left);
    }
    // балансировка после удаления
    private AVLNode balanceAfterDeletion(AVLNode node) {
        if (Objects.isNull(node)) {
            return null;
        }
        node.height = Math.max(height(node.left),
                height(node.right)) + 1;
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public Book findByCipher(String cipher) {
        return findByCipher(root, cipher);
    }
    private Book findByCipher(AVLNode node, String cipher) {
        if (Objects.isNull(node)) {
            return null; // Базовый случай: узел пуст, клиент не найден
        }
        int compareResult =
                cipher.compareTo(node.book.getCipher());
        if (compareResult < 0) {
            return findByCipher(node.left,
                    cipher); // Идем налево
        } else if (compareResult > 0) {
            return findByCipher(node.right,
                    cipher); // Идем направо
        } else {
            return node.book; // Номер паспорта найден
        }
    }
    //  Поиск 1 клиента по ФИО
    public Book findByName(String name) {
        return findByName(root, name);
    }
    private Book findByName(AVLNode node, String name)
    {
        if (Objects.isNull(node)) {
            return null; // Базовый случай: узел пуст, клиент не найден
        }
        if (node.book.getName().equalsIgnoreCase(name)) {
            return node.book; // ФИО найдено
        }
        // Поскольку AVL-дерево не организовано по ФИО, необходимо искать в обоих направлениях
        Book leftSearchResult = findByName(node.left,
                name);
        if (Objects.nonNull(leftSearchResult)) {
            return leftSearchResult;
        }
        return findByName(node.right, name);
    }
    //  Поиск всех клиентов с совпадающим фрагментом ФИО
    public List<Book> findAllByName(String name) {
        return findAllByName(root, new ArrayList<Book>(), name);
    }
    private List<Book> findAllByName(AVLNode node, List<Book> books, String name) {
        if (Objects.nonNull(node)) {
            findAllByName(node.left, books, name);
            if(node.book.getName().toLowerCase().contains(name.toLowerCase()))
                books.add(node.book);
            findAllByName(node.right, books, name);
        }
        return books;
    }
    public List<Book> findAllByAuthor(String namePart){
        return findAllByAuthor(root, new ArrayList<>(), namePart);
    }
    private List<Book> findAllByAuthor(AVLNode node, List<Book> books, String namePart){
        if (Objects.nonNull(node)) {
            findAllByName(node.left, books, namePart);
            if(node.book.getAuthors().toLowerCase().contains(namePart.toLowerCase()))
                books.add(node.book);
            findAllByName(node.right, books, namePart);
        }
        return books;
    }

    public void clear(){
        root = null;
    }


}