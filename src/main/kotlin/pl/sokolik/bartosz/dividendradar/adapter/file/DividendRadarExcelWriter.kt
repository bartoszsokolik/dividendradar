package pl.sokolik.bartosz.dividendradar.adapter.file

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.COMPANY
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.DGR_10_YEAR
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.DGR_1_YEAR
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.DGR_3_YEAR
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.DGR_5_YEAR
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.DIV_YIELD
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.FAIR_VALUE
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.FAIR_VALUE_PERCENT
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.FIVE_YEAR_AVG_YIELD
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.NO_YEARS
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.PRICE
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.P_BV
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.P_E
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.REVENUE_ONE_YEAR
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.ROE
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.ROTC
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.SECTOR
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.SYMBOL
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarExcelDictionary.values
import pl.sokolik.bartosz.dividendradar.infrastructure.DividendRadarCompanyDocument
import java.io.ByteArrayOutputStream
import java.math.BigDecimal

@Component
class DividendRadarExcelWriter {

    fun create(entries: List<DividendRadarCompanyDocument>) : ByteArrayOutputStream {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet()

        val headerRow = sheet.createRow(0)
        var position = 0;
        values().forEach {
            val cell = headerRow.createCell(position)
            cell.setCellValue(it.value)
            sheet.autoSizeColumn(position)
            position += 1
        }

        var rowNum = 1
        entries.forEach {
            val row = sheet.createRow(rowNum)
            createCell(row, it.symbol, SYMBOL, CellType.STRING)
            createCell(row, it.company, COMPANY, CellType.STRING)
            createCell(row, it.sector, SECTOR, CellType.STRING)
            createCell(row, it.noYears, NO_YEARS, CellType.NUMERIC)
            createCell(row, it.price, PRICE, CellType.NUMERIC)
            createCell(row, it.divYield, DIV_YIELD, CellType.NUMERIC)
            createCell(row, it.fiveAvgDividendYield, FIVE_YEAR_AVG_YIELD, CellType.NUMERIC)
            createCell(row, it.dgr1Year, DGR_1_YEAR, CellType.NUMERIC)
            createCell(row, it.dgr3Year, DGR_3_YEAR, CellType.NUMERIC)
            createCell(row, it.dgr5Year, DGR_5_YEAR, CellType.NUMERIC)
            createCell(row, it.dgr10Year, DGR_10_YEAR, CellType.NUMERIC)
            createCell(row, it.fairValue, FAIR_VALUE, CellType.NUMERIC)
            createCell(row, it.fairValuePercent, FAIR_VALUE_PERCENT, CellType.NUMERIC)
            createCell(row, it.revenueOneYear, REVENUE_ONE_YEAR, CellType.NUMERIC)
            createCell(row, it.roe, ROE, CellType.NUMERIC)
            createCell(row, it.rotc, ROTC, CellType.NUMERIC)
            createCell(row, it.pe, P_E, CellType.NUMERIC)
            createCell(row, it.pbv, P_BV, CellType.NUMERIC)

            rowNum += 1
        }

        val out = ByteArrayOutputStream()
        workbook.write(out)
        workbook.close()
        return out
    }

    private fun createCell(row: Row, dividendRadarEntryValue: Any?, dictionaryValue: DividendRadarExcelDictionary, cellType: CellType) {
        val cell = row.createCell(dictionaryValue.position, cellType)

        when (dividendRadarEntryValue) {
            null -> {}
            is String -> cell.setCellValue(dividendRadarEntryValue)
            is Int -> cell.setCellValue(dividendRadarEntryValue.toDouble())
            else -> cell.setCellValue((dividendRadarEntryValue as BigDecimal).toDouble())
        }
    }
}