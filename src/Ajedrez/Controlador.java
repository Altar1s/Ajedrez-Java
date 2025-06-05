package Ajedrez;

import java.util.List;

import Ajedrez.Piezas.Pieza;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controlador {

	private Tablero tablero;
	private Game game;

	@FXML
	private GridPane tableroChess;

	// ASIGNAR EVENTHANDLERS A LOS LABELS CUANDO SE CREA EL TABLERO
	private void setEventHandlersVentana() {
		for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
			for (int colIndex = 0; colIndex < 8; colIndex++) {
				Label label = (Label) getCasillaVentana(rowIndex, colIndex);
				label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
						seleccionar(event);
					}
					if (event.getButton() == MouseButton.SECONDARY) {
						System.out.println("a");
					}
				});
			}
		}
	}

	// INSERTAR LAS IMAGENES EN LOS LABELS ACUERDO AL TABLERO
	public void drawPiezasVentana() {
		for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
			for (int colIndex = 0; colIndex < 8; colIndex++) {
				Pieza pieza = tablero.getTablero()[rowIndex][colIndex];
				Label label = (Label) getCasillaVentana(rowIndex, colIndex);
				resetColor(label);
				ImageView imgvw = new ImageView(pieza.getImg());
				imgvw.setFitWidth(100);
				imgvw.setFitHeight(100);
				label.setGraphic(imgvw);
			}
		}
	}

	// MANDA AL JUEGO LAS COORDENADAS DEL LABEL CUANDO SE LE DA CLICK IZQ
	private void seleccionar(MouseEvent event) {
		Node source = (Node) event.getSource();
		Integer columnIndex = GridPane.getColumnIndex(source);
		Integer rowIndex = GridPane.getRowIndex(source);
		if (columnIndex == null) {
			columnIndex = 0;
		}
		if (rowIndex == null) {
			rowIndex = 0;
		}
		if (game.comprobarCoor(rowIndex, columnIndex)) {
			quitarEventHandlers();
		}
	}

	private void quitarEventHandlers() {
		for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
			for (int colIndex = 0; colIndex < 8; colIndex++) {
				Label label = (Label) getCasillaVentana(rowIndex, colIndex);
				Label newLabel = new Label();
				newLabel.setStyle(label.getStyle());
				newLabel.setGraphic(label.getGraphic());
				tableroChess.getChildren().remove(label);
				tableroChess.add(newLabel, colIndex, rowIndex);
			}
		}
	}

	// OBTENER UNA NODE DEL GRIDPANE SEGUN LAS COORDENADAS QUE LE PASES
	private Node getCasillaVentana(int row, int col) {
		Node casillaTablero = null;
		List<Node> casillasTablero = tableroChess.getChildren();
		for (Node node : casillasTablero) {
			Integer rowIndex = GridPane.getRowIndex(node);
			Integer colIndex = GridPane.getColumnIndex(node);
			if (rowIndex == null)
				rowIndex = 0;
			if (colIndex == null)
				colIndex = 0;
			if (rowIndex == row && colIndex == col) {
				casillaTablero = node;
				break;
			}
		}
		return casillaTablero;
	}

	// INICIALIZA EL TABLERO
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
		drawPiezasVentana();
	}

	// INICIALIZA EL JUEGO
	public void setGame(Game game) {
		this.game = game;
		setEventHandlersVentana();
	}

	// RECIBE COORDENADAS DEL MAIN Y MANDA A COLOREAR LAS CASILLAS
	public void colorearCasillasAnteriores(Integer row0, Integer col0, Integer row1, Integer col1, Integer rowSelected,
			Integer colSelected) {
		drawPiezasVentana();
		pintarCasillaVentana(row0, col0);
		pintarCasillaVentana(row1, col1);
		pintarCasillaVentana(rowSelected, colSelected);
		
	}

	// AGARRA LA CASILLA DE LAS COORDENADAS Y LO MANDA A PINTAR Y LO REEMPLAZA EN EL
	// GRIDPANE
	private void pintarCasillaVentana(Integer row, Integer col) {
		Node aux = null;
		if (!(row == null && col == null)) {
			aux = getCasillaVentana(row, col);
			tableroChess.getChildren().remove(aux);
			cambiarColor(aux);
			tableroChess.add(aux, col, row);
		}
	}

	// PINTA LA CASILLA DEPENDIENDO DE SU COLOR
	private void cambiarColor(Node casilla) {
		if (casilla.getStyle().equals("-fx-background-color: #edd6b0;")) {
			casilla.setStyle("-fx-background-color: #F5EA78;");
		} else if (casilla.getStyle().equals("-fx-background-color: #b88762;")) {
			casilla.setStyle("-fx-background-color: #DBC34F;");
		}
	}

	// CUANDO SE DIBUJA EL TABLERO ESTO DEVUELVE TODAS LAS CASILLAS A SU COLOR
	// ORIGINAL
	private void resetColor(Label label) {
		if (label.getStyle().equals("-fx-background-color: #F5EA78;")
				|| label.getStyle().equals("-fx-background-color: #EB6F5D;")) {
			label.setStyle("-fx-background-color: #edd6b0;");
		} else if (label.getStyle().equals("-fx-background-color: #DBC34F;")
				|| label.getStyle().equals("-fx-background-color: #EB6F5C;")) {
			label.setStyle("-fx-background-color: #b88762;");
		}
	}

	public void colorearJaque(int coorX, int coorY) {
		Label label = (Label) getCasillaVentana(coorX, coorY);
		if (label.getStyle().equals("-fx-background-color: #edd6b0;")) {
			label.setStyle("-fx-background-color: #EB6F5D;");
		} else if (label.getStyle().equals("-fx-background-color: #b88762;")) {
			label.setStyle("-fx-background-color: #EB6F5C;");
		}
	}
}
