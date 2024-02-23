package object.weapon;

import entity.Entity;
import main.GamePanel;
import object.projectile.OBJ_Fireball;

public class OBJ_Burgeon extends Entity {
    GamePanel gp;
    public OBJ_Burgeon(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 100;
        type = type_wand;
        name = "Burgeon";
        down1 = setup("/objects/burgeon",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Burgeon\n\nLet the world burn";
        projectile = new OBJ_Fireball(gp);
        castSpeed = 30;
        useCost = 2;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 5;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        projectile4 = new OBJ_Fireball(gp);
        projectile4.maxLife = 160;
        projectile4.set(worldX, worldY, direction,"", alive, user);
        gp.projectileList.add(projectile4);

        if(direction == "left" || direction == "right") {
            projectile2 = new OBJ_Fireball(gp);
            projectile2.maxLife = 160;
            projectile2.set(worldX, worldY + 70, direction,"up", alive, user);
            gp.projectileList.add(projectile2);
            projectile3 = new OBJ_Fireball(gp);
            projectile3.maxLife = 160;
            projectile3.set(worldX, worldY - 70, direction,"down", alive, user);
            gp.projectileList.add(projectile3);

            projectile5 = new OBJ_Fireball(gp);
            projectile5.maxLife = 160;
            projectile5.set(worldX, worldY + 20, direction,"up", alive, user);
            gp.projectileList.add(projectile5);
            projectile6 = new OBJ_Fireball(gp);
            projectile6.maxLife = 160;
            projectile6.set(worldX, worldY - 20, direction,"down", alive, user);
            gp.projectileList.add(projectile6);
        }
        else if(direction == "up" || direction == "down") {
            projectile2 = new OBJ_Fireball(gp);
            projectile2.maxLife = 160;
            projectile2.set(worldX - 70, worldY, direction,"right", alive, user);
            gp.projectileList.add(projectile2);
            projectile3 = new OBJ_Fireball(gp);
            projectile3.maxLife = 160;
            projectile3.set(worldX + 70, worldY, direction,"left", alive, user);
            gp.projectileList.add(projectile3);

            projectile5 = new OBJ_Fireball(gp);
            projectile5.maxLife = 160;
            projectile5.set(worldX - 20, worldY, direction,"right", alive, user);
            gp.projectileList.add(projectile5);
            projectile6 = new OBJ_Fireball(gp);
            projectile6.maxLife = 160;
            projectile6.set(worldX + 20, worldY, direction,"left", alive, user);
            gp.projectileList.add(projectile6);
        }

        user.mana = user.mana - useCost;
        gp.playSE(10);
    }
}
