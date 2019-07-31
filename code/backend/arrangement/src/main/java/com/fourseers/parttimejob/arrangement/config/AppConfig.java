package com.fourseers.parttimejob.arrangement.config;

import com.fourseers.parttimejob.arrangement.dto.JobDto;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Tag;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages="com.fourseers.parttimejob")
@EnableJpaRepositories(basePackages="com.fourseers.parttimejob")
@EntityScan(basePackages="com.fourseers.parttimejob")
public class AppConfig {

    @Autowired
    private Environment environment;

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

    @Bean
    public RestHighLevelClient esClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(
                        environment.getProperty("app.es.rest.host", "localhost"),
                        Integer.parseInt(environment.getProperty("app.es.rest.port", "9200")),
                        environment.getProperty("app.es.rest.protocol", "http")),
                new HttpHost(
                        environment.getProperty("app.es.comm.host", "localhost"),
                        Integer.parseInt(environment.getProperty("app.es.comm.port", "9300")),
                        environment.getProperty("app.es.comm.protocol", "http"))
        ));
    }
}
