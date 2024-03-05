package object.armor;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class OBJ_Spider_Boots extends Entity {
    public OBJ_Spider_Boots(GamePanel gp) {
        super(gp);

        type = type_accessory;
        name = "Spider Boots";
        down1 = setup("/armor/spider_boots", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "Increase def\nby 1\nSet bonus: +10 def";
        price = 10;
        albumOrigin = spiderr;
        armorSetName = "spider";
        armorSetOrigin = "spider";
        armorType = "boots";
        //rarity = "legendary";
        int j = new Random().nextInt(100) + 1;
        if(j < 35) rarity = "common";
        else if(j < 65) rarity = "uncommon";
        else if(j < 85) rarity = "rare";
        else if(j < 95) rarity = "epic";
        else if(j < 100) rarity = "legendary";
    }
    public void effect(Entity user) {
        user.defense += defenseValue;
        user.incrementArmorSetCounter(armorSetName, this);
    }
    public void revertEffect(Entity user) {
        user.defense -= defenseValue;
        user.decrementArmorSetCounter(armorSetName, this);
    }
    public void effectBonus(Entity user) {
        user.defense += 10;
    }
    public void revertEffectBonus(Entity user) {
        user.defense -= 10;
    }
}