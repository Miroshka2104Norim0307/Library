package org.example;

import lombok.Data;

import java.security.spec.InvalidParameterSpecException;
import java.util.regex.Pattern;

@Data
public class Operation {
    private String numbOfReadTicket;
    private String cipherBook;
    private String dateIssuingBook;
    private String dateReturnBook;
    public void setNumbOfReadTicket(String numbOfReadTicket) throws InvalidParameterSpecException {
        if(Pattern.compile("^(A|Ч|В)\\d{4}-\\d{2}").matcher(numbOfReadTicket).matches()){
            this.numbOfReadTicket = numbOfReadTicket;
            return;
        }
        throw new InvalidParameterSpecException("Неправильно введён номер билета");
    }

    public void setCipherBook(String cipherBook)  throws InvalidParameterSpecException {
        if (Pattern.compile("^\\d{3}.\\d{3}$").matcher(cipherBook).matches()){
            this.cipherBook = cipherBook;
        }
        else {
            throw new InvalidParameterSpecException("Неверный формат шифра");
        }
    }

    public void setDateIssuingBook(String dateIssuingBook) throws InvalidParameterSpecException {
        if (Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(dateIssuingBook).matches()){
            this.dateIssuingBook = dateIssuingBook;
        }
        else {
            throw new InvalidParameterSpecException("Неверный формат даты");
        }
    }

    public void setDateReturnBook(String dateReturnBook) throws InvalidParameterSpecException  {
        if (Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(dateReturnBook).matches()){
            this.dateReturnBook = dateReturnBook;
        }
        else {
            throw new InvalidParameterSpecException("Неверный формат даты");
        }

    }



}
