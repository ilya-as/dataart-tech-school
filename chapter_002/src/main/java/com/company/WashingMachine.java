package com.company;

public class WashingMachine extends HomeDevices {
    public WashingMachine(long serialnumber) {
        super(serialnumber);
    }

    @Override
    public void work() {
        System.out.println("I'm washing clothes");
    }

    @Override
    public String toString() {
        return "I'm a washing machine";
    }
}
