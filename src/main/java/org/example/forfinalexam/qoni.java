package org.example.forfinalexam;

import java.sql.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class qoni extends Application{

    public static Connection kavshiri() throws SQLException{

        String url = "jdbc:sqlserver://FELINISY;databaseName=forFinal;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String pass = "!234";


        return DriverManager.getConnection(url, user, pass);
    }

    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);

        Button btcreate  = new Button("CREATE");
        Button btdelete = new Button("DELETE");
        Button btupdate = new Button("UPDATE");

        btcreate.setOnAction(e -> {
            vbox.getChildren().clear();
            vbox.getChildren().addAll(btcreate,btdelete,btupdate);
            vbox.getChildren().add(addTask());

        });

        btupdate.setOnAction(e -> {
            vbox.getChildren().clear();
            vbox.getChildren().addAll(btcreate,btdelete,btupdate);
            vbox.getChildren().add(updateTask());
        });

        btdelete.setOnAction(e -> {
            vbox.getChildren().clear();
            vbox.getChildren().addAll(btcreate,btdelete,btupdate);
           vbox.getChildren().add(deleteTask());
        });

        vbox.getChildren().addAll(btcreate,btdelete,btupdate);



        Scene scene = new Scene(vbox,400,500);

        stage.setTitle("Final Exam");
        stage.setScene(scene);
        stage.show();


    }

    private HBox addTask(){
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);



        TextField tf1 = new TextField();
        TextField tf2 = new TextField();


        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {

            String frs = tf1.getText().toString();
            String scn = tf2.getText().toString();
            try(Connection con = kavshiri();
                PreparedStatement pstmt = con.prepareStatement("insert into tasks(title,descriptions) values(?,?)")){
                pstmt.setString(1,frs);
                pstmt.setString(2,scn);
                pstmt.execute();
                System.out.println("Added");

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        });
        hbox.getChildren().addAll(tf1,tf2,addButton);
        return hbox;
    }

    private HBox updateTask() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        TextArea tx = new TextArea();
        tx.setPrefSize(600, 300);
        tx.setEditable(false);

        try (Connection con = kavshiri();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from tasks");

            while (rs.next()) {
                tx.appendText(rs.getString("title") + "\n");
                tx.appendText(rs.getString("descriptions") + "\n");

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("descriptions");

                tx.appendText("ID: " + id + "\n");
                tx.appendText("Title: " + title + "\n");
                tx.appendText("Description: " + desc + "\n");
                tx.appendText("------------------------------\n");
            }
            vbox.getChildren().add(tx);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }



        TextField tf1 = new TextField();
        TextField tf3 = new TextField();

        Button updateButton = new Button("Update");

        vbox.getChildren().addAll(tf1,tf3,updateButton);
        updateButton.setOnAction(e -> {

            int first = Integer.parseInt(tf1.getText().toString());
            int id = Integer.parseInt(tf3.getText().toString());


        try (Connection con = kavshiri();
             PreparedStatement pstmt = con.prepareStatement("update tasks set completed = ? where id=?")) {
            pstmt.setInt(1, first);
            pstmt.setInt(2, id);
            pstmt.execute();
            System.out.println("updated");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        });
        hbox.getChildren().addAll(vbox);
        return hbox;

    }

    private HBox deleteTask() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        TextArea tx = new TextArea();
        tx.setPrefSize(600, 300);
        tx.setEditable(false);

        try (Connection con = kavshiri();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from tasks");

            while (rs.next()) {
                tx.appendText(rs.getString("title") + "\n");
                tx.appendText(rs.getString("descriptions") + "\n");

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("descriptions");

                tx.appendText("ID: " + id + "\n");
                tx.appendText("Title: " + title + "\n");
                tx.appendText("Description: " + desc + "\n");
                tx.appendText("------------------------------\n");
            }



        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        TextField tf1 = new TextField();
        Button deleteButton = new Button("Delete");
        vbox.getChildren().addAll(tx,tf1,deleteButton);

        deleteButton.setOnAction(e -> {
           int id = Integer.parseInt(tf1.getText().toString());

            try (Connection con = kavshiri();
                 PreparedStatement pstmt = con.prepareStatement("delete from tasks where id=?")) {
                pstmt.setInt(1, id);
                pstmt.execute();
                System.out.println("deleted");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        });
        hbox.getChildren().addAll(vbox);

        return hbox;
    }
}
	
