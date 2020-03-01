package com.ricode.cardwords.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    fun insertWord(word: Word)

    @Update
    fun updateState(word: Word)

    @Query("select count(id) from word")
    fun getPackSize(): Int

    @Query("select * from word where id=:id")
    fun getWord(id: Int): Word

    @Query("select * from word where state=0 limit :numberOfWords")
    fun getNUnusedWords(numberOfWords: Int): Word

    @Query("select * from word where state=1")
    fun getLearnWords(): List<Word>

    @Query("select * from word where state=2")
    fun getTestWords(): List<Word>

    @Query("select * from word where state=3")
    fun getRepeatWords(): List<Word>
}