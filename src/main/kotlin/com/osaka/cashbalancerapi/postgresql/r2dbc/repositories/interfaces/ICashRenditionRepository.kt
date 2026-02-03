package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.CashRenditionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ICashRenditionRepository : CoroutineCrudRepository<CashRenditionEntity, UUID>
