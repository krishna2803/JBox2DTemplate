import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class Application extends PApplet {

    ArrayList<Shape2D> entities;
    Wall walls[];

    @Override
    public void settings() { 
        size(640, 640);
    }

    // x, y, zoom
    PVector camera = new PVector(0.0f, 0.0f, 1.0f);
    float camSpeed = 6.0f;
    float zoomSenstivity = 0.05f;

    @Override
    public void setup() {
        Physx2D.create(this);

        entities = new ArrayList<Shape2D>();

        walls = new Wall[] {
            new Wall(0, -height/2,  width+2.5f, 5),
            new Wall(0,  height/2,  width+2.5f, 5),
            new Wall(-width/2, 0, 5, height+2.5f),
            new Wall( width/2, 0, 5, height+2.5f)
        };
    }

    @Override
    public void draw() {
        Physx2D.step();
        background(211);

        textSize(28);
        fill(71);
        textAlign(CENTER, CENTER);
        text("Press <space> to add shapes\nUse mouse to move the scane and mouse wheel zoom", width/2, height/2);

        translate(camera.x + width*0.5f, camera.y + height*0.5f);
        scale(camera.z);

        fill(51, 241);
        noStroke();
        entities.forEach(e -> e.show());

        for (Wall wall : walls)
            if (wall != null)
                wall.show();
    }


    @Override
    public void mouseDragged() {
        PVector delta = new PVector(
            mouseX - pmouseX,
            mouseY - pmouseY
        );

        delta.mult(camSpeed).limit(camSpeed * 1.2f);
        camera.add(delta);
    }

    @Override
    public void mouseWheel(MouseEvent e) {
        camera.z = max(0.5f, camera.z-e.getCount() * zoomSenstivity);
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            float r = random(1.0f);
            if (r < 0.5f)
                entities.add(Physx2D.randomBox());
            else 
                entities.add(Physx2D.randomCircle());
        }
    }

    public static void main(String ... args) {
        PApplet.main("Application");
    }
}
