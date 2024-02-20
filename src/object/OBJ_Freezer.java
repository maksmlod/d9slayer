package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Freezer extends Entity {
    GamePanel gp;
    public OBJ_Freezer(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 102;
        type = type_wand;
        name = "Freezer";
        down1 = setup("/objects/freezer",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Ice Wand\n\nFrost Nova";
        projectile = new OBJ_Iceball(gp);
        castSpeed = 15;
        useCost = 1;
        canMeleeAttack = false;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(direction == "left" || direction == "right") {
            projectile4 = new OBJ_Iceball(gp);
            projectile4.speed = 3;
            projectile4.set(worldX, worldY, direction, "up", alive, user);
            gp.projectileList.add(projectile4);

            projectile3 = new OBJ_Iceball(gp);
            projectile3.speed = 3;
            projectile3.set(worldX, worldY, direction, "down", alive, user);
            gp.projectileList.add(projectile3);

            projectile2 = new OBJ_Iceball(gp);
            projectile2.speed = 3;
            projectile2.set(worldX, worldY, direction, "", alive, user);
            gp.projectileList.add(projectile2);

            projectile5 = new OBJ_Iceball(gp);
            projectile5.speed = 3;
            projectile5.set(worldX, worldY, "down", "", alive, user);
            gp.projectileList.add(projectile5);

            projectile6 = new OBJ_Iceball(gp);
            projectile6.speed = 3;
            projectile6.set(worldX, worldY, "up", "", alive, user);
            gp.projectileList.add(projectile6);
        }
        else {
            projectile4 = new OBJ_Iceball(gp);
            projectile4.speed = 3;
            projectile4.set(worldX, worldY, direction, "right", alive, user);
            gp.projectileList.add(projectile4);

            projectile3 = new OBJ_Iceball(gp);
            projectile3.speed = 3;
            projectile3.set(worldX, worldY, direction, "left", alive, user);
            gp.projectileList.add(projectile3);

            projectile2 = new OBJ_Iceball(gp);
            projectile2.speed = 3;
            projectile2.set(worldX, worldY, direction, "", alive, user);
            gp.projectileList.add(projectile2);

            projectile5 = new OBJ_Iceball(gp);
            projectile5.speed = 3;
            projectile5.set(worldX, worldY, "right", "", alive, user);
            gp.projectileList.add(projectile5);

            projectile6 = new OBJ_Iceball(gp);
            projectile6.speed = 3;
            projectile6.set(worldX, worldY, "left", "", alive, user);
            gp.projectileList.add(projectile6);
        }

        user.mana = user.mana - useCost;
        gp.playSE(14);
    }
}
