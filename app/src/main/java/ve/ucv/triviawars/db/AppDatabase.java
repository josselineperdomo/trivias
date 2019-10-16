package ve.ucv.triviawars.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import ve.ucv.triviawars.async.SeedDatabaseWorker;
import ve.ucv.triviawars.db.converter.StringListConverter;
import ve.ucv.triviawars.db.dao.QuestionDao;
import ve.ucv.triviawars.db.dao.TriviaDao;
import ve.ucv.triviawars.db.dao.TriviaUserJoinDao;
import ve.ucv.triviawars.db.dao.UserDao;
import ve.ucv.triviawars.db.entity.ProfileEntity;
import ve.ucv.triviawars.db.entity.QuestionEntity;
import ve.ucv.triviawars.db.entity.TriviaEntity;
import ve.ucv.triviawars.db.entity.TriviaUserJoinEntity;
import ve.ucv.triviawars.db.entity.UserEntity;
import ve.ucv.triviawars.utilities.Constants;

@Database(entities = {
        ProfileEntity.class,
        UserEntity.class,
        TriviaEntity.class,
        QuestionEntity.class,
        TriviaUserJoinEntity.class},
        version = 1)
@TypeConverters({StringListConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract TriviaDao triviaDao();

    public abstract QuestionDao questionDao();

    public abstract TriviaUserJoinDao triviaUserJoinDao();

    private static volatile AppDatabase sInstance;

    public synchronized static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = buildDatabase(context);
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SeedDatabaseWorker.class).build();
                        WorkManager.getInstance(context).enqueue(workRequest);
                    }
                })
           .build();
    }
}
