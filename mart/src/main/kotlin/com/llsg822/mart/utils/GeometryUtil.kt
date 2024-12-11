package com.llsg822.mart.utils

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel

val geometryFactory = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING))

fun createPoint(x: Double, y: Double, srid: Int): Point {
    return geometryFactory.createPoint(Coordinate(x, y)).also {
        it.srid = srid
    }
}