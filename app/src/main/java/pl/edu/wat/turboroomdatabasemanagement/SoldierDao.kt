package pl.edu.wat.turboroomdatabasemanagement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SoldierDao {
    @Query("SELECT * FROM soldier")
    fun getSoldiers() : List<Soldier>

    @Query("SELECT * FROM soldier WHERE rank LIKE :rank LIMIT 10")
    fun findSoldierByRank(rank : String): List<Soldier>

    @Query("SELECT * FROM soldier WHERE name LIKE :name LIMIT 10")
    fun findSoldierByName(name : String): List<Soldier>

    @Query("SELECT * FROM soldier WHERE age LIKE :age ")
    fun findSoldierByAge(age : Int): List<Soldier>

    @Query("DELETE FROM soldier")
    fun clearSoldierTable()

    @Query("SELECT COUNT(*) FROM soldier")
    fun getSoldierTableSize() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(soldier : Soldier)
}