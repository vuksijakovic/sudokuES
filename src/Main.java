import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Main extends Application{
	private static SudokuGrid sudokuGrid;
	private static int N=9;
	public static void main(String[] args)
	{
		sudokuGrid = new SudokuGrid();
		int niz[] = {0,0,0,1,2,5,4,0,0,0,0,8,4,0,0,0,0,0,4,2,0,8,0,0,0,0,0,0,3,0,0,0,0,0,9,5,0,6,0,9,0,2,0,1,0,5,1,0,0,0,0,0,6,0,0,0,0,0,0,3,0,4,9,0,0,0,0,0,7,2,0,0,0,0,1,2,9,8,0,0,0};
		sudokuGrid.changeGrid(niz);
		launch(args);
	}
	static int broj = 1;
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vbox = new VBox(5);
		Button btn = new Button("Next");
		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(10);
		sudokuGrid.refresh();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				Label lbl = new Label(sudokuGrid.getElement()[i][j].getNumber()+"");
				if(sudokuGrid.getElement()[i][j].getNumber()==0) {
					lbl.setText(sudokuGrid.getElement()[i][j].getUsed()+"");
				}
				VBox vbox1 = new VBox();
				vbox1.getChildren().add(lbl);
				vbox1.setAlignment(Pos.CENTER);
				gp.add(vbox1, j, i);	
			}
		
		}
		
		gp.setGridLinesVisible(true);
		vbox.getChildren().addAll(btn, gp);
		btn.setOnAction(e->{
			System.out.println(broj+".");
			broj++;
			sudokuGrid.proces();
			gp.getChildren().clear();
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					Label lbl = new Label(sudokuGrid.getElement()[i][j].getNumber()+"");
					if(sudokuGrid.getElement()[i][j].getNumber()==0) {
						lbl.setText(sudokuGrid.getElement()[i][j].getUsed()+"");
					}
					VBox vbox1 = new VBox();
					vbox1.getChildren().add(lbl);
					vbox1.setAlignment(Pos.CENTER);
					gp.add(vbox1, j, i);
				}
				
			}
			gp.setGridLinesVisible(true);
		});
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
