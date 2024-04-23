package Ajedrez.Piezas;

import Ajedrez.Jugador;
import Ajedrez.Tablero;

public class Peon extends Pieza {
	private final int vectorMov;
	private final int promocionRow;
	private int lastMoveTurn;
	private int rowEnPassantEnemigo;
	
	public Peon(Tablero tablero, Jugador jugador, Jugador enemigo, String nombre, String notAlgbr, int valor, int coorX,
			int coorY) {
		super(tablero, jugador, enemigo, nombre, notAlgbr, valor, coorX, coorY);
		if (color.equalsIgnoreCase("Blanco")) {
			img = setImg("/img/wp");
			vectorMov = -1;
			rowEnPassantEnemigo = 3;
			promocionRow = 0;
		} else {
			img = setImg("/img/bp");
			vectorMov = +1;
			rowEnPassantEnemigo = 4;
			promocionRow = 7;
		}
	}

	@Override
	public void revisarCasillasControladas(int i, int j) {
		for (int row = 0; row < tablero.getNumRows(); row++) {
			for (int col = 0; col < tablero.getNumCols(); col++) {
				Pieza pieza = tablero.getTablero()[row][col];
				if (row == i + vectorMov && (col == j - 1 || col == j + 1)) {
					if (pieza.jugador == this.jugador) {
						casillasProteger.add(pieza);
					} else if (!(pieza instanceof Casilla)) {
						casillasAtacar.add(pieza);
					}
				} else if (col == j && (row == i + vectorMov || (row == i + (vectorMov * 2) && numMov == 0))
						&& pieza instanceof Casilla) {
					casillasMover.add(pieza);
				} else if ((row == i) && (col == j - 1 || col == j + 1) && pieza instanceof Peon
						&& pieza.getJugador() == enemigo && isEnPassantPossible(pieza)) {
					casillasAtacar.add(tablero.getTablero()[(pieza.getCoorX() + vectorMov)][pieza.getCoorY()]);
				}
			}
		}
	}

	@Override
	public void actualizarCoordenadas(int coorX, int coorY) {
		this.coorX = coorX;
		this.coorY = coorY;
		this.lastMoveTurn = jugador.getMoves();
	}

	public boolean isEnPassantPossible(Pieza pieza) {
		Peon peon = (Peon) pieza;
		return peon.numMov == 1 && peon.coorX == rowEnPassantEnemigo && Math.abs(peon.getLastMoveTurn() - jugador.getMoves()) == 1;
	}

	public int getLastMoveTurn() {
		return lastMoveTurn;
	}

	public boolean isEnPassanted() {
		for (Pieza piezaAtacada : casillasAtacar) {
			if (piezaAtacada instanceof Casilla && piezaAtacada.getCoorX() == coorX
					&& piezaAtacada.getCoorY() == coorY) {
				return true;
			}
		}
		return false;
	}

	public int getVectorMov() {
		return vectorMov;
	}
	
	
}
