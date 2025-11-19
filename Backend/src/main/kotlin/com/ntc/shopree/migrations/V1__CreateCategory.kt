package com.ntc.shopree.migrations

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource


class V1__CreateCategory : BaseJavaMigration() {

    @Override
    override fun migrate(context: Context) {
        JdbcTemplate(SingleConnectionDataSource(context.connection, true))
            .execute("")

    }
}