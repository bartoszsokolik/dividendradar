package pl.sokolik.bartosz.dividendradar.domain.exception

import java.time.LocalDate

class DividendRadarSummaryExistsException(summaryDate: LocalDate) : RuntimeException("Dividend radar summary for $summaryDate already exists.") {
}