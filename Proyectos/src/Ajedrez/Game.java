package Ajedrez;

import Ajedrez.Piezas.Pieza;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Game extends Application {
	private final Tablero tablero = new Tablero();
	private Controlador controlador;
	private Pieza piezaEsperando;
	private boolean turno = true;
	private boolean isPiezaSelected = false;
	private Integer row0 = null;
	private Integer col0 = null;
	private Integer row1 = null;
	private Integer col1 = null;
	private Integer rowSelected = null;
	private Integer colSelected = null;

	// LLAMAR A LA FUNCION START
	public static void main(String[] args) {
		launch(args);
	}

	// INICIAR LA VENTANA CON TODOS SUS COMPONENTES
	@Override
	public void start(Stage escenario) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
		Parent root = loader.load();
		controlador = loader.getController();
		controlador.setTablero(tablero);
		controlador.setGame(this);
		Scene escena = new Scene(root);
		escenario.setScene(escena);
		escenario.setResizable(false);
		Image icon = new Image("icon.png");
		escenario.getIcons().add(icon);
		escenario.setTitle("Ajedrez");
		escenario.show();
	}

	// LOGICA DEL JUEGO
	public boolean comprobarCoor(int row, int col) {
		Pieza pieza = tablero.getTablero()[row][col];
		String color = pieza.getColor();
		// que solo pueda seleccionar un color de fichas dependiendo del turno
		if (turno && color.equalsIgnoreCase("Blanco") || !turno && color.equalsIgnoreCase("Negro")) {
			piezaEsperando = tablero.getTablero()[row][col];
			isPiezaSelected = true;
			rowSelected = row;
			colSelected = col;
			controlador.colorearCasillasAnteriores(row0, col0, row1, col1, rowSelected, colSelected);
		}
		// hacer movimiento consecuente a la seleccion
		if (turno && !color.equalsIgnoreCase("Blanco") && isPiezaSelected
				|| !turno && !color.equalsIgnoreCase("Negro") && isPiezaSelected) {
			if (tablero.checkCasilla(row, col, piezaEsperando)) {
				row0 = rowSelected;
				col0 = colSelected;
				row1 = row;
				col1 = col;
				rowSelected = null;
				colSelected = null;
				controlador.drawPiezasVentana();
				controlador.colorearCasillasAnteriores(row0, col0, row1, col1, rowSelected, colSelected);
				if (turno) {
					turno = false;
				} else {
					turno = true;
				}
			} else {
				rowSelected = null;
				colSelected = null;
				controlador.colorearCasillasAnteriores(row0, col0, row1, col1, rowSelected, colSelected);
			}
			isPiezaSelected = false;
		}
		//PINTA AL REY DE ROJO SI ESTA EN JAQUE
		if (tablero.getBlancas().isJaque() || tablero.getNegras().isJaque()) {
			Jugador jugador = tablero.getBlancas().isJaque()? tablero.getBlancas():tablero.getNegras();
			controlador.colorearJaque(jugador.getRey().getCoorX(),jugador.getRey().getCoorY());
		}
		//SI HAY JAQUE MATE CONGELA EL TABLERO
		if (tablero.getBlancas().isJaqueMate() || tablero.getNegras().isJaqueMate()) {
			return true;
		}
		return false;
	}
}
