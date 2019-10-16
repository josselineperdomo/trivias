package ve.ucv.triviawars.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "trivia_user_join",
        primaryKeys = { "trivia_id", "user_id" },
        foreignKeys = {
                @ForeignKey(entity = TriviaEntity.class,
                        parentColumns = "id",
                        childColumns = "trivia_id"),
                @ForeignKey(entity = UserEntity.class,
                        parentColumns = "id",
                        childColumns = "user_id")
        },
        indices = {@Index("trivia_id"), @Index("user_id")})
public class TriviaUserJoinEntity {
    @ColumnInfo(name = "trivia_id")
    private int triviaId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "ranking")
    private int ranking;

    @ColumnInfo(name = "points")
    private int points;

    public TriviaUserJoinEntity(int triviaId, int userId, int ranking, int points) {
        this.triviaId = triviaId;
        this.userId = userId;
        this.ranking = ranking;
        this.points = points;
    }

    public int getTriviaId() {
        return triviaId;
    }

    public void setTriviaId(int triviaId) {
        this.triviaId = triviaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}