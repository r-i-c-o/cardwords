package com.ricode.cardwords.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(word: Word)

    @Update
    suspend fun updateState(word: Word)

    @Query("select count(id) from word")
    suspend fun getPackSize(): Int

    @Query("select * from word where id=:id")
    suspend fun getWord(id: Int): Word

    //TODO test fun, needs to be deleted
    @Query("select * from word")
    suspend fun getAllWords(): List<Word>

    @Query("select * from word where title=:title")
    suspend fun getWordByTitle(title: String): Word

    @Query("select * from word where state=0 limit :numberOfWords")
    suspend fun getNUnusedWords(numberOfWords: Int): Word

    @Query("select * from word where state=1")
    suspend fun getLearnWords(): List<Word>

    @Query("select * from word where state=2")
    suspend fun getTestWords(): List<Word>

    @Query("select * from word where state=3")
    suspend fun getRepeatWords(): List<Word>
}