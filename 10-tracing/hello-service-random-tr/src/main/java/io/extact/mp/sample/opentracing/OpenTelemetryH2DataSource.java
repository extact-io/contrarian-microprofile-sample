
package io.extact.mp.sample.opentracing;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

import io.opentelemetry.instrumentation.jdbc.datasource.OpenTelemetryDataSource;

public class OpenTelemetryH2DataSource implements DataSource {

    private JdbcDataSource origin;
    private DataSource delegate;

    public OpenTelemetryH2DataSource() {
        origin = new JdbcDataSource(); // getConnectionを処理する実体DataSource
        delegate = new OpenTelemetryDataSource(origin);
    }


    // --------------------------------------------------- delegate method to DataSource

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return delegate.unwrap(iface);
    }
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delegate.isWrapperFor(iface);
    }
    public Connection getConnection() throws SQLException {
        return delegate.getConnection();
    }
    public Connection getConnection(String username, String password) throws SQLException {
        return delegate.getConnection(username, password);
    }
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate.getParentLogger();
    }
    public PrintWriter getLogWriter() throws SQLException {
        return delegate.getLogWriter();
    }
    public void setLogWriter(PrintWriter out) throws SQLException {
        delegate.setLogWriter(out);
    }
    public void setLoginTimeout(int seconds) throws SQLException {
        delegate.setLoginTimeout(seconds);
    }
    public int getLoginTimeout() throws SQLException {
        return delegate.getLoginTimeout();
    }
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return delegate.createConnectionBuilder();
    }
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return delegate.createShardingKeyBuilder();
    }


    // HikariCPからreflectionで呼び出されるため実体に対して設定するメソッドを
    // 実装しておく必要がある
    // --------------------------------------------------- delegate method to JdbcDataSource

    public String getURL() {
        return origin.getURL();
    }
    public void setURL(String url) {
        origin.setURL(url);
    }
    public String getUrl() {
        return origin.getURL();
    }
    public void setUrl(String url) {
        origin.setURL(url);
    }
    public String getPassword() {
        return origin.getPassword();
    }
    public void setPassword(String password) {
        origin.setPassword(password);
    }
    public String getUser() {
        return origin.getUser();
    }
    public void setUser(String user) {
        origin.setUser(user);
    }
    public String getDescription() {
        return origin.getDescription();
    }
    public void setDescription(String description) {
        setDescription(description);
    }
}
