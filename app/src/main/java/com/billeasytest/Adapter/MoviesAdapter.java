package com.billeasytest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.billeasytest.Constant;
import com.billeasytest.Model.Movie;
import com.billeasytest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;

    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int position) {

        viewHolder.tvTitle.setText(movieList.get(position).getOriginalTitle());

        if (!movieList.get(position).getPoster_path().isEmpty()) {
            Picasso.with(mContext)
                    .load(Constant.UrlPath.MOVIEDB_POSTER_URL + movieList.get(position).getPoster_path())
                    .fit()
                    .into(viewHolder.ivThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public ImageView ivThumbnail;

        public MyViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvTitle);
            ivThumbnail = view.findViewById(R.id.ivThumbnail);

        }
    }
}
