package pl.sokolik.bartosz.dividendradar.domain

import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.model.DividendRadarEntry
import pl.sokolik.bartosz.dividendradar.domain.model.DividendRadarImportEntry

@Component
class DividendRadarImportEntryMapper {

    fun map(importedEntries: List<DividendRadarImportEntry>): List<DividendRadarEntry> {
        return importedEntries.map { map(it) }
    }

    private fun map(importEntry: DividendRadarImportEntry) : DividendRadarEntry {
        return DividendRadarEntry(
            importEntry.getString(DividendRadarExcelDictionary.SYMBOL.value),
            importEntry.getString(DividendRadarExcelDictionary.COMPANY.value),
            importEntry.getString(DividendRadarExcelDictionary.SECTOR.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.NO_YEARS.value)?.toInt(),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.PRICE.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.DIV_YIELD.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.FIVE_YEAR_AVG_YIELD.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.DGR_1_YEAR.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.DGR_3_YEAR.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.DGR_5_YEAR.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.DGR_10_YEAR.value),
            importEntry.getString(DividendRadarExcelDictionary.FAIR_VALUE.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.FAIR_VALUE_PERCENT.value)?.toInt(),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.REVENUE_ONE_YEAR.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.ROE.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.ROTC.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.P_E.value),
            importEntry.getBigDecimal(DividendRadarExcelDictionary.P_BV.value),
        )
    }
}