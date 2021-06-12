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
            Thread.sleep(20000);
            // 开局10s无生成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MusicPlayer play = new MusicPlayer("a-lot-of.mp3");
        new Thread(play).start();

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
                int row = (int) (Math.random() * 5);//?
                int remainder = (int) (Math.random() * 50) % 7;
                Zombie tempZombie;
                if (remainder < 3)
                    tempZombie = new Zombie(controller).getZombie("NormalZombie");
                else if (remainder < 5)
                    tempZombie = new Zombie(controller).getZombie("hzyZombie");
                else
                    tempZombie = new Zombie(controller).getZombie("BucketZombie");
                tempZombie.setRow(row);
                System.out.println(row);
                controller.getLayeredPane().add(tempZombie, Integer.valueOf(400));
                controller.addZombie(tempZombie, row);
                new Thread(tempZombie).start();
            } catch (InterruptedException e) {
                // e.printStackTrace();
                break;
            }
        }

        Thread.currentThread().interrupt();
    }
}