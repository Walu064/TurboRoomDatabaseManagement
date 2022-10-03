package pl.edu.wat.turboroomdatabasemanagement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soldier")
data class Soldier (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "rank")
    var rank: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "age")
    var age : Int){}