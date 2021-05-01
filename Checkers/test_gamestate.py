from gamestate import GameState
from board import Board
from botplayer import BotPlayer
from checkerutilities import CheckerUtilities
checkerutilities = CheckerUtilities()
board = Board(checkerutilities)
botplayer = BotPlayer(board)


def test_constructor():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    botplayer = BotPlayer(board)
    g = GameState(board, botplayer)
    turn = 'black'
    assert(g.turn_color == turn)
    assert(g.red_kings == g.black_kings == 0)
    assert(g.piece_from == g.piece_to == g.valid_col_row == None)
    assert(g.board == board)
    assert(g.black_score == g.red_score == 0)
    assert(g.next_capture is None)
    assert(botplayer == botplayer)


def test_next_turn():
    g = GameState(board, botplayer, 'dark red')
    assert g.turn_color == 'dark red'
    g = GameState(board, botplayer, 'black')
    assert g.turn_color == 'black'


def test_valid_board_pos():
    g = GameState(board, botplayer)
    assert g.valid_board_pos(550, 500) is None
    assert g.valid_board_pos(-500, 500) is None
    assert g.valid_board_pos(-250, -250) is None
    assert g.valid_board_pos(320, -300) is None
    assert g.valid_board_pos(-150, 58) is None
    assert g.valid_board_pos(80, 172) is None
    assert g.valid_board_pos(-150, 50) is None
    assert g.valid_board_pos(-150, -50) is None
    assert g.valid_board_pos(100, -200) is None
    assert g.valid_board_pos(100, 150) == (6, 0)
    assert g.valid_board_pos(-180, 187) == (0, 0)
    assert g.valid_board_pos(-96, -122) == (2, 6)
    assert g.valid_board_pos(186, -151) == (7, 7)


def test_keep_score_and_piece_count():
    g = GameState(board, botplayer, 'black')
    g.black_score = 9
    g.red_score = 9
    g.board.red_left = 9
    g.board.black_left = 9
    board.piece_locations =\
        {(1, 3): 'black', (3, 5): 'black', (5, 5): 'black',
         (1, 7): 'dark red', (6, 2): 'dark red', (1, 5): 'dark red'}
    g.keep_score_and_piece_count((6, 2))
    assert(g.black_score == 10)
    assert(g.red_score == 9)
    assert(g.board.black_left == 9)
    assert(g.board.red_left == 8)
    g.keep_score_and_piece_count((1, 3))
    assert(g.black_score == 10)
    assert(g.red_score == 10)
    assert(g.board.black_left == 8)
    assert(g.board.red_left == 8)


def test_is_blocked_from_moving():
    g = GameState(board, botplayer)
    pieces_moves = {((2, 2), (1, 3)): None, ((2, 2), (4, 4)): (3, 3)}
    assert(g.is_blocked_from_moving(pieces_moves) is False)
    pieces_moves = {((1, 1), (0, 0)): None}
    assert(g.is_blocked_from_moving(pieces_moves) is False)
    pieces_moves = {}
    assert(g.is_blocked_from_moving(pieces_moves))


def test_reset_move():
    # reset typical piece_to/from cases
    g = GameState(board, botplayer)
    g.piece_from == (1, 5)
    g.piece_to == (2, 4)
    g.reset_move()
    assert(g.piece_from is None)
    assert(g.piece_to is None)
    # reset empty tuple and tuple with unexpected values
    g.piece_from == ()
    g.piece_to == (-5, 5.5)
    g.reset_move()
    assert(g.piece_from is None)
    assert(g.piece_to is None)
    # reset with unexpected string
    g.piece_from == 'dark red'
    g.piece_to == ()
    g.reset_move()
    assert(g.piece_from is None)
    assert(g.piece_to is None)


def test_is_first_click():
    # red checker, piece_from exists
    g = GameState(board, botplayer, 'dark red')
    g.valid_col_row == (2, 2)
    g.board.piece_locations == {(2, 4): 'black', (2, 2): 'dark red',
                                (0, 6): 'dark red', (4, 6): 'black'}
    g.piece_from == (3, 3)
    assert(g.is_first_click() is False)
    # red checker, piece_from DNE
    g = GameState(board, botplayer, 'dark red')
    g.valid_col_row == (7, 3)
    g.board.piece_locations == {(7, 3): 'dark red', (4, 6): 'black',
                                (0, 6): 'dark red'}
    g.piece_from = None
    assert(g.is_first_click() is False)
    # red checker, empty piece_locations
    g.valid_col_row == (2, 2)
    g.board.piece_locations == {}
    g.piece_from = None
    assert(g.is_first_click() is False)
    # black checker, piece_from DNE
    g = GameState(board, botplayer)
    g.valid_col_row == (2, 2)
    g.board.piece_locations == {}
    g.piece_from = None
    assert(g.is_first_click() is False)
    # black checker, not in piece_locations, piece_from DNE
    g.valid_col_row == (7, 3)
    g.board.piece_locations == {(1, 5): 'black'}
    g.piece_from = None
    assert(g.is_first_click() is False)


def test_is_second_click():
    # black checkers, valid
    g = GameState(board, botplayer)
    g.piece_from = (1, 5)
    g.valid_col_row = (2, 4)
    g.board.piece_locations = {(1, 3): 'black', (5, 3): 'black',
                               (1, 5): 'black', (5, 1): 'dark red',
                               (6, 4): 'dark red'}
    assert(g.is_second_click())
    # black checkers, invalid
    g = GameState(board, botplayer)
    g.piece_from = (1, 5)
    g.valid_col_row = (4, 4)
    g.board.piece_locations = {(4, 4): 'black', (5, 3): 'black',
                               (1, 5): 'black', (5, 1): 'dark red',
                               (6, 4): 'dark red'}
    assert(g.is_second_click() is False)
    # red checkers, valid
    g = GameState(board, botplayer, 'dark red')
    g.piece_from = (4, 2)
    g.board.piece_locations = {(0, 2): 'dark red', (4, 2): 'dark red',
                               (3, 3): 'black', (5, 5): 'black'}
    g.valid_col_row = (2, 4)
    assert(g.is_second_click())
    # red checkers, invalid
    g.piece_from = None
    g.valid_col_row = (2, 1)
    g.board.piece_locations = {(1, 3): 'black', (5, 3): 'black',
                               (5, 1): 'dark red', (6, 4): 'dark red'}
    assert(g.is_second_click() is False)


def test_capture_exists():
    g = GameState(board, botplayer)
    g.piece_from = (3, 5)
    board.piece_locations =\
        {(1, 3): 'black', (3, 5): 'black', (5, 5): 'black',
         (3, 4): 'dark red', (6, 2): 'dark red', (1, 5): 'dark red'}
    assert g.capture_exists() == []
    board.piece_locations =\
        {(1, 5): 'black', (2, 4): 'dark red'}
    assert g.capture_exists() == [(3, 3)]
