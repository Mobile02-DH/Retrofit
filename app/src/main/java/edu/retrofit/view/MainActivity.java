package edu.retrofit.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.retrofit.API.controller.ServiceController;
import edu.retrofit.API.helpers.WebServiceInterface;
import edu.retrofit.API.model.Result;
import edu.retrofit.R;

public class MainActivity extends AppCompatActivity implements WebServiceInterface {

    private int pag = 1;
    private int totalPages = 10;
    private TextView txtMovies;
    private ServiceController serviceController;
    private RecyclerView rcvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        serviceController = new ServiceController(this);
        serviceController.callService("top_rated", "pt-BR", pag, "BR");
        setScrollListener();
    }

    private void setScrollListener() {
        rcvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                verifyIsLast(recyclerView);
            }
        });
    }

    private void verifyIsLast(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;

        if (totalItemCount > 0 && endHasBeenReached && pag <= totalPages) {
            //you have reached to the bottom of your recycler view
            pag++;
            serviceController.callService("top_rated", "pt-BR", pag, "BR");
        }

        if (pag == 2) {
            showMessage();
        }
    }

    private void initViews() {
        txtMovies = findViewById(R.id.txtMovies);
        rcvMovies = findViewById(R.id.rcvMovies);
    }

    @Override
    public void success(Object obj) {
        List<Result> movies = (List<Result>) obj;
        if (rcvMovies.getAdapter() == null) {
            //lembrar do transaction manager
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rcvMovies.setLayoutManager(layoutManager);

            MovieAdapter movieAdapter = new MovieAdapter();
            movieAdapter.setMovies(movies);
            rcvMovies.setAdapter(movieAdapter);
        } else {
            Toast.makeText(this, "Finalizou a página " + pag, Toast.LENGTH_LONG).show();
            ((MovieAdapter) rcvMovies.getAdapter()).setMovies(movies);
        }
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Acabou as paginas");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void erro(Throwable throwable) {
        Toast.makeText(this, "Deu erro!", Toast.LENGTH_LONG).show();
    }
}
