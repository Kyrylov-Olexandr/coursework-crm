package com.crm.exceptions;

public class WrongEmailOrPassException extends Exception{
    public WrongEmailOrPassException(String message) {
        super(message);
    }
}
