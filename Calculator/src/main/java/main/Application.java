package main;

public class Application {
    private static final int INPUTFILE_ARGUMENT = 0;

    public static void main(String[] args) {
        Calculator calculator;

        if (args.length == 0)
            calculator = new Calculator();
        else
            calculator = new Calculator(args[INPUTFILE_ARGUMENT]);
        calculator.run();
    }
}