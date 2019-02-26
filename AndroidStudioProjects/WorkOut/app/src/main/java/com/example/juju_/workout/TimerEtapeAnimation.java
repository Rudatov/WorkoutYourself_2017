package com.example.juju_.workout;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by juju_ on 23/08/2016.
 */
public class TimerEtapeAnimation extends Animation {

    private TimerView circle;


    private float oldAngle;
    private float newAngle;


    public TimerEtapeAnimation(TimerView circle, int newAngle) {
        this.oldAngle = circle.getAngle();
        this.newAngle = newAngle;
        this.circle = circle;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();
    }

}