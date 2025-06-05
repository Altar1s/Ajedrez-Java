package Ajedrez.Piezas;

import Ajedrez.Jugador;
import Ajedrez.Tablero;

public class Torre extends Pieza {
	private boolean enroque = true;
	public Torre(Tablero tablero, Jugador jugador, Jugador enemigo, String nombre, String notAlgbr, int valor,int coorX, int coorY) {
		super(tablero, jugador, enemigo, nombre, notAlgbr, valor, coorX, coorY);
		if(color.equalsIgnoreCase("Blanco")) {
			img = setImg("/img/wr");
		}else if(color.equalsIgnoreCase("Negro")) {
			img = setImg("/img/br");
		}
	}
	
	@Override
	public void revisarCasillasControladas(int i, int j) {
		revisarVector(i, j, -1, 0);
		revisarVector(i, j, 0, +1);
		revisarVector(i, j, +1, 0);
		revisarVector(i, j, 0, -1);
	}

	public boolean isEnroque() {
		return enroque;
	}

	public void setEnroque(boolean enroque) {
		this.enroque = enroque;
	}

}
