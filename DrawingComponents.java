interface Point {
    double distanceTo(Point other);
}

class Point2D implements Point {

    int x, y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double distanceTo(Point other) {
        Point2D p = (Point2D) other; 
        return Math.hypot(p.x, p.y);
    }

}

class Point3D implements Point {

    int x, y, z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double distanceTo(Point other) {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
