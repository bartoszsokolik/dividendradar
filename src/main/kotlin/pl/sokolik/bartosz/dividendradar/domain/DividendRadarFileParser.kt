package pl.sokolik.bartosz.dividendradar.domain

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.model.DividendRadarImportEntry
import java.io.ByteArrayInputStream
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.stream.IntStream
import kotlin.streams.toList

@Component
class DividendRadarFileParser {

    companion object {
        private const val SHEET_POSITION = 4
        private const val FIRST_DATA_ROW = 3
        val log: Logger = LoggerFactory.getLogger(DividendRadarFileParser::class.java)
    }

    fun parse(file: ByteArray): List<DividendRadarImportEntry> {
        val workbook = WorkbookFactory.create(ByteArrayInputStream(file))
        val sheet = workbook.getSheetAt(SHEET_POSITION)
        val columns = buildColumns(sheet.getRow(FIRST_DATA_ROW - 1))

        var i = FIRST_DATA_ROW
        var row = sheet.getRow(i)
        val data = mutableListOf<Map<String, String?>>()

        while (row != null) {
            val currentRow = row
            val entry = columns
                .map {
                    val cell: Cell? = currentRow.getCell(it.index)
                    ColumnValue(it, cell?.let { c: Cell -> this.map(c) })
                }.map { it.column.name to it.value }.toMap()

            if (entry.isNotEmpty()) {
                data.add(entry)
            }
            row = sheet.getRow(++i)
        }

        return data.map { DividendRadarImportEntry(it) }
    }

    private fun map(cell: Cell): String? {
        return when (cell.cellType) {
            CellType.NUMERIC -> {
                val value = cell.numericCellValue
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateUtil.getLocalDateTime(value).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                } else {
                    value.toString()
                }
            }
            CellType.STRING, CellType.FORMULA -> cell.stringCellValue
            CellType.BLANK -> null
            else -> {
                log.warn("Cannot handle type ${cell.cellType}")
                null
            }
        }
    }

    private fun buildColumns(row: Row): List<Column> {
        return IntStream.range(0, row.physicalNumberOfCells)
            .mapToObj {
                val cell = row.getCell(it)
                Column(cell.stringCellValue.lowercase(Locale.getDefault()), it)
            }
            .toList()
    }

    data class Column(val name: String, val index: Int)

    data class ColumnValue(val column: Column, val value: String?)
}