package com.zybooks.inventoryapp.repo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zybooks.inventoryapp.model.Account;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM Account WHERE username LIKE :username")
    Account getAccount(String username);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addAccount(Account account);

}
