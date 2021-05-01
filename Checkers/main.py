'''
    Erina Kitamura
    CS 5001
    Fall 2020
    Final Project

    Description: User (black checkers) can play checkers with a computer (red
                 checkers). Starting from the user, the game will be played
                 until there is no more checker piece left for one of the
                 player, or all checker piece for one opponent is blocked from
                 moving.
'''
import turtle
from board import Board
from gamestate import GameState
from checkerutilities import CheckerUtilities
from botplayer import BotPlayer
from constants import SCREEN


checkerutilities = CheckerUtilities()
board = Board(checkerutilities)
botplayer = BotPlayer(board)
gamestate = GameState(board, botplayer)


def main():
    # Draw board
    board.draw_board()

    # Draw the starting set of checkers
    board.populate_starting_checkers()

    # 2 clicks to select checker piece to move and its new location
    print('''{}'s turn'''.format(gamestate.turn_color))
    SCREEN.onclick(gamestate.move_checker_piece)
    turtle.done()


if __name__ == "__main__":
    main()
