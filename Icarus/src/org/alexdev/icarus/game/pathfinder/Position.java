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

	public Position(int x, int y) {
		this.X = x;
		this.Y = y;
		this.Z = 0;
	}

	public Position(int x, int y, double i) {
		this.X = x;
		this.Y = y;
		this.Z = i;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public double getZ() {
		return Z;
	}

	public void setZ(double z) {
		Z = z;
	}

	public int getBodyRotation() {
        return bodyRotation;
    }

    public void setBodyRotation(int bodyRotation) {
        this.bodyRotation = bodyRotation;
    }

    public int getHeadRotation() {
        return headRotation;
    }

    public void setHeadRotation(int headRotation) {
        this.headRotation = headRotation;
    }

    public int getRotation() {
        return bodyRotation;
    }

    public void setRotation(int headRotation) {
        this.headRotation = headRotation;
        this.bodyRotation = headRotation;
    }

    public Position add(Position other) {
		return new Position(other.getX() + getX(), other.getY() + getY(), other.getZ() + getZ());
	}

	public Position subtract(Position other) {
		return new Position(other.getX() - getX(), other.getY() - getY(), other.getZ() - getZ());
	}


	public int getDistanceSquared(Position point) {
		int dx = this.getX() - point.getX();
		int dy = this.getY() - point.getY();

		return (dx * dx) + (dy * dy);
	}

	public boolean sameAs(Position point) {	
		return (this.X == point.getX() && this.Y == point.getY());
	}

	@Override
	public String toString() {

		return "[" + this.X + ", " + this.Y + "]";
	}

}
