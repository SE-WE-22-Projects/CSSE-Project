package com.csse.healthSphere.config;

import com.csse.healthSphere.util.DateUtil;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Configuration
public class ModelMapperConfig {
    final Converter<String, LocalDate> dateConverter = new AbstractConverter<>() {
        protected LocalDate convert(String source) {
            return source == null ? null : DateUtil.parseDate(source);
        }
    };

    final Converter<String, LocalDateTime> dateTimeConverter = new AbstractConverter<>() {
        protected LocalDateTime convert(String source) {
            return source == null ? null : DateUtil.parseDateTime(source);
        }
    };

    final Converter<String, LocalTime> timeConverter = new AbstractConverter<>() {
        protected LocalTime convert(String source) {
            return source == null ? null : DateUtil.parseTime(source);
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addConverter(dateConverter);
        modelMapper.addConverter(dateTimeConverter);
        modelMapper.addConverter(timeConverter);

        return modelMapper;
    }


}
