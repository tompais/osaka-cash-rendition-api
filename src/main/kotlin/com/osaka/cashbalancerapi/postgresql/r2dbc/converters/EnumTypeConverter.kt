package com.osaka.cashbalancerapi.postgresql.r2dbc.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class EnumTypeConverter : Converter<Enum<*>, Enum<*>> {
    override fun convert(source: Enum<*>): Enum<*> = source
}
