package name.panitz.game2d;

public class Vertex {
	public double x;
	public double y;

	public double magnitude(){
		return Math.sqrt(x*x + y*y);
	}

	public Vertex(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vertex(Vertex vertex) {
		this.x = vertex.x;
		this.y = vertex.y;
	}

	public void add(Vertex that) {
		x += that.x;
		y += that.y;
	}

	public static Vertex add(Vertex v1, Vertex v2){
		return new Vertex(v1.x + v2.x, v1.y + v2.y);
	}
	public static Vertex sub(Vertex v1, Vertex v2){
		return new Vertex(v1.x - v2.x, v1.y - v2.y);
	}

	public void moveTo(Vertex that) {
		x = that.x;
		y = that.y;
	}

	public Vertex mult(double d) {
		return new Vertex(d * x, d * y);
	}

	@Override
	public String toString() {
		return "Vertex{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	//This is a basic lerp function
	//lerp is short for linear interpolation
	//It is used to smoothly move from one point to another
	public static Vertex Lerp(Vertex a, Vertex b, double t){
		return new Vertex(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t);
	}
	public Vertex normalized(){
		return new Vertex(this).mult(1d / this.magnitude());
	}
	public void Normalize(){
		var n = this.normalized();
		x = n.x;
		y = n.y;
	}
}
