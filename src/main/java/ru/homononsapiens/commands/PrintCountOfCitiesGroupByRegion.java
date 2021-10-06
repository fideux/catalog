package ru.homononsapiens.commands;

import ru.homononsapiens.City;
import ru.homononsapiens.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrintCountOfCitiesGroupByRegion extends Command {
    @Override
    public void execute() throws SQLException {
        for (Map.Entry<String, Integer> set : Database.selectCountOfCitiesGroupBy("region").entrySet()) {
            System.out.println(set.getKey() + " - " + set.getValue());
        }
    }
}
