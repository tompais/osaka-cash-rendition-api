package com.osaka.cashbalancerapi.postgresql.r2dbc.config

import com.osaka.cashbalancerapi.enums.CreditNoteType
import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import com.osaka.cashbalancerapi.enums.InvoiceType
import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.PaymentMethodType
import com.osaka.cashbalancerapi.enums.Shift
import com.osaka.cashbalancerapi.postgresql.r2dbc.converters.EnumTypeConverter
import com.osaka.cashbalancerapi.postgresql.r2dbc.converters.IntegerToUIntConverter
import com.osaka.cashbalancerapi.postgresql.r2dbc.converters.UIntToIntegerConverter
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class R2dbcConfiguration(
    @Value($$"${spring.r2dbc.url}") private val r2dbcUrl: String,
    @Value($$"${spring.r2dbc.username}") private val username: String,
    @Value($$"${spring.r2dbc.password}") private val password: String,
) : AbstractR2dbcConfiguration() {
    companion object {
        private const val R2DBC_POSTGRESQL_PREFIX = "r2dbc:postgresql://"
        private const val R2DBC_PREFIX = "r2dbc:"
        private const val DEFAULT_POSTGRES_PORT = 5432
        private const val DEFAULT_DATABASE = "postgres"
    }

    @Bean
    override fun connectionFactory(): ConnectionFactory =
        PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration
                .builder()
                .host(extractHost(r2dbcUrl))
                .port(extractPort(r2dbcUrl))
                .database(extractDatabase(r2dbcUrl))
                .username(username)
                .password(password)
                .codecRegistrar(
                    EnumCodec
                        .builder()
                        .withEnum("shift", Shift::class.java)
                        .withEnum("location", Location::class.java)
                        .withEnum("currency", Currency::class.java)
                        .withEnum("credit_note_type", CreditNoteType::class.java)
                        .withEnum("delivery_platform", DeliveryPlatform::class.java)
                        .withEnum("invoice_type", InvoiceType::class.java)
                        .withEnum("payment_method_type", PaymentMethodType::class.java)
                        .build(),
                ).forceBinary(true)
                .build(),
        )

    /**
     * Extrae el host de la URL de R2DBC.
     * Formato esperado: r2dbc:postgresql://host:port/database
     */
    private fun extractHost(url: String): String =
        url
            .removePrefix(R2DBC_POSTGRESQL_PREFIX)
            .removePrefix(R2DBC_PREFIX)
            .split("/")[0]
            .split(":")[0]

    /**
     * Extrae el puerto de la URL de R2DBC.
     * Si no se especifica, retorna 5432 (puerto por defecto de PostgreSQL).
     */
    private fun extractPort(url: String): Int =
        url
            .removePrefix(R2DBC_POSTGRESQL_PREFIX)
            .removePrefix(R2DBC_PREFIX)
            .split("/")[0]
            .split(":")
            .getOrNull(1)
            ?.toIntOrNull() ?: DEFAULT_POSTGRES_PORT

    /**
     * Extrae el nombre de la base de datos de la URL de R2DBC.
     * Si no se especifica, retorna "postgres" (base de datos por defecto).
     */
    private fun extractDatabase(url: String): String =
        url
            .removePrefix(R2DBC_POSTGRESQL_PREFIX)
            .removePrefix(R2DBC_PREFIX)
            .split("/")
            .getOrNull(1) ?: DEFAULT_DATABASE

    @Bean
    override fun r2dbcCustomConversions(): R2dbcCustomConversions =
        listOf(
            // UInt converters
            UIntToIntegerConverter(),
            IntegerToUIntConverter(),
            EnumTypeConverter(),
        ).let { converters ->
            R2dbcCustomConversions(storeConversions, converters)
        }
}
