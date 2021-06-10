package Sun;

import Control.*;

public class SunProducer implements Runnable {
    private IController controller;

    public SunProducer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!controller.isRunning()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (controller.isRunning()) {
            try {
                Thread.sleep((int) (Math.random() * 1000) + 4000);
                // 4-5s随机生成一个

                new Thread(new Sun(controller)).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
    }
}