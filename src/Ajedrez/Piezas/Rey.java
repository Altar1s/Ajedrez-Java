package Ajedrez.Piezas;

import Ajedrez.Jugador;
import Ajedrez.Tablero;

public class Rey extends Pieza {
	private int rowEnr;

	public Rey(Tablero tablero, Jugador jugador, Jugador enemigo, String nombre, String notAlgbr, int valor, int coorX,
			int coorY) {
		super(tablero, jugador, enemigo, nombre, notAlgbr, valor, coorX, coorY);
		if (color.equalsIgnoreCase("Blanco")) {
			img = setImg("/img/wk");
			rowEnr = 7;
		} else if (color.equalsIgnoreCase("Negro")) {
			img = setImg("/img/bk");
			rowEnr = 0;
		}
	}

	@Override
	public void revisarCasillasControladas(int i, int j) {
		for (int row = 0; row < tablero.getNumRows(); row++) {
			for (int col = 0; col < tablero.getNumCols(); col++) {
				Pieza pieza = tablero.getTablero()[row][col];
				if ((Math.abs(i - row) == 1 || i == row) && (Math.abs(j - col) == 1 || j == col)
						&& !(i == row && j == col)) {
					if (pieza instanceof Casilla && isCasillaSegura(row, col)) {
						casillasMover.add(pieza);
					} else if (pieza.jugador == this.jugador) {
						casillasProteger.add(pieza);
					} else if (!pieza.isProtegida() && pieza.getJugador() == enemigo) {
						casillasAtacar.add(pieza);
					}
				}
			}
		}
		if (isEnroquePosible(tablero.getTablero()[rowEnr][0]) && casillasMover.contains(tablero.getTablero()[rowEnr][3])
				&& isCasillaSegura(rowEnr, 2)) {
			casillasMover.add(tablero.getTablero()[rowEnr][2]);
		}
		if (isEnroquePosible(tablero.getTablero()[rowEnr][7]) && casillasMover.contains(tablero.getTablero()[rowEnr][5])
				&& isCasillaSegura(rowEnr, 6)) {
			casillasMover.add(tablero.getTablero()[rowEnr][6]);
		}
	}

	public boolean isEnroquePosible(Pieza torre) {
		return !isKingAttacked() && numMov == 0 && torre.getNumMov() == 0 && torre.getCasillasProteger().contains(this);
	}

	public boolean isCasillaSegura(int row, int col) {
		for (Pieza piezaEnemiga : enemigo.getPiezas()) {
			if (!(piezaEnemiga instanceof Peon)) {
				if (piezaEnemiga.getCasillasMover().contains(tablero.getTablero()[row][col])) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isKingAttacked() {
		for (Pieza piezaEnemiga : enemigo.getPiezas()) {
			if (piezaEnemiga.getCasillasAtacar().contains(this)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEnroqued() {
		return coorX == rowEnr && (coorY == 2 || coorY == 6) && numMov == 1;
	}

	public int getRowEnr() {
		return rowEnr;
	}
}
