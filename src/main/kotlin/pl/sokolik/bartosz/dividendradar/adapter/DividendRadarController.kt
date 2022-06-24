package pl.sokolik.bartosz.dividendradar.adapter

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/v1/dividend-radar")
class DividendRadarController(private val getDividendRadarFileQuery: GetDividendRadarFileQuery) {

    @GetMapping()
    fun getDividendRadar(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dividendRadarDate: LocalDate?): ResponseEntity<ByteArray> {
        val dividendRadarFileData = getDividendRadarFileQuery.getDividendRadarFile(dividendRadarDate)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${dividendRadarFileData.filename}")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(dividendRadarFileData.file.toByteArray())
    }
}