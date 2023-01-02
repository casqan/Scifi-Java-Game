package name.panitz.game2d.corona;

import java.util.List;

import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.ImageObject;
import name.panitz.game2d.Vertex;

import java.util.ArrayList;
import java.awt.event.*;
import static java.awt.event.KeyEvent.*;

record Corona(GameObj player, List<List<? extends GameObj>> goss, int width, int height, List<Person> personen)
		implements Game {

	Corona() {
		this(new ImageObject(new Vertex(200, 200), new Vertex(0, 0), "spritze.png"), new ArrayList<>(), 1200, 700,
				new ArrayList<>());
	}

	public void init() {
		goss().clear();
		personen().clear();
		goss.add(personen());
		for (int i = 0; i < 500; i++) {
			personen().add(new Person(new Vertex(Math.random() * (width() - 20), Math.random() * (height() - 20))));
		}
	}

	public void keyPressedReaction(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case VK_RIGHT -> player().velocity().add(new Vertex(0.2, 0));
		case VK_LEFT -> player().velocity().add(new Vertex(-0.2, 0));
		case VK_DOWN -> player().velocity().add(new Vertex(0, 0.2));
		case VK_UP -> player().velocity().add(new Vertex(0, -0.2));
		}
	}

	public void doChecks() {
		for (var m1 : personen()) {

			if (m1.pos().x < 0 || m1.pos().x + m1.width() > width()) {
				m1.velocity().x *= -1;
			}
			if (m1.pos().y < 0 || m1.pos().y + m1.height() > height()) {
				m1.velocity().y *= -1;
			}

			if (m1.touches(player()))
				m1.impfen();

			if (m1.status == Person.Status.infiziert) {
				m1.restInfektionnsDauer--;
				if (m1.restInfektionnsDauer == 0)
					m1.genesen();
			}

			for (var m2 : personen())
				if (m1.touches(m2) && m1.status == Person.Status.infiziert)
					m2.infizieren();
		}
	}

	// public void move(){if (status!=Status.infiziert) super.move();}

	public static void main(String[] args) {
		new Corona().play();
	}

	@Override
	public boolean won() {
		return false;
	}

	@Override
	public boolean lost() {
		return false;
	}
}
