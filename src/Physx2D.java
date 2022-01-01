import processing.core.PApplet;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.Joint;

public abstract class Physx2D {

    protected static PApplet parent;

    private static World world;

    private static Vec2 translate;
	public  static float scale = 10.0f;

    private static Body ground;

    protected Physx2D() {}

    /**
     * create the box2d world
     * @param _parent PApplet to render stuff into
     */
    public static void create(PApplet _parent) {
        if (parent == null && _parent != null) {
            parent = _parent;
            translate = new Vec2(parent.width*.5f, parent.height*.5f);

            createWorld(new Vec2(0.0f, -9.80665f), true, true);
        }
    }

    /**
     * create the box2d world with given world gravity
     * @param _parent PApplet to render stuff into
     * @param g gravity vector
     */
    public static void create(PApplet _parent, Vec2 g) {
        if (parent == null && _parent != null) {
            parent = _parent;
            translate = new Vec2(parent.width*.5f, parent.height*.5f);

            createWorld(g, true, true);
        }
    }

    /**
     * update positions and velocities with 1/60 delta time
     */
    public static void step() {
        float dt = 0.016666f; // 1/60
		step(dt,10, 8);
		world.clearForces();
    }

    /**
     * update positions and velocities
     * @param dt delta time
     * @param v_iter number of velocity iterations
     * @param p_iter number of position iterations
     */
    public static void step(float dt, int v_iter, int p_iter) {
		world.step(dt, v_iter, p_iter);
	}

	public static void setWarmStarting(boolean b) {
		world.setWarmStarting(b);
	}
	
	public static void setContinuousPhysics(boolean b) {
		world.setContinuousPhysics(b);
	}
	
    /**
     * create world
     * @param gravity world gravity
     * @param warmStarting
     * @param continous
     */
	public static void createWorld(Vec2 gravity, boolean warmStarting, boolean continous) {
		world = new World(gravity);
		setWarmStarting(warmStarting);
		setContinuousPhysics(continous);
		ground = world.createBody(new BodyDef());
	}

    /**
     * get the ground body
     * useful for MouseJoint
     * @return ground
     */
    public Body getGround() {
        return ground;
    }

    /**
     * set world gravity
     * @param v gravity vector
     */
    public static void gravity(Vec2 v) {
        world.setGravity(v);
    }

    /**
     * create body from BodyDef
     * @param bd body def
     * @return
     */
    public static Body create(BodyDef bd) {
        return world.createBody(bd);
    }

    /**
     * create joint from JointDef
     * @param jd joint def
     * @return
     */
    public static Joint create(JointDef jd) {
        return world.createJoint(jd); 
    }

    /**
     * destroy body
     * @param body
     */
    public static void destroy(Body body) {
        world.destroyBody(body);
    }

    /**
     * destroy joint
     * @param joint
     */
    public static void destroy(Joint joint) {
        world.destroyJoint(joint); 
    }

    /**
     * get screen pixel position of body
     * @param body
     * @return
     */
    public Vec2 pos(Body body) {
        return pixels(body.getTransform().p);
    }

    /**
     * convert coordinates from screen to world
     * @param x
     * @param y
     * @return world coordinate vector
     */
    public static Vec2 world(float x, float y) {
		return new Vec2(
            map(x, translate.x, translate.x + scale, 0.0f, 1.0f),
            map(map(y, parent.height, 0.0f, 0.0f, parent.height), translate.y, translate.y + scale, 0.0f, 1.0f)
        );
    }

    /**
     * convert coordinates from screen to world
     * @param v screen vector 
     * @return world coordinate vector
     */
    public Vec2 world(Vec2 v) { 
        return world(v.x, v.y);
    }

    /**
     * convert coordinates from world to screen
     * @param x
     * @param y
     * @return world coordinate vector
     */
    public Vec2 pixels(float x, float y) {
		return new Vec2(
            map(x, 0.0f, 1.0f, translate.x, translate.x + scale),
            map(map(y, 0.0f, 1.0f, translate.y, translate.y + scale), 0.0f, parent.height, parent.height, 0.0f)
        );
    }

    /**
     * convert coordinates from screen to world
     * @param v screen vector 
     * @return pixel coordinate vector
     */
    public Vec2 pixels(Vec2 v) {
        return pixels(v.x, v.y);
    }

    /**
     * convert vector from screen to world
     * @param x
     * @param y
     * @return world vector
     */
    public Vec2 worldv(float x, float y) {
		return new Vec2(x / scale, -y / scale);
    }

    /**
     * convert vector from screen to world
     * @param v screen vector 
     * @return world vector
     */
    public Vec2 worldv(Vec2 v) {
        return worldv(v.x, v.y);
    }

    /**
     * convert vector from world to screen
     * @param x
     * @param y
     * @return screen vector
     */
    public Vec2 pixelsv(float x, float y) {
		return new Vec2(x * scale, -y * scale);
    }


    /**
     * convert vector from screen to world
     * @param v world vector
     * @return screen vector
     */
    public Vec2 pixelsv(Vec2 v) {
        return pixels(v.x, v.y);
    }

    /**
     * convert scalar from screen to world units
     * @param x
     * @return scalar in world units
     */
    public float world(float x) {
        return x / scale;
    }

    /**
     * convert scalar from world to screen units
     * @param x
     * @return scalar in screen units
     */
    public float pixels(float x) {
        return x * scale;
    }

    /**
     * square of euclidean distance between two points
     * @param p
     * @param q
     * @return square distance
     */
    public static float distSq(Vec2 p, Vec2 q) {
        return (p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y);
    }
    
    /**
     * create a square box with random side at random screen position
     * @return Box
     */
    public Box randomBox() {
        float s = parent.random(50, 100);
        return new Box(parent.random(parent.width), parent.random(parent.height), s, s);
    }

    /**
     * create a circle with random radius at random screen position
     * @return Circle
     */
    public Circle randomCircle() {
        float r = parent.random(100, 125) * 0.5f;
        return new Circle(parent.random(parent.width), parent.random(parent.height), r);
    }

    /**
     * draw a line between two points
     * @param u
     * @param v
     */
    public static void line(Vec2 u, Vec2 v) {
        parent.line(u.x, u.y, v.x, v.y);
    }

    /**
     *
     * Re-maps a number from one range to another.<br />
     * @param value the incoming value to be converted
     * @param start1 lower bound of the value's current range
     * @param stop1 upper bound of the value's current range
     * @param start2 lower bound of the value's target range
     * @param stop2 upper bound of the value's target range
     */
    static private final float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }
}
