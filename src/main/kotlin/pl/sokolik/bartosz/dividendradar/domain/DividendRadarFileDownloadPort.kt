package pl.sokolik.bartosz.dividendradar.domain


interface DividendRadarFileDownloadPort {

    fun download(date: String): ByteArray
}