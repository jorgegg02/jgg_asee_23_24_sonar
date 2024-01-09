package es.unex.asee.mercapp.data.api

import com.google.gson.annotations.SerializedName


data class CategoryPageInfo (

    @SerializedName("id"          ) var id         : Int?                  = null,
    @SerializedName("name"        ) var name       : String?               = null,
    @SerializedName("order"       ) var order      : Int?                  = null,
    @SerializedName("layout"      ) var layout     : Int?                  = null,
    @SerializedName("published"   ) var published  : Boolean?              = null,
    @SerializedName("categories"  ) var categories : ArrayList<SubCategoryInfo> = arrayListOf(),
    @SerializedName("is_extended" ) var isExtended : Boolean?              = null

)