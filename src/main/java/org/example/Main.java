package org.example;

import com.github.curiousoddman.rgxgen.RgxGen;
import lombok.SneakyThrows;

import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.example.Commands.UNSUPPORTED;

public class Main {
    private final static   RgxGen NUMBER_TICKET_GEN = RgxGen.parse("^(A|Ч|В)\\d{4}-\\d{2}");
    private final static RgxGen CIPHER_BOOK_GEN = RgxGen.parse("^\\d{3}.\\d{3}$");
    private final static HashTable <String, Reader> READER_LIST = new HashTable<>();
    private final static AVLTree BOOK_LIST = new AVLTree();
    private final static DoubleLinearList OPERATION_LIST = new DoubleLinearList();
    public static void main(String[] args) throws InvalidParameterSpecException {
        createTestData();
        System.out.println(LocalDate.now());
        Scanner scan = new Scanner(System.in);
        System.out.println("Доступные команды: ");
        for (Commands c : Commands.values()){
            if(c != UNSUPPORTED)
                System.out.printf(c.getCommand() + ", ");
        }

        while (true) {
            System.out.print("\nВведите команду: ");
            String command = scan.nextLine();
            switch (Commands.parse(command)) {
                case REGISTER_READER -> registerReader();
                case PRINT_READERS -> printAllReaders();
                case PRINT_ALL_BOOK -> printAllBook();
                case RETURN_BOOK -> returnBook();
                case CHECK_OUT_BOOK -> checkOutBook();
                case PRINT_ALL_OPERATION -> printAllOperations();
                case DELETE_READER -> deleteReader();
                case DELETE_ALL_READERS -> clearAllReaders();
                case FIND_READER -> findReaderWithBooks();
                case FIND_READERS_BY_FULLNAME -> findRedersByFullname();
                case ADD_BOOK -> addBook();
                case DELETE_BOOK ->  deleteBook();
                case DELETE_ALL_BOOKS -> deleteAllBook();
                case FIND_BOOK_BY_CIPHER -> findBookByCipher();
                case FIND_BOOK -> findBook();
                case EXIT -> System.exit(0);
                default -> System.out.println("Команда не поддерживается ");
            }
        }
    }
    private static void findBook(){
        System.out.println("Введите 1 для поиска по ФИО автора, введите 2 для поиска по названию книги");
        Scanner scan = new Scanner(System.in);
        int input = scan.nextInt();
        scan.nextLine();
        List<Book> booksList;
        if(input == 1){
            System.out.println("Введите ФИО автора книги");
            String fullName = scan.nextLine();
           booksList = BOOK_LIST.findAllByAuthor(fullName);
        } else if (input == 2) {
            System.out.println("Введите название книги");
            String name = scan.nextLine();
           booksList = BOOK_LIST.findAllByName(name);
        } else {
            System.out.println("Ошибка, попробуйте снова");
            return;
        }
        System.out.println("Список найденных книг");
        for (Book book : booksList){
            System.out.println(String.format("Шифр книги:%s, Автор:%s, Название:%s, Издательство:%s, Год издания:%s, Количество доступных книг:%d", book.getCipher(),book.getAuthors(), book.getName(), book.getPublishing(), book.getYearPublishing(), book.getAmountAvailableCopy()));
        }

    }

    private static void findBookByCipher() {
        System.out.println("Введите шифр книги");
        Scanner scan = new Scanner(System.in);
        String cipher = scan.nextLine();
        Book foundedBook = BOOK_LIST.findByCipher(cipher);
        if (foundedBook == null){
            System.out.println("Книга не найдена");
            return;
        }
        System.out.println("Данные о найденной книге: " + foundedBook.toString());
        List<Operation> operations = OPERATION_LIST.findAllByCipherBook(cipher);
        List<String> readersTicketsList = new ArrayList<>();
        for (Operation op : operations) {
            if (op.getDateReturnBook() == null){
                readersTicketsList.add(op.getNumbOfReadTicket());
            }
        }
        for (String numTickRead : readersTicketsList){
            Reader reader = READER_LIST.get(numTickRead);
            if (reader == null){
                System.out.println("Пользователь не найден! ");
            }
            else {
                String fullName = reader.getFullName();
                System.out.println("Номер читательского билета: " + numTickRead + "\nФИО: " + fullName);
            }

        }
    }

    private static void deleteAllBook() {
        BOOK_LIST.clear();
        System.out.println("Все книги удалены! ");
    }

    private static void deleteBook() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите шифр книги: ");
        String cipher = scan.nextLine();
        BOOK_LIST.deleteBook(cipher);
        System.out.println("Книга удалена ");
    }

    @SneakyThrows
    private static void addBook() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите название книги: ");
        String name = scan.nextLine();
        System.out.println("Введите автора книги: ");
        String authors = scan.nextLine();
        System.out.println("Название издательства: ");
        String publishing = scan.nextLine();
        System.out.println("Введите год издания: ");
        int yearPublishing = scan.nextInt();
        scan.nextLine();
        System.out.println("Введите количество копий: ");
        int amountCopy = scan.nextInt();
        scan.nextLine();
        System.out.println("Введите количество доступных для выдачи копий: ");
        int amountAvailableCopy = scan.nextInt();
        scan.nextLine();
        Book book = new Book();
        book.setCipher(CIPHER_BOOK_GEN.generate());
        book.setName(name);
        book.setAuthors(authors);
        book.setPublishing(publishing);
        book.setYearPublishing(yearPublishing);
        book.setAmountCopy(amountCopy);
        book.setAmountAvailableCopy(amountAvailableCopy);
        BOOK_LIST.insert(book);
        System.out.println("Книга создана ");
    }

    private static void findRedersByFullname() {
        System.out.println("Введите фамилию: ");
        Scanner scan = new Scanner(System.in);
        String fullName = scan.nextLine();
        for (Reader reader : READER_LIST.getValues()){
            if (fullName.equals(reader.getFullName())){
                System.out.println("Фамилия пользователя: " + fullName + "\n" + "Номер читательского билета пользователя: " + reader.getTicketNumber());
            }
        }
    }

    private static void findReaderWithBooks() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите номер билета");
        String ticketNumber = scan.nextLine();
      Reader reader = READER_LIST.get(ticketNumber);
        if (reader == null) {
            System.out.println("Пользователь с таким билетом не найден. Введите новый. ");
        }
       List<Operation> operations = OPERATION_LIST.findAll(ticketNumber);
        StringBuilder sb = new StringBuilder();

        for (Operation operation : operations){
            if (operation.getDateReturnBook() == null){
                sb.append(operation.getCipherBook());
            }
        }
        if (sb.isEmpty()){
            System.out.println("У читателя нет книг");
        }
        else {
            System.out.println("Книги, которые есть у читателя");
            System.out.println(sb.toString());
        }
    }

    private static void deleteReader() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите номер билета");
        String ticketNumber = scan.nextLine();
        if (READER_LIST.containsKey(ticketNumber)){
            READER_LIST.remove(ticketNumber);
            System.out.println("Пользователь удалён");
        }
        else {
            System.out.println("Пользователь с таким билетом не существует. Введите другой номер билета.");
        }
    }

    @SneakyThrows
    public static void createTestData(){
        Reader reader1 = new Reader();
        Reader reader2 = new Reader();
        reader1.setFullName("jfujdju djuhduiud djdsjd");
        reader1.setTicketNumber(NUMBER_TICKET_GEN.generate());
        reader2.setFullName("kjvjkk kjhkjkj kjfkjskjd");
        reader2.setTicketNumber(NUMBER_TICKET_GEN.generate());
        READER_LIST.put(reader1.getTicketNumber(), reader1);
        READER_LIST.put(reader2.getTicketNumber(), reader2);

        Book book1 = new Book();
        Book book2 = new Book();
        book1.setName("kjfjkfvjfewilij");
        book1.setAuthors("Франц Кафка");
        book1.setPublishing("МИФ");
        book1.setAmountCopy(2);
        book1.setAmountAvailableCopy(1);
        book1.setCipher(CIPHER_BOOK_GEN.generate());
        book2.setName("kjdjkdkdsvkdsvkldc");
        book2.setCipher(CIPHER_BOOK_GEN.generate());
        BOOK_LIST.insert(book1);
        BOOK_LIST.insert(book2);

    }
    @SneakyThrows
    public static void registerReader(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите ФИО: ");
        String fullName = scan.nextLine();

        System.out.println("Введите год рождения: ");
        int birthYear = scan.nextInt();
        scan.nextLine();

        System.out.println("Введите адрес: ");
        String adress = scan.nextLine();

        System.out.println("Введите место работы или учёбы: ");
        String employment = scan.nextLine();
        String s = NUMBER_TICKET_GEN.generate();
        System.out.println("Вам присвоен номер: " + s);

        Reader reader1 = new Reader();
        reader1.setFullName(fullName).setBirthYear(birthYear).setAddress(adress).setEmployment(employment).setTicketNumber(s);
       READER_LIST.put(reader1.getTicketNumber(), reader1);

    }
    public static void printAllReaders(){
        List<Reader> readerList = READER_LIST.getValues();
        System.out.println("Список всех читателей: ");
        for (int i = 0; i < readerList.size(); i++){
            System.out.println(readerList.get(i));
        }
    }
    public static void printAllOperations() {
        System.out.println("Список всех читателей: ");
        OPERATION_LIST.print();
    }
    public static void printAllBook(){
        System.out.println("Список всех книг: ");
        List<Book> bookList = BOOK_LIST.getAllBooks();
        for (int i = 0; i < bookList.size(); i++){
            System.out.println(bookList.get(i));
        }
    }

    public static void clearAllReaders(){
        READER_LIST.clear();
        System.out.println("Все читатели удалены");
    }
    @SneakyThrows
    public static void returnBook(){
        System.out.println("Введите название книги: ");
        Scanner sc = new Scanner(System.in);
        String nameBook = sc.nextLine();
        System.out.println("Введите номер читательского билета: ");
        String readerTicketNumber = sc.nextLine();
        Book book = BOOK_LIST.findByName(nameBook);
        if (book == null){
            System.out.println("Книга не найдена!");
            return;
        }
        Reader reader = READER_LIST.get(readerTicketNumber);
        if (reader == null){
            System.out.println("Читатель не найден!");
            return;
        }
        Operation operation1 =  OPERATION_LIST.find(book.getCipher(), readerTicketNumber);
        if (operation1 == null){
            System.out.println("Данная книга не была выдана данному читателю!");
            return;
        }
        operation1.setDateReturnBook(LocalDate.now().toString());
        System.out.println("Возврат книги зарегистрирована");
    }
    @SneakyThrows
    public static void checkOutBook(){
        System.out.println("Введите название книги: ");
        Scanner sc = new Scanner(System.in);
        String nameBook = sc.nextLine();
        System.out.println("Введите номер читательского билета: ");
        String readerTicketNumber = sc.nextLine();
        Book book = BOOK_LIST.findByName(nameBook);
        if (book == null){
            System.out.println("Книга не найдена!");
            return;
        }
        Reader reader = READER_LIST.get(readerTicketNumber);
        if (reader == null){
            System.out.println("Читатель не найден!");
            return;
        }
        Operation operation1 = new Operation();
        operation1.setNumbOfReadTicket(reader.getTicketNumber());
        operation1.setCipherBook(book.getCipher());
        operation1.setDateIssuingBook(LocalDate.now().toString());
        OPERATION_LIST.add(operation1);
        System.out.println("Выдача книги зарегистрирована");

    }
//    private String ticketNumber;
//    private String fullName;
//    private int birthYear;
//    private String address;
//    private String employment;

}