package pl.sokolik.bartosz.dividendradar.infrastructure

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(value = "dividend-radar-client", url = "\${dividend.radar.url}")
interface DividendRadarClient {

    @GetMapping("/Dividend%20Radar%20{radarDate}.xlsx")
    fun getDividendRadarFile(@PathVariable radarDate: String): ByteArray
}