package org.alexdev.icarus.game.pathfinder;

public class Position {

    private int X;
    private int Y;
    private double Z;
    private int bodyRotation;
    private int headRotation;

    public Position() {
        this(0, 0, 0);
    }

    /**
     * Instantiates a new position.
     *
     * @param x the x
     * @param y the y
     */
    public Position(int x, int y) {
        this.X = x;
        this.Y = y;
        this.Z = 0;
    }

    /**
     * Instantiates a new position.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Position(int x, int y, double z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX() {
        return X;
    }

    /**
     * Sets the x.
     *
     * @param x the new x
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY() {
        return Y;
    }

    /**
     * Sets the y.
     *
     * @param y the new y
     */
    public void setY(int y) {
        Y = y;
    }

    /**
     * Gets the z.
     *
     * @return the z
     */
    public double getZ() {
        return Z;
    }

    /**
     * Sets the z.
     *
     * @param z the new z
     */
    public void setZ(double z) {
        Z = z;
    }

    /**
     * Gets the body rotation.
     *
     * @return the body rotation
     */
    public int getBodyRotation() {
        return bodyRotation;
    }

    /**
     * Sets the body rotation.
     *
     * @param bodyRotation the new body rotation
     */
    public void setBodyRotation(int bodyRotation) {
        this.bodyRotation = bodyRotation;
    }

    /**
     * Gets the head rotation.
     *
     * @return the head rotation
     */
    public int getHeadRotation() {
        return headRotation;
    }

    /**
     * Sets the head rotation.
     *
     * @param headRotation the new head rotation
     */
    public void setHeadRotation(int headRotation) {
        this.headRotation = headRotation;
    }

    /**
     * Gets the rotation.
     *
     * @return the rotation
     */
    public int getRotation() {
        return bodyRotation;
    }

    /**
     * Sets the rotation.
     *
     * @param headRotation the new rotation
     */
    public void setRotation(int headRotation) {
        this.headRotation = headRotation;
        this.bodyRotation = headRotation;
    }

    /**
     * Adds the.
     *
     * @param other the other
     * @return the position
     */
    public Position add(Position other) {
        return new Position(other.getX() + getX(), other.getY() + getY(), other.getZ() + getZ());
    }

    /**
     * Subtract.
     *
     * @param other the other
     * @return the position
     */
    public Position subtract(Position other) {
        return new Position(other.getX() - getX(), other.getY() - getY(), other.getZ() - getZ());
    }

    /**
     * Gets the distance squared.
     *
     * @param point the point
     * @return the distance squared
     */
    public int getDistanceSquared(Position point) {
        int dx = this.getX() - point.getX();
        int dy = this.getY() - point.getY();

        return (dx * dx) + (dy * dy);
    }
    
    /**
     * Gets the square in front.
     *
     * @return the square in front
     */
    public Position getSquareInFront() {
        Position square = new Position(this.X, this.Y);

        if (this.bodyRotation == 0) {
            square.Y--;
        } else if (this.bodyRotation == 2) {
            square.X++;
        } else if (this.bodyRotation == 4) {
            square.Y++;
        } else if (this.bodyRotation == 6) {
            square.X--;
        }

        return square;
    }

    /**
     * Gets the square left.
     *
     * @return the square left
     */
    public Position getSquareLeft() {

        Position square = new Position(this.X, this.Y);

        if (this.bodyRotation == 0) {
            square.X--;
        } else if (this.bodyRotation == 2) {
            square.Y++;
        } else if (this.bodyRotation == 4) {
            square.X++;
        } else if (this.bodyRotation == 6) {
            square.Y++;
        }

        return square;
    }
    
    /**
     * Gets the square right.
     *
     * @return the square right
     */
    public Position getSquareRight() {

        Position square = new Position(this.X, this.Y);

        if (this.bodyRotation == 0) {
            square.X++;
        } else if (this.bodyRotation == 2) {
            square.Y--;
        } else if (this.bodyRotation == 4) {
            square.X--;
        } else if (this.bodyRotation == 6) {
            square.Y--;
        }

        return square;
    }

    /**
     * Checks if is match.
     *
     * @param point the point
     * @return true, if is match
     */
    public boolean isMatch(Position point) {    
        return (this.X == point.getX() && this.Y == point.getY());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + this.X + ", " + this.Y + "]";
    }

}
