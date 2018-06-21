package com.vergjor.android.partyup;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserInfoDao {

    @Query("SELECT COUNT(user_name) FROM user")
    int numberOfUsers();

    @Query("SELECT COUNT(eventTitle) FROM UserSavedEvents")
    int numberOfSavedEvents();

    @Query("SELECT COUNT(eventTitle) FROM UserReservations")
    int numberOfUserReservations();

    @Query("SELECT COUNT(eventTitle) FROM OwnerCreatedEvents")
    int numberOfOwnerCreatedEvents();

    @Query("SELECT * FROM UserReservations")
    List<Events> userReservations();

    @Query("SELECT * FROM OwnerCreatedEvents")
    List<CreatedEvents> createdEvents();

    @Query("SELECT * FROM UserSavedEvents")
    List<Events> userSavedEvents();

    @Query("DELETE FROM UserSavedEvents WHERE eventTitle LIKE :etitle")
    void deleteSavedEvent(String etitle);

    @Query("DELETE FROM UserReservations WHERE eventTitle LIKE :etitle")
    void deleteReservation(String etitle);

    @Query("SELECT user_type FROM user")
    int getUserType();

    @Query("SELECT user_name FROM user")
    String getUserName();

    @Query("SELECT tax_owner FROM user")
    String getTaxOfOwner();

    @Query("SELECT addressOwner FROM user")
    String getAddressOfCaffee();

    @Query("SELECT numReservations FROM OwnerCreatedEvents WHERE eventTitle LIKE :etitle")
    String getEventNumResertvations(String etitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewReservation(UserReservations userReservations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveEvent(UserSavedEvents userSavedEvents);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewCreatedEvent(OwnerCreatedEvents ownerCreatedEvents);

}
