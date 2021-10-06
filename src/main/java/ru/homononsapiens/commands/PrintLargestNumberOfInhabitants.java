package ru.homononsapiens.commands;

import ru.homononsapiens.City;
import ru.homononsapiens.Database;

import java.sql.SQLException;
import java.util.List;

public class PrintLargestNumberOfInhabitants extends Command {
    @Override
    public void execute() throws SQLException {
        List<City> cities = Database.selectAllCities();

        int indexOfLargestNumber = 0;

        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getPopulation() > cities.get(indexOfLargestNumber).getPopulation()) {
                indexOfLargestNumber = i;
            }
        }

        System.out.println("[" + indexOfLargestNumber + "] = " + cities.get(indexOfLargestNumber).getPopulation());
    }
}
