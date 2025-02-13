package com.example;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.HashMap;
import java.util.Map;

public class PokeApiSchema extends AbstractSchema {

    @Override
    protected Map<String, Table> getTableMap() {
        Map<String, Table> tableMap = new HashMap<>();
        tableMap.put("POKEMON", new PokeApiTable());
        return tableMap;
    }
}
