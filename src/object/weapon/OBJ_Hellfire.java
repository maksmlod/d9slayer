package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.projectile.OBJ_Fireball;

import java.util.ArrayList;

public class OBJ_Hellfire extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[300];
    public int i = 0;
    public OBJ_Hellfire(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "fire";
        weapon_id = 100;
        type = type_wand;
        name = "Hellfire";
        down1 = setup("/objects/hellfire",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "\"The true apocalypse\"";
        projectile = new OBJ_Fireball(gp);
        for(int i = 0; i < 300; i++) {
            projectiles[i] = new OBJ_Fireball(gp);
        }
        castSpeed = 30;
        useCost = 2;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 50;
        rarity = "epic";
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 280) i = 0;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "right","", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "right","up", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "up","right", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "up","", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "left","up", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "up","left", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "left","", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "down","left", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "left","down", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "down","", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "down","right", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, "right","down", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 320;
        projectiles[i].speed = 20;
        projectiles[i].set(worldX, worldY, "right","round", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 320;
        projectiles[i].speed = 20;
        projectiles[i].set(worldX, worldY, "down","round", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 320;
        projectiles[i].speed = 20;
        projectiles[i].set(worldX, worldY, "left","round", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        projectiles[i].maxLife = 320;
        projectiles[i].speed = 20;
        projectiles[i].set(worldX, worldY, "up","round", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        user.mana = user.mana - useCost;
        gp.playSE(10);
    }
}
