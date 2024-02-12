import java.sql.*;
import java.util.Scanner;


public class User {
    private String username;
    private String password;
    private Connection conn;
    private functionsDB db = new functionsDB();

    User(){
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите ваше имя: ");
        String username= scn.nextLine();
        System.out.print("Введите ваш пароль: ");
        String password= scn.nextLine();

        this.username = username;
        this.password = password;
    }

    User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void connectDB(String dbName){
        System.out.println("Подключение...");
        conn = db.connectToDB(dbName, username, password);
    }

    public boolean isConnect(){
        return (conn != null);
    }

    public void showTable(String tableName){
        try{
            ResultSet table = db.readData(conn, tableName);

            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                if(j==2){
                    System.out.print(table.getMetaData().getColumnName(j) + "\t\t\t\t\t|");
                }else System.out.print(table.getMetaData().getColumnName(j) + "\t\t\t|");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    String s = table.getString(j);
                    if(s == null) continue;
                    if(j==2) s+="\t\t\t\t";
                    else s+="\t\t\t";
                    System.out.print(s);
                }
                System.out.println();
            }
            System.out.println();


        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void insertGame(String tableName){
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите названия игры: ");
        String nameGame = scn.nextLine();
        System.out.print("Введите год выпуска: ");
        String yearGame = scn.nextLine();

        System.out.print("Введите жанр игры: ");
        String genreGame = scn.nextLine();
        System.out.print("Введите цену игры: ");
        String costGame = scn.nextLine();

        db.insertRow(conn, tableName, nameGame, Integer.parseInt(yearGame),genreGame, Integer.parseInt(costGame));

    }


    public void deleteRow(String tableName){
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите название: ");
        String nameGame = scn.nextLine();
        if(!db.checkIfitExist(conn, tableName, nameGame)){
            System.out.println("Такой имени нету :(");
            return;
        }
        System.out.println("Удаленно");
        db.deleteRowByName(conn, tableName, nameGame);

    }

    public void insertUser(String tableName){
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите имя пользавателя: ");
        String username = scn.nextLine();
        db.insertRow(conn, tableName, username);
    }

    public void showUserInfo(String tableName1, String tableName2){
        Scanner scn = new Scanner(System.in);
        int i = 1;
        System.out.print("Введите id пользавателя: ");
        String userId = scn.nextLine();
        try{
            ResultSet res = db.readData(conn, tableName1, tableName2, Integer.parseInt(userId));
            System.out.println("Библеотека пользователя: ");
            while(res.next()){
                System.out.println(i +" "+ res.getString("name"));
                i++;
            }
            if (i==1) System.out.println("У пользователя нету игр.");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
