package pl.edu.agh.to2.turtle.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.turtle.model.Tortoise;

public class PwCommand implements Command {

    private static final Logger logger = LogManager.getLogger(PwCommand.class.getName());

    private int angle;
    private Tortoise tortoise;

    public PwCommand(int angle, Tortoise tortoise) {
        this.angle = angle;
        this.tortoise = tortoise;
    }

    public String toString() {
        return "pw " + this.angle;
    }

    @Override
    public void execute() {
        double newAngle = tortoise.rotate(angle);
        logger.info("Angle: " + newAngle);
    }
}
