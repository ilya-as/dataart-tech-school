package com.company;

import java.util.Objects;

public class TVSet extends HomeDevices {
    private int channel;

    public TVSet(long serialnumber, int channel) {
        super(serialnumber);
        this.channel = channel;
    }

    public boolean setChannel(int newChannel) {
        if (checkChannel(newChannel)) {
            this.channel = newChannel;
            return true;
        }
        return false;
    }

    private boolean checkChannel(int channel) {
        if (channel > 30) {
            System.out.println("Канал не входит в диапазон приема!");
            return false;
        }

        if (channel < 0) {
            System.out.println("Значение канала не может быть отрицательным!");
            return false;
        }
        return true;
    }

    @Override
    public void work() {
        System.out.println("I'm showing TV program channel: " + channel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVSet tvSet = (TVSet) o;
        return channel == tvSet.channel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel);
    }

    @Override
    public String toString() {
        return "I'm a TVSet. " +
                "current channel" + channel;
    }
}
