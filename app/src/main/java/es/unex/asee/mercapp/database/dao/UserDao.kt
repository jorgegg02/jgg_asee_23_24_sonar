package es.unex.asee.mercapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.asee.mercapp.data.model.User


@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Insert
    suspend fun insert(user: User): Long

}
