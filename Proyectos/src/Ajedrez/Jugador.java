package Ajedrez;

import java.util.ArrayList;
import java.util.List;

import Ajedrez.Piezas.Pieza;
import Ajedrez.Piezas.Rey;

public class Jugador {
	private final List<Pieza> piezas = new ArrayList<Pieza>();
	private final List<Pieza> cementerio = new ArrayList<Pieza>();
	private final String color;
	private int moves = 0;
	private boolean jaqueMate = false;

	public Jugador(String color) {
		this.color = color;
	}

	public void asignarPieza(Pieza pieza) {
		piezas.add(pieza);
	}

	public void mandarPiezaCementerio(Pieza pieza) {
		piezas.remove(pieza);
		cementerio.add(pieza);
	}

	public String getColor() {
		return color;
	}

	public List<Pieza> getPiezas() {
		return piezas;
	}

	public List<Pieza> getCementerio() {
		return cementerio;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves += moves;
	}

	public Pieza getRey() {
		Pieza aux = null;
		for (Pieza pieza : this.piezas) {
			if (pieza instanceof Rey) {
				aux = pieza;
			}
		}
		return aux;
	}

	public void setJaqueMate(boolean jaqueMate) {
		this.jaqueMate = jaqueMate;
	}

	public boolean isJaqueMate() {
		return jaqueMate;
	}
	
	public boolean isJaque() {
		return ((Rey)getRey()).isKingAttacked();
	}

}
