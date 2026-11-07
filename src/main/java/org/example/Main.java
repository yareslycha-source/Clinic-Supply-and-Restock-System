
package org.example;

import org.example.dao.DatabaseInitializer;
import org.example.dao.UserDao;
import org.example.ui.MenuUi;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);

        MenuUi menuUi = new MenuUi(userDao, scanner);
        menuUi.start();

        scanner.close();
    }
}