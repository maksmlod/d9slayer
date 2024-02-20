package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Ice_Wand extends Entity {
    GamePanel gp;
    public OBJ_Ice_Wand(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 101;
        type = type_wand;
        name = "Ice wand";
        down1 = setup("/objects/ice_wand",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Ice Wand\n\nFast Ice Projectile";
        projectile = new OBJ_Iceball(gp);
        castSpeed = 15;
        useCost = 1;
        canMeleeAttack = false;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        projectile4 = new OBJ_Iceball(gp);
        projectile4.set(worldX, worldY, direction, "",alive, user);
        gp.projectileList.add(projectile4);

        user.mana = user.mana - useCost;
        gp.playSE(14);
    }
}
