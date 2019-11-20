package de.thro.inf.prg3.a07.api;

import de.thro.inf.prg3.a07.model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Peter Kurfer on 11/19/17.
 */

public interface OpenMensaAPI {
	// TODO add method to get meals of a day


	@GET("/api/v2/canteens/229/days/{date}/meals")
	Call<List<Meal>> getMeals(@Path("date") String date);





	// example request: GET /canteens/229/days/2017-11-22/meals
}
