package org.example.sudoku.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Getter
public class Board {
    private final List<List<Space>> spaces;

    public StatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return StatusEnum.NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> isNull(s.getActual()
                )) ? StatusEnum.INCOMPLETE : StatusEnum.COMPLETE;
    }

    public boolean hasErrors(){
        if (getStatus() == StatusEnum.NON_STARTED) return false;

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) &&
                        !s.getActual().equals(s.getExpected())
                );
    }

    public boolean changeValue(final int col, final int row, final int value){
        Space space = spaces.get(col).get(row);

        if (space.isFixed()) return false;

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row){
        Space space = spaces.get(col).get(row);
        if (space.isFixed()) return false;

        space.clearSpace();
        return true;
    }

    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(StatusEnum.COMPLETE);
    }

}
