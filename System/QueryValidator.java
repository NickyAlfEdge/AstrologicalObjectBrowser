package AOQueryRunner.System;

import AOQueryRunner.Exceptions.InvalidConditionException;
import AOQueryRunner.Exceptions.InvalidOperatorException;
import AOQueryRunner.Exceptions.InvalidValueException;
/**
 * AOQueryRunner.System.QueryValidator Class    -  reads Query File, validates queries and outputs a string for subsequent parsing.
 *
 * @author Nicky Edge
 * @version 05/04/2020
 */
public class QueryValidator {

    // instance var
    private String validQuery;

    /**
     * QueryRunner Object Constructor
     *
     * @param query    - query line being validated
     */
    public QueryValidator(String query) throws InvalidConditionException, InvalidOperatorException, InvalidValueException  {

        validQuery = validateQueries(query);
    }

    public String getValidQuery() {
        return validQuery;
    }


    /**
     * readQueryFile method
     *
     * @param query - the query being validated
     * @return validQuery - StringBuilder.toString value of valid query
     */
    private String validateQueries(String query) throws InvalidConditionException, InvalidOperatorException, InvalidValueException {

        // query StringBuilder
        StringBuilder sb = new StringBuilder();
        // line being parsed
        String[] words = query.trim().split("\\s++");
        // count the queries, used for error tracking
        int queryCounter = 0;

        String primaryQueryTag = words[queryCounter].trim();
        queryCounter++;
        String objectType = words[queryCounter].trim();
        if (words.length > 2) {
            queryCounter++;
            String secondaryQueryTag = words[queryCounter].trim();
            String conLine = query.substring(query.indexOf(' ', query.indexOf("Where"))+1);
            ConditionValidator condition = new ConditionValidator(conLine);
            String conditions = condition.validateConditions(condition);
            if (!conditions.equals("")) {
                sb.append(primaryQueryTag).append(" ").append(objectType).append(" ").append(secondaryQueryTag).append(conditions);
            }
        } else {
            // create two parameter Query String
            sb.append(primaryQueryTag).append(" ").append(objectType);
        }
        return sb.toString();
    }
}
