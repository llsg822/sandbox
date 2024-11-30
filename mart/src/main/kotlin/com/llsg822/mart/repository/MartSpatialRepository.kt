package com.llsg822.mart.repository

import com.llsg822.mart.entity.MartEntity
import com.llsg822.mart.entity.MartId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MartSpatialRepository: JpaRepository<MartEntity, MartId> {
    @Query("""
        select new com.llsg822.mart.repository.FindNearByMartsQueryDto(
            m,
            cast(
                st_distance(
                    geography(m.coordinate),
                    geography(
                        st_setsrid(
                            st_makepoint(:longitude, :latitude), 4326
                        )
                    )
                ) as double
            )
        )
        from MartEntity m
        order by
            st_distance(
                geography(m.coordinate),
                geography(
                    st_setsrid(
                        st_makepoint(:longitude, :latitude), 4326
                    )
                )
            ) asc
    """)
    fun findNearByMarts(
        latitude: Double,
        longitude: Double,
        pageable: Pageable,
    ): Page<FindNearByMartsQueryDto>
}

data class FindNearByMartsQueryDto(
    val mart: MartEntity,
    val distance: Double,
)