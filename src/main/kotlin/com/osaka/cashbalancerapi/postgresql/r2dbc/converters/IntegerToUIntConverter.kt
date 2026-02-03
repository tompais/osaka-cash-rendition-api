package com.osaka.cashbalancerapi.postgresql.r2dbc.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

// Converter para Integer -> UInt (lectura de BD)
@ReadingConverter
class IntegerToUIntConverter : Converter<Int, UInt> {
    override fun convert(source: Int): UInt = source.toUInt()
}
