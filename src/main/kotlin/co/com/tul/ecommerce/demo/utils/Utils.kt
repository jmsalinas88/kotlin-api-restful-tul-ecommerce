package co.com.tul.ecommerce.demo.utils

import java.util.*

class Utils {

    companion object{
        fun  strIdsToUUIDList(ids : String): List<UUID>{
            return ids.split(",").map { UUID.fromString(it.trim()) }
        }
    }
}