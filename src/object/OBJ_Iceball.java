package object;

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
        speed = 10;
        maxLife = 120;
        life = maxLife;
        attack = 2;
        alive = false;
        getImage();

        solidArea.x = 6;
        solidArea.y = 6;
        solidArea.width = 18;
        solidArea.height = 18;

        projectileTileArea.x = 22;
        projectileTileArea.y = 22;
        projectileTileArea.width = 4;
        projectileTileArea.height = 4;
    }
    public void getImage() {
        up1 = setup("/projectile/iceball_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/iceball_up_1",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/iceball_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/iceball_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/iceball_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/iceball_left_1",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/iceball_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/iceball_right_1",gp.tileSize,gp.tileSize);
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
