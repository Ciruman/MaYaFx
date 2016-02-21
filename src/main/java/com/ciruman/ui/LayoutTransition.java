package com.ciruman.ui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.concurrent.Callable;

public class LayoutTransition extends Transition {

    private DoubleProperty finalXProperty = new SimpleDoubleProperty();
    private DoubleProperty finalYProperty = new SimpleDoubleProperty();
    private DoubleProperty animatedXProperty = new SimpleDoubleProperty();
    private DoubleProperty animatedYProperty = new SimpleDoubleProperty();
    private DoubleProperty layoutXProperty;
    private DoubleProperty layoutYProperty;

    public LayoutTransition(final ObjectBinding<Point2D> offset, DoubleProperty layoutXProperty, DoubleProperty layoutYProperty) {
        this.layoutXProperty = layoutXProperty;
        this.layoutYProperty = layoutYProperty;
        setCycleDuration(Duration.millis(200));
        setInterpolator(Interpolator.EASE_BOTH);

        layoutXProperty.bind(animatedXPropertyProperty());
        layoutYProperty.bind(animatedYPropertyProperty());

        finalXProperty.isNotEqualTo(animatedXProperty).or((finalYProperty.isNotEqualTo(animatedYProperty))).and(statusProperty().isNotEqualTo(Status.RUNNING)).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue)playFromStart();
            }
        });

        finalXProperty.bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return offset.get().getX();
            }
        }, offset));

        finalYProperty.bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return offset.get().getY();
            }
        }, offset));
    }

    public DoubleProperty animatedXPropertyProperty() {
        return animatedXProperty;
    }

    public DoubleProperty animatedYPropertyProperty() {
        return animatedYProperty;
    }

    @Override
    protected void interpolate(double currentPosition) {
        double distanceX = finalXProperty.get()-layoutXProperty.get();
        animatedXProperty.set(layoutXProperty.get() + (currentPosition * distanceX));
        double distanceY = finalYProperty.get()-layoutYProperty.get();
        animatedYProperty.set(layoutYProperty.get()+(currentPosition * distanceY));
    }
}
