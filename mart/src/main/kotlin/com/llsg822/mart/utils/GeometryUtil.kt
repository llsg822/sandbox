package com.llsg822.mart.utils

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel

val geometryFactory = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING), 4326)

fun createPoint(latitude: Double, longitude: Double): Point {
    return geometryFactory.createPoint(Coordinate(longitude, latitude))
}