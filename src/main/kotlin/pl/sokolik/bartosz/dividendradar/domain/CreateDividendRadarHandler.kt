package pl.sokolik.bartosz.dividendradar.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.adapter.db.DbDividendRadarRepository
import pl.sokolik.bartosz.dividendradar.domain.exception.DividendRadarSummaryExistsException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Component
class CreateDividendRadarHandler(
    private val dividendRadarFileDownloadService: DividendRadarFileDownloadPort,
    private val dividendRadarFileParser: DividendRadarFileParser,
    private val dividendRadarImportEntryMapper: DividendRadarImportEntryMapper,
    private val dividendRadarRepository: DbDividendRadarRepository
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CreateDividendRadarHandler::class.java)
    }

    fun handle(summaryDate: LocalDate) {
        if (dividendRadarRepository.existsBySummaryDate(summaryDate)) {
            throw DividendRadarSummaryExistsException(summaryDate)
        }

        val dividendRadarFile = dividendRadarFileDownloadService.download(summaryDate.toString())
        val parsedFile = dividendRadarFileParser.parse(dividendRadarFile)
        val dividendRadarEntries = dividendRadarImportEntryMapper.map(parsedFile)
        dividendRadarRepository.saveAll(dividendRadarEntries, summaryDate)
        log.info("Finished saving dividend radar entries with date: {}", summaryDate)
    }
}