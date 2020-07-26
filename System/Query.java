package AOQueryRunner.System;

import java.util.*;
/**
 * AOQueryRunner.System.Query Class -  AOQueryRunner.System.Query Object class, used to create valid queries.
 *
 * @author Nicky Edge
 * @version 07/04/2020
 */
public class Query {

    // instance variables
    private String firstKeyWord;
    private String objectType;
    private String secondKeyWord;
    private ArrayList<Condition> conditions;

    // Class constructor with four parameters
    public Query(String firstKeyWord, String objectType, String secondKeyWord, ArrayList<Condition> conditions) {
        this.firstKeyWord = firstKeyWord;
        this.objectType = objectType;
        this.secondKeyWord = secondKeyWord;
        this.conditions = conditions;
    }

    // Class constructor with two parameters
    public Query(String primaryKeyWord, String objectType) {
        this.firstKeyWord = primaryKeyWord;
        this.objectType = objectType;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    /**
     * Query class toString override
     *
     * @return - Query object to be printed.
     */
    @Override
    public String toString() {
        String query;
        if (this.secondKeyWord == null && this.conditions == null) {
            query = String.format("%s %s", this.firstKeyWord, this.objectType);
        } else {
            query = String.format("%s %s %s %s", this.firstKeyWord, this.objectType, this.secondKeyWord, this.conditions);
        }
        return query;
    }
}



