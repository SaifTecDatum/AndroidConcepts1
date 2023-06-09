package com.myapps.androidconcepts.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.myapps.androidconcepts.Models.PracticeEntity;

import java.util.List;

@Dao
public interface PracticeDao {

    @Insert
    void insertPracticeData(PracticeEntity practiceEntity);

    @Query("SELECT EXISTS (SELECT * FROM PracticeEntity WHERE startTime = :startTime)")
    boolean isExists(String startTime);

    @Query("SELECT * FROM PracticeEntity")
    List<PracticeEntity> getAllPracticeData();

    @Query("DELETE FROM PracticeEntity")
    void deleteAllPracticeData();

}