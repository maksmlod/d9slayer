package object.weapon;

import entity.Entity;
import main.GamePanel;
import object.projectile.OBJ_Divineshot;
import object.projectile.OBJ_Iceball;

public class OBJ_Heaven_Reaper extends Entity {
    GamePanel gp;
    public OBJ_Heaven_Reaper(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 103;
        canMeleeAttack = true;
        type = type_sword;
        name = "Heaven Reaper";
        down1 = setup("/objects/heaven_reaper",gp.tileSize,gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "Shots divine\nprojectile";
        haveProjectile = true;
        projectile = new OBJ_Divineshot(gp);
        price = 69;
        rarity = "legendary";
    }
    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        projectile2 = new OBJ_Divineshot(gp);
        projectile2.attack = 100;
        projectile2.set(worldX, worldY, direction, "",alive, user);
        gp.projectileList.add(projectile2);

        //gp.playSE(14);
    }
}
