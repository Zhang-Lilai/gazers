package org.tgo.gazerbase.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tgo.gazerbase.mocker.SophonMocker;

@Configuration
public class RobotTestConfiguration {
    @Bean
    public SophonMocker sophonMocker(){
        return new SophonMocker();
    }
}
