package com.fourseers.parttimejob.warehouse.config;

import com.fourseers.parttimejob.common.entity.CV;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Industry;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.warehouse.dto.CVDto;
import com.fourseers.parttimejob.warehouse.dto.NewCVDto;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.dto.UserShopDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages="com.fourseers.parttimejob")
@EnableJpaRepositories(basePackages="com.fourseers.parttimejob")
@EntityScan(basePackages="com.fourseers.parttimejob")
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Integer, Industry> integerIndustryConverter = mappingContext -> {
            Industry industry = new Industry();
            industry.setIndustryId(mappingContext.getSource());
            return industry;
        };

        Converter<Etc.Education, String> educationStringConverter =
                mappingContext -> mappingContext.getSource().getName();

        Converter<String, Etc.Education> stringEducationConverter =
                mappingContext -> Etc.Education.fromName(mappingContext.getSource());

        modelMapper.addMappings(new PropertyMap<ShopDto, Shop>() {
            @Override
            protected void configure() {
                using(integerIndustryConverter).map(source.getIndustry(), destination.getIndustry());
            }
        });

        modelMapper.addMappings(new PropertyMap<Shop, ShopDto>() {
            @Override
            protected void configure() {
                map().setIndustry(source.getIndustry().getIndustryId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Shop, UserShopDto>() {
            @Override
            protected void configure() {
                map().setIndustry(source.getIndustry().getIndustryName());
            }
        });

        modelMapper.addMappings(new PropertyMap<CVDto, CV>() {
            @Override
            protected void configure() {
                using(stringEducationConverter).map(source.getEducation(), destination.getEducation());
            }
        });

        modelMapper.addMappings(new PropertyMap<CV, CVDto>() {
            @Override
            protected void configure() {
                using(educationStringConverter).map(source.getEducation(), destination.getEducation());
            }
        });

        modelMapper.addMappings(new PropertyMap<NewCVDto, CV>() {
            @Override
            protected void configure() {
                using(stringEducationConverter).map(source.getEducation(), destination.getEducation());
            }
        });

        modelMapper.addMappings(new PropertyMap<CV, NewCVDto>() {
            @Override
            protected void configure() {
                using(educationStringConverter).map(source.getEducation(), destination.getEducation());
            }
        });

        return modelMapper;
    }
}
