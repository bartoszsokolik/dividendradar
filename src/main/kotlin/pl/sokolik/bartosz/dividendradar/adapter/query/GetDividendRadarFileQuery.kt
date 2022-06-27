package pl.sokolik.bartosz.dividendradar.adapter.query

import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.adapter.file.DividendRadarExcelWriter
import pl.sokolik.bartosz.dividendradar.adapter.db.DbDividendRadarRepository
import pl.sokolik.bartosz.dividendradar.adapter.rest.DividendRadarFileData
import pl.sokolik.bartosz.dividendradar.infrastructure.DividendRadarCompanyDocument
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Component
class GetDividendRadarFileQuery(
    private val dbDividendRadarRepository: DbDividendRadarRepository,
    private val dividendRadarExcelWriter: DividendRadarExcelWriter
) {

    companion object {
        private const val AT_FAIR_VALUE = "At Fair Value"
        private const val IN_THE_MARGIN_OF_SAFETY = "In the Margin of Safety"
        private const val FILENAME = "dividend-radar-%s.xlsx"
    }

    fun getDividendRadarFile(dividendRadarDate: LocalDate?): DividendRadarFileData {
        val summaryDate = dividendRadarDate?.takeIf { it.dayOfWeek == DayOfWeek.FRIDAY } ?: LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY))
        val filteredCompanies = dbDividendRadarRepository.load(summaryDate).filter { dividendRadarPredicate(it) }
        val file = dividendRadarExcelWriter.create(filteredCompanies)
        return DividendRadarFileData(file, String.format(FILENAME, summaryDate.toString()))
    }

    private fun dividendRadarPredicate(entry: DividendRadarCompanyDocument) =
        priceToEarningPredicate(entry) && roePredicate(entry) && fairValuePredicate(entry) && divYieldPredicate(entry)

    private fun priceToEarningPredicate(entry: DividendRadarCompanyDocument) =
        entry.pe < BigDecimal(16)

    private fun roePredicate(entry: DividendRadarCompanyDocument) = entry.roe?.let { it > BigDecimal(20) } ?: false

    private fun fairValuePredicate(entry: DividendRadarCompanyDocument) =
        entry.fairValue == AT_FAIR_VALUE || entry.fairValue == IN_THE_MARGIN_OF_SAFETY

    private fun divYieldPredicate(entry: DividendRadarCompanyDocument) =
        entry.divYield < BigDecimal(6.5) && entry.divYield > BigDecimal(2)
}
