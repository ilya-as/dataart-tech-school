package com.company;

public class Main {
    public static void main(String[] args) {
        demoEqualsAndHashCode();
        demoEncapsulation();
        demoPublicAndPrivate();
    }

    private static void demoPublicAndPrivate() {
        System.out.println("Переключим канал телевизора");
        TVSet tvSet = new TVSet(11, 1);
        tvSet.setChannel(-5);
        tvSet.setChannel(2);

        System.out.println("Послушаем музыку");
        MusicPlayer musicPlayer = new MusicPlayer(32, 10);
        musicPlayer.work();
    }

    private static void demoEncapsulation() {
        HomeDevices device1 = new TVSet(11, 1);
        System.out.println(device1.toString());
        System.out.println("Включаем телевизор");
        device1.work();
        System.out.println(device1.toString());

        HomeDevices device2 = new MusicPlayer(222, 11);
        HomeDevices device3 = new WashingMachine(333);

        HomeDevices[] devicesArray = new HomeDevices[3];
        devicesArray[0] = device1;
        devicesArray[1] = device2;
        devicesArray[2] = device3;
        System.out.println("Демонстрируем полиморфизм");
        for (int deviceNumber = 0; deviceNumber < devicesArray.length; deviceNumber++) {
            System.out.println(devicesArray[deviceNumber].toString());
        }
    }

    private static void demoEqualsAndHashCode() {
        TVSet tvSet1 = new TVSet(123, 1);
        TVSet tvSet2 = new TVSet(123, 1);
        TVSet tvSet3 = new TVSet(42, 2);
        System.out.println("Сравнение с помощью ==");
        System.out.println(tvSet1 == tvSet2);
        System.out.println("Сравнение с помощью equals");
        System.out.println(tvSet1.equals(tvSet2));
        System.out.println("Хеш коды");
        System.out.println(tvSet1.hashCode() + " " + tvSet2.hashCode() +
                " " + tvSet3.hashCode());
    }
}
