import java.util.HashMap;
import java.util.Map;

public class MazeGame {
    private static abstract class Wall {
        public abstract void enter();
    }

    static class Room {
        private final Map<Direction, Wall> sides = new HashMap<>();
        private final int roomNo;

        public Room(int roomNo) {
            this.roomNo = roomNo;
        }

        public Wall getSide(Direction direction) {
            return sides.get(direction);
        }

        public void setSide(Direction direction, Wall wall) {
            sides.put(direction, wall);
        }

        public int getRoomNo() {
            return roomNo;
        }
    }

    private static class DoorWall extends Wall {
        private final Room r1;
        private final Room r2;
        private boolean isOpen;

        public DoorWall(Room r1, Room r2) {
            this.r1 = r1;
            this.r2 = r2;
            this.isOpen = false;
        }

        @Override
        public void enter() {
            if (isOpen) {
                System.out.println("Entering through an open door");
            } else {
                System.out.println("Door is closed, cannot enter");
            }
        }
    }

    static class Maze {
        private final Map<Integer, Room> rooms = new HashMap<>();

        public void addRoom(Room r) {
            rooms.put(r.getRoomNo(), r);
        }

        public Room roomNo(int r) {
            return rooms.get(r);
        }
    }

    private static class MazeBuilder {
        private Maze maze;

        public MazeBuilder() {
            this.maze = new Maze();
        }

        public void buildRoom(int roomNo) {
            Room room = new Room(roomNo);
            // Add walls to the room
            room.setSide(Direction.NORTH, new Wall() {
                @Override
                public void enter() {
                    System.out.println("Entering a wall to the north");
                }
            });
            room.setSide(Direction.EAST, new Wall() {
                @Override
                public void enter() {
                    System.out.println("Entering a wall to the east");
                }
            });
            room.setSide(Direction.SOUTH, new Wall() {
                @Override
                public void enter() {
                    System.out.println("Entering a wall to the south");
                }
            });
            room.setSide(Direction.WEST, new Wall() {
                @Override
                public void enter() {
                    System.out.println("Entering a wall to the west");
                }
            });
            maze.addRoom(room);
        }

        public void buildDoorWall(int room1, int room2) {
            Room r1 = maze.roomNo(room1);
            Room r2 = maze.roomNo(room2);
            DoorWall d = new DoorWall(r1, r2);
            r1.setSide(Direction.NORTH, d);
            r2.setSide(Direction.SOUTH, d);
        }

        public Maze getMaze() {
            return maze;
        }
    }

    public static void main(String[] args) {
        MazeBuilder mazeBuilder = new MazeBuilder();
        mazeBuilder.buildRoom(1);
        mazeBuilder.buildRoom(2);
        mazeBuilder.buildDoorWall(1, 2);
        Maze maze = mazeBuilder.getMaze();

        // Print out the details of the maze
        for (int i = 1; i <= 2; i++) {
            Room room = maze.roomNo(i);
            System.out.println("Room " + room.getRoomNo() + " walls:");
            for (Direction direction : Direction.values()) {
                Wall wall = room.getSide(direction);
                if (wall instanceof DoorWall) {
                    System.out.println("Direction " + direction + ": Door Wall");
                } else {
                    System.out.println("Direction " + direction + ": Regular Wall");
                }
            }
            System.out.println();
        }
    }

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}

