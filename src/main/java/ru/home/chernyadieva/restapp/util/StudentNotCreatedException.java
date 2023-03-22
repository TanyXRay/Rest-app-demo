package ru.home.chernyadieva.restapp.util;

public class StudentNotCreatedException extends RuntimeException{

    public StudentNotCreatedException(String msg) {
        super(msg);
    }
}
