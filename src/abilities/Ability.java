package abilities;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class Ability {
    public GamePanel gp;

    public static int cooldownTime = 0;
    public int cost = 0;
    public String name = "";
    public int abilityNumber = 0;
    public int dashTimer = 0;
    public int dashFlag = 0;
    public String dashDirection = "down";
    public int dashDuration = 0;
    public int dashCooldown = 0;
    public int dashAllDuration = 0;

    public Ability(GamePanel gp) {
        this.gp = gp;
    }

    public void effect(Entity user) {

    }
    public void revertEffect(Entity user) {

    }
}
