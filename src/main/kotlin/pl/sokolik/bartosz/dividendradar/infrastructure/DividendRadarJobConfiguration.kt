package pl.sokolik.bartosz.dividendradar.infrastructure

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import pl.sokolik.bartosz.dividendradar.adapter.job.FetchDividendRadarSummaryJob
import javax.annotation.PostConstruct

@Configuration
@ConditionalOnProperty(name = ["dividend.radar.should.schedule"], havingValue = "true")
class DividendRadarJobConfiguration(
    @Value("\${dividend.radar.cron}") private val cron: String,
    private val scheduler: Scheduler
) {

    companion object {
        private const val FETCH_DIVIDEND_RADAR_SUMMARY = "FETCH_DIVIDEND_RADAR_SUMMARY"
        val log: Logger = LoggerFactory.getLogger(DividendRadarJobConfiguration::class.java)
    }

    @PostConstruct
    fun schedule() {
        if (!scheduler.checkExists(getJobKey())) {
            val jobDetail = jobDetail()
            val jobTrigger = jobTrigger(jobDetail)
            scheduler.scheduleJob(jobDetail, jobTrigger)
            log.info("Created job for \"FetchDividendRadarSummary\"")
        }
    }

    private fun getJobKey(): JobKey = JobKey(FETCH_DIVIDEND_RADAR_SUMMARY)

    private fun jobDetail(): JobDetail =
        JobBuilder.newJob(FetchDividendRadarSummaryJob::class.java)
            .withIdentity(getJobKey())
            .storeDurably()
            .build()

    private fun jobTrigger(jobDetail: JobDetail): Trigger =
        TriggerBuilder
            .newTrigger()
            .forJob(jobDetail)
            .withIdentity(FETCH_DIVIDEND_RADAR_SUMMARY)
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule(cron))
            .build()

}