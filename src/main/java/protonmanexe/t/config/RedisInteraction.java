package protonmanexe.t.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import static protonmanexe.t.Constants.*;

@Configuration
@Scope("singleton")
public class RedisInteraction {

    // start of Task 7
    public class RedisConfig {
        private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    
        // call host, port and daatbase from file
        @Value("${spring.redis.host}") 
        private String redisHost;

        @Value("${spring.redis.port}") 
        private Optional<Integer> redisPort;

        @Value("${spring.redis.database}")
        private Integer redisDatabase;
        
        @Bean("MyRedis")
        public RedisTemplate<String, Object> redisTemplate() {

            final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            logger.info("redis host port > " + redisHost + ' ' + redisPort.get());

            // set redis properties
            config.setHostName(redisHost);
            config.setPort(redisPort.get());
            config.setDatabase(redisDatabase);
        
            // check environment variable for password
            String redisPassword = System.getenv(ENV_REDIS_PASSWORD);

            if (redisPassword != null && (redisPassword.trim().length() > 0)) {
                config.setPassword(redisPassword); // get redis pw
                logger.info("redisPassword is set"); 
            } else {
                logger.info("Password is empty");
                System.exit(1);
            }
            
            final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
            final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient); 
            jedisFac.afterPropertiesSet();
                        
            final RedisTemplate<String, Object> template = new RedisTemplate();
            template.setConnectionFactory(jedisFac);
            
            template.setKeySerializer(new StringRedisSerializer()); 
            RedisSerializer<Object> serializer 
                    = new JdkSerializationRedisSerializer(getClass().getClassLoader());
            template.setValueSerializer(serializer);

            return template;
        }
    }
    // Task 7 is continued in BookController
}