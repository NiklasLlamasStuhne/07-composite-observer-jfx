package de.thro.inf.prg3.a07.controllers;

import com.google.gson.Gson;
import de.thro.inf.prg3.a07.api.OpenMensaAPI;
import de.thro.inf.prg3.a07.model.Meal;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController implements Initializable {

	private OpenMensaAPI openMensaAPI;


	// use annotation to tie to component in XML
	@FXML
	private Button btnRefresh;

	@FXML
	private ListView<Meal> mealsList;


	@FXML
	private Button btnClose;

	@FXML
	private CheckBox chkVegetarian;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient client = new OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build();

		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://openmensa.org/")
			.client(client)
			.build();

		openMensaAPI = retrofit.create(OpenMensaAPI.class);

		// set the event handler (callback)
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				String today = sdf.format(new Date());
				// TODO unwrap the body
				openMensaAPI.getMeals(today).enqueue(new Callback<List<Meal>>() {
					@Override
					public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {

						ObservableList<Meal> ol = FXCollections.observableList(response.body());
						mealsList.setItems(ol);
					}

					@Override
					public void onFailure(Call<List<Meal>> call, Throwable t) {

					}
				});
			}
		});

		btnClose.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				//Dieser Button schließt
				System.exit(1);
			}


		});

		chkVegetarian.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event){

				List<Meal> nml = new ArrayList<Meal>();
				for(Meal a:mealsList.getItems()){
					if(a.isVegetarian()){

						nml.add(a);
					}
				}
				ObservableList<Meal> ol = FXCollections.observableList(nml);
				mealsList.setItems(ol);

			}



		});

	}
}
