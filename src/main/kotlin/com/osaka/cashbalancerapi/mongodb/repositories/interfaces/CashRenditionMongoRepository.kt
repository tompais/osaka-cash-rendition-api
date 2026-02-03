package com.osaka.cashbalancerapi.repositories.mongodb

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CashRenditionMongoRepository : CoroutineCrudRepository<CashRenditionDocument, UUID>
