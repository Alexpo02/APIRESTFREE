package edu.pract5.apirestfree.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "alimentos")
data class Alimento(
    @SerializedName("carbohydrates_total_g")
    val carbohydratesTotalG: Double,
    @SerializedName("cholesterol_mg")
    val cholesterolMg: Int,
    @SerializedName("fat_saturated_g")
    val fatSaturatedG: Double,
    @SerializedName("fat_total_g")
    val fatTotalG: Double,
    @SerializedName("fiber_g")
    val fiberG: Double,
    @PrimaryKey
    @SerializedName("name")
    val name: String,
    @SerializedName("potassium_mg")
    val potassiumMg: Int,
    @SerializedName("sodium_mg")
    val sodiumMg: Int,
    @SerializedName("sugar_g")
    val sugarG: Double,
) : Parcelable