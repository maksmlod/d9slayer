package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.projectile.OBJ_Fireball;
import object.projectile.OBJ_Iceball;

public class OBJ_Freezer extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[100];
    public int i = 0;
    public OBJ_Freezer(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "ice";
        weapon_id = 102;
        type = type_wand;
        name = "Freezer";
        down1 = setup("/objects/freezer",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Frost Nova";
        projectile = new OBJ_Iceball(gp);
        castSpeed = 15;
        useCost = 1;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 20;
        rarity = "rare";
        producesAura = true;
        for(int i = 0; i < 100; i++) {
            projectiles[i] = new OBJ_Iceball(gp);
        }
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 90) i = 0;
        if(direction == "left" || direction == "right") {
            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, direction, "up", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, direction, "down", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, direction, "", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, "down", "", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, "up", "", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
        }
        else {
            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, direction, "right", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, direction, "left", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, direction, "", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, "right", "", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].speed = 3;
            projectiles[i].set(worldX, worldY, "left", "", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
        }

        user.mana = user.mana - useCost;
        gp.playSE(14);
    }
}
