package ve.ucv.triviawars.viewmodels;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ve.ucv.triviawars.TriviaWars;
import ve.ucv.triviawars.db.dao.QuestionDao;
import ve.ucv.triviawars.db.entity.QuestionEntity;

import static ve.ucv.triviawars.utilities.Constants.ANSWER_TIME_MILLISEC;
import static ve.ucv.triviawars.utilities.Constants.DEFAULT_QUESTIONS_LIST_SIZE;
import static ve.ucv.triviawars.utilities.Constants.SECOND;

public class TriviaQuestionsViewModel extends AndroidViewModel {

    private LiveData<List<QuestionEntity>> triviaQuestionsList;

    private MutableLiveData<Integer> questionsCounter;
    private MutableLiveData<Integer> totalQuestions;

    private MutableLiveData<List<String>> currentAnswerOptions;
    private MutableLiveData<QuestionEntity> currentQuestion;

    private MutableLiveData<Integer> score;

    private CountDownTimer countDownTimer;
    private MutableLiveData<Long> answerTime;

    private MutableLiveData<Boolean> gameStart;
    private MutableLiveData<Boolean> gameFinished;


    public TriviaQuestionsViewModel(@NonNull Application application, int triviaId) {
        super(application);

        QuestionDao questionDao = ((TriviaWars) application).getDb().questionDao();
        triviaQuestionsList = questionDao.getTriviaQuestions(triviaId, DEFAULT_QUESTIONS_LIST_SIZE);
        totalQuestions = new MutableLiveData<>();

        currentQuestion = new MutableLiveData<>();
        currentAnswerOptions = new MutableLiveData<>();
        answerTime = new MutableLiveData<>();

        questionsCounter = new MutableLiveData<>(0);
        score = new MutableLiveData<>(0);

        gameFinished = new MutableLiveData<>(false);
        gameStart = new MutableLiveData<>(false);

        countDownTimer = new CountDownTimer(ANSWER_TIME_MILLISEC + SECOND, SECOND) {
            @Override
            public void onTick(long l) {
                answerTime.setValue(l/SECOND);
            }

            @Override
            public void onFinish() {
                stopCountDownTimer();
            }
        };
    }

    public MutableLiveData<Long> getAnswerTime() {
        return answerTime;
    }

    public MutableLiveData<Integer> getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int size) {
        totalQuestions.setValue(size);
    }


    public LiveData<List<QuestionEntity>> getTriviaQuestionsList() {
        return triviaQuestionsList;
    }

    public MutableLiveData<Integer> getQuestionsCounter() {
        return questionsCounter;
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public MutableLiveData<Boolean> getGameFinished() {
        return gameFinished;
    }

    public MutableLiveData<List<String>> getCurrentAnswerOptions() {
        return currentAnswerOptions;
    }

    private QuestionEntity getNextQuestion () {
        List<QuestionEntity> questionsList = triviaQuestionsList.getValue();
        Integer index = questionsCounter.getValue();
        if (questionsList == null || questionsList.isEmpty()) {
            return null;
        }
        if (index == null || index >= questionsList.size()) {
            return null;
        }
        return questionsList.get(index);
    }

    private void setNextQuestion() {
        QuestionEntity questionEntity = getNextQuestion();
        if (questionEntity == null) {
            gameFinished.setValue(true);
            stopCountDownTimer();
        } else {
            currentQuestion.setValue(questionEntity);
            questionsCounter.setValue(questionsCounter.getValue()+1);
            setCurrentAnswerOptions(questionEntity);
            startCountDownTimer();
        }
    }

    public void setCurrentAnswerOptions(QuestionEntity questionEntity) {
        List<String> answers = new ArrayList<>();
        List<String> incorrectAnswers = questionEntity.getIncorrectAnswers();

        for(String incorrectAnswer: incorrectAnswers) {
            answers.add(incorrectAnswer);
        }
        answers.add(questionEntity.getAnswer());
        Collections.shuffle(answers);
        currentAnswerOptions.setValue(answers);
    }

    private void startCountDownTimer() {
        stopCountDownTimer();
        countDownTimer.start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        stopCountDownTimer();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    public MutableLiveData<QuestionEntity> getCurrentQuestion() {
        return currentQuestion;
    }

    public void setGameStart() {
        this.gameStart.setValue(true);
        setNextQuestion();
    }

    public Boolean isACorrectAnswer(String optionAnswered) {
        QuestionEntity questionEntity = getCurrentQuestion().getValue();
        if (questionEntity == null) {
            return null;
        }
        return questionEntity.getAnswer().equals(optionAnswered);
    }

    public void onQuestionAnswered(String optionAnswered) {
        stopCountDownTimer();
        if (isACorrectAnswer(optionAnswered)) {
            score.setValue(score.getValue() + currentQuestion.getValue().getPoints());
        }
        setNextQuestion();
    }

    public void onTimeout() {
        stopCountDownTimer();
        setNextQuestion();
    }

}
