package DatabaseManagament;

import Management.Word;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryingData {
    /*list mô phỏng */

    public static void pushAllWord(ObservableList<Word> mylist) throws ClassNotFoundException, SQLException {

        Connection connection = MySQLConnUtils.getMySQLConnection();
        Statement statement = connection.createStatement();
        String sql = "Select word,detail from  word.tbl_edict";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String word = rs.getString("word");
            String detail = rs.getString("detail");
            Word newWord = new Word();
            newWord.setWord_target(word);
            newWord.setWord_explain(detail);
            mylist.add(newWord);
        }
        connection.close();
    }
    public static void insertIntoDB (String word, String detail) throws ClassNotFoundException, SQLException {
        Connection connection = MySQLConnUtils.getMySQLConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into tbl_edict (idx,word,detail) value (null," + "\"" + word + "\"" + "," + "\"" + detail + "\"" +  ");";
        statement.executeUpdate(sql);
        connection.close();
    }
    public static void removeInDB(String a) throws ClassNotFoundException, SQLException {
        Connection connection = MySQLConnUtils.getMySQLConnection();
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM tbl_edict WHERE word = \"" + a + "\"";
        statement.executeUpdate(sql);
        connection.close();
    }
    public void changeWord(String word,String newWord) throws ClassNotFoundException, SQLException  {
        Connection connection = MySQLConnUtils.getMySQLConnection();
        Statement statement = connection.createStatement();
        String sql = "UPDATE tbl_edict\n" + "SET word = REPLACE(word," + "\"" + word + "\"" + ",\""+ newWord + "\");";
        statement.executeUpdate(sql);
        connection.close();
    }
    public void changeDetail(String detail,String newDetail) throws ClassNotFoundException, SQLException{
        Connection connection = MySQLConnUtils.getMySQLConnection();
        Statement statement = connection.createStatement();
        String sql = "UPDATE tbl_edict\n" + "SET detail = REPLACE(detail," + "\"" + detail + "\"" + ",\""+ newDetail + "\");";
        statement.executeUpdate(sql);
        connection.close();
    }
    public static void printDetails(Word a) throws ClassNotFoundException, SQLException {
            String detail = a.getWord_explain();
            int k = detail.indexOf('/');
            int f=0;
            if(k!=-1) {
                f = detail.indexOf('/',k+1);
                String pronunciation = detail.substring(k,f+1);
                System.out.print(pronunciation);
            }
            if(detail.charAt(f)=='/') f++;
            for(int i = f; i<detail.length();i++) {
                char tmp = detail.charAt(i);
                if(tmp!='*')
                System.out.print(detail.charAt(i));
                else System.out.println();
            }
    }
}
