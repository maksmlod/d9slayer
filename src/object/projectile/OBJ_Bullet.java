package object.projectile;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Bullet extends Projectile {
    GamePanel gp;
    public OBJ_Bullet(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "physical";
        name= "Bullet";
        speed = 20;
        maxLife = 150;
        life = maxLife;
        attack = 1;
        useCost = 0;
        alive = false;
        getImage();

        projectileTileArea.x = 22;
        projectileTileArea.y = 22;
        projectileTileArea.width = 4;
        projectileTileArea.height = 4;
    }
    public void getImage() {
        up1 = bullet_down1;
        up2 = bullet_down1;
        down1 = bullet_down1;
        down2 = bullet_down1;
        left1 = bullet_down1;
        left2 = bullet_down1;
        right1 = bullet_down1;
        right2 = bullet_down1;
    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void substractResource(Entity user) {
        user.ammo -= useCost;
    }
    public Color getParticleColor() {
        Color color = Color.black;
        return color;
    }
    public int getParticleSize() {
        int size = 3;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 15;
        return maxLife;
    }
}
