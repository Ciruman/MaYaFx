package com.ciruman.calculations;

import com.ciruman.model.MindMapNodeDirection;
import javafx.geometry.Point2D;

import java.util.logging.Logger;

public class Angle {

    public static final int RADIUS = 200;
    public static final int ANGLE_OFFSET = 90;
    public static final int X_OFFSET = 40;
    Logger logger = Logger.getLogger(Angle.class.getName());

    public Point2D calculatePosition(int indexOfNode, int numberOfNodes, MindMapNodeDirection mindMapNodeDirection){
        logger.info("Calculating position for index: "+indexOfNode+", number of nodes: "+numberOfNodes+", direction: "+mindMapNodeDirection);
        double angle = calculateAngle((indexOfNode+1.0)/(numberOfNodes+1.0), mindMapNodeDirection);
        return getPosition(new Point2D(fixValueWithDirection(X_OFFSET, mindMapNodeDirection),0), RADIUS, angle);
    }

    private double calculateAngle(double position, MindMapNodeDirection mindMapNodeDirection){
            return (180.0 * position) - fixValueWithDirection(ANGLE_OFFSET, mindMapNodeDirection);
    }

    private int fixValueWithDirection(int value, MindMapNodeDirection mindMapNodeDirection){
        return MindMapNodeDirection.RIGHT==mindMapNodeDirection?value:-value;
    }

    private Point2D getPosition(Point2D center, double radius, double angle) {
        double x = center.getX() + radius * Math.cos(Math.toRadians(angle));
        double y = center.getY() + radius * Math.sin(Math.toRadians(angle));
        Point2D p = new Point2D(x, y);
        return p;
    }
}
