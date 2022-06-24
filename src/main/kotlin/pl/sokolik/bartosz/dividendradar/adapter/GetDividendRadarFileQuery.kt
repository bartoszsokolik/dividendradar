package pl.sokolik.bartosz.dividendradar.adapter

import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelWriter
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarFileDownloadPort
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarFileParser
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarImportEntryMapper
import pl.sokolik.bartosz.dividendradar.domain.model.DividendRadarEntry
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.time.DayOfWeek.FRIDAY
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters.previousOrSame

@Component
class GetDividendRadarFileQuery(
    private val dividendRadarFileDownloadService: DividendRadarFileDownloadPort,
    private val dividendRadarFileParser: DividendRadarFileParser,
    private val dividendRadarImportEntryMapper: DividendRadarImportEntryMapper,
    private val dividendRadarExcelWriter: DividendRadarExcelWriter
) {

    companion object {
        private const val AT_FAIR_VALUE = "At Fair Value"
        private const val IN_THE_MARGIN_OF_SAFETY = "In the Margin of Safety"
        private const val FILENAME = "dividend-radar-%s.xlsx"
    }

    fun getDividendRadarFile(dividendRadarDate: LocalDate?): DividendRadarFileData {
        val date = dividendRadarDate?.takeIf { it.dayOfWeek == FRIDAY } ?: LocalDate.now().with(previousOrSame(FRIDAY))
        val dividendRadarFile = dividendRadarFileDownloadService.download(date.toString())
        val parsedFile = dividendRadarFileParser.parse(dividendRadarFile)
        val dividendRadarEntries = dividendRadarImportEntryMapper.map(parsedFile)
        val filteredElements = dividendRadarEntries.filter { dividendRadarPredicate(it) }
        val file =  dividendRadarExcelWriter.create(filteredElements)
        return DividendRadarFileData(file, String.format(FILENAME, date.toString()))
    }

    private fun dividendRadarPredicate(entry: DividendRadarEntry) =
        priceToEarningPredicate(entry) && roePredicate(entry) && fairValuePredicate(entry) && divYieldPredicate(entry)

    private fun priceToEarningPredicate(entry: DividendRadarEntry) =
        entry.pe?.let { i: BigDecimal -> i < BigDecimal(16) } ?: false

    private fun roePredicate(entry: DividendRadarEntry) =
        entry.roe?.let { i: BigDecimal -> i > BigDecimal(20) } ?: false

    private fun fairValuePredicate(entry: DividendRadarEntry) =
        entry.fairValue?.let { i: String -> i == AT_FAIR_VALUE || i == IN_THE_MARGIN_OF_SAFETY } ?: false

    private fun divYieldPredicate(entry: DividendRadarEntry) =
        entry.divYield?.let { it < BigDecimal(6.5) } ?: false
                && entry.divYield?.let { it > BigDecimal(2) } ?: false


}