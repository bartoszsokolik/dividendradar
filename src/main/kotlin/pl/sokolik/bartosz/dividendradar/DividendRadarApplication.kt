package pl.sokolik.bartosz.dividendradar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class DividendRadarApplication() {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<DividendRadarApplication>(*args)
		}
	}
}
