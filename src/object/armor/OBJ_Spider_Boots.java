package object.armor;

import entity.Entity;
import main.GamePanel;

public class OBJ_Spider_Boots extends Entity {
    public OBJ_Spider_Boots(GamePanel gp) {
        super(gp);

        type = type_accessory;
        name = "Spider Boots";
        down1 = setup("/armor/spider_boots", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "Increase def\nby 1\nSet bonus: +10 def";
        rarity = "legendary";
        price = 10;
        albumOrigin = spiderr;
        armorSetName = "spider";
        armorSetOrigin = "spider";
    }
    public void effect(Entity user) {
        user.defense += defenseValue;
        user.incrementArmorSetCounter(armorSetName);
        if(user.armorSetCounters.get(armorSetName) == 4) effectBonus(user);
    }
    public void revertEffect(Entity user) {
        user.defense -= defenseValue;
        user.decrementArmorSetCounter(armorSetName);
        if(user.armorSetCounters.get(armorSetName) == 3) revertEffectBonus(user);
    }
    public void effectBonus(Entity user) {
        user.defense += 10;
    }
    public void revertEffectBonus(Entity user) {
        user.defense -= 10;
    }
}