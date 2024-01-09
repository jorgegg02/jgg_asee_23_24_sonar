package es.unex.asee.mercapp.data.api

import com.google.gson.annotations.SerializedName


data class SuperCategoryPage (

  @SerializedName("next"     ) var next     : String?            = null,
  @SerializedName("count"    ) var count    : Int?               = null,
  @SerializedName("results"  ) var SuperCategories  : ArrayList<SuperCategoryInfo> = arrayListOf(),
  @SerializedName("previous" ) var previous : String?            = null

)