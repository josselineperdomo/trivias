package ve.ucv.triviawars.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import ve.ucv.triviawars.R;


public class BindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void bindImageFromUrl(View view, String imageUrl) {
        if(imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .error(R.drawable.ic_menu_person_outline)
                    .into((ImageView) view);
        }
    }

    @BindingAdapter({"backgroundColor"})
    public static void setBackgroundColor(View view, String backgroundColor) {
        if(backgroundColor != null && !backgroundColor.isEmpty()) {
            view.setBackgroundColor(Color.parseColor(backgroundColor));
        }
    }
}
