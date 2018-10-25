package edu.retrofit.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.retrofit.API.model.Result;
import edu.retrofit.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

	private List<Result> movies = new ArrayList<>();
	@NonNull
	@Override
	public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		//inflar(popular)
		LayoutInflater view = LayoutInflater.from(parent.getContext());
		return new ViewHolder(view.inflate(R.layout.movie_item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
		//setar os dados com a view
		holder.txtTitulo.setText(movies.get(position).getTitle());
	}

	@Override
	public int getItemCount() {
		return movies.size();
	}

	public void setMovies(List<Result> movies) {
		//verificar se o movies já tem informação
		if (movies.size() == 0) {
			this.movies = movies;
		} else {
			this.movies.addAll(movies);
			notifyDataSetChanged();
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		private TextView txtTitulo;

		public ViewHolder(View itemView) {
			super(itemView);
			txtTitulo = itemView.findViewById(R.id.txtTitulo);
		}
	}
}
