package es.unex.asee.mercapp.data.api

import com.google.gson.annotations.SerializedName


data class PriceInstructions (

  @SerializedName("iva"                    ) var iva                  : Int?     = null,
  @SerializedName("is_new"                 ) var isNew                : Boolean? = null,
  @SerializedName("is_pack"                ) var isPack               : Boolean? = null,
  @SerializedName("pack_size"              ) var packSize             : String?  = null,
  @SerializedName("unit_name"              ) var unitName             : String?  = null,
  @SerializedName("unit_size"              ) var unitSize             : Float?     = null,
  @SerializedName("bulk_price"             ) var bulkPrice            : String?  = null,
  @SerializedName("unit_price"             ) var unitPrice            : String?  = null,
  @SerializedName("approx_size"            ) var approxSize           : Boolean? = null,
  @SerializedName("size_format"            ) var sizeFormat           : String?  = null,
  @SerializedName("total_units"            ) var totalUnits           : String?  = null,
  @SerializedName("unit_selector"          ) var unitSelector         : Boolean? = null,
  @SerializedName("bunch_selector"         ) var bunchSelector        : Boolean? = null,
  @SerializedName("drained_weight"         ) var drainedWeight        : String?  = null,
  @SerializedName("selling_method"         ) var sellingMethod        : Float?     = null,
  @SerializedName("price_decreased"        ) var priceDecreased       : Boolean? = null,
  @SerializedName("reference_price"        ) var referencePrice       : String?  = null,
  @SerializedName("min_bunch_amount"       ) var minBunchAmount       : Float?     = null,
  @SerializedName("reference_format"       ) var referenceFormat      : String?  = null,
  @SerializedName("previous_unit_price"    ) var previousUnitPrice    : String?  = null,
  @SerializedName("increment_bunch_amount" ) var incrementBunchAmount : Float?     = null

)