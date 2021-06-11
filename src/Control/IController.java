package Control;

import Card.Card;
import Card.Plant.Plant;
import Shovel.Shovel;
import Card.Zombie.Zombie;

import javax.swing.*;
import java.util.Map;

public interface IController {
    // 控制
    boolean isRunning();
    void setRunning();
    void setLayeredPane(JLayeredPane gameboardView);
    JPanel getTopPanel();
    JLayeredPane getLayeredPane();
    void setPreImg(ImageIcon preImg);
    void setBlurImg(ImageIcon blurImg);
    void setNowPlant(Plant nowPlant);
    void endGame();

    // 阳光
    JLabel getSunCount();
    void setSunCount(int cnt);
    void setSunCount(JLabel cnt);
    int getIntSunCount();
    void addSunCount(int cnt);

    // 铲子
    void setShovel(Shovel shovel);
    void setShovel(boolean b);

    // 卡片
    void addCard(Card card);
    Card getCard();
    void cancelSelectingCard();
    Map<String, Plant> getPlantMap();
    void setCard(Card card);
    void setSelectedIndex(int x);
    void checkCards();

    // 僵尸
    Zombie getAttackedZombie(int row, int x);
    boolean haveZombie(int row);
    void deleteZombie(Zombie zombie, int row);
    void addZombie(Zombie zombie, int row);

    // 植物
    void plantDeath(int row, int column);
    void boom(int row, int column);
    Plant[][] getPlants();

}
