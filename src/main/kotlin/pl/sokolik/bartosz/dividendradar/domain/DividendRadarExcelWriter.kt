package pl.sokolik.bartosz.dividendradar.domain

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.ComparisonOperator
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.model.DividendRadarEntry
import java.io.ByteArrayOutputStream
import java.math.BigDecimal

@Component
class DividendRadarExcelWriter {

    fun create(entries: List<DividendRadarEntry>) : ByteArrayOutputStream {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet()

        val headerRow = sheet.createRow(0)
        var position = 0;
        DividendRadarExcelDictionary.values().forEach {
            val cell = headerRow.createCell(position)
            cell.setCellValue(it.value)
            sheet.autoSizeColumn(position)
            position += 1
        }

        var rowNum = 1
        entries.forEach {
            val row = sheet.createRow(rowNum)
            createCell(row, it.symbol, DividendRadarExcelDictionary.SYMBOL, CellType.STRING)
            createCell(row, it.company, DividendRadarExcelDictionary.COMPANY, CellType.STRING)
            createCell(row, it.sector, DividendRadarExcelDictionary.SECTOR, CellType.STRING)
            createCell(row, it.noYears, DividendRadarExcelDictionary.NO_YEARS, CellType.NUMERIC)
            createCell(row, it.price, DividendRadarExcelDictionary.PRICE, CellType.NUMERIC)
            createCell(row, it.divYield, DividendRadarExcelDictionary.DIV_YIELD, CellType.NUMERIC)
            createCell(row, it.fiveAvgDividendYield, DividendRadarExcelDictionary.FIVE_YEAR_AVG_YIELD, CellType.NUMERIC)
            createCell(row, it.dgr1Year, DividendRadarExcelDictionary.DGR_1_YEAR, CellType.NUMERIC)
            createCell(row, it.dgr3Year, DividendRadarExcelDictionary.DGR_3_YEAR, CellType.NUMERIC)
            createCell(row, it.dgr5Year, DividendRadarExcelDictionary.DGR_5_YEAR, CellType.NUMERIC)
            createCell(row, it.dgr10Year, DividendRadarExcelDictionary.DGR_10_YEAR, CellType.NUMERIC)
            createCell(row, it.fairValue, DividendRadarExcelDictionary.FAIR_VALUE, CellType.NUMERIC)
            createCell(row, it.fairValuePercent, DividendRadarExcelDictionary.FAIR_VALUE_PERCENT, CellType.NUMERIC)
            createCell(row, it.revenueOneYear, DividendRadarExcelDictionary.REVENUE_ONE_YEAR, CellType.NUMERIC)
            createCell(row, it.roe, DividendRadarExcelDictionary.ROE, CellType.NUMERIC)
            createCell(row, it.rotc, DividendRadarExcelDictionary.ROTC, CellType.NUMERIC)
            createCell(row, it.pe, DividendRadarExcelDictionary.P_E, CellType.NUMERIC)
            createCell(row, it.pbv, DividendRadarExcelDictionary.P_BV, CellType.NUMERIC)

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