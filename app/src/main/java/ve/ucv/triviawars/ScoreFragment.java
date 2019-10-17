package ve.ucv.triviawars;


import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import static ve.ucv.triviawars.utilities.Constants.SECOND;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {
    private ImageView triviaCoverImage;
    private FrameLayout rootLayout;

    private TextView triviaScore;

    private Button playAgainBtn;

    private String triviaTitle, triviaImageUrl, triviaBackgroundColor;
    private Integer triviaId, gameScore;

    private MediaPlayer winSoundEffect;

    public ScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_score, container, false);

        rootLayout = view.findViewById(R.id.score_root_layout);
        triviaCoverImage = view.findViewById(R.id.trivia_cover_img);
        triviaScore = view.findViewById(R.id.trivia_score);
        playAgainBtn = view.findViewById(R.id.play_again);

        winSoundEffect = MediaPlayer.create(view.getContext(), R.raw.win_trivia_effect);

        final Bundle arguments = this.getArguments();
        Activity activity = getActivity();

        if (arguments != null && activity != null) {
            triviaId = ScoreFragmentArgs.fromBundle(arguments).getTriviaId();
            triviaTitle = ScoreFragmentArgs.fromBundle(arguments).getTriviaTitle();
            triviaImageUrl = ScoreFragmentArgs.fromBundle(arguments).getTriviaImageUrl();
            triviaBackgroundColor = ScoreFragmentArgs.fromBundle(arguments).getTriviaBackgroundColor();
            gameScore = ScoreFragmentArgs.fromBundle(arguments).getGameScore();
        }

        Glide.with(view.getContext())
                .load(triviaImageUrl)
                .error(R.drawable.ic_menu_person_outline)
                .into(triviaCoverImage);

        rootLayout.setBackgroundColor(Color.parseColor(triviaBackgroundColor));
        triviaScore.setText(String.valueOf(gameScore));
        playAgainBtn.setOnClickListener(view1 -> {
            ScoreFragmentDirections.ActionToTriviaQuestionsFragmentFromPlayAgain direction = ScoreFragmentDirections
                    .actionToTriviaQuestionsFragmentFromPlayAgain(triviaId, triviaTitle, triviaImageUrl, triviaBackgroundColor);
            Navigation.findNavController(view1).navigate(direction);
        });

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            winSoundEffect.start();
        }, SECOND/2);

        return view;
    }

}
