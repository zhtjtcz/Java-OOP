package Card.Zombie;

import Control.*;

public class ZombieProducer implements Runnable {
    private IController controller;

    public ZombieProducer(IController controller) {
        this.controller = controller;
    }

    public int count = 1;

    @Override
    public void run() {

        try {
            Thread.sleep(10000);
            // 开局10s无生成
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
                Thread.sleep((int) (Math.random() * 250) + 5000);
                int row = (int) (Math.random() * 5);
                int type = (int) (Math.random() * 50) % 2;
                Zombie tempZombie;
                //if (type == 0)
                //    tempZombie = new Card.Zombie().normalZombie(controller, row);
                //else
                    tempZombie = new Zombie().BucketZombie(controller, row);

                controller.addZombie(tempZombie, row);
                new Thread(tempZombie).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread.currentThread().interrupt();
    }
}