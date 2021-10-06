package ru.homononsapiens;

import ru.homononsapiens.commands.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        readFileAndImportToDatabase();

        while (true) {
            Command command = printMenuAndGetCommand();
            if (command != null) {
                command.execute();
                System.out.println();
            } else {
                break;
            }
        }
    }

    public static void readFileAndImportToDatabase() throws IOException, SQLException {
        Scanner scanner = new Scanner(new FileInputStream(Property.getProperty("filename")));
        List<City> cities = new ArrayList<>();

        while (scanner.hasNextLine()) {
            cities.add(City.getInstanceByRowString(scanner.nextLine()));
        }

        importToDatabase(cities);
    }

    public static void importToDatabase(List<City> cities) throws SQLException {
        List<City> citiesFromDatabase = Database.selectAllCities();
        cities.removeAll(citiesFromDatabase);

        Database.insertCities(cities);
    }

    public static Command printMenuAndGetCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("Выберете требуемое действие:");
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("[1] Список городов");
        System.out.println("[2] Отсортированный список городов по наименованию в алфавитном порядке по убыванию без учета регистра");
        System.out.println("[3] Отсортированный список городов по федеральному округу и наименованию города внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра");
        System.out.println("[4] Найти индекс элемента и значение с наибольшим количеством жителей города");
        System.out.println("[5] Определить количество городов в разрезе регионов");
        System.out.println("[0] Выход");
        System.out.println("---------------------------------------------------------------------------------------------------");

        System.out.print("\nВведите номер команды: ");
        try {
            switch (scanner.nextInt()) {
                case 1:
                    return new PrintCities();
                case 2:
                    return new PrintOrderedCities();
                case 3:
                    return new PrintOrderedCitiesByTwoColumns();
                case 4:
                    return new PrintLargestNumberOfInhabitants();
                case 5:
                    return new PrintCountOfCitiesGroupByRegion();
                case 0:
                    return null;

                default:
                    System.out.println("Команда не найдена!\n");
                    return null;
            }
        } catch (InputMismatchException ex) {
            System.out.println("------ОШИБКА: ВВЕДИТЕ ЧИСЛО!-------\n");
            return null;
        }
    }
}

