public class Wall extends Box {

    public Wall(float ... args) {
        super(args);
        if (body != null) body.setType(org.jbox2d.dynamics.BodyType.STATIC);
    }
}
