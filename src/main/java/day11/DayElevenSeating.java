package day11;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class DayElevenSeating {

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day11/input.txt"), StandardCharsets.UTF_8);
        SeatState[][] seats = initializeSeats(lines);
        int amountOfAdjacentOccupiedSeats = getAmountOfAdjacentOccupiedSeatsAfterApplyingRulesUntilNoChange(seats);
        System.out.println("Part 1: " + amountOfAdjacentOccupiedSeats);
        int amountOfVisibleOccupiedSeats = getAmountOfVisibleOccupiedSeatsAfterApplyingRulesUntilNoChange(seats);
        System.out.println("Part 2: " + amountOfVisibleOccupiedSeats);
    }

    private static int getAmountOfVisibleOccupiedSeatsAfterApplyingRulesUntilNoChange(SeatState[][] seats) {
        do {
            seats = applyRulesVisible(seats);
        } while (!Arrays.deepEquals(seats, applyRulesVisible(seats)));
        return getAmountOfOccupiedSeats(seats);
    }

    private static int getAmountOfOccupiedSeats(SeatState[][] seats) {
        int occupiedSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == SeatState.OCCUPIED) {
                    occupiedSeats++;
                }
            }
        }
        return occupiedSeats;
    }

    private static SeatState[][] applyRulesVisible(SeatState[][] seats) {
        SeatState[][] newSeats = copyArray(seats);
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                int amountVisibleOccupiedSeats = getAmountVisibleOccupiedSeats(seats, row, col);
                if (seats[row][col] == SeatState.OCCUPIED && amountVisibleOccupiedSeats >= 5) {
                    newSeats[row][col] = SeatState.EMPTY;
                }
                if (seats[row][col] == SeatState.EMPTY && amountVisibleOccupiedSeats == 0) {
                    newSeats[row][col] = SeatState.OCCUPIED;
                }
            }
        }
        return newSeats;
    }

    private static int getAmountVisibleOccupiedSeats(SeatState[][] seats, int row, int col) {
        int amountVisibleOccupiedSeats = 0;
        //only the first seen seat is relevant
        //diagonally top left
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            SeatState state = seats[row - i][col - i];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        //straight up
        for (int i = 1; row - i >= 0; i++) {
            SeatState state = seats[row - i][col];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        //diagonally top right
        for (int i = 1; row - i >= 0 && col + i < seats[row].length; i++) {
            SeatState state = seats[row - i][col + i];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        //right side
        for (int i = 1; col + i < seats[row].length; i++) {
            SeatState state = seats[row][col + i];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        //diagonally bottom right
        for (int i = 1; row + i < seats.length && col + i < seats[row + i].length; i++) {
            SeatState state = seats[row + i][col + i];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        //straight down
        for (int i = 1; row + i < seats.length; i++) {
            SeatState state = seats[row + i][col];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        //diagonally bottom left
        for (int i = 1; row + i < seats.length && col - i >= 0; i++) {
            SeatState state = seats[row + i][col - i];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        // left side
        for (int i = 1; col - i >= 0; i++) {
            SeatState state = seats[row][col - i];
            if (state == SeatState.OCCUPIED) {
                amountVisibleOccupiedSeats++;
                break;
            } else if (state == SeatState.EMPTY) {
                break;
            }
        }
        return amountVisibleOccupiedSeats;
    }

    private static int getAmountOfAdjacentOccupiedSeatsAfterApplyingRulesUntilNoChange(SeatState[][] seats) {
        do {
            seats = applyRulesAdjacent(seats);
        } while (!Arrays.deepEquals(seats, applyRulesAdjacent(seats)));
        return getAmountOfOccupiedSeats(seats);
    }

    private static SeatState[][] applyRulesAdjacent(SeatState[][] seats) {
        SeatState[][] newSeats = copyArray(seats);
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                int amountAdjacentOccupiedSeats = getAmountAdjacentOccupiedSeats(seats, row, col);
                if (seats[row][col] == SeatState.OCCUPIED && amountAdjacentOccupiedSeats >= 4) {
                    newSeats[row][col] = SeatState.EMPTY;
                }
                if (seats[row][col] == SeatState.EMPTY && amountAdjacentOccupiedSeats == 0) {
                    newSeats[row][col] = SeatState.OCCUPIED;
                }
            }
        }
        return newSeats;
    }

    // super ugly, but it works...
    private static int getAmountAdjacentOccupiedSeats(SeatState[][] seats, int row, int col) {
        int amountAdjacentOccupiedSeats = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == j && i == 0) {
                    continue;
                }
                if (isOccupied(seats, row + i, col + j)) {
                    amountAdjacentOccupiedSeats++;
                }
            }
        }
        return amountAdjacentOccupiedSeats;
    }

    private static boolean isOccupied(SeatState[][] seats, int row, int col) {
        try {
            return seats[row][col] == SeatState.OCCUPIED;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private static SeatState[][] copyArray(SeatState[][] seats) {
        SeatState[][] copy = new SeatState[seats.length][seats[0].length];
        for (int i = 0; i < seats.length; i++) {
            System.arraycopy(seats[i], 0, copy[i], 0, seats[i].length);
        }
        return copy;
    }

    private static SeatState[][] initializeSeats(List<String> lines) {
        SeatState[][] seats = new SeatState[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case 'L' -> seats[i][j] = SeatState.EMPTY;
                    case '#' -> seats[i][j] = SeatState.OCCUPIED;
                    default -> seats[i][j] = SeatState.NON_EXISTING;
                }
            }
        }
        return seats;
    }
}
