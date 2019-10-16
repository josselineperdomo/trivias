package ve.ucv.triviawars.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ve.ucv.triviawars.R;
import ve.ucv.triviawars.db.entity.UserEntity;

public class UserRankingRecyclerViewAdapter extends RecyclerView.Adapter<UserRankingRecyclerViewAdapter.UserViewHolder> {

    private List<UserEntity> usersRankingList;
    private Context context;

    public UserRankingRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setUsersRankingList(List<UserEntity> usersRankingList) {
        this.usersRankingList = usersRankingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Integer color = null;

        switch (position){
            case 0:
                color = context.getResources().getColor(R.color.gold);
                break;
            case 1:
                color = context.getResources().getColor(R.color.silver);
                break;
            case 2:
                color = context.getResources().getColor(R.color.bronze);
                break;
        }
        holder.bind(context, usersRankingList.get(position), color);
    }

    @Override
    public int getItemCount() {
        if(usersRankingList == null) {
            return 0;
        }
        return usersRankingList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userPosition, userUsername, userPoints;
        private CircleImageView userAvatar;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userPosition = itemView.findViewById(R.id.list_user_pos);
            userUsername = itemView.findViewById(R.id.list_user_username);
            userPoints = itemView.findViewById(R.id.list_user_pts);
            userAvatar = itemView.findViewById(R.id.list_user_avatar);
        }

        void bind(Context context, UserEntity user, Integer avatarBorderColor){

            userPosition.setText(String.valueOf(user.getRanking()));
            userUsername.setText(user.getUsername());
            userPoints.setText(String.valueOf(user.getPoints()));

            String avatarUrl = user.getAvatarUrl();
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Glide.with(context)
                        .load(user.getAvatarUrl())
                        .fitCenter()
                        .error(R.drawable.ic_menu_person_outline)
                        .into(userAvatar);

                if (avatarBorderColor != null) {
                    userAvatar.setBorderWidth(context.getResources().getDimensionPixelOffset(R.dimen.border_width));
                    userAvatar.setBorderColor(avatarBorderColor);
                }
            }
        }
    }
}
