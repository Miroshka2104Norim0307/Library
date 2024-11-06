package org.example;

public enum Commands {
    REGISTER_READER("Register reader"),
    UNSUPPORTED("Unsupported command"),
    PRINT_READERS("Print readers"),
    EXIT("Exit"),
    PRINT_ALL_BOOK("Print books"),
    PRINT_ALL_OPERATION("Print operations"),
    DELETE_ALL_READERS("Delete readers"),
    CHECK_OUT_BOOK("Check out book"),
    RETURN_BOOK("Return book"),
    FIND_READER("Find reader with books"),
    FIND_READERS_BY_FULLNAME("Find reader by fullname"),
    ADD_BOOK("Add book"),
    DELETE_BOOK("Delete book"),
    DELETE_ALL_BOOKS("Delete all book"),
    FIND_BOOK_BY_CIPHER("Find book by cipher"),
    FIND_BOOK("Find book"),
    DELETE_READER("Delete reader");


    private String command;

    Commands(String command) {
        this.command = command;
    }
    public String getCommand(){
        return command;
    }
    public static Commands parse(String command) {
        for(Commands c : values()) {
            if(c.command.equals(command)){
                return c;
            }
        }
        return UNSUPPORTED;
    }



}
