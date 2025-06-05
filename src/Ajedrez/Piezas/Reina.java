package Ajedrez.Piezas;

import Ajedrez.Jugador;
import Ajedrez.Tablero;

public class Reina extends Pieza {

	public Reina(Tablero tablero, Jugador jugador, Jugador enemigo, String nombre, String notAlgbr, int valor,int coorX, int coorY) {
		super(tablero, jugador, enemigo, nombre, notAlgbr, valor, coorX, coorY);
		if(color.equalsIgnoreCase("Blanco")) {
			img = setImg("/img/wq");
		}else if(color.equalsIgnoreCase("Negro")) {
			img = setImg("/img/bq");
		}
	}
	
	@Override
	public void revisarCasillasControladas(int i, int j) {
		revisarVector(i, j, -1, 0);
		revisarVector(i, j, -1, +1);
		revisarVector(i, j, 0, +1);
		revisarVector(i, j, +1, +1);
		revisarVector(i, j, +1, 0);
		revisarVector(i, j, +1, -1);
		revisarVector(i, j, 0, -1);
		revisarVector(i, j, -1, -1);
	}
}
