package ve.ucv.triviawars.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.db.entity.TriviaEntity;
import ve.ucv.triviawars.ui.DashboardViewPagerFragmentDirections;

public class TriviaRecyclerViewAdapter extends RecyclerView.Adapter<TriviaRecyclerViewAdapter.TriviaViewHolder> {

    private List<TriviaEntity> triviaList;
     private Context context;

    public TriviaRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setTriviaList(List<TriviaEntity> triviaList) {
        this.triviaList = triviaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_trivia, parent, false);
        return new TriviaRecyclerViewAdapter.TriviaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TriviaViewHolder holder, int position) {
        if (triviaList == null) {
            return;
        }
        final TriviaEntity trivia = triviaList.get(position);

        if (trivia != null) {
            holder.triviaTitle.setText(trivia.getTitle());

            String thumbnailUrl = trivia.getThumbnailUrl();

            if(thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
              Glide.with(context)
                      .load(thumbnailUrl)
                      .error(R.drawable.ic_menu_person_outline)
                      .into(holder.triviaThumbnailImg);
            }

            holder.triviaThumbnailImg.setBackgroundColor(Color.parseColor(trivia.getBackgroundColor()));

            holder.itemView.setOnClickListener(v -> {
                Log.d("TriviaViewAdapter", String.format("Enviando id: %d", trivia.getId()));
                DashboardViewPagerFragmentDirections.ActionToTriviaDetail direction = DashboardViewPagerFragmentDirections
                        .actionToTriviaDetail(trivia.getId(), trivia.getTitle());
                Navigation.findNavController(v).navigate(direction);
            });
        }
    }

    @Override
    public int getItemCount() {
        if(triviaList == null) {
            return 0;
        }

        return triviaList.size();
    }

    static class TriviaViewHolder extends RecyclerView.ViewHolder {
        private ImageView triviaThumbnailImg;
        private TextView triviaTitle;

        TriviaViewHolder(View itemView) {
            super(itemView);

            triviaThumbnailImg = itemView.findViewById(R.id.trivia_item_thumbnail_img);
            triviaTitle = itemView.findViewById(R.id.trivia_item_title);
        }
    }
}
