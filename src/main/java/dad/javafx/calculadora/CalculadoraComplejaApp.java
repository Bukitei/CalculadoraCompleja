package dad.javafx.calculadora;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraComplejaApp extends Application {

	private Label primer, segundo, tercero, i1, i2, i3;
	private ComboBox<String> operacionCombo;
	private TextField p1Text, p2Text, s1Text, s2Text, t1Text, t2Text;
	private Separator separador;
	private HBox operaciones, operaciones2, resultado;
	private VBox operacional, matematicasHijo;
	private StringProperty operacion = new SimpleStringProperty();
	private Complejos operacion1 = new Complejos();
	private Complejos operacion2 = new Complejos();
	private Complejos result = new Complejos();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Declaraciones
		
		primer = new Label("+");
		segundo = new Label("+");
		tercero = new Label("+");
		i1 = new Label("i");
		i2 = new Label("i");
		i3 = new Label("i");
		
		operacionCombo = new ComboBox<String>();
		operacionCombo.getItems().addAll("+", "-", "*", "/");
		
		
		p1Text = new TextField();
		p1Text.setPrefColumnCount(4);
		
		p2Text = new TextField();
		p2Text.setPrefColumnCount(4);
		
		s1Text = new TextField();
		s1Text.setPrefColumnCount(4);
		
		s2Text = new TextField();
		s2Text.setPrefColumnCount(4);
		
		t1Text = new TextField();
		t1Text.setDisable(true);
		t1Text.setPrefColumnCount(4);
		
		t2Text = new TextField();
		t2Text.setDisable(true);
		t2Text.setPrefColumnCount(4);
		
		
		separador = new Separator();
		
		
		operaciones = new HBox(5, p1Text, primer, p2Text, i1);
		operaciones.setAlignment(Pos.CENTER);
		operaciones2 = new HBox(5, s1Text, segundo, s2Text, i2);
		operaciones2.setAlignment(Pos.CENTER);
		resultado = new HBox(5, t1Text, tercero, t2Text, i3);
		resultado.setAlignment(Pos.CENTER);
		
		
		
		operacional = new VBox(operacionCombo);
		operacional.setAlignment(Pos.CENTER);
		matematicasHijo = new VBox(operaciones, operaciones2, separador, resultado);
		matematicasHijo.setAlignment(Pos.CENTER);
		
		HBox root = new HBox(5, operacional, matematicasHijo);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		//Bindings
		
		p1Text.textProperty().bindBidirectional(operacion1.realProperty(), new NumberStringConverter());
		p2Text.textProperty().bindBidirectional(operacion1.imaginarioProperty(), new NumberStringConverter());
		s1Text.textProperty().bindBidirectional(operacion2.realProperty(), new NumberStringConverter());
		s2Text.textProperty().bindBidirectional(operacion2.imaginarioProperty(), new NumberStringConverter());
		t1Text.textProperty().bindBidirectional(result.realProperty(), new NumberStringConverter());
		t2Text.textProperty().bindBidirectional(result.imaginarioProperty(), new NumberStringConverter());
		operacion.bind(operacionCombo.getSelectionModel().selectedItemProperty());
		
		operacion.addListener((o, ov, nv) -> onOperacionChanged(nv) );
		
		operacionCombo.getSelectionModel().selectFirst();
		
	}
	
	private void onOperacionChanged(String nv) {
		switch(nv) {
		case "+": result.realProperty().bind(operacion1.realProperty().add(operacion2.realProperty()));
		result.imaginarioProperty().bind(operacion1.imaginarioProperty().add(operacion2.imaginarioProperty()));
		break;
		case "-": result.realProperty().bind(operacion1.realProperty().subtract(operacion2.realProperty()));
		result.imaginarioProperty().bind(operacion1.imaginarioProperty().subtract(operacion2.imaginarioProperty()));
		break;
		case "*": result.realProperty().bind(operacion1.realProperty().multiply(operacion2.realProperty()).subtract(operacion1.imaginarioProperty().multiply(operacion2.imaginarioProperty())));
		result.imaginarioProperty().bind(operacion1.realProperty().multiply(operacion2.imaginarioProperty()).add(operacion1.imaginarioProperty().multiply(operacion2.realProperty())));
		break;
		case "/": result.realProperty().bind(operacion1.realProperty().multiply(operacion2.realProperty()).add(operacion1.imaginarioProperty().multiply(operacion2.imaginarioProperty())).divide(operacion2.realProperty().multiply(operacion2.realProperty()).add(operacion2.imaginarioProperty().multiply(operacion2.imaginarioProperty()))));
		result.imaginarioProperty().bind(operacion1.imaginarioProperty().multiply(operacion2.realProperty()).subtract(operacion1.realProperty().multiply(operacion2.imaginarioProperty())).divide(operacion2.realProperty().multiply(operacion2.realProperty()).add(operacion2.imaginarioProperty().multiply(operacion2.imaginarioProperty()))));
		break;
		}
	}

	public static void main(String[] args) {
		
		launch(args);
	}

}
