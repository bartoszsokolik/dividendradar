package pl.sokolik.bartosz.dividendradar.adapter.rest

import java.math.BigDecimal
import java.time.LocalDate

data class DividendRadarCompanyResponse(
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
