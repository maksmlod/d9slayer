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

        name= "Fireball";
        speed = 5;
        maxLife = 80;
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
        up1 = setup("/projectile/fireball_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/fireball_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/fireball_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/fireball_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/fireball_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/fireball_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/fireball_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/fireball_right_2",gp.tileSize,gp.tileSize);
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
