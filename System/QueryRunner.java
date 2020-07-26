package AOQueryRunner.System;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
/**
 * AOQueryRunner.System.QueryRunner Class    -  reads AOQueryRunner.System.Query File, creating appropriate Query Objects and outputting resulting Queries.
 *
 * @author Nicky Edge
 * @version 05/04/2020
 */
public class QueryRunner {

    private ArrayList<AO> resultsList;

    /**
     * AOQueryRunner.System.QueryRunner Object Constructor
     *
     * @param validQuery         - a validated query String to run
     */
    public QueryRunner(String validQuery, ArrayList<AO> inList) {

        resultsList = run(validQuery, inList);
    }

    public ArrayList<AO> getResultsList() {
        return resultsList;
    }

    /**
     * createQuery method, used to create the appropriate Query objects after they have been validated via the
     * QueryValidator class and passed to the QueryRunner run() method.
     *
     * @param query         - the validated query in String format
     * @return new Query Object
     */
    private Query createQuery(String query) {

        int queryCounter = 0;
        String primaryKeyWord;
        String objectType;
        String secondKeyWord;
        ArrayList<Condition> conditions = new ArrayList<>();

        String[] words = query.split("\\s++");

        primaryKeyWord = words[queryCounter].trim();
        queryCounter++;
        objectType = words[queryCounter].trim();
        queryCounter++;
        secondKeyWord = words[queryCounter].trim();
        queryCounter++;
        while (queryCounter < words.length) {
            String variable = words[queryCounter].trim();
            queryCounter++;
            String operator = words[queryCounter].trim();
            queryCounter++;
            String value;
            if (words[queryCounter].equalsIgnoreCase("desc:")) {
                queryCounter++;
                value = words[queryCounter];
                value = value.replaceAll("-", " ").trim();
            } else {
                value = words[queryCounter].trim();
            }
            queryCounter++;
            conditions.add(new Condition(variable, operator, value));
        }
        return new Query(primaryKeyWord, objectType, secondKeyWord, conditions);
    }

    /**
     * initialiseQuery, reads the operator of conditions and calls the appropriate sort method
     */
    private <T> void initialiseQuery(Query validQuery, ArrayList<T> resultList) {

        ArrayList<Condition> conditions = validQuery.getConditions();

        for (Condition condition : conditions) {
            if (condition.getOperator().equals("<")) {
                lessThan(resultList, condition.getVariableName(), Double.parseDouble(condition.getValue()));
            } else if (condition.getOperator().equals(">")) {
                greaterThan(resultList, condition.getVariableName(), Double.parseDouble(condition.getValue()));
            } else if ((condition.getOperator().equals("=")) && (Constants.numericVarList.contains(condition.getVariableName()))) {
                equalsNum(resultList, condition.getVariableName(), Double.parseDouble(condition.getValue()));
            } else if ((condition.getOperator().equals("=")) && (Constants.nonNumericVarList.contains(condition.getVariableName()))
                        && !(condition.getValue().equalsIgnoreCase("null"))) {
                equalsString(resultList, condition.getVariableName(), condition.getValue());
            } else if ((condition.getOperator().equals("=")) && (Constants.nonNumericVarList.contains(condition.getVariableName()))
                    && (condition.getValue().equalsIgnoreCase("null"))) {
                equalsNull(resultList, condition.getVariableName());
            } else if ((condition.getOperator().equals("!=")) && (Constants.numericVarList.contains(condition.getVariableName()))) {
                notEqualsNum(resultList, condition.getVariableName(), Double.parseDouble(condition.getValue()));
            } else if ((condition.getOperator().equals("!=")) && (Constants.nonNumericVarList.contains(condition.getVariableName()))
                        && !(condition.getValue().equalsIgnoreCase("null"))) {
                notEqualsString(resultList, condition.getVariableName(), condition.getValue());
            } else if ((condition.getOperator().equals("!=")) && (Constants.nonNumericVarList.contains(condition.getVariableName()))
                    && (condition.getValue().equalsIgnoreCase("null"))) {
                notEqualsNull(resultList, condition.getVariableName());
            } else if (condition.getOperator().equals(">=")) {
                greaterThanOrEqual(resultList, condition.getVariableName(), Double.parseDouble(condition.getValue()));
            } else if (condition.getOperator().equals("<=")) {
                lessThanOrEqual(resultList, condition.getVariableName(), Double.parseDouble(condition.getValue()));
            }
        }
    }

    /**
     * varGetMethodName method, used to return the appropriate getter name, in order to use reflection
     *
     * @param varName       - the variable name of the read in query condition
     * @return String getter accessor method name
     */
    private String varGetMethodName(String varName) {

        if ((varName.equalsIgnoreCase("name")) || (varName.equalsIgnoreCase("number"))) {
            return "getName";
        } else if (varName.equalsIgnoreCase("ra")){
            return "getRa";
        } else if (varName.equalsIgnoreCase("decl")){
            return "getDeclination";
        } else if (varName.equalsIgnoreCase("magn")){
            return "getMagnitude";
        } else if (varName.equalsIgnoreCase("distance")){
            return "getDistance";
        } else if (varName.equalsIgnoreCase("albedo")){
            return "getAlbedo";
        } else if (varName.equalsIgnoreCase("type")){
            return "getType";
        } else if (varName.equalsIgnoreCase("constellation")){
            return "getConstellation";
        } else {
            return "getDescription";
        }
   }

    /**
     * lessThan method, removes values from the queried list if they are greater than the desired value
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void lessThan(ArrayList<T> inList, String varName, double value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                double d = (double) method.invoke(inList.get(i));
                if (!(d < value)) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * greaterThan method, removes values from the queried list if they are less than the desired value
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void greaterThan(ArrayList<T> inList, String varName, double value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                double d = (double) method.invoke(inList.get(i));
                if (!(d > value)) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * equalsNum method, removes values from the queried list if they do not match the desired value
     * (this method is for values that are of type double that are equal to the desired value)
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void equalsNum(ArrayList<T> inList, String varName, double value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                double d = (double) method.invoke(inList.get(i));
                if (d != value) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * equalsString method, removes values from the queried list if they do not match the desired value
     * (this method is for values that are of type String that are equal to the desired value)
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void equalsString(ArrayList<T> inList, String varName, String value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                String s = (String) method.invoke(inList.get(i));
                if (!s.equalsIgnoreCase(value)) {
                        inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("Class casting error, please ensure a method exists for: " + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * equalsNull method, removes values from the queried list if they do not match the desired value
     * (this method is for values that are empty.)
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     */
    private <T> void equalsNull(ArrayList<T> inList, String varName) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                String s = (String) method.invoke(inList.get(i));
                if (!(s.isEmpty())) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * notEqualsNum method, removes values from the queried list if they do match the desired value
     * (this method is for values that are of type double that are not equal to the desired value)
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void notEqualsNum(ArrayList<T> inList, String varName, double value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                double d = (double) method.invoke(inList.get(i));
                if (d == value) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * notEqualsString method, removes values from the queried list if they do match the desired value
     * (this method is for values that are of type String that are not equal to the desired value)
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void notEqualsString(ArrayList<T> inList, String varName, String value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                String s = (String) method.invoke(inList.get(i));
                if (s.equalsIgnoreCase(value)) {
                        inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * notEqualsNull method, removes values from the queried list if they do match the desired value
     * (this method is for values that are empty.)
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     */
    private <T> void notEqualsNull(ArrayList<T> inList, String varName) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                String s = (String) method.invoke(inList.get(i));
                if (s.isEmpty()) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * greaterThanOrEqual method, removes values from the queried list if they are less than or equal the desired value
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void greaterThanOrEqual(ArrayList<T> inList, String varName, double value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                double d = (double) method.invoke(inList.get(i));
                if (!(d >= value)) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * lessThanOrEqual method, removes values from the queried list if they are greater than or equal the desired value
     *
     * @param inList        - the desired list of objects to be sorted through
     * @param varName       - the variable name that is being queried
     * @param value         - the target value to be searched for
     */
    private <T> void lessThanOrEqual(ArrayList<T> inList, String varName, double value) {

        for (int i = inList.size() - 1; i >= 0; i--) {
            try {
                Method method = inList.get(i).getClass().getMethod(varGetMethodName(varName));
                double d = (double) method.invoke(inList.get(i));
                if (!(d <= value)) {
                    inList.remove(inList.get(i));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("There has been an error while sorting" + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * run method
     *
     * @param validatedQuery - the validated query being parsed
     */
    private <T> ArrayList<T> run(String validatedQuery, ArrayList<T> list) {
        ArrayList<T> resultList = new ArrayList<>(list);
        Query validQuery = createQuery(validatedQuery);
        initialiseQuery(validQuery, resultList);

        return resultList;
    }
}
