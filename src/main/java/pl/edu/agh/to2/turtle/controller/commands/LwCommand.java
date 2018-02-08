package pl.edu.agh.to2.turtle.controller.commands;

import org.apache.logging.log4j.LogManager;
import pl.edu.agh.to2.turtle.model.Tortoise;

public class LwCommand implements Command {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PwCommand.class.getName());

    private int angle;
    private Tortoise tortoise;

    public LwCommand(int angle, Tortoise tortoise) {
        this.angle = angle;
        this.tortoise = tortoise;
    }

    public String toString() {
        return "lw " + this.angle;
    }

    @Override
    public void execute() {
        double newAngle = tortoise.rotate(-angle);
        logger.info("Angle: " + newAngle);
    }


}
