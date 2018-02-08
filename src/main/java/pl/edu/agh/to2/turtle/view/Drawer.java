package pl.edu.agh.to2.turtle.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import pl.edu.agh.to2.turtle.model.Position;
import pl.edu.agh.to2.turtle.model.Tortoise;
import pl.edu.agh.to2.turtle.model.TortoiseObserver;

import java.util.ArrayList;

public class Drawer implements TortoiseObserver {
    private Tortoise tortoise;
    private Canvas lineCanvas;
    private Canvas tortoiseCanvas;
    private GraphicsContext lineContext;
    private GraphicsContext tortoiseContext;
    private Position lastTortoisePosition;
    private ArrayList<Image> icons;
    private int currentIconIndex;

    private final int lineThickness = 1;

    public Drawer(Tortoise tortoise, Canvas lineCanvas, Canvas tortoiseCanvas) {
        this.tortoise = tortoise;
        this.lineCanvas = lineCanvas;
        this.tortoiseCanvas = tortoiseCanvas;
        lineContext = lineCanvas.getGraphicsContext2D();
        tortoiseContext = tortoiseCanvas.getGraphicsContext2D();
        setIcons();

    }

    private void setIcons() {
        icons = new ArrayList<>();
        icons.add(new Image("/tortoises/tortoise.png"));
        icons.add(new Image("/tortoises/cat.png"));
        icons.add(new Image("/tortoises/frog.png"));
        icons.add(new Image("/tortoises/devil.png"));
        icons.add(new Image("/tortoises/deer.png"));
        icons.add(new Image("/tortoises/angry.png"));
    }

    public void resetField() {
        lineContext.setFill(Color.DARKGREY);
        lineContext.setStroke(Color.BLACK);
        lineContext.setLineWidth(1);

        tortoise.resetTortoise();
        lineContext.fillRoundRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight(), lineThickness, lineThickness);
        lineContext.strokeRoundRect(0, 0, lineCanvas.getWidth(), lineCanvas.getHeight(), lineThickness, lineThickness);
        lineContext.setFill(Color.RED);
        tortoiseContext.clearRect(0, 0, tortoiseCanvas.getWidth(), tortoiseCanvas.getHeight());
        tortoiseContext.drawImage(getCurrentIcon(), tortoise.getPosition().getX() - (getCurrentIcon().getWidth() / 2),
                tortoise.getPosition().getY() - (getCurrentIcon().getHeight() / 2));

        lastTortoisePosition = new Position(tortoise.getPosition().getX(), tortoise.getPosition().getY());

    }

    public void update() {
        if (!tortoise.isFlying()) {
            lineContext.setStroke(Color.WHITE);
            lineContext.strokeLine(
                    lastTortoisePosition.getX(),
                    lastTortoisePosition.getY(),
                    tortoise.getPosition().getX(),
                    tortoise.getPosition().getY());

        }
        changeTortoisePosition();

    }

    public void changeTortoisePosition() {
        drawTortoise();
        lastTortoisePosition.setX(tortoise.getPosition().getX());
        lastTortoisePosition.setY(tortoise.getPosition().getY());
    }

    private void drawTortoise() {
        tortoiseContext.clearRect(0, 0, tortoiseCanvas.getWidth(), tortoiseCanvas.getHeight());
        tortoiseContext.save();
        Rotate rotation = new Rotate(tortoise.getAngle(), tortoise.getPosition().getX(), tortoise.getPosition().getY());
        tortoiseContext.setTransform(rotation.getMxx(), rotation.getMyx(), rotation.getMxy(), rotation.getMyy(),
                rotation.getTx(), rotation.getTy());
        tortoiseContext.drawImage(getCurrentIcon(), tortoise.getPosition().getX() - (getCurrentIcon().getWidth() / 2),
                tortoise.getPosition().getY() - (getCurrentIcon().getWidth() / 2));
        tortoiseContext.restore();
    }

    public int getMinCoordinate() {
        return (int) (getCurrentIcon().getWidth() / 2 + lineThickness);
    }

    public int getMaxYCoordinate() {
        return (int) (lineCanvas.getHeight() - getCurrentIcon().getWidth() / 2 - lineThickness);
    }

    public int getMaxXCoordinate() {
        return (int) (lineCanvas.getWidth() - getCurrentIcon().getWidth() / 2 - lineThickness);
    }


    public Image getCurrentIcon() {
        return icons.get(currentIconIndex);
    }


    public void nextIcon() {
        currentIconIndex = (currentIconIndex + 1) % icons.size();
        changeTortoisePosition();
    }

    public void previousIcon() {
        if (currentIconIndex == 0) currentIconIndex = icons.size() - 1;
        else currentIconIndex = (currentIconIndex - 1) % icons.size();
        changeTortoisePosition();
    }
}
