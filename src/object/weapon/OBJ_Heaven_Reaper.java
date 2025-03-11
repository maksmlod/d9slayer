package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.projectile.OBJ_Divineshot;
import object.projectile.OBJ_Fireball;
import object.projectile.OBJ_Iceball;

public class OBJ_Heaven_Reaper extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[50];
    public int i = 0;
    public OBJ_Heaven_Reaper(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "lightning";
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
        for(int i = 0; i < 50; i++) {
            projectiles[i] = new OBJ_Divineshot(gp);
        }
    }
    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 45) i = 0;
        projectiles[i].attack = 100;
        projectiles[i].set(worldX, worldY, direction, "",alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        //gp.playSE(14);
    }
}
