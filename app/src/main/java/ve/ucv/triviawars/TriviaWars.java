package ve.ucv.triviawars;

import android.app.Application;

import ve.ucv.triviawars.db.AppDatabase;

public class TriviaWars extends Application {

    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = AppDatabase.getInstance(getApplicationContext());
    }


    public AppDatabase getDb() {
        return db;
    }
}
