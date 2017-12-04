package com.example.ajaykumar.drawer.entityObjects;

import java.util.List;

/**
 * Created by AJAY KUMAR on 9/26/2017.
 */
public class LegsObject {

    private List<StepsObject> steps;

    private DistanceObject distance;

    private DurationObject duration;

    public LegsObject(DurationObject duration, DistanceObject distance, List<StepsObject> steps) {
        this.duration = duration;
        this.distance = distance;
        this.steps = steps;
    }

    public List<StepsObject> getSteps() {
        return steps;
    }

    public DistanceObject getDistance() {
        return distance;
    }

    public DurationObject getDuration() {
        return duration;
    }
    public class DistanceObject{
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        String text;
        double value;

    }

    public class DurationObject{
        String text;
        double value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}