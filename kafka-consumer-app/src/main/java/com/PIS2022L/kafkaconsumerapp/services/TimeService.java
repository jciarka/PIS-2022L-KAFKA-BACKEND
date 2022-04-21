package com.PIS2022L.kafkaconsumerapp.services;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface TimeService
{
    LocalDateTime convertUnixTimestampToLocaleDateTime(Long unixTimestamp);
}
