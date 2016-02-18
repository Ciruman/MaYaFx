package com.ciruman.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MindMapNode {

    private final StringProperty text = new SimpleStringProperty();
    private final ObservableList<MindMapNode> childs = FXCollections.observableArrayList();

    public MindMapNode(String text) {
        this.text.set(text);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void addChilds(MindMapNode... childs) {
        this.childs.addAll(childs);
    }

    public ObservableList<MindMapNode> childsProperty(){
        return childs;
    }
}
