package com.github.krashwani.assitflow.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR,
unmappedTargetPolicy = ReportingPolicy.WARN,unmappedSourcePolicy = ReportingPolicy.WARN)
public interface MapperDefaultConfig {
}
