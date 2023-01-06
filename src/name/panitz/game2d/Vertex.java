package name.panitz.game2d;

public class Vertex {
	public double x;
	public double y;

	public double Magnitude(){
		return Math.sqrt(x*x + y*y);
	}

	public Vertex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vertex that) {
		x += that.x;
		y += that.y;

	}

	public void moveTo(Vertex that) {
		x = that.x;
		y = that.y;
	}

	public Vertex mult(double d) {
		return new Vertex(d * x, d * y);
	}

	//This is a basic lerp function
	//lerp is short for linear interpolation
	//It is used to smoothly move from one point to another
	public static Vertex Lerp(Vertex a, Vertex b, double t){
		return new Vertex(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t);
	}
}
