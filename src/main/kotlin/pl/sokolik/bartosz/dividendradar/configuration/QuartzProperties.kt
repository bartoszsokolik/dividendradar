package pl.sokolik.bartosz.dividendradar.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "quartz")
data class QuartzProperties(
    val properties: Map<String, String>
)
