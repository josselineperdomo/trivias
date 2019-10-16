package ve.ucv.triviawars.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ve.ucv.triviawars.db.entity.TriviaEntity;

@Dao
public interface TriviaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TriviaEntity> triviaEntities);

    @Query("DELETE FROM trivia")
    void deleteAll();

    @Query("SELECT * from trivia WHERE id = :triviaId")
    LiveData<TriviaEntity> getTriviaById(int triviaId);

    @Query("SELECT * from trivia ORDER BY category")
    LiveData<List<TriviaEntity>> getAllTrivias();

    @Query("SELECT * FROM trivia WHERE id in (:triviaIds) ORDER BY title ASC")
    LiveData<List<TriviaEntity>> getAllTriviasGivenIds(List<Integer> triviaIds);

}
