package com.dsoft.liverpooldirectory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsoft.liverpooldirectory.model.Comments

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComments(comments: Comments)

    @Query("SELECT * FROM comments_table")
    fun readComments(): LiveData<List<Comments>>

    @Query("DELETE FROM comments_table")
    suspend fun deleteAllComments()
}