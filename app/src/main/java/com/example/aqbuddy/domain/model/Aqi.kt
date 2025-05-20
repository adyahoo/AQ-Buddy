package com.example.aqbuddy.domain.model

import androidx.compose.ui.graphics.Color
import com.example.aqbuddy.utils.getAqiColor
import com.example.aqbuddy.utils.getAqiIcon
import org.osmdroid.util.GeoPoint

data class AqiData(
    val itemID: String,
    val location: GeoPoint,
    val PM2_5: Double,
    val color: Color,
    val icon: Int
) {
    companion object {
        fun from(map: Map<String, Any>) = AqiData(
            itemID = map["itemID"] as String,
            location = GeoPoint(
                map["latitude"] as Double,
                map["longitude"] as Double,
            ),
            PM2_5 = map["PM2.5"] as Double,
            color = (map["PM2.5"] as Double).getAqiColor(),
            icon = (map["PM2.5"] as Double).getAqiIcon(),
        )
    }
}