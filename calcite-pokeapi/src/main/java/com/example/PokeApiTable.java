package com.example;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PokeApiTable extends AbstractTable implements ScannableTable {

    @Override
    public RelDataType getRowType(org.apache.calcite.rel.type.RelDataTypeFactory typeFactory) {
        return typeFactory.builder()
                .add("ID", SqlTypeName.INTEGER)
                .add("NAME", SqlTypeName.VARCHAR)
                .build();
    }

    @Override
    public Enumerable<@Nullable Object[]> scan(DataContext dataContext) {
        List<Object[]> rows = new ArrayList<>();
        try {
            // Chamando a API
            URL url = new URL("https://pokeapi.co/api/v2/pokemon?limit=100");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(connection.getInputStream());
            JsonNode results = rootNode.path("results");

            // Mapeando os dados da API para linhas
            int id = 1;
            for (JsonNode node : results) {
                rows.add(new Object[]{id++, node.path("name").asText()});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Linq4j.asEnumerable(rows);
    }
}
