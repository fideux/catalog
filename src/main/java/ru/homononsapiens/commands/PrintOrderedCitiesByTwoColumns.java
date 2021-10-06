package ru.homononsapiens.commands;

import ru.homononsapiens.City;
import ru.homononsapiens.Database;

import java.sql.SQLException;
import java.util.List;

public class PrintOrderedCitiesByTwoColumns extends Command {
    @Override
    public void execute() throws SQLException {
        List<City> cities = Database.selectAllCitiesOrderByTwoColumns("district", "name", false, true);
        print(cities);
    }
}
