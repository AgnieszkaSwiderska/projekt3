package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Lokalizacja;
import model.Person;
import model.Room;

public class MainWindowController {
	private Main main;
	private Stage primaryStage;
	
	
	@FXML private TableView<Person> tableView;
	@FXML private TableColumn<Person, String> firstNameColumn;
	@FXML private TableColumn<Person, String> lastNameColumn;
	@FXML private TableColumn<Person, String> pokojColumn;
	
	@FXML private TextField imieText;
	@FXML private TextField nazwiskoText; 
	@FXML private TextField nrPokojuText;
	@FXML private TextField hourStartText;
	@FXML private TextField hourStopText;
	@FXML private Label errorLabel;
	
	@FXML private AnchorPane root;
	
	Path path = new Path();

	
	private ObservableList<Person> personList = 
			FXCollections.observableArrayList();
	
	
	private void setTable(){
		personList.add(new Person("Czes³aw","Ga³czyñski","4","10:00","16:00"));
		personList.add(new Person("Jan","Kowalski","14","10:00","14:30"));
		personList.add(new Person("Tadeusz","Stefañski","1","12:30","18:00"));
		personList.add(new Person("Tomasz","Kotowski","10","9:00","13:30"));
		personList.add(new Person("Anna","Lasota","8","9:00","12:20"));
		personList.add(new Person("Andrzej","Wo¿niak","21","7:15","14:00"));
		personList.add(new Person("Piotr","Rataj","13","9:40","15:30"));
		
	}
	
	public void setMain(Main main, Stage primaryStage){
		this.main=main;	
		this.primaryStage=primaryStage;
		
		primaryStage.setTitle("INU praca domowa nr 3 -- Agnieszka Œwiderska");
		setTable();
		tableView.setItems(personList);
		
		path.setStrokeWidth(8);
		path.setStroke(Color.RED);
		root.getChildren().add(path);
		errorLabel.setText("");

	}
	
	public void initialize(){
		firstNameColumn.setCellValueFactory(new PropertyValueFactory <Person, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory <Person, String>("lastName"));
		pokojColumn.setCellValueFactory(new PropertyValueFactory <Person, String>("pokoj"));
	
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(ov,oldVal, newVal) -> {
					//System.out.println(newVal.getFirstName() + " " + newVal.getLastName())
					
					errorLabel.setText("");

					if(!tableView.getSelectionModel().isEmpty())
					{
						imieText.setText(newVal.getFirstName());
						nazwiskoText.setText(newVal.getLastName());
						nrPokojuText.setText(newVal.getPokoj());
						hourStartText.setText(newVal.getHourStart());
						hourStopText.setText(newVal.getHourStop());
						
						//plus rysowanie na obrazku
						zaznaczPokoj(nrPokojuText.getText());
					}
				});
	}
	
	
	@FXML
	public void wczytajPlik(){
		
		Scanner in=null;
		errorLabel.setText("");
		
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Otwórz z pliku");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki tekstowe","*.txt"));
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			
			if(selectedFile != null)
			{
				//System.out.println("Otwórz z pliku " + selectedFile);
				tableView.getSelectionModel().clearSelection();
				personList.clear();
				path.getElements().clear();

				in = new Scanner(selectedFile);

				while(in.hasNext())
				{
					personList.add(new Person(in.next(),in.next(),in.next(),in.next(),in.next()));
				}
			}
						
		}
		catch (IOException e)
		
		{
			e.printStackTrace();
		}
		finally
		{
			if (in!=null)
			{
				in.close();
			}
		}
		
	}
		
	@FXML
	public void zapiszPlik(){
			
		PrintWriter out=null;

		try
		{
			//out=new PrintWriter("D:\\Java\\PracownicyOUT.txt");
			String lineEnd = System.getProperty("line.separator"); //konieczne dla Windowsa pod UNIX wystarczy dodac na koncu \n
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Zapisz do pliku");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki tekstowe","*.txt"));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
			
			if(selectedFile != null)
			{
				//System.out.println("Zapis do pliku " + selectedFile);
				out = new PrintWriter(selectedFile);
				
				for (int i=0; i<personList.size(); i++)
				{
					out.printf(
						"%s %s %s %s %s" + lineEnd, 
						personList.get(i).getFirstName(), personList.get(i).getLastName(), personList.get(i).getPokoj(), personList.get(i).getHourStart(), personList.get(i).getHourStop()
						);
				}
				errorLabel.setText("Zapisano");
			}
			else
				errorLabel.setText("Nie wybrano pliku !");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (out!=null)
			{
				out.close();
			}
		}
	}
	
	@FXML
	public void generujRaport(){

		ArrayList<Person> listaWejsciowa = new ArrayList();
		ArrayList<Person> listaPosortowana = new ArrayList();
		
		for(int i=0; i<personList.size(); i++){
			listaWejsciowa.add(personList.get(i));
		}
		
		while(listaWejsciowa.size()>0){
			int index=0;
			Double czasMax;
			
			czasMax = listaWejsciowa.get(0).czasPracy();
			
			for(int i=1; i<listaWejsciowa.size(); i++){
				Double czas = listaWejsciowa.get(i).czasPracy();
				if(czas>czasMax){
					czasMax=czas;
					index=i;
				}
			}
			listaPosortowana.add(listaWejsciowa.get(index));
			listaWejsciowa.remove(index);
		}
		
		//drukujemy do pliku RAPORT.txt liste listaPosortowana:
		PrintWriter out=null;
		
		try
		{
			out=new PrintWriter("\\RAPORT.txt");
			String lineEnd = System.getProperty("line.separator"); //konieczne dla Windowsa pod UNIX wystarczy dodac na koncu \n
			
			for (int i=0; i<listaPosortowana.size();i++)
			{
				out.printf(
						"%s %s %s %s %s" + lineEnd, 
						listaPosortowana.get(i).getFirstName(), listaPosortowana.get(i).getLastName(), listaPosortowana.get(i).getPokoj(), listaPosortowana.get(i).getHourStart(), listaPosortowana.get(i).getHourStop()
						);
				errorLabel.setText("Raport wygenerowany");
			}		
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (out!=null)
			{
				out.close();
			}
		}
	}
	
	
	@FXML
	public void dodajOsobe(){

		path.getElements().clear();

		if(imieText.getText().isEmpty() || nazwiskoText.getText().isEmpty() || nrPokojuText.getText().isEmpty() || hourStartText.getText().isEmpty() || hourStopText.getText().isEmpty())
			errorLabel.setText("Wype³nij pola !");		
		else
		{
			if(nrPokojuText.getText().matches("\\d+"))
			{
				if(hourStartText.getText().matches("\\d{1,2}:\\d{2}"))
				{
					if(hourStopText.getText().matches("\\d{1,2}:\\d{2}"))
					{
						personList.add(new Person(imieText.getText(),nazwiskoText.getText(),nrPokojuText.getText(),hourStartText.getText(),hourStopText.getText()));	
						imieText.clear();
						nazwiskoText.clear();
						nrPokojuText.clear();
						hourStartText.clear();
						hourStopText.clear();		
						errorLabel.setText("");
					}
					else
						errorLabel.setText("Z³a godzina do:");
				}
				else
					errorLabel.setText("Z³a godzina od:");
			}
			else
				errorLabel.setText("Popraw nr pokoju");
		}
	}
	
	public void zaznaczPokoj(String numer){
		//rysuje punkt wskazuj¹cy na wlasciwy pokoj na planie
		
		int index = (int) Double.parseDouble(numer);
		Room room = Lokalizacja.gdzie(index);
		
		if (room!=null)
		{
			path.getElements().clear();
			int x = room.getCoordinateX();
			int y = room.getCoordinateY();
			
			MoveTo moveTo = new MoveTo(x,y);
			LineTo lineTo = new LineTo(x,y);
			
			path.getElements().addAll(moveTo,lineTo);
		}
		else
		{
			errorLabel.setText("Nie ma na planie !");
			path.getElements().clear();
		}
	}
	
}


