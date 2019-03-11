package micronaut.demo;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Factory
public class DSLFactory {

    @Bean
    @Singleton
    public DSLContext dslContext(DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }
}
