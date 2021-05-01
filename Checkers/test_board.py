from board import Board
from checkerutilities import CheckerUtilities


def test_constructor():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert(board.red_left == board.black_left == 12)
    assert(board.red_kings == board.black_kings == 0)
    assert(board.king_pieces == board.piece_locations == {})
    assert(board.checkerutilities == checkerutilities)


def test_locate_starting_pieces():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.locate_starting_pieces() ==\
        {(1, 7): 'black', (3, 7): 'black', (5, 7): 'black',
         (7, 7): 'black', (0, 6): 'black', (2, 6): 'black',
         (4, 6): 'black', (6, 6): 'black', (1, 5): 'black',
         (3, 5): 'black', (5, 5): 'black', (7, 5): 'black',
         (0, 2): 'dark red', (2, 2): 'dark red', (4, 2): 'dark red',
         (6, 2): 'dark red', (1, 1): 'dark red', (3, 1): 'dark red',
         (5, 1): 'dark red', (7, 1): 'dark red', (0, 0): 'dark red',
         (2, 0): 'dark red', (4, 0): 'dark red', (6, 0): 'dark red'}


def test_upper_lft_origin():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.convert_to_upper_lft_origin((0, 0)) == (-4, 3)
    assert board.convert_to_upper_lft_origin((5, 1)) == (1, 2)
    assert board.convert_to_upper_lft_origin((-3, 2)) == (-7, 1)
    assert board.convert_to_upper_lft_origin((-4, -3)) == (-8, 6)
    assert board.convert_to_upper_lft_origin((3, -2)) == (-1, 5)


def test_update_checker_loc():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    board.locate_starting_pieces()
    # black piece move up diagonally
    board.update_checker_loc((3, 5), (4, 4), 'black')
    assert board.piece_locations ==\
        {(1, 7): 'black', (3, 7): 'black', (5, 7): 'black',
         (7, 7): 'black', (0, 6): 'black', (2, 6): 'black',
         (4, 6): 'black', (6, 6): 'black', (1, 5): 'black',
         (4, 4): 'black', (5, 5): 'black', (7, 5): 'black',
         (0, 2): 'dark red', (2, 2): 'dark red', (4, 2): 'dark red',
         (6, 2): 'dark red', (1, 1): 'dark red', (3, 1): 'dark red',
         (5, 1): 'dark red', (7, 1): 'dark red', (0, 0): 'dark red',
         (2, 0): 'dark red', (4, 0): 'dark red', (6, 0): 'dark red'}
    # red piece move down diagonally
    board.update_checker_loc((0, 2), (1, 3), 'dark red')
    assert board.piece_locations ==\
        {(1, 7): 'black', (3, 7): 'black', (5, 7): 'black',
         (7, 7): 'black', (0, 6): 'black', (2, 6): 'black',
         (4, 6): 'black', (6, 6): 'black', (1, 5): 'black',
         (4, 4): 'black', (5, 5): 'black', (7, 5): 'black',
         (1, 3): 'dark red', (2, 2): 'dark red', (4, 2): 'dark red',
         (6, 2): 'dark red', (1, 1): 'dark red', (3, 1): 'dark red',
         (5, 1): 'dark red', (7, 1): 'dark red', (0, 0): 'dark red',
         (2, 0): 'dark red', (4, 0): 'dark red', (6, 0): 'dark red'}


def test_locate_red_or_black_checkers():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.locate_red_or_black_checkers('black') == []
    board.piece_locations =\
        {(1, 7): 'black', (3, 7): 'black', (5, 7): 'black',
         (7, 7): 'black', (0, 6): 'black', (2, 6): 'black',
         (4, 6): 'black', (6, 6): 'black', (1, 5): 'black',
         (3, 5): 'black', (5, 5): 'black', (7, 5): 'black',
         (0, 2): 'dark red', (2, 2): 'dark red', (4, 2): 'dark red',
         (6, 2): 'dark red', (1, 1): 'dark red', (3, 1): 'dark red',
         (5, 1): 'dark red', (7, 1): 'dark red', (0, 0): 'dark red',
         (2, 0): 'dark red', (4, 0): 'dark red', (6, 0): 'dark red'}
    assert board.locate_red_or_black_checkers('black') ==\
        [(1, 7), (3, 7), (5, 7), (7, 7), (0, 6), (2, 6), (4, 6),
         (6, 6), (1, 5), (3, 5), (5, 5), (7, 5)]
    assert board.locate_red_or_black_checkers('dark red') ==\
        [(0, 2), (2, 2), (4, 2), (6, 2), (1, 1), (3, 1), (5, 1),
         (7, 1), (0, 0), (2, 0), (4, 0), (6, 0)]
    assert board.locate_red_or_black_checkers('yellow') == []


def test_move_to_coord_shift():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.move_to_coord_shift((1, 3), 'move') == (1, 2, 0)
    assert board.move_to_coord_shift((5, 5), 'capture') == (2, 7, 3)
    assert board.move_to_coord_shift((4, 2), 'invalid') == (0, 4, 4)


def test_move_king_to_coord():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.move_king_to_coord((5, 1), 'move') == ((6, 0), (4, 0),
                                                        (6, 2), (4, 2))
    assert board.move_king_to_coord((3, 3), 'capture') == ((5, 1), (1, 1),
                                                           (5, 5), (1, 5))
    assert board.move_king_to_coord((7, 7), 'invalid') == ((7, 7), (7, 7),
                                                           (7, 7), (7, 7))


def move_to_coord():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.move_to_coord((3, 5), 'black', 'move') == (4, 4), (2, 4)
    assert board.move_to_coord((6, 2), 'black', 'capture') == (7, 3), (5, 3)
    assert board.move_to_coord((5, 1), 'dark red', 'move') == (6, 2), (4, 2)
    assert board.move_to_coord((1, 1), 'dark red', 'capture') == (2, 2), (0, 2)


def test_move_forward():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    assert board.move_forward((5, 3), (4, 4), 'dark red')
    assert(board.move_forward((5, 3), (4, 2), 'dark red') is False)
    assert board.move_forward((2, 6), (4, 4), 'black')
    assert(board.move_forward((2, 6), (1, 7), 'black') is False)
    board.king_pieces = {(4, 6): 'black', (2, 4): 'dark red'}
    assert board.move_forward((2, 4), (4, 6), 'dark red')
    assert(board.move_forward((2, 4), (2, 8), 'dark red') is False)
    assert(board.move_forward((4, 6), (4, 6), 'black') is False)


def test_valid_diag_move():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    board.locate_starting_pieces()
    # ordinary piece, single move
    assert(board.valid_diag_move((2, 2), (2, 4), 'dark red') == (False, None))
    assert board.valid_diag_move((6, 2), (7, 3), 'black')
    # ordinary piece, capture move
    assert(board.valid_diag_move((3, 5), (3, 3), 'black') == (False, None))
    assert board.valid_diag_move((2, 2), (4, 4), 'dark red')
    # king piece, single move
    board.king_pieces = {(2, 0): 'black', (1, 1): 'black', (7, 7): 'dark red'}
    assert board.valid_diag_move((1, 1), (0, 0), 'black')
    assert(board.valid_diag_move((2, 1), (0, 0), 'dark red') == (False, None))
    # king piece, capture move
    board.king_pieces = {(4, 6): 'black', (0, 6): 'dark red'}
    assert board.valid_diag_move((4, 6), (6, 2), 'black')
    assert(board.valid_diag_move((0, 6), (3, 3), 'dark red') == (False, None))
    assert(board.valid_diag_move((0, 6), (1, 7), 'green') == (False, None))


def is_empty_square():
    checkerutilities = CheckerUtilities()
    board = Board(checkerutilities)
    board.piece_locations =\
        {(1, 7): 'black', (3, 7): 'black', (5, 7): 'black',
         (7, 7): 'black', (2, 6): 'black', (6, 6): 'black',
         (2, 4): 'black', (4, 4): 'black', (6, 0): 'black',
         (4, 2): 'dark red', (6, 2): 'dark red', (1, 1): 'dark red',
         (3, 1): 'dark red', (5, 1): 'dark red', (7, 1): 'dark red',
         (1, 3): 'dark red', (0, 6): 'dark red', (0, 4): 'dark red'}
    assert(board.is_empty_square((2, 4)) is False)
    assert(board.is_empty_square(()) is False)
    assert board.is_empty_square((7, 7))
