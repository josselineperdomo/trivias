package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ve.ucv.triviawars.TriviaWars;
import ve.ucv.triviawars.db.dao.TriviaDao;
import ve.ucv.triviawars.db.dao.TriviaUserJoinDao;
import ve.ucv.triviawars.db.entity.TriviaEntity;
import ve.ucv.triviawars.db.entity.UserEntity;

public class TriviaDetailViewModel extends AndroidViewModel {

    private LiveData<List<UserEntity>> userRankingListLiveData;
    private LiveData<TriviaEntity> triviaLiveData;
    public ObservableField<TriviaEntity> trivia = new ObservableField<>();

    public TriviaDetailViewModel(@NonNull Application application, int triviaId) {
        super(application);

        TriviaDao triviaDao = ((TriviaWars) application).getDb().triviaDao();
        TriviaUserJoinDao userTriviaDao = ((TriviaWars) application).getDb().triviaUserJoinDao();

        triviaLiveData = triviaDao.getTriviaById(triviaId);
        userRankingListLiveData = userTriviaDao.getUserRankingTopByTrivia(triviaId);
    }

    public LiveData<List<UserEntity>> getUserRankingList() {
        return userRankingListLiveData;
    }

    public LiveData<TriviaEntity> getObservableTrivia() {
        return triviaLiveData;
    }

    public void setTrivia(TriviaEntity trivia) {
        this.trivia.set(trivia);
    }

}
