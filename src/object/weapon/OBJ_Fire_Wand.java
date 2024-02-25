package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.projectile.OBJ_Fireball;

public class OBJ_Fire_Wand extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[50];
    public int i = 0;
    public OBJ_Fire_Wand(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 100;
        type = type_wand;
        name = "Fire wand";
        down1 = setup("/objects/fire_wand",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Triple fireball";
        projectile = new OBJ_Fireball(gp);
        castSpeed = 30;
        useCost = 2;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 10;
        rarity = "uncommon";
        for(int i = 0; i < 50; i++) {
            projectiles[i] = new OBJ_Fireball(gp);
        }
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 43) i = 0;
        projectiles[i].set(worldX, worldY, direction,"", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;
        if(direction == "left" || direction == "right") {
            projectiles[i].set(worldX, worldY + 30, direction,"", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
            projectiles[i].set(worldX, worldY - 30, direction,"", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
        }
        else if(direction == "up" || direction == "down") {
            projectiles[i].set(worldX + 30, worldY, direction,"", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
            projectiles[i].set(worldX - 30, worldY, direction,"", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
        }
        user.mana = user.mana - useCost;
        gp.playSE(10);
    }
}
