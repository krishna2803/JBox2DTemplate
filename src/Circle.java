import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Circle extends Shape2D {
    protected float radius;

    /**
     * create a Physx2D Circle object.
     * atleast 3 args (x, y, r) are required
     * @param xpos x position in screen
     * @param ypos y position in screen
     * @param radius radius of the circle
     * @param density density of the shape (default 1.0)
     * @param friction friction coefficient of the shape (default 0.2)
     * @param restitution restitition coefficient of the shape (default 0.05)
     */
    public Circle(float ... args) {
        int len = args.length;
        if (len < 3) return;

        BodyDef bd = new BodyDef();
        bd.position.set(world(args[0], args[1]));
        bd.type = BodyType.DYNAMIC;

        radius = args[2];
        CircleShape cs = new CircleShape();
        cs.setRadius(world(args[2]));
    
        FixtureDef fd = new FixtureDef();
        fd.density     = len > 3 ? args[3] : 1.0f;
        fd.friction    = len > 4 ? args[4] : 0.2f;
        fd.restitution = len > 5 ? args[5] : 0.05f;
        fd.shape = cs;

        body = create(bd);
        body.createFixture(fd);
    }

    @Override
    public void show() {
        Vec2 pos = pos(body);
        parent.pushMatrix();
        parent.translate(pos.x, pos.y);
        parent.rotate(-body.getAngle());
        parent.circle(0, 0, radius*2.0f);
        parent.popMatrix();
    }
}
