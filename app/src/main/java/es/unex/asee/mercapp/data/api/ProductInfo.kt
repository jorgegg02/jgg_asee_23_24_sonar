package es.unex.asee.mercapp.data.api

import com.google.gson.annotations.SerializedName
import es.unex.asee.mercapp.data.api.SuperCategoryInfo


data class ProductInfo (

    @SerializedName("id"                   ) var id                  : Int?               = null,
    @SerializedName("slug"                 ) var slug                : String?               = null,
    @SerializedName("limit"                ) var limit               : Int?                  = null,
    @SerializedName("badges"               ) var badges              : Badges?               = Badges(),
    @SerializedName("packaging"            ) var packaging           : String?               = null,
    @SerializedName("published"            ) var published           : Boolean?              = null,
    @SerializedName("share_url"            ) var shareUrl            : String?               = null,
    @SerializedName("thumbnail"            ) var thumbnail           : String?               = null,
    @SerializedName("categories"           ) var categories          : ArrayList<SuperCategoryInfo> = arrayListOf(),
    @SerializedName("display_name"         ) var displayName         : String?               = null,
    @SerializedName("unavailable_from"     ) var unavailableFrom     : String?               = null,
    @SerializedName("price_instructions"   ) var priceInstructions   : PriceInstructions?    = PriceInstructions(),
    @SerializedName("unavailable_weekdays" ) var unavailableWeekdays : ArrayList<String>     = arrayListOf()

)