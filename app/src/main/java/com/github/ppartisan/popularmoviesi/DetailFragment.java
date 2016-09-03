package com.github.ppartisan.popularmoviesi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ppartisan.popularmoviesi.view.RatingsView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(MovieModel model) {

        Bundle args = new Bundle();
        args.putParcelable(DetailActivity.DETAIL_MODEL_KEY, model);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        final TextView title = (TextView) root.findViewById(R.id.df_movie_title);
        final ImageView thumbnail = (ImageView) root.findViewById(R.id.fd_thumbnail);
        final TextView ratingsText = (TextView) root.findViewById(R.id.df_ratings_text);
        final RatingsView ratingsView = (RatingsView) root.findViewById(R.id.df_ratings_view);
        final TextView releaseDate = (TextView) root.findViewById(R.id.df_release_date_text);
        final TextView synopsis = (TextView) root.findViewById(R.id.df_synopsis_text);

        MovieModel model = getModel();
        title.setText(model.title);
        final String rating = String.format(Locale.getDefault(),"%.1f", model.averageVote);
        ratingsText.setText(getString(R.string.df_rating_content, rating));
        ratingsView.setScore(model.averageVote);
        releaseDate.setText(model.releaseDate);
        synopsis.setText(model.synopsis);

        Picasso.with(getContext()).load(model.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(thumbnail);

        return root;
    }

    private MovieModel getModel() {
        return getArguments().getParcelable(DetailActivity.DETAIL_MODEL_KEY);
    }

}
