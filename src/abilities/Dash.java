package abilities;

import entity.Entity;
import main.GamePanel;

public class Dash extends Ability {
    public GamePanel gp;

    public int userTempSpeed = 0;
    public static int abilityNumber = 1;



    public Dash(GamePanel gp) {
        super(gp);
        name = "dash";
        cooldownTime = 60;

        dashTimer = 7;
        dashFlag = 0;
        dashDirection = "down";
        dashDuration = 10;
        dashAllDuration = 10;
        dashCooldown = 60;
    }


    public void effect(Entity user) {
        userTempSpeed = user.speed;
        user.speed = 50;
    }
    public void revertEffect(Entity user) {
        user.speed = userTempSpeed;
    }



}
