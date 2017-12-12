package menu;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import game.GameController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMenu extends Application {

	private GM gm;
	private Scene scene;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		Pane root = new Pane();
		root.setPrefSize(800, 600);

		InputStream is = Files.newInputStream(Paths.get("res/images/battleships.jpg"));
		Image img = new Image(is);
		is.close();

		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);

		gm = new GM();

		root.getChildren().addAll(imgView, gm);

		scene = new Scene(root);

		scene.setOnKeyPressed(event -> {

			if (event.getCode() == KeyCode.ESCAPE) {
				if (!gm.isVisible()) {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gm);
					ft.setFromValue(0);
					ft.setToValue(1);
					gm.setVisible(true);
					ft.play();
				} else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gm);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> gm.setVisible(false));
					ft.play();
				}
			}
		});

		primaryStage.setTitle("Battleships");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private class GM extends Parent {
		public GM() {

			VBox menu0 = new VBox(15);
			VBox menu1 = new VBox(15);

			menu0.setTranslateX(50);
			menu0.setTranslateY(220);
			menu1.setTranslateX(50);
			menu1.setTranslateY(220);

			final int offset = 400;

			menu1.setTranslateX(offset);

			MenuButton btnSinglePlayer = new MenuButton(" SINGLEPLAYER");
			btnSinglePlayer.setOnMouseClicked(event -> {
				GameController game = new GameController(controllerEvent -> {
					primaryStage.setScene(scene);
				});
				try {
					Scene temp = game.getScene();
					primaryStage.setScene(temp);
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Failed to launch game");
				}
			});
			MenuButton btnMultiPlayer = new MenuButton(" MULTIPLAYER");
			btnMultiPlayer.setOnMouseClicked(event -> {
				StackPane secondaryLayout = new StackPane();
				secondaryLayout.getChildren().addAll(new Label("Coming Soon.."));

				Scene secondScene = new Scene(secondaryLayout, 600, 800);
				Stage secondStage = new Stage();

				secondStage.setTitle("Multi Player Mode");
				secondStage.setScene(secondScene);
				secondStage.show();
			});

			MenuButton btnExit = new MenuButton(" EXIT");
			btnExit.setOnMouseClicked(event -> {
				Stage stage = new Stage();

				VBox layout = new VBox(10);
				layout.setAlignment(Pos.CENTER);
				Label question = new Label("Are You Sure You Want to Exit?");

				Button yesBtn = new Button("Yes");
				yesBtn.setOnAction(yesEvent -> {
					Platform.exit();
				});

				Button noBtn = new Button("No");

				noBtn.setOnAction(noEvent -> {
					stage.close();
				});
				HBox buttonBox = new HBox(10);
				buttonBox.setAlignment(Pos.CENTER);

				buttonBox.getChildren().addAll(yesBtn, noBtn);
				layout.getChildren().addAll(question, buttonBox);

				Scene resultScene = new Scene(layout, 200, 100);

				stage.setTitle("Exit");
				stage.setScene(resultScene);
				stage.show();

			});

			menu0.getChildren().addAll(btnSinglePlayer, btnMultiPlayer, btnExit);

			Rectangle bg = new Rectangle(800, 600);
			bg.setFill(Color.GRAY);
			bg.setOpacity(0.35);

			this.getChildren().addAll(bg, menu0);
		}

	}

	private static class MenuButton extends StackPane {
		private Text text;

		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(Font.font(20));
			text.setFill(Color.WHITE);

			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));

			this.setAlignment(Pos.CENTER_LEFT);
			this.setRotate(-0.5);
			this.getChildren().addAll(bg, text);

			this.setOnMouseEntered(event -> {
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});

			this.setOnMouseExited(event -> {
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});

			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());

			this.setOnMousePressed(event -> setEffect(drop));
			this.setOnMouseReleased(event -> setEffect(null));
		}

	}

	public static void main(String args[]) {
		launch(args);
	}
}