package ve.ucv.triviawars.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ve.ucv.triviawars.db.entity.QuestionEntity;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<QuestionEntity> questionEntities);

    @Query("DELETE FROM question")
    void deleteAll();

    @Query("SELECT * FROM question WHERE trivia_id <> :triviaId ORDER BY RANDOM() LIMIT 1")
    QuestionEntity getTriviaNotBelongToTrivia(int triviaId);

    @Query("SELECT * FROM question WHERE trivia_id = :triviaId ORDER BY RANDOM() LIMIT :size")
    LiveData<List<QuestionEntity>> getTriviaQuestions(int triviaId, int size);

    @Query("SELECT COUNT(*) FROM question WHERE trivia_id = :triviaId ")
    int countQuestionsByTrivia(int triviaId);
}
