import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class Datenbank extends Application {

    private static final String hostname = "egmontix";
    private static final String port = "1521";
    private static final String sid = "dev3";
    private static final String username = "";
    private static final String password = "" ;
    private static final String url = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + sid;
    HBox hinzufuegen = new HBox();
    HBox filtern = new HBox();

    private TableView table = new TableView();
    private ObservableList<Mitarbeiter> data =
            FXCollections.observableArrayList();


    public static void main(String[] args) {
        launch(args);
    }

    private void getConnection() {
        Connection con = null;
        ResultSet rs = null;
        Statement statement = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = java.sql.DriverManager.getConnection(url, username, password);
            String SQL = "SELECT * FROM MITARBEITERUEBERSICHT";
            statement = con.createStatement();
            rs = statement.executeQuery(SQL);


            while (rs.next()) {
                Mitarbeiter a = new Mitarbeiter();
                a.setMitarbeiterID(rs.getString("ID"));
                a.setVorname(rs.getString("Vorname"));
                a.setNachname(rs.getString("Nachname"));
                a.setGehalt(rs.getString("Gehalt"));
                a.setAbteilung(rs.getString("Abteilung"));
                a.setAbteilungsname(rs.getString("Abteilungsname"));
                a.setStadt(rs.getString("Stadt"));
                a.setLand(rs.getString("Land"));
                a.setBonuszahlung(rs.getString("Bonuszahlung"));
                a.setAuszahlungsdatum(rs.getString("Auszahlungsdatum"));

                data.add(a);
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException s) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException q) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException l) {
                }
            }
        }

    }

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException, IOException {
        primaryStage.setTitle("Mitarbeiterübersicht");
        primaryStage.setWidth(1300);
        primaryStage.setHeight(650);

        Scene scene = new Scene(new Group(), 200, 400);
        scene.getStylesheets().add("Datenbank.css");

        final Label label = new Label("Übersicht");

        table.setEditable(true);
        table.setMinWidth(1200);

        getConnection();
        insertColumns();
        filter();
        insertNewMitarbeiter();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new
                Insets(10, 0, 0, 10));
        vbox.getChildren().
                addAll(label, filtern, table, hinzufuegen);
        ((Group) scene.getRoot()).
                getChildren().
                addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void insertColumns() {
        //ID
        TableColumn<Mitarbeiter, String> idCol = new TableColumn("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().getID());

        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMitarbeiterID(t.getNewValue());
                    }
                }
        );

        //Vorname
        TableColumn<Mitarbeiter, String> vornameCol = new TableColumn("Vorname");
        vornameCol.setCellValueFactory(cellData -> cellData.getValue().getVorname());
        vornameCol.setMinWidth(150);

        vornameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vornameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setVorname(t.getNewValue());
                    }
                }
        );

        //Nachname
        TableColumn<Mitarbeiter, String> nachnameCol = new TableColumn("Nachname");
        nachnameCol.setCellValueFactory(cellData -> cellData.getValue().getNachname());
        nachnameCol.setMinWidth(150);

        nachnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nachnameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNachname(t.getNewValue());
                    }
                }
        );

        //Gehalt
        TableColumn<Mitarbeiter, String> gehaltCol = new TableColumn("Gehalt");
        gehaltCol.setCellValueFactory(
                new PropertyValueFactory("gehalt"));
        gehaltCol.setCellValueFactory(cellData ->
                Bindings.format("%.2f €", Double.valueOf(cellData.getValue().getEuro())));

        gehaltCol.setCellFactory(TextFieldTableCell.forTableColumn());
        gehaltCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGehalt(t.getNewValue());
                    }
                }
        );

        //Abteilung
        TableColumn<Mitarbeiter, String> abteilungCol = new TableColumn("Abteilung");
        abteilungCol.setCellValueFactory(cellData -> cellData.getValue().getAbteilung());
        abteilungCol.setMinWidth(100);

        abteilungCol.setCellFactory(TextFieldTableCell.forTableColumn());
        abteilungCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAbteilung(t.getNewValue());
                    }
                }
        );

        //Abteilungsname
        TableColumn<Mitarbeiter, String> abteilungsnameCol = new TableColumn("Abteilungsname");
        abteilungsnameCol.setCellValueFactory(cellData -> cellData.getValue().getAbteilungsname());
        abteilungsnameCol.setMinWidth(200);

        abteilungsnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        abteilungsnameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAbteilungsname(t.getNewValue());
                    }
                }
        );

        //Stadt
        TableColumn<Mitarbeiter, String> stadtCol = new TableColumn("Stadt");
        stadtCol.setCellValueFactory(cellData -> cellData.getValue().getStadt());

        stadtCol.setCellFactory(TextFieldTableCell.forTableColumn());
        stadtCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStadt(t.getNewValue());
                    }
                }
        );

        //Land
        TableColumn<Mitarbeiter, String> landCol = new TableColumn("Land");
        landCol.setCellValueFactory(cellData -> cellData.getValue().getLand());

        landCol.setCellFactory(TextFieldTableCell.forTableColumn());
        landCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLand(t.getNewValue());
                    }
                }
        );

        //Standort (nestetd)
        TableColumn standortCol = new TableColumn("Standort");
        standortCol.getColumns().addAll(stadtCol, landCol);


        //Hoehe
        TableColumn<Mitarbeiter, String> hoeheCol = new TableColumn("Höhe");
        hoeheCol.setCellValueFactory(cellData -> cellData.getValue().getHoehe());

        hoeheCol.setCellFactory(TextFieldTableCell.forTableColumn());
        hoeheCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBonuszahlung(t.getNewValue());
                    }
                }
        );

        //Auszahlungsdatum
        TableColumn<Mitarbeiter, String> auszahlungsdatumCol = new TableColumn("Auszahlungsdatum");
        auszahlungsdatumCol.setCellValueFactory(cellData -> cellData.getValue().getAuszahlungsdatum());

        auszahlungsdatumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        auszahlungsdatumCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Mitarbeiter, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Mitarbeiter, String> t) {
                        ((Mitarbeiter) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuszahlungsdatum(t.getNewValue());
                    }
                }
        );

        //Bonuszahlungen (nested)
        TableColumn bonuszahlungenCol = new TableColumn("Bonuszahlungen");
        bonuszahlungenCol.getColumns().addAll(hoeheCol, auszahlungsdatumCol);

        table.setItems(data);
        table.getColumns().addAll(idCol, vornameCol, nachnameCol, gehaltCol, abteilungCol, abteilungsnameCol, standortCol, bonuszahlungenCol);
    }

    public void filter() {
        Label label1 = new Label("Name:");
        TextField textField = new TextField();

        filtern.getChildren().addAll(label1, textField);
        filtern.setSpacing(10);

        textField.setPromptText("Filter");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ObservableList<Mitarbeiter> filteredList = FXCollections.observableArrayList();
                for (Mitarbeiter m : data) {
                    if (m.getVorname().toString().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(m);
                    } else if (m.getNachname().toString().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(m);
                    }
                }
                table.setItems(filteredList);
            } else {
                table.setItems(data);
            }
            table.refresh();
        });
    }

    public void insertNewMitarbeiter() {

        final TextField addID = new TextField();
        addID.setPromptText("ID");
        addID.setMaxWidth(65);


        final TextField addVorname = new TextField();
        addVorname.setPromptText("Vorname");
        addVorname.setMaxWidth(100);


        final TextField addNachname = new TextField();
        addNachname.setPromptText("Nachname");
        addNachname.setMaxWidth(120);

        final TextField addGehalt = new TextField();
        addGehalt.setPromptText("Gehalt");
        addGehalt.setMaxWidth(70);


        final TextField addAbteilung = new TextField();
        addAbteilung.setPromptText("Abteilung");
        addAbteilung.setMaxWidth(90);

        final TextField addHoehe = new TextField();
        addHoehe.setPromptText("Hoehe");
        addHoehe.setMaxWidth(100);


        final TextField addAuszahlungsdatum = new TextField();
        addAuszahlungsdatum.setPromptText("Auszahlungsdatum");
        addAuszahlungsdatum.setMaxWidth(160);

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String vorname = addVorname.getText();
                String nachname = addNachname.getText();
                int laengeVorname = vorname.length();
                int laengeNachname = nachname.length();

                if (laengeVorname < 2 || laengeNachname == 1) {
                    error();
                    Text t = new Text(10, 50, "Vor- und Nachname müssen mindestens 2 Buchstaben besitzen!");
                    t.setFont(new Font(16));
                    t.setFill(Color.RED);

                } else if (laengeVorname > 42 || laengeNachname > 42) {
                   errorTooManyCharacters();
                } else if (laengeNachname == 0) {
                    errorNachnameRequieredField();
                } else {
                    data.add(new Mitarbeiter(
                            addID.getText(),
                            addVorname.getText(),
                            addNachname.getText(),
                            addGehalt.getText(),
                            addAbteilung.getText(),
                            addHoehe.getText(),
                            addAuszahlungsdatum.getText()
                    ));
                    Connection con = null;
                    PreparedStatement updateBonuszahlungen = null;
                    PreparedStatement updateMitarbeiter = null;
                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        con = java.sql.DriverManager.getConnection(url, username, password);

                        updateMitarbeiter = con.prepareStatement("INSERT INTO MITARBEITER (ID, VORNAME, NAME, GEHALT, ABTEILUNG) VALUES (?, ?, ?, ?, ?)");
                        updateMitarbeiter.setString(1, addID.getText());
                        updateMitarbeiter.setString(2, addVorname.getText());
                        updateMitarbeiter.setString(3, addNachname.getText());
                        updateMitarbeiter.setString(4, addGehalt.getText());
                        updateMitarbeiter.setString(5, addAbteilung.getText());
                        updateMitarbeiter.executeQuery();

                        String next = "(Bonuszahlungen_seq.NEXTVAL)";
                        updateBonuszahlungen = con.prepareStatement("INSERT INTO BONUSZAHLUNGEN (DATUM, WERT, ID, MITARBEITER) VALUES (?, ? ," + next + ", ?)");
                        updateBonuszahlungen.setString(1, addAuszahlungsdatum.getText());
                        updateBonuszahlungen.setString(2, addHoehe.getText());
                        updateBonuszahlungen.setString(3, addID.getText());
                        updateBonuszahlungen.executeQuery();

                        addID.clear();
                        addVorname.clear();
                        addNachname.clear();
                        addGehalt.clear();
                        addAbteilung.clear();
                        addHoehe.clear();
                        addAuszahlungsdatum.clear();
                    } catch (SQLException p) {
                    } catch (ClassNotFoundException o) {
                    } catch (NullPointerException n) {
                        System.out.println(n);
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException s) {
                            }
                        }
                        if (updateBonuszahlungen != null) {
                            try {
                                updateBonuszahlungen.close();
                            } catch (SQLException er) {
                            }
                        }
                        if (updateMitarbeiter != null) {
                            try {
                                updateMitarbeiter.close();
                            } catch (SQLException et) {
                            }
                        }
                    }
                }
            }
        });


        hinzufuegen.getChildren().addAll(addID, addVorname, addNachname, addGehalt, addAbteilung, addHoehe, addAuszahlungsdatum, addButton);
        hinzufuegen.setSpacing(3);
    }

    public void error () {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        scene.getStylesheets().add("Datenbank.css");
        stage.setTitle("Fehler!");
        stage.setWidth(500);
        stage.setHeight(150);
        final Label label = new Label("Fehler!");

        final Button okayButton = new Button("OK");
        okayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, okayButton);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void errorTooManyCharacters () {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Fehler!");
        stage.setWidth(500);
        stage.setHeight(150);
        final Label label = new Label("Fehler!");
        Text t = new Text(10, 50, "Vor- und Nachname dürfen maximal 42 Buchstaben besitzen!");
        t.setFont(new Font(16));
        t.setFill(Color.RED);

        final Button okayButton = new Button("OK");
        okayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, t, okayButton);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void errorNachnameRequieredField () {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Fehler!");
        stage.setWidth(500);
        stage.setHeight(150);
        final Label label = new Label("Fehler!");
        Text t = new Text(10, 50, "Nachname darf nicht leer sein!");
        t.setFont(new Font(16));
        t.setFill(Color.RED);

        final Button okayButton = new Button("OK");
        okayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, t, okayButton);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}

