import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class Application extends PApplet {

    ArrayList<Shape2D> entities;

    @Override
    public void settings() { 
        size(640, 640);
    }

    // x, y, zoom
    PVector camera;
    float camSpeed = 6.0f;
    float zoomSenstivity = 0.05f;

    @Override
    public void setup() {
        Physx2D.create(this);

        entities = new ArrayList<Shape2D>();

        camera = new PVector();
        camera.z = 1.0f;

    }

    @Override
    public void draw() {
        Physx2D.step();
        background(255);

        translate(camera.x+width*0.5f, camera.y+height*0.5f);
        scale(camera.z);

        entities.forEach(e -> e.show());
    }


    @Override
    public void mouseDragged() {
        PVector delta = new PVector(
            mouseX - pmouseX,
            mouseY - pmouseY
        );

        delta.mult(camSpeed).limit(camSpeed * 1.1f);
        camera.add(delta);
    }

    @Override
    public void mouseWheel(MouseEvent e) {
        camera.z = max(0.5f, camera.z-e.getCount() * zoomSenstivity);
    }

    public static void main(String ... args) {
        PApplet.main("Application");
    }
}
