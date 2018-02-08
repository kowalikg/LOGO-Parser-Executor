package pl.edu.agh.to2.turtle.controller;

import pl.edu.agh.to2.turtle.controller.commands.Command;

import java.util.LinkedList;
import java.util.List;

public class CommandHistory {

    private List<List<Command>> executedCommands;
    private List<List<Command>> undoneCommands;

    public CommandHistory() {
        executedCommands = new LinkedList<>();
        undoneCommands = new LinkedList<>();

    }

    public void addExecutedCommands(List<Command> commands) {
        this.executedCommands.add(commands);
    }


    public List<Command> undo() {
        return transferCommands(executedCommands, undoneCommands);

    }

    public List<Command> redo() {
        return transferCommands(undoneCommands, executedCommands);
    }

    public void rerun() {
        executedCommands.forEach(list -> list.forEach(Command::execute));
    }

    private List<Command> transferCommands(List<List<Command>> from, List<List<Command>> to) {
        if (!from.isEmpty()) {
            List<Command> c = from.get(from.size() - 1);
            to.add(c);
            from.remove(from.size() - 1);
            return c;
        } else {
            return null;
        }
    }
}
