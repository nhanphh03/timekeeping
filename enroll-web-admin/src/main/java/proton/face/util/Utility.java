package proton.face.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import proton.face.config.AppConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class Utility {

    public static void closeObject(Connection cn) {
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void closeObject(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
