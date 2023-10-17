package com.rob_products_data.feature.checkout

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.rob_product_common.utils.DispatcherProvider
import com.rob_products.models.CartItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.File
import java.io.OutputStreamWriter
import javax.inject.Inject
import kotlin.random.Random

class OrderJsonFileStorage @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
    private val dispatchers: DispatcherProvider,
) {

    suspend fun saveOrderToJson(listOfCartItems: List<CartItem>, total: Int): Pair<String?, Boolean> {
        val uniqueID = (100000 + Random.nextInt(900000)).toString()
        val uniqueOrderID = "order_$uniqueID"
        val order =
            Order(orderId = uniqueOrderID, listOfCarts = listOfCartItems, totalPrice = total)
        val orderFileName = "${order.orderId}.json"
        val file = File(context.filesDir, orderFileName)
        return withContext(dispatchers.io) {
            try {
                if (!file.exists()) {
                    file.createNewFile()
                }

                val fileOutputStream = context.openFileOutput(orderFileName, Context.MODE_PRIVATE)
                val writer = OutputStreamWriter(fileOutputStream)

                gson.toJson(order, writer)
                writer.close()
                fileOutputStream.close()
                uniqueID to true
            } catch (e: Exception) {
                e.printStackTrace()
                null to false
            }
        }
    }
}
