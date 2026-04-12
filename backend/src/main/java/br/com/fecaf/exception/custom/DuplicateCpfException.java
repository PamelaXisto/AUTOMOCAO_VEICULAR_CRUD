package br.com.fecaf.exception.custom;

public class DuplicateCpfException extends RuntimeException {

    public DuplicateCpfException() {
        super("Cpf is already registered.");
    }

    public DuplicateCpfException(String message) {
        super(message);
    }
}
