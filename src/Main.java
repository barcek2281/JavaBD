import java.util.Scanner;

public class Main {
    public static boolean running = false;

    public static void main(String[] args) {
        User user = new User();
        user.connectDB("torrentdb");

        Scanner scn = new Scanner(System.in);
        String action;
        boolean running = false;

        do {
            if (user.isConnect()) {
                running = true;
            } else {
                System.out.println("Что-пошло не так..");
                user = new User();
                user.connectDB("torrentdb");
                if (user.isConnect()) running = true;
            }
        } while (!user.isConnect());


        while (running) {
            System.out.println("Выберети что делать\n1) Таблица игр\n2) Таблица пользователей\n0) Завершения сессии");
            action = scn.nextLine();

            switch (action) {
                case "1": {
                    tableGames(user);
                    break;
                }
                case "2": {
                    tableUsers(user);
                    break;
                }
                case "0": {
                    System.out.println("Завершения сессии");
                    running = false;
                    break;
                }
                default: {
                    System.out.println("Попробуйте занова");
                    break;
                }

            }

        }

    }


    public static void tableGames(User user){
        boolean running = true;

        while(running) {

            System.out.println("Что вы хотите сделать?\n" +
                    "1) Посмотреть все доступные игры\n" +
                    "2) Добавить игру\n" +
                    "3) Удалить игру\n" +
                    "0) назад");

            Scanner scn = new Scanner(System.in);
            String action = scn.nextLine();

            switch (action) {
                case "1": {
                    user.showTable("games");
                    break;
                }
                case "2": {
                    user.insertGame("games");
                    break;
                }
                case "3": {
                    user.deleteRow("games");
                    break;
                }
                case "0": {
                    running=false;
                    break;
                }
                default:
                    System.out.println("Попробуйте занова");
                    break;
            }
            System.out.println("__________________________________________________\n");
        }
    }

    public static void tableUsers(User user) {
        boolean running = true;

        while(running) {

            System.out.println("Что вы хотите сделать?\n" +
                    "1) Посмотреть всех пользователей\n"+
                    "2) Добавить пользователя\n"+
                    "3) Удалить пользователя\n" +
                    "4) Узнать библеотеку пользователя\n"+
                    "0) назад");

            Scanner scn = new Scanner(System.in);
            String action = scn.nextLine();

            switch (action) {
                case "1": {
                    user.showTable("users");
                    break;
                }
                case "2":{
                    user.insertUser("users");
                    break;
                }
                case "3":{
                    user.deleteRow("users");
                    break;
                }
                case "4": {
                    user.showUserInfo("games", "users");
                    break;
                }
                case "5":{

                    break;
                }
                case "0": {
                    running=false;
                    break;
                }
                default:
                    System.out.println("Попробуйте занова");
                    break;
            }
            System.out.println("__________________________________________________\n");
        }
    }
}
