package name.panitz.game2d.corona;

import java.awt.*;

import name.panitz.game2d.AbstractGameObj;
import name.panitz.game2d.Vertex;

class Person extends AbstractGameObj {

	static enum Status {
		ungeimpft(Color.BLUE), geimpft(Color.GREEN), infiziert(Color.RED), genesen(Color.ORANGE), gestorben(Color.BLACK);

		Color c;

		Status(Color c) {
			this.c = c;
		}
	}

	public Status status = Status.ungeimpft;

	public int restInfektionnsDauer;

	Person(Vertex pos) {
		super(pos, new Vertex(Math.random() - 0.7, Math.random() - 0.7), 10, 10);
		if (Math.random() < 0.03)
			infizieren();
	}

	public void paintTo(Graphics g) {
		g.setColor(status.c);
		g.fillRect((int) pos().x, (int) pos().y, (int) width(), (int) height());
	}

	void impfen() {
		if (status == Status.ungeimpft)
			status = Status.geimpft;
	}

	void infizieren() {
		if (status == Status.ungeimpft) {
			status = Status.infiziert;
			restInfektionnsDauer = 800;
		}
	}

	void genesen() {
		if (status == Status.infiziert) {
			if (Math.random() < 0.02) {
				status = Status.gestorben;
				velocity = new Vertex(0, 0);
			} else
				status = Status.genesen;

			restInfektionnsDauer = 0;
		}
	}
}
