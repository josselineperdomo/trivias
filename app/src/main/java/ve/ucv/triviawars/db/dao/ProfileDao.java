package ve.ucv.triviawars.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ve.ucv.triviawars.db.entity.ProfileEntity;

@Dao
public interface ProfileDao {

    @Insert
    void insert(ProfileEntity profile);

    @Query("DELETE FROM profile")
    void deleteAll();

    @Query("SELECT * from profile WHERE id = :userId")
    ProfileEntity getProfileById(int userId);
}
