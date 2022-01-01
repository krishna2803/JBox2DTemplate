import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Box extends Shape2D {
    protected float width;
    protected float height;

    /**
     * create a Physx2D Box object.
     * atleast 4 args (x, y, w, h) are required
     * @param xpos x position in screen
     * @param ypos y position in screen
     * @param width width of the box
     * @param height height of the box
     * @param density density of the shape (default 1.0)
     * @param friction friction coefficient of the shape (default 0.2)
     * @param restitution restitition coefficient of the shape (default 0.05)
     */
    public Box(float ... args) {
        int len = args.length;
        if (len < 4) return;

        BodyDef bd = new BodyDef();
        bd.position.set(world(args[0], args[1]));
        bd.type = BodyType.DYNAMIC;

        width = args[2];
        height = args[3];

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(world(args[2]*.5f), world(args[3]*.5f));

        FixtureDef fd = new FixtureDef();
        
        fd.density     = len > 4 ? args[4] : 1.0f;
        fd.friction    = len > 5 ? args[5] : 0.2f;
        fd.restitution = len > 6 ? args[6] : 0.05f;
        fd.shape = ps;
        body = create(bd);
        body.createFixture(fd);
    }

    @Override
    public void show() {
        Vec2 pos = pos(body);
        parent.pushMatrix();
        parent.translate(pos.x, pos.y);
        parent.rotate(-body.getAngle());
        parent.rectMode(processing.core.PApplet.CENTER);
        parent.rect(0, 0, width, height);
        parent.popMatrix();
    }
}
