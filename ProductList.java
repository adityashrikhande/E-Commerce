package com.example.ecommerce;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductList {

    public static TableView<Product> productTable;

    public static Pane getAllProducts(){
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Product> data = FXCollections.observableArrayList();
        data.addAll(new Product(123, "Laptop", (double)(234.5)),
                new Product(1234, "Laptop", (double)(234.5))
        );


        ObservableList<Product> productList = Product.getAllProducts();
        productTable = new TableView<>();
        productTable.setItems(productList);
        productTable.getColumns().addAll(id, name, price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(productTable);

        return tablePane;
    }

    public Product getSelectedProducts(){
        return productTable.getSelectionModel().getSelectedItem();
    }
}
