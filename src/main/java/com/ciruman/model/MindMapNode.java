package com.ciruman.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MindMapNode {

    private final StringProperty text = new SimpleStringProperty();
    private final ObservableList<MindMapNode> leftChilds = FXCollections.observableArrayList();
    private final ObservableList<MindMapNode> rightChilds = FXCollections.observableArrayList();
    private MindMapNodeDirection mindMapNodeDirection;

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
        //TODO it should work also when the direction is not already set
        for (MindMapNode child:childs){
            if (getDirection()==MindMapNodeDirection.RIGHT){
                child.setMindMapNodeDirection(MindMapNodeDirection.RIGHT);
                rightChilds.add(child);
            } else {
                child.setMindMapNodeDirection(MindMapNodeDirection.LEFT);
                leftChilds.add(child);
            }
        }
    }

    private MindMapNodeDirection getDirection() {
        MindMapNodeDirection result;
        switch (getMindMapNodeDirection()){
            case RIGHT:
                result = MindMapNodeDirection.RIGHT;
                break;
            case LEFT:
                result = MindMapNodeDirection.LEFT;
                break;
            case BOTH:
                result = calculateDirection();
                break;
            default:
                result = calculateDirection();
        }
        return result;
    }

    private MindMapNodeDirection calculateDirection() {
        return leftChilds.size()>rightChilds.size()?MindMapNodeDirection.RIGHT:MindMapNodeDirection.LEFT;
    }

    public ObservableList<MindMapNode> leftChildsProperty() {
        return leftChilds;
    }

    public ObservableList<MindMapNode> rightChildsProperty() {
        return rightChilds;
    }

    public MindMapNodeDirection getMindMapNodeDirection() {
        return mindMapNodeDirection;
    }

    public void setMindMapNodeDirection(MindMapNodeDirection mindMapNodeDirection) {
        this.mindMapNodeDirection = mindMapNodeDirection;
    }

    @Override
    public String toString() {
        return text.get();
    }
}
