package com.docencia.semaforo;
import java.util.concurrent.Semaphore;

public class ColorSemaphore implements Runnable {
    private final Colors color;
    private static volatile boolean isActive = true;
    private static final Semaphore turn = new Semaphore(1, true);

    public ColorSemaphore(Colors color) {
        this.color = color;
    }

    public static void main(String[] args) {
        Thread tRojo = new Thread(new ColorSemaphore(Colors.RED));
        Thread tAmbar = new Thread(new ColorSemaphore(Colors.AMBER));
        Thread tVerde = new Thread(new ColorSemaphore(Colors.GREEN));

        tRojo.start();
        tAmbar.start();
        tVerde.start();

        try {
            Thread.sleep(20000);
        } catch (Exception ignored) {
            Thread.currentThread().interrupt();
            ColorSemaphore.switchOff();
            tRojo.interrupt();
            tAmbar.interrupt();
            tVerde.interrupt();
        }

        ColorSemaphore.switchOff();

        tRojo.interrupt();
        tAmbar.interrupt();
        tVerde.interrupt();

        System.out.println("[INFO] Semaforo apagado --------------");
    }

    @Override
    public void run() {
        int timer = (color == Colors.AMBER) ? 1000 : 3000;

        try {
            while (isActive) {
                turn.acquire();
                System.out.println("Color actual: " + color.nameToUpper());
                try {
                    Thread.sleep(timer);
                } finally {
                    turn.release();
                }
            }
        } catch (Exception ignored) {
            Thread.currentThread().interrupt();
            turn.release();
        }
    }

    public static void switchOff() {
        isActive = false;
    }
}
