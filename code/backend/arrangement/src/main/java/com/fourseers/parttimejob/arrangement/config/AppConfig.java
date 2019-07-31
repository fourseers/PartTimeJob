package com.fourseers.parttimejob.arrangement.config;

import com.fourseers.parttimejob.arrangement.dto.JobDto;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Tag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages="com.fourseers.parttimejob")
@EnableJpaRepositories(basePackages="com.fourseers.parttimejob")
@EntityScan(basePackages="com.fourseers.parttimejob")
public class AppConfig {

    @PostConstruct
    void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, Etc.Education> stringEducationConverter = mappingContext -> Etc.Education.fromName(mappingContext.getSource());

        Converter<List<Integer>, List<Tag>> listIntegerListTagConverter = mappingContext -> {
            List<Tag> result = new ArrayList<>();
            for (Integer num : mappingContext.getSource()) {
                Tag tag = new Tag();
                tag.setId(num);
                result.add(tag);
            }
            return result;
        };

        modelMapper.addMappings(new PropertyMap<JobDto, Job>() {
            @Override
            protected void configure() {
                using(stringEducationConverter).map(source.getEducation(), destination.getEducation());
                using(listIntegerListTagConverter).map(source.getTagList(), destination.getTagList());
            }
        });
        return modelMapper;
    }
}
