package pl.sokolik.bartosz.dividendradar.infrastructure

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate
import java.util.UUID

interface DividendRadarSpringDataRepository : MongoRepository<DividendRadarCompanyDocument, UUID> {

    fun findBySymbolAndSummaryDate(symbol: String, summaryDate: LocalDate): DividendRadarCompanyDocument?

    fun findAllBySummaryDate(summaryDate: LocalDate): List<DividendRadarCompanyDocument>

    fun existsBySummaryDate(localDate: LocalDate): Boolean
}