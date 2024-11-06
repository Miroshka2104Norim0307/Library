package org.example;

import lombok.Data;

import java.security.spec.InvalidParameterSpecException;
import java.util.regex.Pattern;

@Data
public class Book {
    private String cipher;
    private String authors;
    private String name;
    private String publishing;
    private int yearPublishing;
    private int amountCopy;
    private int amountAvailableCopy;
    public void setCipher(String cipher) throws InvalidParameterSpecException {

        if (Pattern.compile("^\\d{3}.\\d{3}$").matcher(cipher).matches()){
            this.cipher = cipher;
        }
        else {
            throw new InvalidParameterSpecException("Неверный формат шифра");
        }
    }

    public void setAuthors(String authors) throws InvalidParameterSpecException {
        if( authors.length() >= CommonConstant.MIN_LENGTH_STRING && authors.length() <= CommonConstant.MAX_LENGTH_STRING){
            this.authors = authors;
        }
        else {
            throw new InvalidParameterSpecException("Длина полного имени автора должна быть быть больше 15 и меньше 256!");
        }
    }

    public void setName(String name) throws InvalidParameterSpecException {
        if( name.length() >= CommonConstant.MIN_LENGTH_STRING && name.length() <= CommonConstant.MAX_LENGTH_STRING){
            this.name = name;
        }
        else {
            throw new InvalidParameterSpecException("Длина полного названия книги должна быть быть больше 15 и меньше 256!");
        }

    }

    public void setPublishing(String publishing) throws InvalidParameterSpecException {
        if( publishing.length() >= CommonConstant.MIN_LENGTH_STRING && publishing.length() <= CommonConstant.MAX_LENGTH_STRING){
            this.publishing = publishing;
        }
        else {
            throw new InvalidParameterSpecException("Длина  названия издательства должна быть быть больше 15 и меньше 256!");
        }

    }

    public void setYearPublishing(int yearPublishing) throws InvalidParameterSpecException {
        if (yearPublishing >= CommonConstant.MIN_YEAR_PUBLISHING && yearPublishing <= CommonConstant.MAX_YEAR_PUBLISHING) {
            this.yearPublishing = yearPublishing;
        }
        else {
            throw new InvalidParameterSpecException("Год рождения должен быть от 1960 до 2018 гг.");
        }
    }

    public void setAmountCopy(int amountCopy) throws InvalidParameterSpecException{
        if (amountCopy >= 0 ) {
            this.amountCopy = amountCopy;
        }
        else {
            throw new InvalidParameterSpecException("Количество копий не должно быть отрицательным числом!");
        }

    }

    public void setAmountAvailableCopy(int amountAvailableCopy) throws InvalidParameterSpecException{
        if (amountAvailableCopy >= 0 ) {
            this.amountAvailableCopy = amountAvailableCopy;
        }
        else {
            throw new InvalidParameterSpecException("Количество доступных копий не должно быть отрицательным числом!");
        }

    }

}
