package com.ciruman.calculations;

import com.ciruman.model.MindMapNodeDirection;
import javafx.geometry.Point2D;

import java.util.logging.Logger;

public class Angle {

    public static final double RADIUS = 200;
    public static final double ANGLE_OFFSET = 90;
    public static final double ANGLE_OFFSET_RIGHT = ANGLE_OFFSET;
    public static final double ANGLE_OFFSET_LEFT = -ANGLE_OFFSET;
    public static final double UNSIGNED_X_OFFSET = 0;
    public static final double UNSIGNED_X_OFFSET_RIGHT = UNSIGNED_X_OFFSET;
    public static final double UNSIGNED_X_OFFSET_LEFT = -UNSIGNED_X_OFFSET;
    Logger logger = Logger.getLogger(Angle.class.getName());

    public Point2D calculatePosition(int indexOfNode, int numberOfNodes, MindMapNodeDirection mindMapNodeDirection, int level, double angleFromParent){
        double angleWithDirection = mindMapNodeDirection==MindMapNodeDirection.RIGHT? angleFromParent+90:angleFromParent-90;
//        logger.info("Calculating position for index: "+indexOfNode+", number of nodes: "+numberOfNodes+", direction: "+mindMapNodeDirection);
        double angle = calculateAngle((indexOfNode+1.0)/(numberOfNodes+1.0), mindMapNodeDirection);
//        System.out.println("Calculated angle: "+angle+"" +
//                " \n Angle correction from parent: "+angleWithDirection+
//                " \n Angle from parent: "+angleFromParent);
        Point2D position = getPosition(new Point2D(fixValueWithDirection(mindMapNodeDirection, UNSIGNED_X_OFFSET_RIGHT, UNSIGNED_X_OFFSET_LEFT), 0), (RADIUS * (10.0 - (level)) / 10.0), angle+angleWithDirection);
//        logger.info("Calculated: "+position);
        return position;
    }

    private double calculateAngle(double position, MindMapNodeDirection mindMapNodeDirection){
            return (180.0 * position) - fixValueWithDirection(mindMapNodeDirection, ANGLE_OFFSET_RIGHT, ANGLE_OFFSET_LEFT);
    }

    private double fixValueWithDirection(MindMapNodeDirection mindMapNodeDirection, double rightValue, double leftValue){
        return MindMapNodeDirection.RIGHT==mindMapNodeDirection?rightValue:leftValue;
    }

    private Point2D getPosition(Point2D center, double radius, double angle) {
        double x = center.getX() + radius * Math.cos(Math.toRadians(angle));
        double y = center.getY() + radius * Math.sin(Math.toRadians(angle));
        Point2D p = new Point2D(x, y);
        return p;
    }

    public double calculateAngle(Point2D target){
            logger.info("Calculating angle for target: " +target);
            float angle = (float) Math.toDegrees(Math.atan2(target.getY(), target.getX()));

            if(angle < 0){
                angle += 360;
            }

            logger.info("Calculated angle: " +angle);

            return angle;
    }
}
