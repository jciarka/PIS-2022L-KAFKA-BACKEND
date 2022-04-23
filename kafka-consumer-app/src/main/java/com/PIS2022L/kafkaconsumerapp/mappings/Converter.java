package com.PIS2022L.kafkaconsumerapp.mappings;

public interface Converter<To, From>
{
    To convert(From sourceOrder);
}
