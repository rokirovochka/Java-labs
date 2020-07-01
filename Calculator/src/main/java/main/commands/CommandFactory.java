package main.commands;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CommandFactory {

    public CommandFactory(String name) {
        InputStream inputStream = null;
        Scanner scanner = null;
        try {
            inputStream = CommandFactory.class.getResourceAsStream(name);
            scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String[] commandsDescription = (scanner.nextLine()).split(":");
                commandsNames.put(commandsDescription[0], commandsDescription[1]);
            }

        } catch (RuntimeException ex) {
            System.err.println("Error reading config: " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            System.err.println("Error parsing config: " + ex.getLocalizedMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }
            }

            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public Command getCommand(String name, String args) {
        try {
            return (Command) Class.forName(this.getClass().getPackageName() + "." + commandsNames.get(name)).getDeclaredConstructor(String.class).newInstance(args.isEmpty() ? null : args);
        } catch (Exception ex) {
            System.out.print(ex.getCause());
        }
        return null;
    }

    private Map<String, String> commandsNames = new TreeMap<>();
}
