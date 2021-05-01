'''
    Description:
        This file contains all constants used throughout the classes.
        For coordinate convention of checkerboard used in program, please
        refer to README.md.
'''
import turtle

# turtle
PEN = turtle.Turtle()
SCREEN = turtle.Screen()

# turtle drawing for squares/crowns and ontimer
NUM_SQUARES = 8
SQUARE_SIZE = 50
DRAW_SQUARE = 4
RIGHT_ANGLE = 90
CROWN_HALF_BASE = CROWN_HEIGHT = 20
ARCH_ANGLE = 135
NO_OF_ARCHES = 3
CENTER_X = 0
CENTER_Y = -50
FONTSIZE = 36
PAUSE = 700

# board dimensions
BOARD_SIZE = NUM_SQUARES * SQUARE_SIZE
TOP_ROW = 3
BOTTOM_ROW = -4
MAX_BOUND = HALF_BOARD = (SQUARE_SIZE * NUM_SQUARES) // 2
MIN_BOUND = - (SQUARE_SIZE * NUM_SQUARES) // 2

# checkerboard coordinate system
ROWS = COLS = 8
CENTER_ROW = RED_FIRST_ROW = 0
BLACK_FIRST_ROW = 7
COL_SHFT = 4
ROW_SHFT = -3
CORNER = - BOARD_SIZE // 2
DIAGONAL_MOVE = 1
CAPTURE_MOVE = 2
FIRST_COL = FIRST_ROW = 0
LAST_COL = LAST_ROW = 7

# checker piece
PADDING = 0.5
RADIUS = SQUARE_SIZE // 2 - PADDING
KING_COL_SHIFT = -180
KING_ROW_SHIFT = 165

# piece_to & piece_from coordinate tuple index -> (col, row)
COL = 0
ROW = 1

# checker piece moves
MOVE = 'move'
CAPTURE = 'capture'
RED = 'dark red'
BLACK = 'black'

# computer player moves
PIECE_FROM = 0
PIECE_TO = 1
CAPTURE_PIECE = 2
