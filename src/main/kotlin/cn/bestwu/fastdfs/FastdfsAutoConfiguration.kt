package cn.bestwu.fastdfs

import cn.bestwu.fastdfs.FastdfsAutoConfiguration.FastdfsCondition
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.*
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.util.StringUtils

/**
 * @author Peter Wu
 * @since 1.0.0
 */
@Conditional(FastdfsCondition::class)
@Configuration
@EnableConfigurationProperties(FastdfsProperties::class)
class FastdfsAutoConfiguration {

    @Bean
    fun fastdfsClient(fastdfsProperties: FastdfsProperties): FastdfsClient {
        return FastdfsClient(fastdfsProperties)
    }

    internal class FastdfsCondition : Condition {

        override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
            return StringUtils.hasText(context.environment.getProperty("fastdfs.tracker-servers")) || StringUtils.hasText(context.environment.getProperty("fastdfs.trackerServers"))
        }
    }
}
