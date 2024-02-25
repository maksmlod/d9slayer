package object.projectile;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Iceball extends Projectile {
    GamePanel gp;
    public OBJ_Iceball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name= "Fireball";
        speed = 13;
        maxLife = 120;
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
        up1 = iceball_up1;
        up2 = iceball_up2;
        down1 = iceball_down1;
        down2 = iceball_down2;
        left1 = iceball_left1;
        left2 = iceball_left2;
        right1 = iceball_right1;
        right2 = iceball_right2;
    }

    public Color getParticleColor() {
        Color color = new Color(173,216,230);
        return color;
    }
    public int getParticleSize() {
        int size = 18;
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
