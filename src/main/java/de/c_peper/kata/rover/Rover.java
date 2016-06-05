package de.c_peper.kata.rover;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * X = 0..9 W <-> E ( -> RoverArea)
 * Y = 0..9 S <-> N ( -> RoverArea)
 * direction = N,E,S,W ( -> Direction)
 * R: N->E->S->W->N
 * L: N->W->S->E->N
 */
public class Rover {

    private RoverPosition position;

    private Direction direction;

    private InputActions inputAction;

    public Rover() {
        this(RoverArea.getDefaultField());
    }

    public Rover(RoverArea field) {
        position = new RoverPosition(field, 0, 0);
        direction = Direction.init();
        inputAction = new InputActions();
    }

    public String getPositionString() {
        return position.toString() + "," + direction.toString();
    }

    public Boolean move(int input) {
        return inputAction.act(input);
    }

    public void move(String inputString) {
        inputString.chars()
                .anyMatch((c) -> isNotSuccessful(move(c)));
    }

    public void addObstacle(Integer positionX, Integer positionY) {
        position.getField().addObstacle(new Position(positionX, positionY));
    }

    private Boolean isNotSuccessful(Boolean successOfMovement) {
        return !successOfMovement;
    }

    private class InputActions {

        InputActions() {
            add('R', () -> {
                direction = direction.turnRight();
                return Boolean.TRUE;
            });
            add('L', () -> {
                direction = direction.turnLeft();
                return Boolean.TRUE;
            });
            add('F', () -> direction.forwards(position));
            add('B', () -> direction.backwards(position));
        }

        private Map<Integer, Supplier<Boolean>> map = new HashMap<>();

        private void add(int input, Supplier<Boolean> supplier) {
            map.put(input, supplier);
        }

        private Boolean act(int input) {
            return map.get(input).get();
        }
    }
}