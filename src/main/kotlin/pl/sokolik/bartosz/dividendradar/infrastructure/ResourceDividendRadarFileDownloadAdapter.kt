package pl.sokolik.bartosz.dividendradar.infrastructure

import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarFileDownloadPort

@Service
@Primary
@Profile("fake")
class ResourceDividendRadarFileDownloadAdapter : DividendRadarFileDownloadPort {

    override fun download(date: String): ByteArray {
        return ClassPathResource("dividend-radar.xlsx").inputStream.readAllBytes()
    }
}