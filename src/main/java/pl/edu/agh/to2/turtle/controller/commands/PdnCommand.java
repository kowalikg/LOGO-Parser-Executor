package pl.edu.agh.to2.turtle.controller.commands;

import pl.edu.agh.to2.turtle.model.Tortoise;

public class PdnCommand implements Command {
    private Tortoise tortoise;

    public PdnCommand(Tortoise tortoise) {
        this.tortoise = tortoise;
    }

    public String toString() {
        return "pdn";
    }

    @Override
    public void execute() {
        tortoise.liftTortoise();
    }
}
