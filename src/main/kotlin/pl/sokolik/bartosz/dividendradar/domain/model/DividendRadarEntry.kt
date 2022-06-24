package pl.sokolik.bartosz.dividendradar.domain.model

import java.math.BigDecimal

data class DividendRadarEntry(
    val symbol: String?,
    val company: String?,
    val sector: String?,
    val noYears: Int?,
    val price: BigDecimal?,
    val divYield: BigDecimal?,
    val fiveAvgDividendYield: BigDecimal?,
    val dgr1Year: BigDecimal?,
    val dgr3Year: BigDecimal?,
    val dgr5Year: BigDecimal?,
    val dgr10Year: BigDecimal?,
    val fairValue: String?,
    val fairValuePercent: Int?,
    val revenueOneYear: BigDecimal?,
    val roe: BigDecimal?,
    val rotc: BigDecimal?,
    val pe: BigDecimal?,
    val pbv: BigDecimal?
)