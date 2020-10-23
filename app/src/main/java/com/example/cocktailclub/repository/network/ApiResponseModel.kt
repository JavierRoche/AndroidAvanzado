package com.example.cocktailclub.repository.network

import androidx.room.Entity
import androidx.room.PrimaryKey
// Sino reconoce esta libreria es que faltan las dependencias de Retrofit
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ApiResponseModel(

	@field:SerializedName("drinks")
	var drinks: List<DrinksItem?>? = null

) : Serializable

// Para transformar la response del api en una tabla de Room
@Entity(tableName = "cocktails_tb")

data class DrinksItem(

	@PrimaryKey
	@field:SerializedName("idDrink")
	val idDrink: String,

	@field:SerializedName("strDrink")
	val strDrink: String? = null,

	@field:SerializedName("strCategory")
	val strCategory: String? = null,

	@field:SerializedName("strAlcoholic")
	val strAlcoholic: String? = null,

	@field:SerializedName("strGlass")
	val strGlass: String? = null,

	@field:SerializedName("strInstructions")
	val strInstructions: String? = null,

	@field:SerializedName("strDrinkThumb")
	val strDrinkThumb: String? = null,

	// Ingredients

	@field:SerializedName("strIngredient1")
	val strIngredient1: String? = null,

	@field:SerializedName("strIngredient2")
	val strIngredient2: String? = null,

	@field:SerializedName("strIngredient3")
	val strIngredient3: String? = null,

	@field:SerializedName("strIngredient4")
	val strIngredient4: String? = null,

	@field:SerializedName("strIngredient5")
	val strIngredient5: String? = null,

	@field:SerializedName("strIngredient6")
	val strIngredient6: String? = null,

	// Measures

	@field:SerializedName("strMeasure1")
	val strMeasure1: String? = null,

	@field:SerializedName("strMeasure2")
	val strMeasure2: String? = null,

	@field:SerializedName("strMeasure3")
	val strMeasure3: String? = null,

	@field:SerializedName("strMeasure4")
	val strMeasure4: String? = null,

	@field:SerializedName("strMeasure5")
	val strMeasure5: String? = null,

	@field:SerializedName("strMeasure6")
	val strMeasure6: String? = null,

	@field:SerializedName("bolFavorite")
	var bolFavorite: Boolean = false

) : Serializable