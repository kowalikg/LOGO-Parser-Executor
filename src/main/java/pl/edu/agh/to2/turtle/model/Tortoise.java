package pl.edu.agh.to2.turtle.model;

import java.util.ArrayList;

public class Tortoise {

    private Position position;
    private boolean isFlying;
    private double angle;
    private ArrayList<TortoiseObserver> drawers = new ArrayList<>();
    private int minCoordinateX, minCoordinateY, maxCoordinateX, maxCoordinateY;

    public void addObserver(TortoiseObserver observerDrawer) {
        this.drawers.add(observerDrawer);
    }

    public void setCoordinates(int minCoordinateX, int minCoordinateY, int maxCoordinateX, int maxCoordinateY) {
        this.minCoordinateX = minCoordinateX;
        this.minCoordinateY = minCoordinateY;
        this.maxCoordinateX = maxCoordinateX;
        this.maxCoordinateY = maxCoordinateY;
    }

    public double rotate(double change) {
        angle = (360 + angle + change) % 360;
        notifyObservers();
        return angle;
    }

    public void resetTortoise() {
        isFlying = false;
        position = new Position((maxCoordinateX - minCoordinateX) / 2, (maxCoordinateY - minCoordinateY) / 2);
        angle = 0.0;
    }

    public Position getPosition() {
        return position;
    }

    public void goForward(int steps) {
        go(steps, true);
    }

    public void goBackward(int steps) {
        go(steps, false);
    }

    public void liftTortoise() {
        isFlying = true;
    }

    public void lowerTortoise() {
        isFlying = false;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void go(int steps, boolean forward) {

        int angleChange = forward ? 0 : 180;
        double newAngle = (angle - angleChange);
        Position newPosition = new Position(
                steps * Math.cos(Math.toRadians(newAngle)) + position.getX(),
                steps * Math.sin(Math.toRadians(newAngle)) + position.getY()
        );

        if (newPosition.getX() < minCoordinateX) newPosition.setX(minCoordinateX);
        if (newPosition.getX() > maxCoordinateX) newPosition.setX(maxCoordinateX);
        if (newPosition.getY() < minCoordinateY) newPosition.setY(minCoordinateY);
        if (newPosition.getY() > maxCoordinateY) newPosition.setY(maxCoordinateY);

        position = newPosition;

        notifyObservers();
    }


    public double getAngle() {
        return angle;
    }

    private void notifyObservers() {
        for (TortoiseObserver drawer : drawers) {
            drawer.update();
        }
    }
}
