package object.weapon;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;
import object.projectile.OBJ_Bullet;
import object.projectile.OBJ_Divineshot;
import object.projectile.OBJ_Iceball;

public class OBJ_M4A1S extends Entity {
    GamePanel gp;
    public Projectile[] projectiles = new Projectile[50];
    public int i = 0;
    UtilityTool uTool = new UtilityTool();
    public OBJ_M4A1S(GamePanel gp) {
        super(gp);
        this.gp = gp;

        weapon_id = 101;
        type = type_wand;
        name = "M4A1-S";
        down1 = setup("/objects/m4a1s",gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 0;
        attackArea.height = 0;
        description = "\"NOTHINGG\"";
        projectile = new OBJ_Bullet(gp);
        castSpeed = 5;
        useCost = 0;
        canMeleeAttack = false;
        haveProjectile = true;
        price = 10;
        rarity = "legendary";
        for(int i = 0; i < 50; i++) {
            projectiles[i] = new OBJ_Bullet(gp);
        }
        albumOrigin = spiderr;
    }

    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {
        if(i > 45) i = 0;
        projectiles[i].set(worldX, worldY, direction, "",alive, user,this);
        gp.projectileList.add(projectiles[i]);
        i++;

        user.mana = user.mana - useCost;
        gp.playSE(17);
    }
}
