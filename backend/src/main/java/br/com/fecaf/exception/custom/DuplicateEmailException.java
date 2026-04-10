package br.com.fecaf.exception.custom;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(){
        super("Email is already registered.");
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
