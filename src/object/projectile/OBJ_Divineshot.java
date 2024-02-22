package object.projectile;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Divineshot extends Projectile {
    GamePanel gp;
    public OBJ_Divineshot(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name= "Divine";
        speed = 5;
        maxLife = 120;
        life = maxLife;
        attack = 100;
        alive = false;
        getImage();

        solidArea.x = 2;
        solidArea.y = 2;
        solidArea.width = 44;
        solidArea.height = 44;

        projectileTileArea.x = 22;
        projectileTileArea.y = 22;
        projectileTileArea.width = 4;
        projectileTileArea.height = 4;
    }
    public void getImage() {
        up1 = setup("/projectile/divineshot_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/divineshot_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/divineshot_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/divineshot_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/divineshot_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/divineshot_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/divineshot_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/divineshot_right_2",gp.tileSize,gp.tileSize);
    }

    public Color getParticleColor() {
        Color color = new Color(230,230,0);
        return color;
    }
    public int getParticleSize() {
        int size = 10;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 2;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
