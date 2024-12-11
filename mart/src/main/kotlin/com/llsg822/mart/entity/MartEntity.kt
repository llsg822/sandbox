package com.llsg822.mart.entity

import com.llsg822.mart.utils.createPoint
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.locationtech.jts.geom.Point

typealias MartId = Long

@Entity
@Table(name = "mart")
class MartEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: MartId = 0,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "coordinate_4326", nullable = false, columnDefinition = "geometry(point, 4326)")
    val coordinate4326: Point,
    @Column(name = "coordinate_5179", nullable = false, columnDefinition = "geometry(Point, 5179)")
    val coordinate5179: Point
) {
    companion object {
        fun create(
            name: String,
            latitude: Double,
            longitude: Double,
            x5179: Double,
            y5179: Double,
        ): MartEntity {
            return MartEntity(
                name = name,
                coordinate4326 = createPoint(
                    y = latitude,
                    x = longitude,
                    srid = 4326,
                ),
                coordinate5179 = createPoint(
                    y = y5179,
                    x = x5179,
                    srid = 5179
                ),
            )
        }
    }

    fun updateName(name: String) {
        this.name = name
    }
}