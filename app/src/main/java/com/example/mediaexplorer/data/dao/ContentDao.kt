package com.example.mediaexplorer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mediaexplorer.data.entity.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(content: Content)

    @Update
    suspend fun update(content: Content)

    @Delete
    suspend fun delete(content: Content)

    @Query("SELECT * FROM content WHERE category_name = :categoryName")
    fun getByCategory(categoryName: String): Flow<List<Content>>

    @Query("SELECT * FROM content WHERE id = :id")
    fun getById(id: Int): Flow<Content?>
}
