package es.unex.asee.mercapp.data.api

import com.google.gson.annotations.SerializedName


data class Badges (

  @SerializedName("is_water"           ) var isWater          : Boolean? = null,
  @SerializedName("requires_age_check" ) var requiresAgeCheck : Boolean? = null

)