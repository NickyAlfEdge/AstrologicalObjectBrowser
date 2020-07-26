package AOQueryRunner.GUI;

import AOQueryRunner.Exceptions.InvalidConditionException;
import AOQueryRunner.Exceptions.InvalidOperatorException;
import AOQueryRunner.Exceptions.InvalidValueException;
import AOQueryRunner.System.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * AOQueryRunner.GUI.MainFrame Class -  The main frame of the AO QueryRunner GUI, whereby components are managed and
 *                                      added. Operating as an MVC.
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class MainFrame extends JFrame implements ActionListener {

    // instance vars
    private TablePanel tablePanel;
    private ArrayList<Stars> starsList;
    private ArrayList<Messiers> messierList;
    private ArrayList<Planets> planetsList;
    private Timer timer;
    private JButton advancedSearchBtn;
    private SearchBar searchBar;

    // constructor
    public MainFrame() {
        super("AO Query Runner");
        setLayout(new BorderLayout());
        tablePanel = new TablePanel();
        MainToolBar toolBar = new MainToolBar();
        searchBar = new SearchBar();
        advancedSearchBtn = new JButton("Advanced Query Search");
        searchBar.add(advancedSearchBtn);

        advancedSearchBtn.addActionListener(this);

        /*
         * toolBar listener for the 'add stars file' button within the toolBar, called when an appropriate filename is
         * entered. This is then passed to associative methods in order to populate the starsList and output objects to
         * the stars table.
         */
        toolBar.setStarsListener(new StarsListListener() {
            @Override
            public void starsListAdded(StarsReader stars) {
                if (timer == null) {
                    starsList = stars.getStarsList();
                    tablePanel.getQueryInfo().appendText("Stars File successfully loaded");
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(0);
                    int index = tablePanel.getTabbedPane().indexOfTab("Stars");
                    createStarTable(starsList, index);
                } else {
                    JOptionPane.showMessageDialog(null, "Please add additional files after "
                                    + "the current process has finished.", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        /*
         * toolBar listener for the 'add messiers file' button within the toolBar, called when an appropriate filename is
         * entered. This is then passed to associative methods in order to populate the messierList and output objects to
         * the messiers table.
         */
        toolBar.setMessiersListener(new MessiersListListener() {
            @Override
            public void messiersListAdded(MessiersReader messiers) {
                if (timer == null) {
                    messierList = messiers.getMessiersList();
                    tablePanel.getQueryInfo().appendText("Messiers File successfully loaded");
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(0);
                    int index = tablePanel.getTabbedPane().indexOfTab("Messiers");
                    createMessierTable(messierList, index);
                } else {
                    JOptionPane.showMessageDialog(null, "Please add additional files after "
                            + "the current process has finished.", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        /*
         * toolBar listener for the 'add planets file' button within the toolBar, called when an appropriate filename is
         * entered. This is then passed to associative methods in order to populate the planetsList and output objects to
         * the planets table.
         */
        toolBar.setPlanetsListener(new PlanetsListListener() {
            @Override
            public void planetsListAdded(PlanetsReader planets) {
                if (timer == null) {
                    planetsList = planets.getPlanetsList();
                    tablePanel.getQueryInfo().appendText("Planets File successfully loaded");
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(0);
                    int index = tablePanel.getTabbedPane().indexOfTab("Planets");
                    createPlanetTable(planetsList, index);
                } else {
                    JOptionPane.showMessageDialog(null, "Please add additional files after "
                            + "the current process has finished.", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        /*
         * searchBar listener for the 'search query' button, when a query is populated via the searchBar it is then sent
         * to the corresponding initializer for subsequent parsing and population of data tables.
         */
        searchBar.setStdQuerySearchListener(new StdQuerySearchListener() {
            @Override
            public void standardQuerySearch(String query, String objType) {
                if (timer == null) {
                    if (!(starsList == null) || !(messierList == null) || !(planetsList == null)) {
                        try {
                            initialiseSearch(query, objType);
                        } catch (InvalidConditionException | ArrayIndexOutOfBoundsException ice) {
                            JOptionPane.showMessageDialog(null, "Please ensure that there are no empty Condition Fields",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        } catch (InvalidOperatorException ioe) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid operator for the queried value",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        } catch (InvalidValueException ive) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid value for the queried property",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        } catch (NullPointerException ex) {
                            JOptionPane.showMessageDialog(null, "Please populate the " + objType + " table",
                                    "Error\n", JOptionPane.PLAIN_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There has been an unexpected error while querying",
                                    "Error\n", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please Populate the Tables via adding data files to "
                                + "the program prior to Querying", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Query once the current process has finished.",
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        add(toolBar, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(searchBar, BorderLayout.SOUTH);

        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // actionListeners
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(advancedSearchBtn)) {
            if (timer == null) {
                if (!(starsList == null) || !(messierList == null) || !(planetsList == null)) {
                    AdvancedSearch advancedSearch = new AdvancedSearch();
                    // window listener for the advanced search feature
                    // disables the main searchBar buttons while this feature is open
                    advancedSearch.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowActivated(WindowEvent e) {
                            super.windowActivated(e);
                            advancedSearchBtn.setEnabled(false);
                            searchBar.getSearchBtn().setEnabled(false);
                        }
                    });
                    // window listener for the advanced search feature
                    // enables the main searchBar buttons when this feature is closed
                    advancedSearch.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            advancedSearchBtn.setEnabled(true);
                            searchBar.getSearchBtn().setEnabled(true);
                        }
                    });

                    /*
                     * advancedQuerySearch listener for the 'advanced query search' menu, when a query is populated via
                     * the panel it is then sent to the corresponding initializer for subsequent parsing and population of data tables.
                     */
                    advancedSearch.setAdvancedQuerySearchListener(new AdvancedQuerySearchListener() {
                        @Override
                        public void advancedQuerySearch(String query, String objType) {
                            if (timer == null) {
                                try {
                                    initialiseSearch(query, objType);
                                } catch (InvalidConditionException | ArrayIndexOutOfBoundsException ice) {
                                    JOptionPane.showMessageDialog(null, "Please ensure that there are no empty Condition Fields",
                                            "Error", JOptionPane.PLAIN_MESSAGE);
                                } catch (InvalidOperatorException ioe) {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid operator for the queried value",
                                            "Error", JOptionPane.PLAIN_MESSAGE);
                                } catch (InvalidValueException ive) {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid value for the queried property",
                                            "Error", JOptionPane.PLAIN_MESSAGE);
                                } catch (NullPointerException ex) {
                                    JOptionPane.showMessageDialog(null, "Please populate the " + objType + " table.",
                                            "Error\n", JOptionPane.PLAIN_MESSAGE);
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "There has been an unexpected error while querying",
                                            "Error", JOptionPane.PLAIN_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Please Query once the current process has finished.",
                                        "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Please Populate the Tables via adding data files to "
                            + "the program prior to Querying", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Query once the current process has finished.",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /**
     * createStarTable method,  creates Stars table. Incrementing a timer with each item added to the table and displaying
     *                          it's progress within the consoles JProgressBar.
     *
     * @param starList          - ArrayList<Stars> the star objects to display
     * @param tabIndex          - the index of the stars tab within the GUI to output to
     */
    private void createStarTable(ArrayList<Stars> starList, int tabIndex) {

        DefaultTableModel defaultModel = new DefaultTableModel(Constants.starTblHead, 0);
        JTable table = new JTable(defaultModel);
        tablePanel.getQueryInfo().getQueryBuffer().setMaximum(starList.size() - 1);

        timer = new Timer(1, new ActionListener() {
            int index = 0;
            public void actionPerformed(ActionEvent e) {
                if (index == starList.size()) {
                    timer.stop();
                    timer = null;
                    tablePanel.getQueryInfo().appendText("[" + index + "]" + " Stars" + " - Successfully added to Stars Table");
                } else {
                    defaultModel.addRow(starObjForTable(starList, index));
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(index);
                    index++;
                }
            }
        });
        timer.start();
        formatTable(tabIndex, table);
    }

    /**
     * createMessierTable method,  creates Messiers table. Incrementing a timer with each item added to the table and displaying
     *                             it's progress within the consoles JProgressBar.
     *
     * @param messierList       - ArrayList<Messiers> the messiers objects to display
     * @param tabIndex          - the index of the messiers tab within the GUI to output to
     */
    private void createMessierTable(ArrayList<Messiers> messierList, int tabIndex) {

        DefaultTableModel defaultModel = new DefaultTableModel(Constants.messierTblHead, 0);
        JTable table = new JTable(defaultModel);
        tablePanel.getQueryInfo().getQueryBuffer().setMaximum(messierList.size() - 1);

        timer = new Timer(1, new ActionListener() {
            int index = 0;
            public void actionPerformed(ActionEvent e) {
                if (index == messierList.size()) {
                    timer.stop();
                    timer = null;
                    tablePanel.getQueryInfo().appendText("[" + index + "]" + " Messiers" + " - Successfully added to Messiers Table");
                } else {
                    defaultModel.addRow(messierObjForTable(messierList, index));
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(index);
                    index++;
                }
            }
        });
        timer.start();
        formatTable(tabIndex, table);
    }

    /**
     * createPlanetTable method,   creates Planets table. Incrementing a timer with each item added to the table and displaying
     *                             it's progress within the consoles JProgressBar.
     *
     * @param planetList       - ArrayList<Planets> the planets objects to display
     * @param tabIndex          - the index of the planets tab within the GUI to output to
     */
    private void createPlanetTable(ArrayList<Planets> planetList, int tabIndex) {

        DefaultTableModel defaultModel = new DefaultTableModel(Constants.planetTblHead, 0);
        JTable table = new JTable(defaultModel);
        tablePanel.getQueryInfo().getQueryBuffer().setMaximum(planetList.size() - 1);

        timer = new Timer(1, new ActionListener() {
        int index = 0;
        public void actionPerformed(ActionEvent e) {
            if (index == planetList.size()) {
                timer.stop();
                timer = null;
                tablePanel.getQueryInfo().appendText("[" + index + "]" + " Planets" + " - Successfully added to Planets Table");
            } else {
                    defaultModel.addRow(planetObjForTable(planetList, index));
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(index);
                    index++;
                }
            }
        });
        timer.start();
        formatTable(tabIndex, table);
    }

    /**
     * createResultTable method,   creates query results table. Incrementing a timer with each item added to the table and displaying
     *                             it's progress within the consoles JProgressBar.
     *
     * @param resultList        - ArrayList<AO> the resulting query objects to display
     * @param tabIndex          - the index of the query results tab within the GUI to output to
     * @param objType           - the query results object type, to populate the table with the corresponding object
     */
    private void createResultTable(ArrayList<AO> resultList, int tabIndex, String objType) {

        DefaultTableModel defaultModel = new DefaultTableModel(matchObjHeader(objType), 0);
        JTable table = new JTable(defaultModel);
        tablePanel.getQueryInfo().getQueryBuffer().setMaximum(resultList.size() - 1);

        timer = new Timer(1, new ActionListener() {
            int index = 0;
            public void actionPerformed(ActionEvent e) {
                if (index == resultList.size()) {
                    timer.stop();
                    timer = null;
                    tablePanel.getQueryInfo().appendText("[" + index + "]" + " results" + " - Successfully added to Query Results Table");
                } else {
                    if (objType.equalsIgnoreCase("Stars")) {
                        try {
                            defaultModel.addRow(starAOObjForTable(resultList, index));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There has been an unexpected error while querying",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else if (objType.equalsIgnoreCase("Messiers")) {
                        try {
                            defaultModel.addRow(messierAOObjForTable(resultList, index));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There has been an unexpected error while querying",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else if (objType.equalsIgnoreCase("Planets")) {
                        try {
                            defaultModel.addRow(planetAOObjForTable(resultList, index));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There has been an unexpected error while querying",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    tablePanel.getQueryInfo().getQueryBuffer().setValue(index);
                    index++;
                }
            }
        });
        timer.start();
        formatTable(tabIndex, table);
    }

    /**
     * starObjForTable method,     creates Object array object with the properties of the star at the corresponding index
     *                             passed to the method. To then populate the associative JTable.
     *
     * @param starList          - ArrayList<Stars> the list being analysed
     * @param index             - the index of target object to create an Object[] from
     * @return new Object[]
     */
    private Object[] starObjForTable(ArrayList<Stars> starList, int index) {

        return new Object[]{
                starList.get(index).getName(),
                BigDecimal.valueOf(starList.get(index).getRa()).toPlainString(),
                BigDecimal.valueOf(starList.get(index).getDeclination()).toPlainString(),
                BigDecimal.valueOf(starList.get(index).getMagnitude()).toPlainString(),
                BigDecimal.valueOf(starList.get(index).getDistance()).toPlainString(),
                starList.get(index).getType(),
                starList.get(index).getConstellation()
        };
    }

    /**
     * messierObjForTable method,     creates Object array object with the properties of the messier at the corresponding index
     *                                passed to the method. To then populate the associative JTable.
     *
     * @param messierList          - ArrayList<Messiers> the list being analysed
     * @param index                - the index of target object to create an Object[] from
     * @return new Object[]
     */
    private Object[] messierObjForTable(ArrayList<Messiers> messierList, int index) {

        return new Object[]{
                messierList.get(index).getName(),
                BigDecimal.valueOf(messierList.get(index).getRa()).toPlainString(),
                BigDecimal.valueOf(messierList.get(index).getDeclination()).toPlainString(),
                BigDecimal.valueOf(messierList.get(index).getMagnitude()).toPlainString(),
                BigDecimal.valueOf(messierList.get(index).getDistance()).toPlainString(),
                messierList.get(index).getConstellation(),
                messierList.get(index).getDescription()
        };
    }

    /**
     * planetObjForTable method,      creates Object array object with the properties of the planet at the corresponding index
     *                                passed to the method. To then populate the associative JTable.
     *
     * @param planetList           - ArrayList<Planets> the list being analysed
     * @param index                - the index of target object to create an Object[] from
     * @return new Object[]
     */
    private Object[] planetObjForTable(ArrayList<Planets> planetList, int index) {

        return new Object[]{
                    planetList.get(index).getName(),
                    BigDecimal.valueOf(planetList.get(index).getRa()).toPlainString(),
                    BigDecimal.valueOf(planetList.get(index).getDeclination()).toPlainString(),
                    BigDecimal.valueOf(planetList.get(index).getMagnitude()).toPlainString(),
                    BigDecimal.valueOf(planetList.get(index).getDistance()).toPlainString(),
                    BigDecimal.valueOf(planetList.get(index).getAlbedo()).toPlainString()
        };
    }

    /**
     * initialiseSearch method,       method used to differentiate between queries, as "Select 'messier'/'planets'/'stars
     *                                has an upper bound of 16 and other queries begin with 'select 'object' where' breaching
     *                                this. Calling the corresponding methods for either query type.
     *
     * @param query                  - the parsed query to be validated and initialised
     * @param objType                - the object type being queried
     */
    private void initialiseSearch(String query, String objType) throws Exception {

        ArrayList<AO> resultList;

        if (query.length() <= 16) {
            resultList = new ArrayList<>(getAOTypeList(objType));
            tablePanel.getQueryInfo().appendText("Query Successful");
            tablePanel.getQueryInfo().getQueryBuffer().setValue(0);
            int index = tablePanel.getTabbedPane().indexOfTab("Query Results");
            createResultTable(resultList, index, objType);
        } else {
            resultList = new ArrayList<>(getAOTypeList(objType));
            QueryValidator unvalidatedQuery = new QueryValidator(query);
            QueryRunner validatedQuery = new QueryRunner(unvalidatedQuery.getValidQuery(), resultList);
            resultList = validatedQuery.getResultsList();
            tablePanel.getQueryInfo().appendText("Query Successful");
            tablePanel.getQueryInfo().getQueryBuffer().setValue(0);
            int index = tablePanel.getTabbedPane().indexOfTab("Query Results");
            createResultTable(resultList, index, objType);
        }
    }

    /**
     * getAOTypeList method,           used to retrieve a holder arraylist for the resulting data than can then be iterated
     *                                 through for subsequent population of data tables.
     *
     * @param objType                  - the object type being queried
     * @return ArrayList<AO>
     */
    private ArrayList<AO> getAOTypeList(String objType) {

        ArrayList<AO> resultHolder = new ArrayList<>();

        if (objType.equalsIgnoreCase("Stars")) {
            resultHolder.addAll(starsList);
        } else if (objType.equalsIgnoreCase("Messiers")) {
            resultHolder.addAll(messierList);
        } else if (objType.equalsIgnoreCase("Planets")){
            resultHolder.addAll(planetsList);
        } else {
            JOptionPane.showMessageDialog(null, "There has been an unexpected error while querying",
                    "Error", JOptionPane.PLAIN_MESSAGE);
        }
        return resultHolder;
    }

    /**
     * starAOObjForTable method,     creates Object array object with the properties of the star at the corresponding index
     *                               passed to the method. To then populate the associative query result JTable.
     *
     * @param starAOList            - ArrayList<AO> the list being analysed
     * @param index                 - the index of target object to create an Object[] from
     * @return new Object[]
     */
    private Object[] starAOObjForTable(ArrayList<AO> starAOList, int index) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method starType = starAOList.get(index).getClass().getMethod("getType");
        String type = (String) starType.invoke(starAOList.get(index));
        Method starConst = starAOList.get(index).getClass().getMethod("getConstellation");
        String constellation = (String) starConst.invoke(starAOList.get(index));

        return new Object[]{
                starAOList.get(index).getName(),
                BigDecimal.valueOf(starAOList.get(index).getRa()).toPlainString(),
                BigDecimal.valueOf(starAOList.get(index).getDeclination()).toPlainString(),
                BigDecimal.valueOf(starAOList.get(index).getMagnitude()).toPlainString(),
                BigDecimal.valueOf(starAOList.get(index).getDistance()).toPlainString(),
                type,
                constellation
        };
    }

    /**
     * messierAOObjForTable method,     creates Object array object with the properties of the messier at the corresponding index
     *                                  passed to the method. To then populate the associative query result JTable.
     *
     * @param messierAOList             - ArrayList<AO> the list being analysed
     * @param index                     - the index of target object to create an Object[] from
     * @return new Object[]
     */
    private Object[] messierAOObjForTable(ArrayList<AO> messierAOList, int index) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method messierConst = messierAOList.get(index).getClass().getMethod("getConstellation");
        String constellation = (String) messierConst.invoke(messierAOList.get(index));
        Method messierDesc = messierAOList.get(index).getClass().getMethod("getDescription");
        String description = (String) messierDesc.invoke(messierAOList.get(index));

        return new Object[]{
                messierAOList.get(index).getName(),
                BigDecimal.valueOf(messierAOList.get(index).getRa()).toPlainString(),
                BigDecimal.valueOf(messierAOList.get(index).getDeclination()).toPlainString(),
                BigDecimal.valueOf(messierAOList.get(index).getMagnitude()).toPlainString(),
                BigDecimal.valueOf(messierAOList.get(index).getDistance()).toPlainString(),
                constellation,
                description
        };
    }

    /**
     * planetAOObjForTable method,      creates Object array object with the properties of the planet at the corresponding index
     *                                  passed to the method. To then populate the associative query result JTable.
     *
     * @param planetAOList              - ArrayList<AO> the list being analysed
     * @param index                     - the index of target object to create an Object[] from
     * @return new Object[]
     */
    private Object[] planetAOObjForTable(ArrayList<AO> planetAOList, int index) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method planetAlbedo = planetAOList.get(index).getClass().getMethod("getAlbedo");
        double albedo = (double) planetAlbedo.invoke(planetAOList.get(index));

        return new Object[]{
                planetAOList.get(index).getName(),
                BigDecimal.valueOf(planetAOList.get(index).getRa()).toPlainString(),
                BigDecimal.valueOf(planetAOList.get(index).getDeclination()).toPlainString(),
                BigDecimal.valueOf(planetAOList.get(index).getMagnitude()).toPlainString(),
                BigDecimal.valueOf(planetAOList.get(index).getDistance()).toPlainString(),
                BigDecimal.valueOf(albedo).toPlainString()
        };
    }

    /**
     * matchObjHeader method,          matches the table header String[] array to the passed object time, returning it for
     *                                 subsequent implementation within the associative table type.
     *
     * @param objType                  - the object type being queried
     * @return String[]
     */
    private String[] matchObjHeader(String objType) {

        if (objType.equalsIgnoreCase("Stars")) {
            return Constants.starTblHead;
        } else if (objType.equalsIgnoreCase("Messiers")) {
            return Constants.messierTblHead;
        } else {
            return Constants.planetTblHead;
        }
    }

    /**
     * formatTable method,              formats the passed table at the corresponding index.
     *
     * @param tabIndex                  - the tab of the table being formatted
     * @param table                     - the table being formatted
     */
    private void formatTable(int tabIndex, JTable table) {

        table.setBackground(Color.white);
        table.setBorder(BorderFactory.createLineBorder(Color.black));
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);
        tablePanel.getTabbedPane().setComponentAt(tabIndex, new JScrollPane(table));
    }
}
