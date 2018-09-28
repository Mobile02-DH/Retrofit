package edu.retrofit.API.DAO;

import edu.retrofit.API.helpers.RetrofitConfig;
import edu.retrofit.API.helpers.Service;
import edu.retrofit.API.helpers.WebServiceInterface;
import edu.retrofit.API.model.MovieResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//encarregado de se comunicar com o serviço
public class DAOService {
	private WebServiceInterface listener;
	private Call<MovieResults> call;
	private Service service;
	private final String API_KEY = "bde8033d3274c91b292a5293c6349052";

	public DAOService(WebServiceInterface listener) {
		this.listener = listener;

		//criar o client
		service = RetrofitConfig.callService().create(Service.class);

	}

	public void callService(String categoria, String idioma, int pag, String regiao){
		call = service.getMovies(categoria, API_KEY, idioma, pag, regiao);
		manageService();
	}

	public void manageService(){
		call.enqueue(new Callback<MovieResults>() {
			@Override
			public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
				listener.success(response.body().getResults());
			}

			@Override
			public void onFailure(Call<MovieResults> call, Throwable t) {
				listener.erro(t);
			}
		});
	}

}
