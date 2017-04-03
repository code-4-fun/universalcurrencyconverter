package com.example.demo.parser;

import com.example.demo.model.conditions.ValidationError;

import java.util.HashMap;
import java.util.Map;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class QueryParser implements IParser {

    private Map<QueryWrapper, String> queries = new HashMap<>();

    @Override
    public void parse(String line) {
        if (!isLineEmpty(line) && line.startsWith("how") && line.endsWith("?")) {
            line = line.replace("?", "");
            String data[] = line.split(" is ");
            if (data.length != 2) {
                throw new ValidationError("I have no idea what you are talking about here - " + line);
            }

            QueryType type = QueryType.parse(data[0]);
            String equation = data[0].substring(data[0].indexOf(type.startWith));
            QueryWrapper queryWrapper = new QueryWrapper(type, equation);
            queries.put(queryWrapper, data[1].trim());

        } else {
            return;
        }
    }

    public Map<QueryWrapper, String> getQueries() {
        return queries;
    }

    static class QueryWrapper {
        private QueryType queryType;
        private String equation;

        public QueryWrapper(QueryType queryType, String equation) {
            this.queryType = queryType;
            this.equation = equation;
        }

        public QueryType getQueryType() {
            return queryType;
        }

        public String getEquation() {
            return equation;
        }
    }

    enum QueryType {
        CreditQuery("how many Credits"),
        ValueQuery("how much");

        QueryType(String startWith) {
            this.startWith = startWith;
        }

        static QueryType parse(String line) {
            QueryType[] queryTypes = QueryType.values();
            for (int i = 0; i < queryTypes.length; i++) {
                if (queryTypes[i].startWith.equals(line)) {
                    return queryTypes[i];
                }
            }
            return null;
        }

        String startWith;
    }

}
