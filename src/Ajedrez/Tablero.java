package Ajedrez;

import java.util.ArrayList;

import Ajedrez.Piezas.Arfil;
import Ajedrez.Piezas.Caballo;
import Ajedrez.Piezas.Casilla;
import Ajedrez.Piezas.Peon;
import Ajedrez.Piezas.Pieza;
import Ajedrez.Piezas.Reina;
import Ajedrez.Piezas.Rey;
import Ajedrez.Piezas.Torre;

public class Tablero {
	private final int numCols = 8;
	private final int numRows = 8;
	private final Jugador blancas = new Jugador("Blanco");
	private final Jugador negras = new Jugador("Negro");
	private final Pieza[][] tablero = new Pieza[numRows][numCols];

	public Tablero() {
		rellenarTablero();
		setProteccionPiezasFalse();
		actualizarControlPiezas();
		actualizarProteccionPiezas();
		actualizarControlRey(blancas);
		actualizarControlRey(negras);
		imprimirTablero();
	}

	// RELLENAR TABLERO
	private void rellenarTablero() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				switch (row) {
				case 0:
					ponerPiezasEspeciales(row, col, negras, blancas);
					break;
				case 1:
					tablero[row][col] = new Peon(this, negras, blancas, "Peon", "P", 1, row, col);
					break;
				case 6:
					tablero[row][col] = new Peon(this, blancas, negras, "Peon", "P", 1, row, col);
					break;
				case 7:
					ponerPiezasEspeciales(row, col, blancas, negras);
					break;
				default:
					tablero[row][col] = new Casilla(this, row, col);
					break;
				}
			}
		}
	}

	// PONER PÎEZAS ESPECIALES
	private void ponerPiezasEspeciales(int i, int j, Jugador jugador, Jugador enemigo) {
		switch (j) {
		case 0, 7:
			tablero[i][j] = new Torre(this, jugador, enemigo, "Torre", "R", 5, i, j);
			break;
		case 1, 6:
			tablero[i][j] = new Caballo(this, jugador, enemigo, "Caballo", "N", 3, i, j);
			break;
		case 2, 5:
			tablero[i][j] = new Arfil(this, jugador, enemigo, "Arfil", "B", 3, i, j);

			break;
		case 3:
			tablero[i][j] = new Reina(this, jugador, enemigo, "Reina", "Q", 9, i, j);
			break;
		case 4:
			tablero[i][j] = new Rey(this, jugador, enemigo, "Rey", "K", 10, i, j);
			break;
		}
	}

	// IMPRIMIR TABLERO
	private void imprimirTablero() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				System.out.print("[" + tablero[row][col] + "]");
			}
			System.out.print("\n");
		}
		System.out.println("=========================");
	}

	private void setProteccionPiezasFalse() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (!(tablero[i][j] instanceof Casilla)) {
					Pieza pieza = tablero[i][j];
					pieza.setProtegida(false);
				}
			}
		}
	}

	private void actualizarControlPiezas() {
		borrarDatosControl(blancas);
		borrarDatosControl(negras);
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (!(tablero[i][j] instanceof Casilla)) {
					Pieza pieza = tablero[i][j];
					pieza.revisarCasillasControladas(i, j);
				}
			}
		}
	}

	private void actualizarControlRey(Jugador jugador) {
		Pieza rey = jugador.getRey();
		rey.getCasillasMover().clear();
		rey.getCasillasProteger().clear();
		rey.getCasillasAtacar().clear();
		rey.revisarCasillasControladas(rey.getCoorX(), rey.getCoorY());
	}

	// BORRA TODOS LOS DATOS DE LAS PIEZAS DE LOS JUGADORES ANTES DE ACTUALIZARLAS
	private void borrarDatosControl(Jugador jugador) {
		for (Pieza pieza : jugador.getPiezas()) {
			pieza.getCasillasMover().clear();
			pieza.getCasillasProteger().clear();
			pieza.getCasillasAtacar().clear();
		}
	}

	// ACTUALIZAR SI LA PIEZA ESTA SIENDO PROTEGIDA O NO
	private void actualizarProteccionPiezas() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Pieza piezaProteger = tablero[i][j];
				for (Pieza pieza : piezaProteger.getCasillasProteger()) {
					pieza.setProtegida(true);
				}
			}
		}
	}

	public boolean checkCasilla(int row, int col, Pieza piezaEsperando) {
		Pieza piezaObjetivo = tablero[row][col];
		int row0 = piezaEsperando.getCoorX();
		int col0 = piezaEsperando.getCoorY();
		// SI LAS COORDENADAS DADAS ESTAN DENTRO DEL RANGO DE CONTROL DE LA PIEZA
		// HACER EL MOVIMIENTO
		if (piezaEsperando.getCasillasMover().contains(piezaObjetivo)
				|| piezaEsperando.getCasillasAtacar().contains(piezaObjetivo)) {
			realizarCambio(row, col, piezaEsperando);
			// DETECTA SI SE QUIERE HACER EL ENROQUE
			if (piezaEsperando instanceof Rey && ((Rey) piezaEsperando).isEnroqued()) {
				if (piezaEsperando.getCoorY() == 2) {
					int rowEnrq = ((Rey) piezaEsperando).getRowEnr();
					Casilla enrAux = new Casilla(this, rowEnrq, 0);
					tablero[rowEnrq][3] = tablero[rowEnrq][0];
					tablero[rowEnrq][0].actualizarCoordenadas(rowEnrq, 3);
					tablero[rowEnrq][0] = enrAux;

				} else if (piezaEsperando.getCoorY() == 6) {
					int rowEnrq = ((Rey) piezaEsperando).getRowEnr();
					Casilla enrAux = new Casilla(this, rowEnrq, 7);
					tablero[rowEnrq][5] = tablero[rowEnrq][7];
					tablero[rowEnrq][7].actualizarCoordenadas(rowEnrq, 5);
					tablero[rowEnrq][7] = enrAux;
				}
			}
			// EN PASSANT
			if (piezaEsperando instanceof Peon && ((Peon) piezaEsperando).isEnPassanted()) {
				Pieza casillaEnPassanted = null;
				for (Pieza piezaAtacada : piezaEsperando.getCasillasAtacar()) {
					if (piezaAtacada instanceof Casilla) {
						casillaEnPassanted = piezaAtacada;
						break;
					}
				}
				int vectorMov = ((Peon) piezaEsperando).getVectorMov();
				Pieza CasillaAux = new Casilla(this, casillaEnPassanted.getCoorX() - vectorMov,
						casillaEnPassanted.getCoorY());
				piezaEsperando.getEnemigo().mandarPiezaCementerio(
						tablero[casillaEnPassanted.getCoorX() - vectorMov][casillaEnPassanted.getCoorY()]);
				tablero[casillaEnPassanted.getCoorX() - vectorMov][casillaEnPassanted.getCoorY()] = CasillaAux;
			}
			setProteccionPiezasFalse();
			actualizarControlPiezas();
			actualizarProteccionPiezas();
			actualizarControlRey(blancas);
			actualizarControlRey(negras);
			// DETECTA SI EL REY ESTA EN JAQUE Y SI ES ASI RESTRINGE EL MOVIMIENTO
			if (isReyEnJaque(piezaEsperando.getJugador())) {
				deshacerMov(row, col, row0, col0, piezaObjetivo, piezaEsperando);
				return false;
			}
			// MANDAR PIEZA AL CEMENTERIO SI NO ES UNA CASILLA
			if (!(piezaObjetivo instanceof Casilla)) {
				piezaObjetivo.getJugador().mandarPiezaCementerio(piezaObjetivo);
			}
			if (isReyEnJaque(piezaEsperando.getEnemigo()) && isReyInJaqueMate(piezaEsperando.getEnemigo())) {
				piezaEsperando.getEnemigo().setJaqueMate(true);
			}
			imprimirTablero();
			return true;
		}
		return false;
	}

	private void realizarCambio(int row, int col, Pieza piezaEsperando) {
		Pieza casillaRemplazo = new Casilla(this, piezaEsperando.getCoorX(), piezaEsperando.getCoorY());
		// REALIZAR EL CAMBIO DE CASILLAS
		tablero[piezaEsperando.getCoorX()][piezaEsperando.getCoorY()] = casillaRemplazo;
		tablero[row][col] = piezaEsperando;
		// ACTUALIZAR DATOS DE PIEZA MOVIDA
		piezaEsperando.actualizarCoordenadas(row, col);
		piezaEsperando.setNumMov(+1);

	}

	private void deshacerMov(int row1, int col1, int row0, int col0, Pieza pieza0, Pieza pieza1) {
		tablero[row1][col1] = pieza0;
		tablero[row0][col0] = pieza1;
		pieza1.actualizarCoordenadas(row0, col0);
		pieza1.setNumMov(-1);
		setProteccionPiezasFalse();
		actualizarControlPiezas();
		actualizarProteccionPiezas();
		actualizarControlRey(blancas);
		actualizarControlRey(negras);
	}

	private boolean isReyEnJaque(Jugador jugador) {
		Pieza rey = jugador.getRey();
		if (((Rey) rey).isKingAttacked()) {
			return true;
		}
		return false;
	}

	private boolean isReyInJaqueMate(Jugador jugador) {
		Pieza rey = jugador.getRey();
		ArrayList<Pieza> movs = new ArrayList<Pieza>();
		ArrayList<Pieza> atacs = new ArrayList<Pieza>();
		for (Pieza pieza : jugador.getPiezas()) {
			int prevRow = pieza.getCoorX();
			int prevCol = pieza.getCoorY();
			movs.addAll(pieza.getCasillasMover());
			atacs.addAll(pieza.getCasillasAtacar());
			for (Pieza mov : movs) {
				int row = mov.getCoorX();
				int col = mov.getCoorY();
				realizarCambio(row, col, pieza);
				setProteccionPiezasFalse();
				actualizarControlPiezas();
				actualizarProteccionPiezas();
				actualizarControlRey(blancas);
				actualizarControlRey(negras);
				if (!((Rey) rey).isKingAttacked()) {
					deshacerMov(row, col, prevRow, prevCol, mov, pieza);
					return false;
				}
				deshacerMov(row, col, prevRow, prevCol, mov, pieza);
			}
			for (Pieza atc : atacs) {
				int row = atc.getCoorX();
				int col = atc.getCoorY();
				realizarCambio(row, col, pieza);
				setProteccionPiezasFalse();
				actualizarControlPiezas();
				actualizarProteccionPiezas();
				actualizarControlRey(blancas);
				actualizarControlRey(negras);
				if (!((Rey) rey).isKingAttacked()) {
					deshacerMov(row, col, prevRow, prevCol, atc, pieza);
					return false;
				}
				deshacerMov(row, col, prevRow, prevCol, atc, pieza);
			}
			movs.clear();
			atacs.clear();
		}
		return true;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public Pieza[][] getTablero() {
		return tablero;
	}

	public Jugador getBlancas() {
		return blancas;
	}

	public Jugador getNegras() {
		return negras;
	}
}
