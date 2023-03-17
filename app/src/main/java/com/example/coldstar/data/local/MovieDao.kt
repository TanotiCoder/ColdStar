package com.example.coldstar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertMovieInList(myListMovie: MyListMovie)

    @Query("DELETE FROM watch_list_table WHERE mediaId =:mediaId")
    suspend fun removeFromList(mediaId: Int)

    @Query("DELETE FROM watch_list_table")
    suspend fun deleteList()

    @Query("SELECT EXISTS (SELECT 1 FROM watch_list_table WHERE mediaId = :mediaId)")
    suspend fun exists(mediaId: Int): Int

    @Query("SELECT * FROM watch_list_table ORDER BY addedOn DESC")
    fun getAllWatchListData(): Flow<List<MyListMovie>>
}

