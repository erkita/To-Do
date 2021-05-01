from constants import RED, PIECE_TO, PIECE_FROM, CAPTURE_PIECE
from copy import deepcopy
import random


class BotPlayer:
    '''
        Class -- BotPlayer
            Represents the computer (red checkers) player's selection for
            checker piece movements.
        Attributes:
            board -- object of Board type, used to call methods from Board
        Methods:
            bot_move - finds and returns valid moves and captures (if
                       applicable) for the red checker piece player,
                       uses random
            all_possible_bot_moves - finds all possible moves for bot to make
                                     given all checker locations on board
            selected_move_coordinates - randomly selects a capture move if
                                        capture is possible, if not randomly
                                        selects an ordinary move, uses random
    '''
    def __init__(self, board):
        '''
            Constructor -- creates a new instance of Bot player
            Parameters:
                self -- the current Board object
        '''
        self.board = board

    def bot_move(self):
        '''
            Method -- bot_move
                Selects the moves coordinate (piece_from, piece_to,
                capture_piece) for the computer player. Uses random.
            Parameters:
                self -- the current BotPlayer object
            Returns:
                Returns row, column coordindate of piece_from, piece_to,
                and capture_piece. Returns capture_piece as None if no
                capture is available.
        '''
        # get current piece location from board
        piece_locations = deepcopy(self.board.piece_locations)
        # dict of all possible moves {(piece_from, piece_to): capture}
        all_red_pieces_moves = self.board.all_possible_moves(piece_locations,
                                                             RED)
        # get a dictionary of all possible moves
        ordinary_moves, capture_moves =\
            self.all_possible_bot_moves(all_red_pieces_moves)
        # get coordinates of the checker move
        return self.selected_move_coordinates(ordinary_moves, capture_moves)

    def all_possible_bot_moves(self, all_red_pieces_moves):
        '''
            Method -- all_possible_bot_moves
                Determines all possible moves for all current pieces available
                on checkerboard.
            Parameters:
                self -- the current BotPlayer object
                all_red_pieces_moves -- all red pieces on checkerboard
            Returns:
                Returns a list of all ordinary moves and capture moves
                described as a tuple of coordinates that could be made by the
                computer player. A list of tuples.
        '''
        ordinary_moves = []
        capture_moves = []
        for move, capture in all_red_pieces_moves.items():
            if capture is not None:
                # capture_moves = [(piece_from, piece_to, capture)]
                capture_moves.append((move[PIECE_TO], move[PIECE_FROM],
                                      capture))
            else:
                # ordinary_moves = [(piece_to, piece_from)]
                ordinary_moves.append((move[PIECE_TO], move[PIECE_FROM]))
        return ordinary_moves, capture_moves

    def selected_move_coordinates(self, ordinary_moves, capture_moves):
        '''
            Method -- selected_move_coordinates
                Randomly selects one of the checker piece move that could
                be made by the computer player. Uses random.
            Parameters:
                self -- the current BotPlayer object
                ordinary_moves -- col, row coordinate for 1 move over, a tuple
                capture_moves -- col, row coordinate for capture move, a tuple
            Returns:
                If a capture can be made, returns randomly selected capture
                move (piece_from, piece_to, capture) described by tuples.
                Returns randomly selected ordinary move otherwise (piece_from,
                piece_to, None) described by tuples.
        '''
        if not bool(capture_moves):
            ordinary_moves = random.choice(ordinary_moves)
            piece_from = ordinary_moves[PIECE_TO]
            piece_to = ordinary_moves[PIECE_FROM]
            capture = None
        else:
            capture_move = random.choice(capture_moves)
            piece_from = capture_move[PIECE_TO]
            piece_to = capture_move[PIECE_FROM]
            capture = capture_move[CAPTURE_PIECE]
        return piece_from, piece_to, capture
