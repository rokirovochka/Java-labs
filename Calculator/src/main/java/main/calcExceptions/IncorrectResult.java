package main.calcExceptions;

public class IncorrectResult extends ArithmeticException {
    public IncorrectResult(String message) {
        super(message);
    }

}