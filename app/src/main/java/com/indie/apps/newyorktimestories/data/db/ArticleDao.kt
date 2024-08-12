package com.indie.apps.newyorktimestories.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.indie.apps.newyorktimestories.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface  ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(data: Article) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles : List<Article>) : List<Long>

    @Transaction
    @Query("SELECT * FROM articles WHERE section = :section")
    suspend fun getRecordsFromSection(section: String): List<Article>

    @Transaction
    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getRecordsFromId(id: Long): Article

    @Transaction
    @Query("SELECT * FROM articles WHERE section = :section AND (title LIKE  '%' || :searchQuery || '%' OR kicker LIKE  '%' || :searchQuery || '%')")
    suspend fun searchRecordsFromSection(searchQuery: String, section: String): List<Article>

/*

    @Transaction
    @Query("Select * from articles where section = :section")
    fun getSearchRecordsFromSection(section: String):Flow<List<Article>>
*/

    @Transaction
    @Query("DELETE FROM articles WHERE section = :section")
    suspend fun deleteRecordsFromSection(section: String): Int
}