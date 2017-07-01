package Serialize.SerializableGame;

import java.io.*;

/**
 * Created by SuperDenissio on 30.06.2017.
 */
public class GameSeverTest {
    public static void main(String[] args) {
        GameCharacter one = new GameCharacter(100,"Бурундук", new String[]{"Зуб", "Анус", "Око Саурона"});
        GameCharacter two = new GameCharacter(200,"Орк", new String[]{"Топор", "Палица", "Зуб Бурундука"});
        GameCharacter three = new GameCharacter(50,"Охотник", new String[]{"Лук", "Пистолет", "Палка-ебалка"});

        // код, меняющий значения персонажей

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("game.ser"));
            os.writeObject(one);
            os.writeObject(two);
            os.writeObject(three);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        one = null;
        two = null;
        three = null;

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("game.ser"));
            GameCharacter oneRes = (GameCharacter) is.readObject();
            GameCharacter twoRes = (GameCharacter) is.readObject();
            GameCharacter threeRes = (GameCharacter) is.readObject();

            System.out.println(oneRes.getType());
            System.out.println(twoRes.getType());
            System.out.println(threeRes.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
