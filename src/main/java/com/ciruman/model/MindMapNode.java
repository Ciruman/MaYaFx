package com.ciruman.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MindMapNode {

    public static final int ROOT_NODE = Integer.MIN_VALUE;
    public static final int ROOT_LEVEL = 0;
    private final StringProperty text = new SimpleStringProperty();
    private final ObservableList<MindMapNode> leftChilds = FXCollections.observableArrayList();
    private final ObservableList<MindMapNode> rightChilds = FXCollections.observableArrayList();
    private MindMapNodeDirection mindMapNodeDirection;

    private final IntegerProperty level = new SimpleIntegerProperty(ROOT_LEVEL);
    private final IntegerProperty mainBranch = new SimpleIntegerProperty(ROOT_NODE);

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

    public int getLevel() {
        return level.get();
    }

    public boolean isRoot(){
        return level.get()==ROOT_LEVEL;
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }


    public int getMainBranch() {
        return mainBranch.get();
    }

    public IntegerProperty mainBranchProperty() {
        return mainBranch;
    }

    public void setMainBranch(int mainBranch) {
        this.mainBranch.set(mainBranch);
    }

    @Override
    public String toString() {
        return text.get();
    }
}
