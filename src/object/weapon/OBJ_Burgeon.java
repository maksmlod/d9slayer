package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.projectile.OBJ_Fireball;

public class OBJ_Burgeon extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[80];
    public int i = 0;
    public OBJ_Burgeon(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "fire";
        weapon_id = 100;
        type = type_wand;
        name = "Burgeon";
        down1 = setup("/objects/burgeon",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Let the world burn";
        projectile = new OBJ_Fireball(gp);
        for(int i = 0; i < 80; i++) {
            projectiles[i] = new OBJ_Fireball(gp);
        }
        castSpeed = 30;
        useCost = 2;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 20;
        rarity = "rare";
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 70) i = 0;

        projectiles[i].maxLife = 160;
        projectiles[i].set(worldX, worldY, direction,"", alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        if(direction == "left" || direction == "right") {

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX, worldY + 70, direction,"up", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX, worldY - 70, direction,"down", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX, worldY + 20, direction,"up", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX, worldY - 20, direction,"down", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
        }
        else if(direction == "up" || direction == "down") {

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX - 70, worldY, direction,"right", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX + 70, worldY, direction,"left", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX - 20, worldY, direction,"right", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;

            projectiles[i].maxLife = 160;
            projectiles[i].set(worldX + 20, worldY, direction,"left", alive, user,this);
            gp.projectileList.add(projectiles[i]);
            i++;
        }
        user.mana = user.mana - useCost;
        gp.playSE(10);
    }
}
