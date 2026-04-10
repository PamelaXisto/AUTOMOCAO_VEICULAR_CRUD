package br.com.fecaf.exception.custom;

public class DuplicateCpfException extends RuntimeException {
    public DuplicateCpfException(String message) {
        super(message);
    }
}
