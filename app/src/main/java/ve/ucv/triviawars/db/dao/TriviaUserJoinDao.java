package ve.ucv.triviawars.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ve.ucv.triviawars.db.entity.TriviaEntity;
import ve.ucv.triviawars.db.entity.TriviaUserJoinEntity;
import ve.ucv.triviawars.db.entity.UserEntity;

@Dao
public interface TriviaUserJoinDao {

    @Insert
    void insert(TriviaUserJoinEntity triviaUserJoin);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TriviaUserJoinEntity> triviaUserJoinEntities);

    @Query("DELETE FROM trivia_user_join")
    void deleteAll();

    @Query("SELECT user.* FROM user " +
            "INNER JOIN trivia_user_join " +
            "ON user.id = trivia_user_join.user_id " +
            "WHERE trivia_user_join.trivia_id = :triviaId " +
            "ORDER BY trivia_user_join.ranking ASC")
    List<UserEntity> getUsersByTrivia(final int triviaId);

    @Query("SELECT user.id, user.username, user.avatar_url, trivia_user_join.ranking, trivia_user_join.points " +
            "FROM user INNER JOIN trivia_user_join " +
            "ON user.id = trivia_user_join.user_id " +
            "WHERE trivia_user_join.trivia_id = :triviaId " +
            "ORDER BY trivia_user_join.ranking ASC " +
            "LIMIT 3")
    LiveData<List<UserEntity>> getUserRankingTopByTrivia(final int triviaId);

    @Query("SELECT trivia.* FROM trivia " +
            "INNER JOIN trivia_user_join " +
            "ON trivia.id = trivia_user_join.trivia_id " +
            "WHERE trivia_user_join.user_id = :userId ")
    List<TriviaEntity> getTriviasByUser(final int userId);
}
