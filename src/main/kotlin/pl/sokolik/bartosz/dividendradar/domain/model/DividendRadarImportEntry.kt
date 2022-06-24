package pl.sokolik.bartosz.dividendradar.domain.model

import java.math.BigDecimal

data class DividendRadarImportEntry(val data: Map<String, String?>) {
    fun getString(key: String): String? {
        return data[key]
    }

    fun getBigDecimal(key: String): BigDecimal? {
        return data[key]?.let {s: String -> BigDecimal(s) }
    }

}