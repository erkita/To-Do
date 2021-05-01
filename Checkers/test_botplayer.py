from board import Board
from checkerutilities import CheckerUtilities
from botplayer import BotPlayer
checkerutilities = CheckerUtilities()
board = Board(checkerutilities)
bot = BotPlayer(board)


def test_constructor():
    assert(bot.board == board)


def test_all_possible_bot_moves():
    all_red_pieces_moves = {((0, 2), (1, 3)): None, ((2, 2), (3, 3)): None,
                            ((2, 2), (3, 5)): None, ((4, 2), (5, 3)): None}
    assert bot.all_possible_bot_moves(all_red_pieces_moves) ==\
        ([((0, 2), (1, 3)), ((2, 2), (3, 3)), ((2, 2), (3, 5)), ((4, 2),
         (5, 3))], [])
    all_red_pieces_moves = {((2, 2), (3, 3)): None, ((4, 2), (3, 3)): None,
                            ((6, 2), (5, 3)): None, ((1, 3), (3, 5)): (2, 4)}
    assert bot.all_possible_bot_moves(all_red_pieces_moves) ==\
        ([((2, 2), (3, 3)), ((4, 2), (3, 3)), ((6, 2), (5, 3))],
         [((1, 3), (3, 5), (2, 4))])
    all_red_pieces_moves = {((1, 7), (3, 5)): (2, 6), ((6, 2), (4, 4)): (5, 4)}
    assert bot.all_possible_bot_moves(all_red_pieces_moves) ==\
        ([], [((1, 7), (3, 5), (2, 6)), ((6, 2), (4, 4), (5, 4))])
