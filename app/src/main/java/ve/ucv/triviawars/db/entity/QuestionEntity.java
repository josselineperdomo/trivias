package ve.ucv.triviawars.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "question",
        foreignKeys = @ForeignKey(entity = TriviaEntity.class,
        parentColumns = "id",
        childColumns = "trivia_id"),
        indices = {@Index("id"), @Index("trivia_id")})
public class QuestionEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "points")
    private int points;

    @ColumnInfo(name = "answer")
    private String answer;

    @ColumnInfo(name = "incorrect_answers")
    private List<String> incorrectAnswers;

    @ColumnInfo(name = "trivia_id")
    private int triviaId;

    public QuestionEntity(int id, String question, String imageUrl, int points, String answer, List<String> incorrectAnswers, int triviaId) {
        this.id = id;
        this.question = question;
        this.imageUrl = imageUrl;
        this.points = points;
        this.answer = answer;
        this.incorrectAnswers = incorrectAnswers;
        this.triviaId = triviaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public int getTriviaId() {
        return triviaId;
    }

    public void setTriviaId(int triviaId) {
        this.triviaId = triviaId;
    }
}