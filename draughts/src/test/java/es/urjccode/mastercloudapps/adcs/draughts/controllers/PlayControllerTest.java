package es.urjccode.mastercloudapps.adcs.draughts.controllers;

import es.urjccode.mastercloudapps.adcs.draughts.models.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayControllerTest {

    private PlayController playController;

    @Test
    public void testGivenPlayControllerWhenMoveThenOk() {
        Game game = new GameBuilder().build();
        playController = new PlayController(game, new State());
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(4, 1);
        playController.move(origin, target);
        assertEquals(playController.getColor(target), Color.WHITE);
        assertFalse(game.isBlocked());
    }

    @Test
    public void testGivenPlayControllerWhenMoveWithoutPiecesThenIsBlocked() {
        Game game = new GameBuilder().rows(
            "        ",
            "        ",
            "        ",
            "        ",
            " n      ",
            "b       ",
            "        ",
            "        ").build();
        playController = new PlayController(game, new State());
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(3, 2);
        playController.move(origin, target);
        assertEquals(playController.getColor(target), Color.WHITE);
        assertTrue(game.isBlocked());
    }

    @Test
    public void testGivenPlayControllerWhenMoveWithoutMovementsThenIsBlocked() {
        Game game = new GameBuilder().rows(
            "        ",
            "  b b   ",
            "   n    ",
            "  b b   ",
            "     b  ",
            "b       ",
            "        ",
            "        ").build();
        playController = new PlayController(game, new State());
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(4, 1);
        playController.move(origin, target);
        assertEquals(playController.getColor(target), Color.WHITE);
        assertTrue(game.isBlocked());
    }

    @Test
    public void testGivenPlayControllerWhenCancelThenOk() {
        Game game = new GameBuilder().build();
        playController = new PlayController(game, new State());
        playController.cancel();
        assertEquals(Color.BLACK, playController.getColor());
        assertFalse(game.isBlocked());
    }

    @Test
    public void testGivenGameWhenMoveBlackAndNoEatThenLostPiece (){
        Game game = new GameBuilder()
            .color(Color.BLACK)
            .rows(
                " n n n n",
                "n n n n ",
                " n   n n",
                "    n   ",
                " b b    ",
                "    b b ",
                " b b b b",
                "b b b b "
            )
            .build();
        Game expected = new GameBuilder()
            .color(Color.WHITE)
            .rows(
                " n n n n",
                "n n n n ",
                " n   n  ",
                "      n ",
                " b b    ",
                "    b b ",
                " b b b b",
                "b b b b "
            )
            .build();
        assertNull(game.move(
            new Coordinate(2,7),
            new Coordinate(3, 6)
        ));
        assertEquals(game, expected);
    }

    @Test
    public void testGivenGameWhenMoveBlackAndNoEatThenLostRandomPiece () {
        Game game = new GameBuilder().rows(
            "        ",
            "    n   ",
            "     b  ",
            "  n     ",
            "   b    ",
            "  n n   ",
            "        ",
            "        ").build();
        assertNull(game.move(
            new Coordinate(4,3),
            new Coordinate(3, 4)
        ));
        Piece b1 = game.getPiece(new Coordinate(3, 4));
        Piece b2 = game.getPiece(new Coordinate(2, 5));
        assertTrue(b1 == null || b2 == null);
    }

}
