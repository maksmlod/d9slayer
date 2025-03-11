package object.projectile;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "fire";
        name= "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        alive = false;
        getImage();

        solidArea.x = 10;
        solidArea.y = 10;
        solidArea.width = 28;
        solidArea.height = 28;

        projectileTileArea.x = 22;
        projectileTileArea.y = 22;
        projectileTileArea.width = 4;
        projectileTileArea.height = 4;
    }
    public void getImage() {
        up1 = fireball_up1;
        up2 = fireball_up2;
        down1 = fireball_down1;
        down2 = fireball_down2;
        left1 = fireball_left1;
        left2 = fireball_left2;
        right1 = fireball_right1;
        right2 = fireball_right2;
    }

    public Color getParticleColor() {
        Color color = new Color(240,50,3);
        return color;
    }
    public int getParticleSize() {
        int size = 10; // 6 pixels
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
