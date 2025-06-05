package Ajedrez.Piezas;

import java.util.ArrayList;
import java.util.List;

import Ajedrez.Jugador;
import Ajedrez.Tablero;
import javafx.scene.image.Image;

public abstract class Pieza {
	protected final List<Pieza> casillasMover = new ArrayList<Pieza>();
	protected final List<Pieza> casillasAtacar = new ArrayList<Pieza>();
	protected final List<Pieza> casillasProteger = new ArrayList<Pieza>();
	protected final String nombre;
	protected final String notAlgbr;
	protected final int valor;
	protected final Tablero tablero;
	protected final Jugador jugador;
	protected final Jugador enemigo;
	protected final String color;
	protected Image img;
	protected int numMov = 0;
	protected int coorX;
	protected int coorY;
	protected boolean protegida;

	// CONSTRUCTOR PIEZAS
	public Pieza(Tablero tablero, Jugador jugador, Jugador enemigo, String nombre, String notAlgbr, int valor,
			int coorX, int coorY) {
		this.jugador = jugador;
		this.enemigo = enemigo;
		this.nombre = nombre;
		this.notAlgbr = notAlgbr;
		this.valor = valor;
		this.tablero = tablero;
		this.coorX = coorX;
		this.coorY = coorY;
		this.color = jugador.getColor();
		jugador.asignarPieza(this);
	}

	// CONSTRUCTOR CASILLAS VACIAS
	public Pieza(Tablero tablero, int coorX, int coorY) {
		this.coorX = coorX;
		this.coorY = coorY;
		this.nombre = "Casilla";
		this.notAlgbr = " ";
		this.valor = 0;
		this.tablero = tablero;
		this.jugador = null;
		this.enemigo = null;
		this.color = "";
	}

	public void actualizarCoordenadas(int coorX, int coorY) {
		this.coorX = coorX;
		this.coorY = coorY;
	}

	public void revisarCasillasControladas(int i, int j) {
	}

	protected void revisarVector(int i, int j, int vRow, int vCol) {
		for (int row = i + vRow, col = j + vCol; row >= 0 && row < 8 && col >= 0
				&& col < 8; row = row + vRow, col = col + vCol) {
			if (!isCasillaAccesible(row, col))
				break;
		}
	}

	private boolean isCasillaAccesible(int row, int col) {
		Pieza pieza = tablero.getTablero()[row][col];
		if (pieza instanceof Casilla) {
			casillasMover.add(pieza);
		} else {
			if (pieza.jugador == this.jugador) {
				casillasProteger.add(pieza);
			} else {
				casillasAtacar.add(pieza);
			}
			return false;
		}
		return true;
	}

	public String getNotAlgbr() {
		return notAlgbr;
	}

	public List<Pieza> getCasillasMover() {
		return casillasMover;
	}

	public List<Pieza> getCasillasAtacar() {
		return casillasAtacar;
	}

	public List<Pieza> getCasillasProteger() {
		return casillasProteger;
	}

	public String toString() {
		return notAlgbr;
	}

	public boolean isProtegida() {
		return protegida;
	}

	public void setProtegida(boolean protegida) {
		this.protegida = protegida;
	}

	public Image setImg(String imgPath) {
		Image image = null;
		image = new Image(imgPath + ".png");
		return image;
	}

	public Image getImg() {
		return img;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public int getNumMov() {
		return numMov;
	}

	public String getColor() {
		return color;
	}

	public int getCoorX() {
		return coorX;
	}

	public int getCoorY() {
		return coorY;
	}

	public void setNumMov(int numMov) {
		this.numMov += numMov;
		this.jugador.setMoves(numMov);
	}

	public Jugador getEnemigo() {
		return enemigo;
	}
	
	
}
