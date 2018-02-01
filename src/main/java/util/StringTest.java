package util;

public class StringTest {
    public static void main(String args[]) throws Exception {
        String sql = "CREATE INDEX IDX_CLIENT_LOG ON TB_CLIENT_LOG(LOCAL_TIME)";
        String tableName = "TB_CLIENT_LOG";
        String newTableName = "TB_CLIENT_LOG_201803";
        System.out.println(updateTableIndexName(sql,tableName,newTableName));
    }

    public static String updateTableIndexName(String sql, String tableName, String newTableName) throws Exception {
        // 解析SQL获取其中的表面
        String result;
        int startIndex = sql.indexOf(" ON ");
        if (startIndex < 0) {
            // 索引不正确
            return null;
        }
        int endIndex = sql.indexOf("(");
        if (endIndex < 0) {
            // 错误的SQL
            return null;
        }
        result =  sql.replaceFirst(tableName, newTableName);
        return result.replaceFirst("IDX_CLIENT_LOG",newTableName);
    }
}
