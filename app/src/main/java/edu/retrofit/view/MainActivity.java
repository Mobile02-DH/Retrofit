package edu.retrofit.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.retrofit.API.controller.ServiceController;
import edu.retrofit.API.helpers.WebServiceInterface;
import edu.retrofit.API.model.Result;
import edu.retrofit.R;

public class MainActivity extends AppCompatActivity implements WebServiceInterface {

	private int pag=1;
	private int totalPages=20;
	TextView txtMovies;
	ServiceController serviceController;
	RecyclerView rcvMovies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtMovies = findViewById(R.id.txtMovies);
		rcvMovies = findViewById(R.id.rcvMovies);

		serviceController = new ServiceController(this);
		serviceController.callService("top_rated", "pt-BR", pag, "BR");


		rcvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if(dy>0 && pag <= totalPages){
					//scrolling down
					serviceController.callService("top_rated", "pt-BR", pag, "BR");
				}else {
					//scolling up
				}
			}
		});

	}

	@Override
	public void success(Object obj) {
		pag++;
		List<Result> movies = (List<Result>) obj;
		if (rcvMovies.getAdapter() == null) {
			//lembrar do transaction manager
			RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
			rcvMovies.setLayoutManager(layoutManager);

			MovieAdapter movieAdapter = new MovieAdapter();
			movieAdapter.setMovies(movies);
			rcvMovies.setAdapter(movieAdapter);
		} else {
			Toast.makeText(this, "Finalizou a página "+pag, Toast.LENGTH_LONG).show();
			((MovieAdapter) rcvMovies.getAdapter()).setMovies(movies);
		}
	}

	@Override
	public void erro(Throwable throwable) {
		Toast.makeText(this, "Deu erro!", Toast.LENGTH_LONG).show();
	}

}
