package pl.edu.agh.to2.turtle.controller.commands;

import pl.edu.agh.to2.turtle.model.Tortoise;

public class OpsCommand implements Command {
    private Tortoise tortoise;

    public OpsCommand(Tortoise tortoise) {
        this.tortoise = tortoise;
    }

    public String toString() {
        return "ops";
    }

    @Override
    public void execute() {
        tortoise.lowerTortoise();
    }
}
