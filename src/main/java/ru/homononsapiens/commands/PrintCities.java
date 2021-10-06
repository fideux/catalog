package ru.homononsapiens.commands;

import ru.homononsapiens.City;
import ru.homononsapiens.Database;

import java.sql.SQLException;
import java.util.List;

public class PrintCities extends Command {
    @Override
    public void execute() throws SQLException {
        List<City> cities = Database.selectAllCities();
        print(cities);
    }
}
