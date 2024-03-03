package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.OBJ_Spider_Scale;
import object.projectile.OBJ_Divineshot;
import object.projectile.OBJ_Iceball;
import object.projectile.OBJ_Webshot;

public class OBJ_Web_Staff extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[50];
    public int i = 0;
    public OBJ_Web_Staff(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 101;
        type = type_wand;
        name = "Web staff";
        down1 = setup("/objects/web_staff",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Fast Ice Projectile";
        projectile = new OBJ_Webshot(gp);
        castSpeed = 15;
        useCost = 1;
        canMeleeAttack = false;
        haveProjectile = true;
        rarity = "rare";
        for(int i = 0; i < 50; i++) {
            projectiles[i] = new OBJ_Webshot(gp);
        }
        needItemToBuy = true;
        neededItemToBuy = new OBJ_Spider_Scale(gp);
        neededItemAmount = 10;
        price = 10;
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
