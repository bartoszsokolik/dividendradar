package pl.sokolik.bartosz.dividendradar.configuration

import org.quartz.spi.TriggerFiredBundle
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.scheduling.quartz.SpringBeanJobFactory

class AutowiringSpringBeanJobFactory : SpringBeanJobFactory, ApplicationContextAware {

    private var beanFactory: AutowireCapableBeanFactory? = null

    constructor() : super()

    constructor(context: ApplicationContext) : super() {
        this.beanFactory = context.autowireCapableBeanFactory
    }

    override fun createJobInstance(bundle: TriggerFiredBundle): Any {
        val job = super.createJobInstance(bundle)
        beanFactory?.autowireBean(job)
        beanFactory?.initializeBean(job, job.javaClass.name)
        return job
    }
}