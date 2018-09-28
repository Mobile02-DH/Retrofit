package edu.retrofit.API.helpers;

import edu.retrofit.API.model.MovieResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

	@GET("movie/{category}")
	Call<MovieResults> getMovies(
		@Path("category") String category,
		@Query("api_key") String api_key,
		@Query("language") String language,
		@Query("page") int page,
		@Query("origin") String origin
	);

	//Call<MovieResults> getMovies(String category, String api_key, String language, int page, String origin);
}
