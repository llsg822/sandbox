package com.llsg822.mart.repository

import com.llsg822.mart.entity.MartId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class MartSpatialRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun transform(
        latitude: Double,
        longitude: Double,
        srid: Int,
    ): TransformQueryDto {
        val params = MapSqlParameterSource()
            .addValue("latitude", latitude)
            .addValue("longitude", longitude)
            .addValue("srid", srid)

        val query = """
            select
                st_x(transformed) x,
                st_y(transformed) y
            from (
                select st_transform(
                    st_setsrid(st_makepoint(:longitude, :latitude), 4326),
                    :srid
                ) transformed
            ) t
    """
        val results = jdbcTemplate.query(query, params) { rs, _ ->
            TransformQueryDto(
                x = rs.getDouble("x"),
                y = rs.getDouble("y")
            )
        }
        return results[0]
    }

    fun findNearByMarts(
        latitude: Double,
        longitude: Double,
        pageable: Pageable,
    ): Page<FindNearByMartsQueryDto> {
        val params = MapSqlParameterSource()
            .addValue("latitude", latitude)
            .addValue("longitude", longitude)
            .addValue("limit", pageable.pageSize)
            .addValue("offset", pageable.offset)

        val mainQuery = """
            select
                m.id martId,
                m.name name,
                st_y(m.coordinate_4326) latitude,
                st_x(m.coordinate_4326) longitude,
                st_distance(
                    m.coordinate_5179,
                    st_transform(
                        st_setsrid(st_makepoint(:longitude, :latitude), 4326),
                        5179
                    )
                ) distance
            from mart m
            order by m.coordinate_5179 <-> st_transform(
                st_setsrid(st_makepoint(:longitude, :latitude), 4326),
                5179
            ) asc
            offset :offset rows fetch next :limit rows only
        """

        val countQuery = """
            select count(m.id)
            from mart m
        """

        // 전체 건수 조회
        val total = jdbcTemplate.queryForObject(countQuery, params, Long::class.java) ?: 0

        // 데이터 조회 및 DTO 매핑
        val results = jdbcTemplate.query(mainQuery, params) { rs, _ ->
            FindNearByMartsQueryDto(
                martId = rs.getLong("martId"),
                name = rs.getString("name"),
                latitude = rs.getDouble("latitude"),
                longitude = rs.getDouble("longitude"),
                distance = rs.getDouble("distance"),
            )
        }

        return PageImpl(results, pageable, total)
    }
}

data class FindNearByMartsQueryDto(
    val martId: MartId,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Double,
)

data class TransformQueryDto(
    val x: Double,
    val y: Double,
)