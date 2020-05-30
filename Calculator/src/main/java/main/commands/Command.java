package main.commands;

import main.DataStorage;

public interface Command {
    void execute(DataStorage dataStorage);
}
