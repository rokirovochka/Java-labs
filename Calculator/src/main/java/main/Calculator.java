package main;

import main.commands.Command;
import main.commands.CommandFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Calculator {
    private static final String CONFIG_LOCATION = "/config.txt";

    public Calculator(String fileName) {
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            scanner = new Scanner(System.in);
        }
    }

    public Calculator() {
        scanner = new Scanner(System.in);
    }

    void run() {
        CommandFactory factory = new CommandFactory(CONFIG_LOCATION);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.charAt(0) == '#') continue;
            String[] splittedLine = line.split("[ \n]", 2);
            Command command = factory.getCommand(splittedLine[0], splittedLine.length == 1 ? "" : splittedLine[1]);

            if (command == null) {
                System.out.println(": " + splittedLine[0] + " is unknown command");
                continue;
            }
            try {
                command.execute(dataStorage);
            } catch (RuntimeException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    private DataStorage dataStorage = new DataStorage();
    private Scanner scanner;
}