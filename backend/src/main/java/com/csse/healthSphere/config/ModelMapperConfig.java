package com.csse.healthSphere.config;

import com.csse.healthSphere.util.DateUtil;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Configuration
public class ModelMapperConfig {
    Converter<String, LocalDate> dateConverter = new AbstractConverter<>() {
        protected LocalDate convert(String source) {
            return source == null ? null : DateUtil.parseDate(source);
        }
    };

    Converter<String, LocalDateTime> dateTimeConverter = new AbstractConverter<>() {
        protected LocalDateTime convert(String source) {
            return source == null ? null : DateUtil.parseDateTime(source);
        }
    };

    Converter<String, LocalTime> timeConverter = new AbstractConverter<>() {
        protected LocalTime convert(String source) {
            return source == null ? null : DateUtil.parseTime(source);
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(dateConverter);
        modelMapper.addConverter(dateTimeConverter);
        modelMapper.addConverter(timeConverter);

        return modelMapper;
    }


}
