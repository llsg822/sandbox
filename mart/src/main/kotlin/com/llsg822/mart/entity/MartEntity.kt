package com.llsg822.mart.entity

import com.llsg822.mart.utils.createPoint
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.locationtech.jts.geom.Point

typealias MartId = Long

@Entity
class MartEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: MartId = 0,
    var name: String,
    @Column(columnDefinition = "geometry(point, 4326)")
    val coordinate: Point,
) {
    companion object {
        fun create(
            name: String,
            latitude: Double,
            longitude: Double,
        ): MartEntity {
            return MartEntity(
                name = name,
                coordinate = createPoint(latitude, longitude)
            )
        }
    }

    fun updateName(name: String) {
        this.name = name
    }
}