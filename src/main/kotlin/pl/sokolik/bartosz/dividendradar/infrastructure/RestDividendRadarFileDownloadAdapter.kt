package pl.sokolik.bartosz.dividendradar.infrastructure

import org.springframework.stereotype.Service
import pl.sokolik.bartosz.dividendradar.domain.DividendRadarFileDownloadPort

@Service
class RestDividendRadarFileDownloadAdapter(private val feignClient: DividendRadarClient) : DividendRadarFileDownloadPort {

    override fun download(date: String): ByteArray {
        return feignClient.getDividendRadarFile(date)
    }
}