package ve.ucv.triviawars.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ve.ucv.triviawars.db.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void insert(UserEntity userEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserEntity> userEntities);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user ORDER BY ranking ASC")
    LiveData<List<UserEntity>> getAllUsers();
}
