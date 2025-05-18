package nhanph.timekeeping.admin.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
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
