package pl.edu.agh.to2.turtle.controller;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.to2.turtle.controller.commands.Command;
import pl.edu.agh.to2.turtle.model.Position;
import pl.edu.agh.to2.turtle.model.Tortoise;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class ParserTest {

    private Tortoise testTortoise;
    private Parser parser;
    private static final int minX = 12;
    private static final int minY = 12;
    private static final int maxX = 888;
    private static final int maxY = 388;
    private static final int startX = (maxX - minX) / 2;
    private static final int startY = (maxY - minY) / 2;

    @Before
    public void initialize() {
        testTortoise = new Tortoise();
        testTortoise.addObserver(() -> {
        });
        testTortoise.setCoordinates(minX, minY, maxX, maxY);
        parser = new Parser();
    }

    @Test
    public void makeCommandNpCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("np 20", testTortoise);
        testCommands.get(0).execute();
        assertEquals(startX + 20, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }

    @Test
    public void makeCommandNpCommandWithTrailingWhitespace() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("np 11  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(startX + 11, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }

    @Test
    public void makeCommandNpCommandWithWhitespaceOnBothSides() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("   np 150  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(startX + 150, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }

    @Test
    public void makeCommandWsCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("ws 4", testTortoise);
        testCommands.get(0).execute();
        assertEquals(startX - 4, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }

    @Test
    public void makeCommandWsCommandWithTrailingWhitespace() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("ws 87  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(startX - 87, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }

    @Test
    public void makeCommandWsCommandWithWhitespaceOnBothSides() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("   ws 20  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(startX - 20, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }


    @Test
    public void makeCommandOpsCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("ops", testTortoise);
        testCommands.get(0).execute();
        assertEquals(false, testTortoise.isFlying());
    }

    @Test
    public void makeCommandOpsCommandWithTrailingWhitespace() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("ops    ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(false, testTortoise.isFlying());
    }

    @Test
    public void makeCommandOpsCommandWithWhitespaceOnBothSides() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("   ops  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(false, testTortoise.isFlying());
    }

    @Test
    public void makeCommandPdnCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("pdn", testTortoise);
        testCommands.get(0).execute();
        assertEquals(true, testTortoise.isFlying());
    }

    @Test
    public void makeCommandPdnCommandWithTrailingWhitespace() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("pdn    ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(true, testTortoise.isFlying());
    }

    @Test
    public void makeCommandPdnCommandWithWhitespaceOnBothSides() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("   pdn  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(true, testTortoise.isFlying());
    }

    @Test
    public void makeCommandPwCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("pw 45", testTortoise);
        testCommands.get(0).execute();
        assertEquals(45.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeCommandPwCommandWithTrailingWhitespace() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("pw 45  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(45.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeCommandLwCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("lw 22", testTortoise);
        testCommands.get(0).execute();
        assertEquals(360.0 - 22.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeCommandLwCommandWithTrailingWhitespace() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeCommand("lw 65  ", testTortoise);
        testCommands.get(0).execute();
        assertEquals(360.0 - 65.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeGroupCommandSingleLoop() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand("powtorz 4 [np 10 lw 90] ", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
        assertEquals(0.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeGroupCommandLoopMixedWithCommands() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand(" np 10 powtorz 6 [lw 60 np 20] np 20 ", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX + 30, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
        assertEquals(0.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeGroupCommandRectangleProcedure() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand(" oto kwadrat (bok) {powtorz 4 [np bok pw 90]}", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(true, parser.getAndResetIfSetProcedure());
    }

    @Test
    public void makeGroupCommandMixedCommands() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand("np 100      pw 60 np 100  pw 240 np 100 ", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX + 200, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
        assertEquals(360.0 - 60.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeGroupCommandLoopWithOneCommand() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand("powtorz 2 [pw 90] ", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
        assertEquals(180.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeGroupCommandLoopWithProcedure() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand(" oto kwadrat (bok) {powtorz 4 [np bok pw 90]}", testTortoise);
        testCommands.forEach(Command::execute);
        testCommands = parser.makeGroupCommand("powtorz 4 [kwadrat 90]", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
        assertEquals(0.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void makeGroupCommandLoopInsideLoop() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand("powtorz 2 [powtorz 3 [np 100 pw 60]]", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
        assertEquals(0.0, testTortoise.getAngle(), 0.1);
    }

    @Test
    public void permutationsOfCommands() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand("ws 10 ws 55 np 4 np 576 lw 90 pw 180 np 5 ws 102", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(maxX, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY + 5.0 - 102.0, testTortoise.getPosition().getY(), 1.0);
    }

    @Test
    public void permutationsOfCommands2() {
        testTortoise.resetTortoise();
        List<Command> testCommands = parser.makeGroupCommand("lw 10 lw 5 pw 172 np 132 pw 23 np 91 pw 157 np 132 pw 23 np 91", testTortoise);
        testCommands.forEach(Command::execute);
        assertEquals(startX, testTortoise.getPosition().getX(), 1.0);
        assertEquals(startY, testTortoise.getPosition().getY(), 1.0);
    }
}