package object.weapon;

import entity.Entity;
import main.GamePanel;

public class OBJ_Normal_Sword extends Entity {
    public OBJ_Normal_Sword(GamePanel gp) {
            super(gp);

            damageType = "physical";
            canMeleeAttack = true;
            type = type_sword;
            name = "Normal Sword";
            down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
            attackValue = 1;
            attackArea.width = 36;
            attackArea.height = 36;
            description = "miecz";
            knockBackPower = 10;
    }

}
