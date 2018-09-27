package edu.retrofit.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

	Button btnSearch;
	TextView txtMovies;
	ServiceController serviceController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnSearch = findViewById(R.id.btnSearch);
		txtMovies = findViewById(R.id.txtMovies);
		serviceController = new ServiceController(this);

	}

	@Override
	public void success(Object obj) {
		List<Result> movies = (List<Result>) obj;
		StringBuilder titles = new StringBuilder();
		for(Result r: movies){
			titles.append(" "+ r.getTitle());
		}
		txtMovies.setText(titles);
	}

	@Override
	public void erro(Throwable throwable) {
		Toast.makeText(this, "Deu erro!", Toast.LENGTH_LONG).show();
	}

	public void pesquisar(View view){
		serviceController.callService("top_rated", "pt-BR", 1, "BR");
	}
}
