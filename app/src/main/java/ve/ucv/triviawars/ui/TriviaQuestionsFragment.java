package ve.ucv.triviawars.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.List;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.databinding.FragmentTriviaQuestionBinding;
import ve.ucv.triviawars.db.entity.QuestionEntity;
import ve.ucv.triviawars.viewmodels.TriviaQuestionsViewModel;
import ve.ucv.triviawars.viewmodels.TriviaQuestionsViewModelFactory;

import static ve.ucv.triviawars.utilities.Constants.ANSWER_TIME_SEC;
import static ve.ucv.triviawars.utilities.Constants.OPTIONS_LIST_SIZE;
import static ve.ucv.triviawars.utilities.Constants.SECOND;

public class TriviaQuestionsFragment extends Fragment {
    private Context context;

    private Integer triviaId;
    private String triviaTitle, triviaImageUrl, triviaBackgroundColor;

    private TriviaQuestionsViewModel triviaQuestionsViewModel;
    private FragmentTriviaQuestionBinding binding;

    private List<MaterialButton> optionsBtn;
    private ImageView questionImg;

    private ProgressBar countDownProgressBar;

    private MediaPlayer correctAnswerSoundEffect, wrongAnswerSoundEffect;

    private Animation shakeAnimation;

    private boolean canAnswer;

    public static TriviaQuestionsFragment newInstance() {
        return new TriviaQuestionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trivia_question, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());

        View view = binding.getRoot();

        context = getContext();

        optionsBtn = Arrays.asList(
                view.findViewById(R.id.answer_one_btn),
                view.findViewById(R.id.answer_two_btn),
                view.findViewById(R.id.answer_three_btn),
                view.findViewById(R.id.answer_four_btn));

        questionImg = view.findViewById(R.id.question_img);

        for (Button optionBtn : optionsBtn) {
            optionBtn.setOnClickListener(onAnswerClick);
        }

        canAnswer = false;
        shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        correctAnswerSoundEffect = MediaPlayer.create(context, R.raw.correct_answer_effect);
        wrongAnswerSoundEffect = MediaPlayer.create(context, R.raw.wrong_answer_effect);

        countDownProgressBar = view.findViewById(R.id.countdown_progress_bar);
        countDownProgressBar.setMax(ANSWER_TIME_SEC);


        final Bundle arguments = this.getArguments();
        Activity activity = getActivity();

        if (arguments != null && activity != null) {

            triviaId = TriviaQuestionsFragmentArgs.fromBundle(arguments).getTriviaId();
            triviaTitle = TriviaQuestionsFragmentArgs.fromBundle(arguments).getTriviaTitle();
            triviaImageUrl = TriviaQuestionsFragmentArgs.fromBundle(arguments).getTriviaImageUrl();
            triviaBackgroundColor = TriviaQuestionsFragmentArgs.fromBundle(arguments).getTriviaBackgroundColor();

            initData(activity.getApplication(), view);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (activity != null) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Activity activity = getActivity();
        if (activity != null) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    }

    private void initData(Application application, View view) {
        triviaQuestionsViewModel = ViewModelProviders
                .of(this, new TriviaQuestionsViewModelFactory(application, triviaId))
                .get(TriviaQuestionsViewModel.class);

        binding.setTriviaQuestionsViewModel(triviaQuestionsViewModel);

        triviaQuestionsViewModel.getTriviaQuestionsList().observe(this, triviaQuestionsEntities -> {
            if (triviaQuestionsEntities != null && !triviaQuestionsEntities.isEmpty()) {
                triviaQuestionsViewModel.setTotalQuestions(triviaQuestionsEntities.size());
                triviaQuestionsViewModel.setGameStart();
            }
        });

        triviaQuestionsViewModel.getCurrentQuestion().observe(this, new Observer<QuestionEntity>() {
            @Override
            public void onChanged(QuestionEntity questionEntity) {
                if (questionEntity != null && questionEntity.getImageUrl() != null && !questionEntity.getImageUrl().isEmpty()) {
                    questionImg.setVisibility(View.VISIBLE);
                } else {
                    questionImg.setVisibility(View.GONE);
                }
            }
        });

        triviaQuestionsViewModel.getGameFinished().observe(this, gameFinished -> {
            if (gameFinished) {
                Integer score = triviaQuestionsViewModel.getScore().getValue();

                TriviaQuestionsFragmentDirections.ActionToScoreFragment direction = TriviaQuestionsFragmentDirections
                        .actionToScoreFragment(score, triviaId, triviaTitle, triviaImageUrl, triviaBackgroundColor);
                Navigation.findNavController(view).navigate(direction);
            }
        });

        triviaQuestionsViewModel.getCurrentAnswerOptions().observe(this, optionsValues -> {
            if (optionsValues != null && optionsValues.size() == OPTIONS_LIST_SIZE) {
                resetUI(optionsValues);
            }
        });

        triviaQuestionsViewModel.getAnswerTime().observe(this, time -> {
            if (time != null) {
                if (time == 0L) {
                    canAnswer = false;
                    showAnswers();
                    wrongAnswerSoundEffect.start();
                    vibrationEffect();

                    Handler handler = new Handler();
                    handler.postDelayed(() ->
                        triviaQuestionsViewModel.onTimeout()
                    , 1500);
                }
                int timeProgress = ANSWER_TIME_SEC - time.intValue();
                countDownProgressBar.setProgress(timeProgress);
            }
        });
    }

    private void resetUI(List<String> optionsValues) {
        for(int i=0; i<optionsValues.size(); ++i) {
            MaterialButton optionBtn = optionsBtn.get(i);
            optionBtn.setText(optionsValues.get(i));
            optionBtn.setBackgroundColor(getResources().getColor(R.color.magenta));
        }
        canAnswer = true;
    }

    private void showAnswers() {
        for(Button btn : optionsBtn) {
            Boolean isACorrectAnswer =  triviaQuestionsViewModel.isACorrectAnswer((String) btn.getText());
            int answerColor = isACorrectAnswer ? getResources().getColor(R.color.correctGreen)
                    : getResources().getColor(R.color.errorRed);

            btn.setBackgroundColor(answerColor);
        }
    }

    public void vibrationEffect() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(SECOND/2, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    private View.OnClickListener onAnswerClick = view -> {
        Button btn = (Button) view;
        String optionAnsweredText = (String)btn.getText();

        if (canAnswer) {
            canAnswer = false;
            if (triviaQuestionsViewModel.isACorrectAnswer(optionAnsweredText)) {
                correctAnswerSoundEffect.start();
            } else {
                btn.startAnimation(shakeAnimation);
                wrongAnswerSoundEffect.start();
                vibrationEffect();

            }

            showAnswers();

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                triviaQuestionsViewModel.onQuestionAnswered(optionAnsweredText);
            }, 1500);
        }
    };
}
