/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Island {

        Image img;
        int x, y, speed;
        Random gen;

        Island(Image img, int x, int y, int speed, Random gen) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.gen = gen;
        }

        public void update() {
            y += speed;
            if (y >= 480) {
                y = -100;
                x = Math.abs(gen.nextInt() % (640 - 30));
            }
        }

        public void draw(ImageObserver obs, Graphics2D g2) {
            g2.drawImage(img, x, y, obs);
        }
    }
