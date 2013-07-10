package com.fix;

import org.richfaces.model.TreeNodeImpl;

/**
 * This class represents tree node
 */
public class OptionTreeNode extends TreeNodeImpl {

    private String name;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getType() {
        return type;
    }

    public void setType(String aType) {
        type = aType;
    }

}
