package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ve.ucv.triviawars.TriviaWars;
import ve.ucv.triviawars.db.dao.UserDao;
import ve.ucv.triviawars.db.entity.UserEntity;

public class UserRankingViewModel extends AndroidViewModel {
    private UserDao userDao;
    private LiveData<List<UserEntity>> usersLiveData;

    public UserRankingViewModel(@NonNull Application application) {
        super(application);

        userDao = ((TriviaWars)application).getDb().userDao();
        usersLiveData = userDao.getAllUsers();
    }

    public LiveData<List<UserEntity>> getUsersList() {
        return usersLiveData;
    }
}
