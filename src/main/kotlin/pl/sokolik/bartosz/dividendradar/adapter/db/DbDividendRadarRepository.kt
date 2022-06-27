package pl.sokolik.bartosz.dividendradar.adapter.db

import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.model.DividendRadarEntry
import pl.sokolik.bartosz.dividendradar.infrastructure.DividendRadarCompanyDocument
import pl.sokolik.bartosz.dividendradar.infrastructure.DividendRadarSpringDataRepository
import java.time.LocalDate
import java.util.UUID

@Component
class DbDividendRadarRepository(private val dividendRadarSpringDataRepository: DividendRadarSpringDataRepository) {

    fun save(dividendRadarEntry: DividendRadarEntry, summaryDate: LocalDate) {
        dividendRadarSpringDataRepository.save(map(dividendRadarEntry, summaryDate))
    }

    fun saveAll(dividendRadarEntries: List<DividendRadarEntry>, summaryDate: LocalDate) {
        dividendRadarSpringDataRepository.saveAll(dividendRadarEntries.map { map(it, summaryDate) })
    }

    fun load(symbol: String, summaryDate: LocalDate) : DividendRadarCompanyDocument? {
        return dividendRadarSpringDataRepository.findBySymbolAndSummaryDate(symbol, summaryDate)
    }

    fun load(summaryDate: LocalDate): List<DividendRadarCompanyDocument> {
        return dividendRadarSpringDataRepository.findAllBySummaryDate(summaryDate)
    }

    fun existsBySummaryDate(summaryDate: LocalDate): Boolean {
        return dividendRadarSpringDataRepository.existsBySummaryDate(summaryDate)
    }

    private fun map(dividendRadarEntry: DividendRadarEntry, summaryDate: LocalDate) : DividendRadarCompanyDocument {
        return DividendRadarCompanyDocument(
            UUID.randomUUID(),
            dividendRadarEntry.symbol!!,
            dividendRadarEntry.company!!,
            dividendRadarEntry.sector!!,
            dividendRadarEntry.noYears!!,
            dividendRadarEntry.price!!,
            dividendRadarEntry.divYield!!,
            dividendRadarEntry.fiveAvgDividendYield,
            dividendRadarEntry.dgr1Year,
            dividendRadarEntry.dgr3Year,
            dividendRadarEntry.dgr5Year,
            dividendRadarEntry.dgr10Year,
            dividendRadarEntry.fairValue!!,
            dividendRadarEntry.fairValuePercent!!,
            dividendRadarEntry.revenueOneYear!!,
            dividendRadarEntry.roe,
            dividendRadarEntry.rotc,
            dividendRadarEntry.pe!!,
            dividendRadarEntry.pbv!!,
            summaryDate)
    }
}