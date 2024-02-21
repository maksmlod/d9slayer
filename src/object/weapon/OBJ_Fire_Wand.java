package object.weapon;

import entity.Entity;
import main.GamePanel;
import object.projectile.OBJ_Fireball;

public class OBJ_Fire_Wand extends Entity {
    GamePanel gp;
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
        description = "Fire Wand\n\nFireball projectile\nis tripled";
        projectile = new OBJ_Fireball(gp);
        castSpeed = 30;
        useCost = 2;
        canMeleeAttack = false;
        haveProjectile = true;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        projectile4 = new OBJ_Fireball(gp);
        projectile4.set(worldX, worldY, direction,"", alive, user);
        gp.projectileList.add(projectile4);

        if(direction == "left" || direction == "right") {
            projectile2 = new OBJ_Fireball(gp);
            projectile2.set(worldX, worldY + 30, direction,"", alive, user);
            gp.projectileList.add(projectile2);
            projectile3 = new OBJ_Fireball(gp);
            projectile3.set(worldX, worldY - 30, direction,"", alive, user);
            gp.projectileList.add(projectile3);
        }
        else if(direction == "up" || direction == "down") {
            projectile2 = new OBJ_Fireball(gp);
            projectile2.set(worldX + 30, worldY, direction,"", alive, user);
            gp.projectileList.add(projectile2);
            projectile3 = new OBJ_Fireball(gp);
            projectile3.set(worldX - 30, worldY, direction,"", alive, user);
            gp.projectileList.add(projectile3);
        }

        user.mana = user.mana - useCost;
        gp.playSE(10);
    }
}
