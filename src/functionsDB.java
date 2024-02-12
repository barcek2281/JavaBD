import javax.swing.plaf.nimbus.State;
import java.security.spec.ECField;
import java.sql.*;

public class functionsDB {

    public Connection connectToDB(String dbName, String username, String password){
        Connection conn=null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName ,username, password);
            if(conn!=null){
                System.out.println("Успешное подключение");
            }
            else{
                System.out.println("Подключение не удалось");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }

    public ResultSet readData(Connection conn, String table_name){
        Statement statement;
        ResultSet res=null;
        try {
            String query=String.format("select * from %s",table_name);
            statement=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            res=statement.executeQuery(query);
        }
        catch (Exception e){
            System.out.println(e);
        }

        return res;
    }

    public ResultSet readData(Connection conn, String tableName1, String tableName2, int userId){

        Statement statement;
        ResultSet res=null;

        try{
            String query=String.format("SELECT g.name "+
                    "FROM %s g "+
                    "JOIN %s u ON u.uid = '%d' "+
                    "WHERE g.id = ANY(u.game_library)",tableName1, tableName2, userId);
            statement=conn.createStatement();
            res=statement.executeQuery(query);

        }catch (Exception e){
            System.out.println(e);
        }

        return res;
    }

    public void insertRow(Connection conn, String tableName, String userName){
        Statement statement;
        try{
            String query=String.format("insert into %s(name) values('%s')", tableName, userName);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Пользователь добавлен");

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void insertRow(Connection conn, String tableName, String nameGame, int yearGame, String genreGame, int costGame){
        Statement statement;
        try {
            String query=String.format("insert into %s(name, year, genre, cost) values('%s','%d', '%s', '%d');",tableName, nameGame, yearGame,  genreGame, costGame);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Игра добавленна");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteRowByName(Connection conn,String tableName, String name){
        Statement statement;
        try{
            String query=String.format("delete from %s where name='%s'",tableName, name);
            statement=conn.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean checkIfitExist(Connection conn, String tableName, String name){
        boolean flag=true;
        Statement statement;
        try {

            // SQL-запрос для проверки существования записи с указанным именем
            String query = String.format("SELECT COUNT(*) FROM %s WHERE name = '%s'",tableName, name);
            statement=conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int count = resultSet.getInt(1);

                if (count <= 0) {
                    flag = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
