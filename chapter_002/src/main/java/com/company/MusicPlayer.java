package com.company;

public class MusicPlayer extends HomeDevices {
    int memorySize;

    public MusicPlayer(long serialnumber, int memorySize) {
        super(serialnumber);
        this.memorySize = memorySize;
    }

    @Override
    public void work() {
        System.out.println("I'm playing music");
    }

    @Override
    public String toString() {
        return "I'm a music player,with memory size: " + memorySize;
    }
}
