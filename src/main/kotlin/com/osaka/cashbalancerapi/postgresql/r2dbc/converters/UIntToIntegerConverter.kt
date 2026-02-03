package com.osaka.cashbalancerapi.postgresql.r2dbc.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

// Converter para UInt -> Integer (escritura en BD)
@WritingConverter
class UIntToIntegerConverter : Converter<UInt, Int> {
    override fun convert(source: UInt): Int = source.toInt()
}
