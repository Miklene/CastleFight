package com.miklene.castlefight.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ban(@PrimaryKey(autoGenerate = true) val id: Long, val banFightId: Long, val owner: String, val ban: String) {
}