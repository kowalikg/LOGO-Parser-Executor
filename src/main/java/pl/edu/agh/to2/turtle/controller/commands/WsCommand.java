package pl.edu.agh.to2.turtle.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.turtle.model.Tortoise;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(WsCommand.class.getName());
    private static final NumberFormat formatter = new DecimalFormat("#0.0");

    private int steps;
    private Tortoise tortoise;

    public WsCommand(int steps, Tortoise tortoise) {
        this.steps = steps;
        this.tortoise = tortoise;
    }

    public String toString() {
        return "ws " + this.steps;
    }

    @Override
    public void execute() {
        tortoise.goBackward(steps);
        logger.info("X: " + formatter.format(tortoise.getPosition().getX()) + " Y: " + formatter.format(tortoise.getPosition().getY()));
    }
}
