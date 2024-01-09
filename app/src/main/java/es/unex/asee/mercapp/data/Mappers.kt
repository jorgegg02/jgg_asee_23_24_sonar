package es.unex.asee.mercapp.data


import es.unex.asee.mercapp.data.api.CategoryFromSuperCategory
import es.unex.asee.mercapp.data.api.CategoryPageInfo
import es.unex.asee.mercapp.data.api.PriceInstructions
import es.unex.asee.mercapp.data.api.ProductInfo
import es.unex.asee.mercapp.data.api.SubCategoryInfo
import es.unex.asee.mercapp.data.api.SuperCategoryInfo
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.Product


fun ProductInfo.toProduct() = Product(
    productId = id ?: 0,
    productName = slug ?: "",
    productPackaging = packaging ?: "",
    productUnitPrice = priceInstructions?.unitPrice?.toFloat()?: 0.0f,
    productBulkPrice = priceInstructions?.bulkPrice?.toFloat()?: 0.0f,
    imagePath = thumbnail ?: "",
    //"@drawable/mercapp_banner",
    isInCart = false

)

fun Product.toProductInfo() = ProductInfo(
    id = productId,
    slug = productName,
    packaging = productPackaging,
    priceInstructions = PriceInstructions(
        unitPrice = productUnitPrice.toString(),
        bulkPrice = productBulkPrice.toString()
    )
)

fun SubCategoryInfo.toGenericCategory() = GenericCategory(
    GenCatId = id ?: 0,
    GenCatName = name ?: "",
    GenCatOrder = order ?: 0,
    GenCatLevel = 3
)

fun CategoryPageInfo.toGenericCategory() = GenericCategory(
    GenCatId = id ?: 0,
    GenCatName = name ?: "",
    GenCatOrder = order ?: 0,
    GenCatLevel = 2
)

fun SuperCategoryInfo.toGenericCategory() = GenericCategory(
    GenCatId = id ?: 0,
    GenCatName = name ?: "",
    GenCatOrder = order ?: 0,
    GenCatLevel = 1
)

fun CategoryFromSuperCategory.toGenericCategory() = GenericCategory(
    GenCatId = id ?: 0,
    GenCatName = name ?: "",
    GenCatOrder = order ?: 0,
    GenCatLevel = 2
)


