package com.criollofood.bootapp.customer.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ConfigurationProperties("oracle.criollofood.datasource")
public class DatabaseConfig {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConfig.class);

    @NotNull
    private String url;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String driverClassName;
    @NotNull
    private String walletLocation;

    @Bean(name = "oracleDataSource")
    @Primary
    public DataSource oracleDataSource(@Value("${datasource.single:false}") String dataSourceSingle,
                                       @Value("${datasource.maxPoolSize:5}") Integer maxPoolSize,
                                       @Value("${datasource.timeout.connection:30000}") Long timeoutConnection) throws SQLException {
        try {
            OracleDataSource oracleDataSource = new OracleDataSource();
            Properties props = new Properties();

            props.put("oracle.net.wallet_location",
                    String.format(
                            "(source=(method=file)(method_data=(directory=%s)))", walletLocation
                    )
            );

            oracleDataSource.setConnectionProperties(props);

            url = String.format("%s?TNS_ADMIN=%s", url, walletLocation);

            oracleDataSource.setDriverType(driverClassName);
            oracleDataSource.setURL(url);
            oracleDataSource.setUser(username);
            oracleDataSource.setPassword(password);

            HikariConfig hkConfig = new HikariConfig();
            hkConfig.setDataSource(oracleDataSource);
            hkConfig.setPoolName("HikariCFPool");
            hkConfig.setMaximumPoolSize(maxPoolSize);
            hkConfig.setConnectionTimeout(timeoutConnection);

            HikariDataSource hikariDataSource = new HikariDataSource(hkConfig);
            LOGGER.debug("Pool Size Max: "+ hikariDataSource.getMaximumPoolSize());
            LOGGER.debug("Connection Timeout: "+ hikariDataSource.getConnectionTimeout());

            if (dataSourceSingle.equals("true")) {
                LOGGER.info("Datasource class: SingleConnectionDataSource.class");
                return new SingleConnectionDataSource(oracleDataSource.getConnection(), true);
            } else {
                LOGGER.info("Datasource class: HikariDataSource.class");
                return oracleDataSource;
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Error de conexión a la base de datos url: %s", url), e);
            throw e;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setWalletLocation(String walletLocation) {
        this.walletLocation = walletLocation;
    }
}
