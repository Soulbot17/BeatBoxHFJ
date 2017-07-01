package Serialize.SerializableGame;

import java.io.Serializable;

/**
 * Created by SuperDenissio on 30.06.2017.
 */
public class GameCharacter implements Serializable{
    int power;
    String type;
    String[] weapons;
    public GameCharacter(int p, String t, String[] w) {
        power = p;
        type = t;
        weapons = w;
    }

    public int getPower(){
        return power;
    }

    public String getType() {
        return type;
    }

    public String getWeapons() {
        String weaponslist = "";
        for (int i = 0; i<weapons.length;i++) {
            weaponslist += weapons[i]+" ";
        }
        return weaponslist;
    }
}
