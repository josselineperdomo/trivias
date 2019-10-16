package ve.ucv.triviawars.async;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;

import ve.ucv.triviawars.db.AppDatabase;
import ve.ucv.triviawars.db.dao.QuestionDao;
import ve.ucv.triviawars.db.dao.TriviaDao;
import ve.ucv.triviawars.db.dao.TriviaUserJoinDao;
import ve.ucv.triviawars.db.dao.UserDao;
import ve.ucv.triviawars.db.entity.QuestionEntity;
import ve.ucv.triviawars.db.entity.TriviaEntity;
import ve.ucv.triviawars.db.entity.TriviaUserJoinEntity;
import ve.ucv.triviawars.db.entity.UserEntity;
import ve.ucv.triviawars.utilities.Constants;

public class SeedDatabaseWorker extends Worker {

    private static final String TAG = SeedDatabaseWorker.class.getName();

    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private <T> List<T> loadDataFromFile(@NonNull String filename, Class<T> clazz) {
        Log.d(TAG, String.format("Opening file: %s", filename));
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream inputStream = assetManager.open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer);

            return new Gson().fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
        } catch (Exception e) {
            Log.e(TAG, String.format("Error trying to opening file: %s", filename));
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

        TriviaDao triviaDao = appDatabase.triviaDao();
        QuestionDao questionDao = appDatabase.questionDao();
        UserDao userDao = appDatabase.userDao();
        TriviaUserJoinDao triviaUserJoinDao = appDatabase.triviaUserJoinDao();

        triviaDao.deleteAll();
        questionDao.deleteAll();
        userDao.deleteAll();
        triviaUserJoinDao.deleteAll();

        userDao.insertAll(loadDataFromFile(Constants.USER_DATA_FILENAME, UserEntity.class));
        triviaDao.insertAll(loadDataFromFile(Constants.TRIVIA_DATA_FILENAME, TriviaEntity.class));
        triviaUserJoinDao.insertAll(loadDataFromFile(Constants.TRIVIA_USER_DATA_FILENAME, TriviaUserJoinEntity.class));
        questionDao.insertAll(loadDataFromFile(Constants.QUESTION_DATA_FILENAME, QuestionEntity.class));

        return Result.success();
    }
}
