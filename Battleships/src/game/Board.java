package game;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {

	private VBox rows = new VBox();
	private VBox layout = new VBox();
	private boolean enemy = false;
	public int ships = 5;

	public Board(boolean enemy, EventHandler<? super MouseEvent> handler) {
		Label label;
		if (enemy) {
			label = new Label("Enemy Board");
		} else {
			label = new Label("Your Board");
		}

		// Make a 10 x 10 grid of cells
		this.enemy = enemy;
		for (int y = 0; y < 10; y++) {
			HBox row = new HBox();
			for (int x = 0; x < 10; x++) {
				Cell c = new Cell(x, y, this, enemy);
				c.setOnMouseClicked(handler);
				row.getChildren().add(c);
			}
			rows.getChildren().add(row);
		}
		layout.getChildren().addAll(label, rows);
		getChildren().addAll(layout);
	}

	public boolean placeShip(Ship ship, int x, int y) {
		if (canPlaceShip(ship, x, y)) {
			int length = ship.type;

			if (ship.vertical) {
				for (int i = y; i < y + length; i++) {
					Cell cell = getCell(x, i);
					cell.ship = ship;
					if (!enemy) {
						cell.setFill(Color.WHITE);
						cell.setStroke(Color.GREEN);
					}
				}
			} else {
				for (int i = x; i < x + length; i++) {
					Cell cell = getCell(i, y);
					cell.ship = ship;
					if (!enemy) {
						cell.setFill(Color.WHITE);
						cell.setStroke(Color.GREEN);
					}
				}
			}
			return true;
		}
		return false;
	}

	public Cell getCell(int x, int y) {
		return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
	}

	private Cell[] getNeighbors(int x, int y) {
		Point2D[] points = new Point2D[] { new Point2D(x - 1, y), new Point2D(x + 1, y), new Point2D(x, y - 1),
				new Point2D(x, y + 1) };

		List<Cell> neighbors = new ArrayList<Cell>();

		for (Point2D p : points) {
			if (isValidPoint(p)) {
				neighbors.add(getCell((int) p.getX(), (int) p.getY()));
			}
		}
		return neighbors.toArray(new Cell[0]);
	}

	private boolean canPlaceShip(Ship ship, int x, int y) {
		int length = ship.type;

		if (ship.vertical) {
			for (int i = y; i < y + length; i++) {
				if (!isValidPoint(x, i))
					return false;

				Cell cell = getCell(x, i);
				if (cell.ship != null)
					return false;

				for (Cell neighbor : getNeighbors(x, i)) {
					if (!isValidPoint(x, i))
						return false;

					if (neighbor.ship != null)
						return false;
				}
			}
		} else {
			for (int i = x; i < x + length; i++) {
				if (!isValidPoint(i, y))
					return false;

				Cell cell = getCell(i, y);
				if (cell.ship != null)
					return false;

				for (Cell neighbor : getNeighbors(i, y)) {
					if (!isValidPoint(i, y))
						return false;

					if (neighbor.ship != null)
						return false;
				}
			}
		}
		return true;
	}

	private boolean isValidPoint(Point2D point) {
		return isValidPoint(point.getX(), point.getY());
	}

	private boolean isValidPoint(double x, double y) {
		return x >= 0 && x < 10 && y >= 0 && y < 10;
	}

	public class Cell extends Rectangle {
		public int x, y;
		public Ship ship = null;
		public boolean wasShot = false;

		private Board board;
		private boolean isEnemy;

		public Cell(int x, int y, Board board, boolean isEnemy) {
			super(30, 30);
			this.x = x;
			this.y = y;
			this.board = board;
			this.isEnemy = isEnemy;
			setFill(Color.LIGHTGRAY);
			setStroke(Color.BLACK);

			this.setOnMouseEntered(event -> {
				if (getFill().equals(Color.LIGHTGREY)) {
					setFill(new Color(0.7, 0.7, 0.7, 1.0));
				}
				setStroke(Color.WHITE);
			});

			this.setOnMouseExited(event -> {
				if (getFill().equals(new Color(0.7, 0.7, 0.7, 1.0))) {
					setFill(Color.LIGHTGRAY);
				}
				setStroke(Color.BLACK);
			});
		}

		public boolean shoot() {
			wasShot = true;
			setFill(Color.BLACK);

			if (ship != null) {
				ship.hit();
				setFill(Color.RED);
				if (!ship.isAlive()) {
					if(!isEnemy) {
						ship.getGC().generateText(ship.shipHitResponse());
					}
					board.ships--;
					// display text saying ship was destroyed.
				}
				return true;
			}
			return false;
		}
	}
	//efsfsdfds
}