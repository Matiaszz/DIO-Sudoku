package org.example.sudoku.model;

import lombok.Getter;

@Getter
public enum StatusEnum {
    NON_STARTED("Non started"),
    INCOMPLETE("Incomplete"),
    COMPLETE("Complete");

    private final String label;
    StatusEnum(final String label){
        this.label = label;
    }
}
