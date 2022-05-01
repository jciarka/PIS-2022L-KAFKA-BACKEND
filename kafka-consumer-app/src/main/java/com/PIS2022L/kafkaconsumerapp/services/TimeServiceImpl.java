package com.PIS2022L.kafkaconsumerapp.services;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
public class TimeServiceImpl implements TimeService
{

    @Override
    public LocalDateTime convertUnixTimestampToLocaleDateTime(final Long unixTimestamp)
    {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTimestamp), TimeZone.getDefault().toZoneId());
    }
}
