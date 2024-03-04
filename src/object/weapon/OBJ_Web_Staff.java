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
    public Projectile[] projectiles = new Projectile[80];
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
        description = "Web shot";
        projectile = new OBJ_Webshot(gp);
        castSpeed = 15;
        useCost = 1;
        canMeleeAttack = false;
        haveProjectile = true;
        rarity = "rare";
        for(int i = 0; i < 80; i++) {
            projectiles[i] = new OBJ_Webshot(gp);
        }
        needItemToBuy = true;
        neededItemToBuy = new OBJ_Spider_Scale(gp);
        neededItemAmount = 10;
        price = 10;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 70) i = 0;
        projectiles[i].set(worldX, worldY, direction, "",alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        user.mana = user.mana - useCost;
        gp.playSE(14);
    }
    public void reactAfterDamagingMonster(int worldX, int worldY, String direction, boolean alive, Entity user, Entity monster) {
        projectiles[i].set(worldX, worldY + 2*monster.solidArea.height, "down", "",alive, gp.player,this);
        gp.projectileList.add(projectiles[i]);
        i++;
        projectiles[i].set(worldX + 2*monster.solidArea.width, worldY, "right", "",alive, gp.player,this);
        gp.projectileList.add(projectiles[i]);
        i++;
        projectiles[i].set(worldX - monster.solidArea.width, worldY, "left", "",alive, gp.player,this);
        gp.projectileList.add(projectiles[i]);
        i++;
        projectiles[i].set(worldX, worldY - monster.solidArea.height, "up", "",alive, gp.player,this);
        gp.projectileList.add(projectiles[i]);
        i++;


        if(i > 70) i = 0;
    }
}
