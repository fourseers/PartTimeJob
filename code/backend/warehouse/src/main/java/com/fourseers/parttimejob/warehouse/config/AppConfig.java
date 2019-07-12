package com.fourseers.parttimejob.warehouse.config;

import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.entity.Industry;
import com.fourseers.parttimejob.warehouse.entity.Shop;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Integer, Industry> integerIndustryConverter = mappingContext -> {
            Industry industry = new Industry();
            industry.setIndustryId(mappingContext.getSource());
            return industry;
        };

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

        return modelMapper;
    }
}
