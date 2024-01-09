package es.unex.asee.mercapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import es.unex.asee.mercapp.data.model.User
import androidx.room.RoomDatabase
import es.unex.asee.mercapp.data.model.CrossRefCategorySubCategory
import es.unex.asee.mercapp.data.model.CrossRefSubCategoryProduct
import es.unex.asee.mercapp.data.model.CrossRefSuperCategoryCategory
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.data.model.UserProductCrossRef
import es.unex.asee.mercapp.database.dao.CategoryDAO
import es.unex.asee.mercapp.database.dao.UserDao


@Database(entities = [User::class, GenericCategory::class, Product::class, UserProductCrossRef::class, CrossRefSuperCategoryCategory::class, CrossRefCategorySubCategory::class, CrossRefSubCategoryProduct::class], version = 1)
abstract class MercappDatabase : RoomDatabase() {


    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDAO


    companion object {
        private var INSTANCE: MercappDatabase? = null

        fun getInstance(context: Context): MercappDatabase? {
            if (INSTANCE == null) {
                synchronized(MercappDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MercappDatabase::class.java, "MercappDatabase.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}