package pl.sokolik.bartosz.dividendradar.adapter.job

import org.quartz.JobExecutionContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import pl.sokolik.bartosz.dividendradar.domain.CreateDividendRadarHandler
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Component
class FetchDividendRadarSummaryJob(private val createDividendRadarHandler: CreateDividendRadarHandler) :
    QuartzJobBean() {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(FetchDividendRadarSummaryJob::class.java)
    }

    override fun executeInternal(context: JobExecutionContext) {
        log.info("Processing \"FetchDividendRadarSummaryJob\" started.")
        val summaryDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY))
        createDividendRadarHandler.handle(summaryDate)
        log.info("Processing \"FetchDividendRadarSummaryJob\" finished.")
    }
}