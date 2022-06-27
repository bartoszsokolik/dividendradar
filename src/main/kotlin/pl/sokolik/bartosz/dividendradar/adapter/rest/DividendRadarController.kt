package pl.sokolik.bartosz.dividendradar.adapter.rest

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.sokolik.bartosz.dividendradar.adapter.query.GetDividendRadarCompanyQuery
import pl.sokolik.bartosz.dividendradar.adapter.query.GetDividendRadarFileQuery
import pl.sokolik.bartosz.dividendradar.domain.CreateDividendRadarHandler
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@RestController
@RequestMapping("/v1/dividend-radar")
class DividendRadarController(
    private val getDividendRadarFileQuery: GetDividendRadarFileQuery,
    private val getDividendRadarCompanyQuery: GetDividendRadarCompanyQuery,
    private val createDividendRadarHandler: CreateDividendRadarHandler
) {

    @GetMapping("/download")
    fun getDividendRadarExcelFile(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) summaryDate: LocalDate?): ResponseEntity<ByteArray> {
        val dividendRadarFileData = getDividendRadarFileQuery.getDividendRadarFile(summaryDate)
        return ResponseEntity.ok()
            .header(CONTENT_DISPOSITION, "attachment; filename=${dividendRadarFileData.filename}")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(dividendRadarFileData.file.toByteArray())
    }

    @GetMapping
    fun get(
        @RequestParam("symbol") symbol: String,
        @RequestParam("summaryDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) summaryDate: LocalDate
    ): ResponseEntity<DividendRadarCompanyResponse> {
        return ResponseEntity.ok()
            .body(getDividendRadarCompanyQuery.getDividendRadarCompany(symbol, summaryDate))
    }

    @PostMapping("/trigger")
    fun triggerDividendRadarJob(
        @RequestParam(value = "summaryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) summaryDate: LocalDate?) {
        createDividendRadarHandler.handle(summaryDate ?: LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)))
    }
}