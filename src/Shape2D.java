import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

public abstract class Shape2D extends Physx2D {
    protected Body body;
    
    protected Shape2D() {}

    /**
     * render shape
     */
    public void show() {}

    /**
     * destroy shape
     */
    public void destroy() {
        destroy(body);
    }

    /**
     * get shape body
     * @return shape body
     */
    public Body getBody() {
        return body;
    }

    /**
     * check if a point lies in inside this shape
     * @param point
     * @return true if point lies inside this shape
     */
    public boolean contains(Vec2 point) {
        for (Fixture fixture = body.getFixtureList(); fixture != null; fixture = fixture.getNext())
            if(fixture.testPoint(point)) return true;
        return false;
    }

    /**
     * forcefully set the linear velocity of the body
     * useful for initializing initial velocity
     * @param v velocity vector
     */
    public void setLinearVelocity(Vec2 v) {
        body.setLinearVelocity(v);
    }

    /**
     * set the angular velocity
     * useful for initializing initial angular velocity
     * @param w angular velocity ω
     */
    public void setAngularVelocity(float w) {
        body.setAngularVelocity(w);
    }

    /**
     * change body type
     * @param type BodyType.STATIC BodyType.DYNAMIC BodyType.KINEMATIC
     */
    public void setBodyType(BodyType type) {
        body.setType(type);
    }
}
