package pl.sokolik.bartosz.dividendradar.infrastructure

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Document(collection = "dividend_radar_company")
data class DividendRadarCompanyDocument(
    @Id
    val id: UUID,
    val symbol: String,
    val company: String,
    val sector: String,
    val noYears: Int,
    val price: BigDecimal,
    val divYield: BigDecimal,
    val fiveAvgDividendYield: BigDecimal? = null,
    val dgr1Year: BigDecimal? = null,
    val dgr3Year: BigDecimal? = null,
    val dgr5Year: BigDecimal? = null,
    val dgr10Year: BigDecimal? = null,
    val fairValue: String,
    val fairValuePercent: Int,
    val revenueOneYear: BigDecimal,
    val roe: BigDecimal?,
    val rotc: BigDecimal?,
    val pe: BigDecimal,
    val pbv: BigDecimal,
    val summaryDate: LocalDate
)