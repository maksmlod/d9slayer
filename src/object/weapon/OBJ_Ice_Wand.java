package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.projectile.OBJ_Divineshot;
import object.projectile.OBJ_Iceball;

public class OBJ_Ice_Wand extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[50];
    public int i = 0;
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
        description = "Fast Ice Projectile";
        projectile = new OBJ_Iceball(gp);
        castSpeed = 15;
        useCost = 1;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 10;
        rarity = "uncommon";
        for(int i = 0; i < 50; i++) {
            projectiles[i] = new OBJ_Iceball(gp);
        }
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 45) i = 0;
        projectiles[i].set(worldX, worldY, direction, "",alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        user.mana = user.mana - useCost;
        gp.playSE(14);
    }
}
