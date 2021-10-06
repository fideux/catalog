package ru.homononsapiens.commands;

import ru.homononsapiens.City;

import java.sql.SQLException;
import java.util.List;

public abstract class Command {
    abstract public void execute() throws SQLException;

    public void print(List<City> cities) {
        for (City city : cities) {
            System.out.println(city);
        }
    }
}
