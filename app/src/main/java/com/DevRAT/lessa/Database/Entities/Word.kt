package com.DevRAT.lessa.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "word_table")

data class Word (
    //@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id" )val id: Int,
    @PrimaryKey @ColumnInfo (name = "Palabra") val palabra: String,
    @ColumnInfo (name = "Categoria") val categoria: String,
    @ColumnInfo (name = "seña") val seña: Int
)