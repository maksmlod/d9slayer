package object.weapon;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heaven_Reaper extends Entity {
    public OBJ_Heaven_Reaper(GamePanel gp) {
        super(gp);

        weapon_id = 103;
        canMeleeAttack = true;
        type = type_sword;
        name = "Heaven Reaper";
        down1 = setup("/objects/heaven_reaper",gp.tileSize,gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "Heaven Reaper\n\nShots divine\nprojectile";
    }
}
