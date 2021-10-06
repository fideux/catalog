package ru.homononsapiens;

import javax.sql.rowset.RowSetWarning;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Connection connection;

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = Property.getProperty("jdbc.url");
            String username = Property.getProperty("jdbc.username");
            String password = Property.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    private static List<City> addResultToCityList(ResultSet resultSet) throws SQLException {
        List<City> cities = new ArrayList<>();
        while (resultSet.next()) {
            cities.add(new City(resultSet.getString("name"),
                    resultSet.getString("region"),
                    resultSet.getString("district"),
                    resultSet.getLong("population"),
                    resultSet.getString("foundation")));
        }
        return cities;
    }

    public static Map<String, Integer> selectCountOfCitiesGroupBy(String column) throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = "SELECT region, COUNT(name) AS count FROM cities GROUP BY region";

        ResultSet resultSet = statement.executeQuery(query);
        Map<String, Integer> regions = new HashMap<>();
        while (resultSet.next()) {
            regions.put(resultSet.getString("region"), resultSet.getInt("count"));
        }

        return regions;
    }

    public static List<City> selectAllCities() throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = "SELECT id, name, region, district, population, foundation FROM cities";

        return addResultToCityList(statement.executeQuery(query));
    }

    public static List<City> selectAllCitiesOrderBy(String column, boolean asc, boolean register) throws SQLException {
        Statement statement = getConnection().createStatement();
        String sort = asc ? "ASC" : "DESC";
        column = (!register) ? String.format("lcase(%s)", column) : column;

        String query = String.format("SELECT id, name, region, district, population, foundation " +
                "FROM cities ORDER BY %s %s", column, sort);

        return addResultToCityList(statement.executeQuery(query));
    }

    public static List<City> selectAllCitiesOrderByTwoColumns(String mainColumn, String addColumn, boolean asc, boolean register) throws SQLException {
        Statement statement = getConnection().createStatement();
        String sort = asc ? "ASC" : "DESC";
        mainColumn = (!register) ? String.format("lcase(%s)", mainColumn) : mainColumn;
        addColumn = (!register) ? String.format("lcase(%s)", addColumn) : addColumn;

        String query = String.format("SELECT id, name, region, district, population, foundation " +
                "FROM cities ORDER BY %s %s, %s %s", mainColumn, sort, addColumn, sort);

        return addResultToCityList(statement.executeQuery(query));
    }

    public static boolean insertCities(List<City> cities) throws SQLException {
        if (cities == null || cities.isEmpty()) return false;

        Statement statement = getConnection().createStatement();
        StringBuilder query = new StringBuilder("INSERT INTO cities (name, region, district, population, foundation) VALUES ");

        for (int i = 0; i < cities.size(); i++) {
            query.append(String.format("('%s', '%s', '%s', %d, %s)",
                    cities.get(i).getName(), cities.get(i).getRegion(),
                    cities.get(i).getDistrict(), cities.get(i).getPopulation(),
                    cities.get(i).getFoundation()));
            if (i != cities.size() - 1) {
                query.append(", ");
            }
        }

        statement.executeUpdate(query.toString());
        return true;
    }
}
