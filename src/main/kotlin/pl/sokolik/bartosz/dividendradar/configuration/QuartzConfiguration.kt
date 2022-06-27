package pl.sokolik.bartosz.dividendradar.configuration

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import java.util.Properties

@Configuration
class QuartzConfiguration {

    @Bean
    fun autowiringSpringBeanJobFactory(applicationContext: ApplicationContext): AutowiringSpringBeanJobFactory {
        return AutowiringSpringBeanJobFactory()
    }

    @Bean
    fun schedulerFactoryBean(
        quartzProperties: QuartzProperties,
        context: ApplicationContext,
        autowiringSpringBeanJobFactory: AutowiringSpringBeanJobFactory
    ): SchedulerFactoryBean {
        val quartzScheduler = SchedulerFactoryBean()
        quartzScheduler.setQuartzProperties(getQuartzProperties(quartzProperties))
        quartzScheduler.setWaitForJobsToCompleteOnShutdown(true)
        quartzScheduler.setJobFactory(autowiringSpringBeanJobFactory)
        quartzScheduler.setApplicationContext(context)
        return quartzScheduler
    }

    private fun getQuartzProperties(quartzProperties: QuartzProperties): Properties {
        val properties = Properties()
        properties.putAll(quartzProperties.properties)
        return properties
    }
}