package org.example;

import lombok.*;

import java.security.spec.InvalidParameterSpecException;
import java.util.regex.Pattern;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Data

public class Reader {


    private String ticketNumber;
    private String fullName;
    private int birthYear;
    private String address;
    private String employment;

    public Reader setTicketNumber(String ticketNumber) throws InvalidParameterSpecException {
        if (Pattern.compile("^(A|Ч|В)\\d{4}-\\d{2}").matcher(ticketNumber).matches()) {
            this.ticketNumber = ticketNumber;
        }
        else{
            throw new InvalidParameterSpecException("Неправильно введён билет");
        }
        return this;
    }
    public Reader setFullName(String fullName) throws InvalidParameterSpecException {

        if( fullName.length() >= CommonConstant.MIN_LENGTH_STRING && fullName.length() <= CommonConstant.MAX_LENGTH_STRING){
            this.fullName = fullName;
        }
        else {
            throw new InvalidParameterSpecException("Длина полного имени должна быть быть больше 15 и меньше 256!");
        }
        return this;
    }

    public Reader setBirthYear(int birthYear) throws InvalidParameterSpecException {
        //min year = 1960 max = 2018
        if (birthYear >= CommonConstant.MIN_YEAR_BIRTHDAY && birthYear <= CommonConstant.MAX_YEAR_BIRTHDAY) {
            this.birthYear = birthYear;
        }
        else {
            throw new InvalidParameterSpecException("Год рождения должен быть от 1960 до 2018 гг.");
        }
        return this;
    }

    public Reader setAddress(String address) throws InvalidParameterSpecException  {
        if( address.length() >= CommonConstant.MIN_LENGTH_STRING && address.length() <= CommonConstant.MAX_LENGTH_STRING){
            this.address = address;
        }
        else {
            throw new InvalidParameterSpecException("Длина адреса должна быть быть больше 15 и меньше 256!");
        }
        return this;

    }

    public Reader setEmployment(String employment) throws InvalidParameterSpecException {
        if( employment.length() >= CommonConstant.MIN_LENGTH_STRING && employment.length() <= CommonConstant.MAX_LENGTH_STRING){
            this.employment = employment;
        }
        else {
            throw new InvalidParameterSpecException("Длина названия рода деятельности должна быть быть больше 15 и меньше 256!");
        }
        return this;
    }
}
