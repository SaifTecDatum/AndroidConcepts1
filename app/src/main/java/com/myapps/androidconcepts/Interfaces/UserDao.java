package com.myapps.androidconcepts.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.myapps.androidconcepts.Models.Users;

import java.util.List;

@Dao
public interface UserDao {

/*  @Query("SELECT * FROM Users WHERE uid IN (:userIds)")  //hereWeCanShowOnlySelectedUsersToTheListByUsingTheirUid's.
    List<Users> loadAllByIds(int[] userIds);


    @Query("SELECT * FROM Users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    Users findByName(String first, String last);        //hereWeCanSearchForUsersInSearchBarUsingTheirFName&LName.


    @Query("DELETE FROM Users")
    void deleteAllData();       //Deleting all Data in the Database.
    */

    @Insert
    void insertrecord(Users users);     //insertQuery


    @Query("SELECT EXISTS(SELECT * FROM Users WHERE uid = :userId)")        //dataExistenceCheckingQuery
    Boolean is_exists(int userId);


    @Query("SELECT * FROM Users")           //gettingAllData
    List<Users> getAllUsers();


    @Query("DELETE FROM Users WHERE uid = :id")         //deleteByIdQuery
    void deleteById(int id);


    @Query("UPDATE Users SET first_name = :firstName, last_name = :lastName WHERE uid = :id")       //updateByIdQuery
    void updateById(int id, String firstName, String lastName);
}