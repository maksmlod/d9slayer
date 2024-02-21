package object.weapon;

import entity.Entity;
import main.GamePanel;
import object.projectile.OBJ_Fireball;

public class OBJ_Hellfire extends Entity {
    GamePanel gp;
    public OBJ_Hellfire(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 100;
        type = type_wand;
        name = "Hellfire";
        down1 = setup("/objects/hellfire",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "Hellfire\n\n\"The true apocalypse\"";
        projectile = new OBJ_Fireball(gp);
        castSpeed = 30;
        useCost = 2;
        canMeleeAttack = false;
        haveProjectile = true;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        projectile2 = new OBJ_Fireball(gp);
        projectile2.maxLife = 160;
        projectile2.set(worldX, worldY, "right","", alive, user);
        gp.projectileList.add(projectile2);

        projectile3 = new OBJ_Fireball(gp);
        projectile3.maxLife = 160;
        projectile3.set(worldX, worldY, "right","up", alive, user);
        gp.projectileList.add(projectile3);

        projectile4 = new OBJ_Fireball(gp);
        projectile4.maxLife = 160;
        projectile4.set(worldX, worldY, "up","right", alive, user);
        gp.projectileList.add(projectile4);

        projectile5 = new OBJ_Fireball(gp);
        projectile5.maxLife = 160;
        projectile5.set(worldX, worldY, "up","", alive, user);
        gp.projectileList.add(projectile5);

        projectile6 = new OBJ_Fireball(gp);
        projectile6.maxLife = 160;
        projectile6.set(worldX, worldY, "left","up", alive, user);
        gp.projectileList.add(projectile6);

        projectile7 = new OBJ_Fireball(gp);
        projectile7.maxLife = 160;
        projectile7.set(worldX, worldY, "up","left", alive, user);
        gp.projectileList.add(projectile7);

        projectile8 = new OBJ_Fireball(gp);
        projectile8.maxLife = 160;
        projectile8.set(worldX, worldY, "left","", alive, user);
        gp.projectileList.add(projectile8);

        projectile9 = new OBJ_Fireball(gp);
        projectile9.maxLife = 160;
        projectile9.set(worldX, worldY, "down","left", alive, user);
        gp.projectileList.add(projectile9);

        projectile10 = new OBJ_Fireball(gp);
        projectile10.maxLife = 160;
        projectile10.set(worldX, worldY, "left","down", alive, user);
        gp.projectileList.add(projectile10);

        projectile11 = new OBJ_Fireball(gp);
        projectile11.maxLife = 160;
        projectile11.set(worldX, worldY, "down","", alive, user);
        gp.projectileList.add(projectile11);

        projectile12 = new OBJ_Fireball(gp);
        projectile12.maxLife = 160;
        projectile12.set(worldX, worldY, "down","right", alive, user);
        gp.projectileList.add(projectile12);

        projectile13 = new OBJ_Fireball(gp);
        projectile13.maxLife = 160;
        projectile13.set(worldX, worldY, "right","down", alive, user);
        gp.projectileList.add(projectile13);

        projectile14 = new OBJ_Fireball(gp);
        projectile14.maxLife = 320;
        projectile14.speed = 20;
        projectile14.set(worldX, worldY, "right","round", alive, user);
        gp.projectileList.add(projectile14);

        projectile15 = new OBJ_Fireball(gp);
        projectile15.maxLife = 320;
        projectile15.speed = 20;
        projectile15.set(worldX, worldY, "down","round", alive, user);
        gp.projectileList.add(projectile15);

        projectile16 = new OBJ_Fireball(gp);
        projectile16.maxLife = 320;
        projectile16.speed = 20;
        projectile16.set(worldX, worldY, "left","round", alive, user);
        gp.projectileList.add(projectile16);

        projectile17 = new OBJ_Fireball(gp);
        projectile17.maxLife = 320;
        projectile17.speed = 20;
        projectile17.set(worldX, worldY, "up","round", alive, user);
        gp.projectileList.add(projectile17);


        user.mana = user.mana - useCost;
        gp.playSE(10);
    }
}
