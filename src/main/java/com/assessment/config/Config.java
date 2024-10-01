package com.assessment.config;

import com.assessment.PrototypeData.ToolData;
import com.assessment.PrototypeData.ToolInfo;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class Config {

    @Bean
    public Mapper getMapper(){
        return DozerBeanMapperBuilder.buildDefault();
    }

    @Bean
    public ToolData getToolData(){
        return new ToolData(getToolInfo());
    }

    private Map<String, ToolInfo> getToolInfo(){
        Map<String, ToolInfo> toolInfo = new HashMap<>();
        toolInfo.put("CHNS", new ToolInfo("Chainsaw", "Stihl", 1.49, true, false, true));
        toolInfo.put("LADW", new ToolInfo("Ladder", "Werner", 1.99, true, true, false));
        toolInfo.put("JAKD", new ToolInfo("Jackhammer", "DeWalt", 2.99, true, false, false));
        toolInfo.put("JAKR", new ToolInfo("Jackhammer", "Ridgid", 2.99, true, false, false));

        log.debug("Tool data successfully loaded.");
        return toolInfo;
    }
}
