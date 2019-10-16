package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ve.ucv.triviawars.TriviaWars;
import ve.ucv.triviawars.db.dao.TriviaDao;
import ve.ucv.triviawars.db.entity.TriviaEntity;

public class TriviaListViewModel extends AndroidViewModel {
    private TriviaDao triviaDao;
    private LiveData<List<TriviaEntity>> triviaLiveData;

    public TriviaListViewModel(@NonNull Application application) {
        super(application);

        triviaDao = ((TriviaWars)application).getDb().triviaDao();
        triviaLiveData = triviaDao.getAllTrivias();
    }

    public LiveData<List<TriviaEntity>> getTriviasList() {
        return triviaLiveData;
    }
}
