package es.unex.asee.mercapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import es.unex.asee.mercapp.data.model.CrossRefCategorySubCategory
import es.unex.asee.mercapp.data.model.CrossRefSubCategoryProduct
import es.unex.asee.mercapp.data.model.CrossRefSuperCategoryCategory
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.data.model.UserProductCrossRef


@Dao
interface CategoryDAO {

    @Query("SELECT * FROM genericcategory WHERE GenCatLevel = 1")
    suspend fun getSuperCategories(): List<GenericCategory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSuperCategories(Categories: List<GenericCategory>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCategories(Categories: List<GenericCategory>)

    //SuperCategory-Category operations

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: GenericCategory)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSupercategoryCategoryRelation(crossRef: CrossRefSuperCategoryCategory)

    @Transaction
    suspend fun insertCategoryAndRelate(category: GenericCategory, superCategory: GenericCategory) {
        insertCategory(category)
        insertSupercategoryCategoryRelation(CrossRefSuperCategoryCategory(superCategory.GenCatId, category.GenCatId))
    }

    @Query("SELECT * FROM genericcategory WHERE GenCatLevel = 2 AND GenCatId IN (SELECT categoryId FROM crossrefsupercategorycategory WHERE superCategoryId = :superCategoryId)")
    suspend fun getCategories(superCategoryId: Int): List<GenericCategory>

    @Query("SELECT * FROM genericcategory WHERE GenCatLevel = 1 AND GenCatId IN (SELECT supercategoryId FROM crossrefsupercategorycategory WHERE categoryId = :categoryId)")
    suspend fun getSuperCategoryFromCategory(categoryId: Int): GenericCategory

    //Category-SubCategory operations

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubCategorie(subCategory: GenericCategory)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategorySubCategoryRelation(crossRef: CrossRefCategorySubCategory)

    @Transaction
    suspend fun insertSubCategoryAndRelate(subCategory: GenericCategory, category: GenericCategory) {
        insertSubCategorie(subCategory)
        insertCategorySubCategoryRelation(CrossRefCategorySubCategory(category.GenCatId, subCategory.GenCatId))
    }

    @Query("SELECT * FROM genericcategory WHERE GenCatLevel = 3 AND GenCatId IN (SELECT subCategoryId FROM crossrefcategorysubcategory WHERE categoryId = :categoryId)")
    suspend fun getSubCategories(categoryId: Int): List<GenericCategory>

    @Query("SELECT * FROM genericcategory WHERE GenCatLevel = 2 AND GenCatId IN (SELECT categoryId FROM crossrefcategorysubcategory WHERE subCategoryId = :subCategoryId)")
    suspend fun getCategoryFromSubCategory(subCategoryId: Int): GenericCategory

    //SubCategory-Product operations

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubCategoryProductRelation(crossRef: CrossRefSubCategoryProduct)

    @Transaction
    suspend fun insertProductAndRelate(product: Product, subCategory: GenericCategory) {
            insertProduct(product)
            insertSubCategoryProductRelation(CrossRefSubCategoryProduct(subCategory.GenCatId, product.productId))
    }

    @Query("SELECT * FROM product WHERE productId IN (SELECT productId FROM crossrefsubcategoryproduct WHERE subCategoryId = :subCategoryId)")
    suspend fun getProducts(subCategoryId: Int): List<Product>


    //User-Product operations

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserProductRelation(UserProduct: UserProductCrossRef)

    suspend fun insertProductInUserCart(userId: Long, productId: Int) {
        insertUserProductRelation(UserProductCrossRef(userId, productId))
    }

    @Delete
    suspend fun deleteUserProductRelation(UserProduct: UserProductCrossRef)

    suspend fun deleteProductFromUserCart(userId: Long, productId: Int) {
        deleteUserProductRelation(UserProductCrossRef(userId, productId))
    }

    @Transaction
    @Query("SELECT * FROM product WHERE productId IN (SELECT productId FROM UserProductCrossRef WHERE userId = :userId)")
    fun getProductsFromUserCart(userId: Long): LiveData<List<Product>>

}