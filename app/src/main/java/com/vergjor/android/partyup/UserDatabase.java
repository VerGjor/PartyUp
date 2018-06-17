package com.vergjor.android.partyup;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, UserReservations.class, UserSavedEvents.class, OwnerCreatedEvents.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserInfoDao userInfoDao();
}
