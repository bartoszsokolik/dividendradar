package pl.sokolik.bartosz.dividendradar.adapter.query

import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.adapter.db.DbDividendRadarRepository
import pl.sokolik.bartosz.dividendradar.adapter.rest.DividendRadarCompanyResponse
import pl.sokolik.bartosz.dividendradar.infrastructure.DividendRadarCompanyDocument
import java.time.LocalDate

@Component
class GetDividendRadarCompanyQuery(private val dbDividendRadarRepository: DbDividendRadarRepository) {

    fun getDividendRadarCompany(symbol: String, summaryDate: LocalDate) : DividendRadarCompanyResponse {
        return dbDividendRadarRepository.load(symbol, summaryDate)
            ?.let { DividendRadarCompanyResponseMapper.map(it) }
            ?: throw NoSuchElementException("No company with symbol $symbol and summaryDate $summaryDate")
    }

    object DividendRadarCompanyResponseMapper {
        fun map(dividendRadarCompanyDocument: DividendRadarCompanyDocument): DividendRadarCompanyResponse {
            return DividendRadarCompanyResponse(
                dividendRadarCompanyDocument.symbol,
                dividendRadarCompanyDocument.company,
                dividendRadarCompanyDocument.sector,
                dividendRadarCompanyDocument.noYears,
                dividendRadarCompanyDocument.price,
                dividendRadarCompanyDocument.divYield,
                dividendRadarCompanyDocument.fiveAvgDividendYield,
                dividendRadarCompanyDocument.dgr1Year,
                dividendRadarCompanyDocument.dgr3Year,
                dividendRadarCompanyDocument.dgr5Year,
                dividendRadarCompanyDocument.dgr10Year,
                dividendRadarCompanyDocument.fairValue,
                dividendRadarCompanyDocument.fairValuePercent,
                dividendRadarCompanyDocument.revenueOneYear,
                dividendRadarCompanyDocument.roe,
                dividendRadarCompanyDocument.rotc,
                dividendRadarCompanyDocument.pe,
                dividendRadarCompanyDocument.pbv,
                dividendRadarCompanyDocument.summaryDate,
            )
        }
    }
}