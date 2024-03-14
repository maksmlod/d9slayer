package main;

import java.io.*;

public class Config {
    GamePanel gp;
    public Config(GamePanel gp) {
        this.gp = gp;
    }
    public void saveConfig() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
        //full screen
        if(gp.fullScreenOn == true) {
            bw.write("On");
        }
        if(gp.fullScreenOn == false) {
            bw.write("Off");
        }
        bw.newLine();
        //music volume
        bw.write(String.valueOf(gp.music.volumeScale));
        bw.newLine();
        //se volume
        bw.write(String.valueOf(gp.sound.volumeScale));
        bw.newLine();
        bw.close();
    }
    public void loadConfig() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("config.txt"));
        String s = br.readLine();
        //full screen
        if(s.equals("On")) {
            gp.fullScreenOn = true;
        }
        if(s.equals("Off")) {
            gp.fullScreenOn = false;
        }
        //music volume
        s = br.readLine();
        gp.music.volumeScale = Integer.parseInt(s);

        //se volume
        s = br.readLine();
        gp.sound.volumeScale = Integer.parseInt(s);

        br.close();
    }
}
