package ve.ucv.triviawars.db.dao;

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

    @Query("SELECT * from question WHERE trivia_id <> :triviaId ORDER BY RANDOM() LIMIT 1")
    QuestionEntity getTriviaNotBelongToTrivia(int triviaId);

    @Query("SELECT * from question WHERE trivia_id = :triviaId ORDER BY RANDOM() LIMIT 10")
    List<QuestionEntity> getTriviaQuestions(int triviaId);

    @Query("SELECT COUNT(*) FROM question WHERE trivia_id = :triviaId ")
    int countQuestionsByTrivia(int triviaId);
}
