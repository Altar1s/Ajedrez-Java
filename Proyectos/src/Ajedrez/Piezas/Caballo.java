package Ajedrez.Piezas;

import java.util.ArrayList;
import java.util.List;

import Ajedrez.Jugador;
import Ajedrez.Tablero;

public class Caballo extends Pieza {

	public Caballo(Tablero tablero, Jugador jugador, Jugador enemigo, String nombre, String notAlgbr, int valor,int coorX, int coorY) {
		super(tablero, jugador, enemigo, nombre, notAlgbr, valor, coorX, coorY);
		if(color.equalsIgnoreCase("Blanco")) {
			img = setImg("/img/wn");
		}else if(color.equalsIgnoreCase("Negro")) {
			img = setImg("/img/bn");
		}
	}

	@Override
	public void revisarCasillasControladas(int i, int j) {
		List<Pieza> casillasControladas = new ArrayList<Pieza>();
		for (int row = 0; row < tablero.getNumRows(); row++) {
			for (int col = 0; col < tablero.getNumCols(); col++) {
				if ((row == i - 1 || row == i + 1) && (col == j + 2 || col == j - 2)) {
					casillasControladas.add(tablero.getTablero()[row][col]);
				} else if ((row == i - 2 || row == i + 2) && (col == j + 1 || col == j - 1)) {
					casillasControladas.add(tablero.getTablero()[row][col]);
				}
			}
		}
		for (Pieza casilla : casillasControladas) {
			if (casilla instanceof Casilla) {
				casillasMover.add(casilla);
			} else if (casilla.jugador == this.jugador) {
				casillasProteger.add(casilla);
			} else {
				casillasAtacar.add(casilla);
			}
		}
	}
}
