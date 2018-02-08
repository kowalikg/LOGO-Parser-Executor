package pl.edu.agh.to2.turtle.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.agh.to2.turtle.controller.commands.Command;
import pl.edu.agh.to2.turtle.controller.commands.NpCommand;
import pl.edu.agh.to2.turtle.model.Tortoise;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NpTest {

    private Tortoise tortoise;
    private static final int minX = 12;
    private static final int minY = 12;
    private static final int maxX = 888;
    private static final int maxY = 388;
    private static final int startX = (maxX - minX) / 2;
    private int expectedX;
    private int stepsAlongX;

    public NpTest(int expectedX, int stepsAlongX) {
        this.expectedX = expectedX;
        this.stepsAlongX = stepsAlongX;
    }

    @Before
    public void initialize() {
        tortoise = new Tortoise();
        tortoise.addObserver(() -> {
        });
        tortoise.setCoordinates(minX, minY, maxX, maxY);
    }

    @Parameterized.Parameters
    public static Collection getCoordinates() {
        List<Object[]> coordinates = new LinkedList<>();
        for (int i = startX; i <= maxX; i++) {
            coordinates.add(new Object[]{i, i - startX});
        }
        return coordinates;
    }

    @Test
    public void simpleNp() {
        tortoise.resetTortoise();
        Command command = new NpCommand(stepsAlongX, tortoise);
        command.execute();
        assertEquals(expectedX, tortoise.getPosition().getX(),1.0);
    }


}
