import turtle
import constants as const


class Board:
    '''
        Class -- Board
            Represents the checker board.
        Attributes:
            red_left -- quantity of red checker pieces left on checkerboard,
                        an integer
            black_left -- quantity of black checker pieces left on
                          checkerboard, an integer
            red_kings -- quantity of red king checker pieces, an integer
            black_kings -- quantity of black king checker pieces, an integer
            king_pieces -- dictionary of king piece locations on board
            piece_locations -- dictionary of row, column coordinates of all
                               checker pieces on the checkerboard
            checkerutilities -- object of CheckerUtilities type, used for
                                drawing checker piece, including king
        Methods:
            draw_board - draws 8x8 checker board, uses turtle
            draw_square - draws a single square, uses turtle
            color_squares - fills in light gray square in checker patter,
                            uses turtle
            locate_starting_pieces - creates dictionary to store all row, col
                                     coordinates and piece color of all checker
                                     pieces at the beginning of the game
            convert_to_upper_lft_origin - changes origin to upper-left corner
                                          of board, converts row, col
                                          coordinate accordingly
            populate_starting_checkers - draws starting set of checkers, uses
                                         turtle
            paint_over_square - redraws square where the piece has moved from,
                                uses turtle
            move_piece - moves one checker piece from old to new checker
                         square, uses turtle
            update_checker_loc - updates piece_location, deletes unoccupied
                                 coordinate and adds newly occupied coordinate
            locate_red_or_black_checkers - locates all checker pieces of
                                           requested color, a list
            move_to_coord_shift - determines the amount of shift the piece
                                  needs for a specific move
            move_king_to_coord - finds possible move coordinates for king piece
            move_to_coord - finds possible move coordinates for ordinary piece
            move_forward - checks if ordinary or king piece moves forward
            capture_piece_color - finds the checker color of the capture piece
            king_can_capture - finds capture piece and its capture move for
                               king piece
            can_capture - finds apture piece and its capture move for ordinary
                          piece
            is_empty_square - checks if the square does not have any checker
                              piece in place or not
            is_not_corner_piece - checks if the piece is at a first/last column
                                  or row
            make_king_piece - makes an ordinary piece into king, uses turtle
            find_capture_moves - finds coordinate of opponent piece to capture
                                  and its capture move
            valid_diag_move - determines if the checker piece can move over a
                              square or capture move
            get_valid_moves_for_bot - gets coordinate to move to and piece to
                                      capture if applicable
            is_within_bounds - checks if the checker piece is within the board
            all_possible_moves - identifies all possible future moves of the
                                 checker pieces given its color and current
                                 board locations
    '''
    def __init__(self, checkerutilities):
        '''
            Constructor -- creates a new instance of Board
            Parameters:
                self -- the current Board object
        '''
        self.red_left = self.black_left = 12
        self.red_kings = self.black_kings = 0
        self.king_pieces = {}
        self.piece_locations = {}
        self.checkerutilities = checkerutilities

    def draw_board(self):
        '''
            Method -- draw_board
                Uses turtle to sets up drawing canvas size, drawing component,
                and board outline. Draws a 8x8 checker board with white and
                light gray squares.
            Parameters:
                self -- the current Board object
        '''
        # Set drawing canvas size
        const.PEN.hideturtle()
        window_size = const.BOARD_SIZE + const.SQUARE_SIZE
        turtle.setup(window_size, window_size)
        turtle.screensize(const.BOARD_SIZE, const.BOARD_SIZE)
        turtle.bgcolor("white")
        turtle.tracer(0, 0)
        const.PEN.color("black", "light gray")
        # Set board outline
        const.PEN.setposition(const.CORNER, const.CORNER)
        self.draw_square(const.BOARD_SIZE, None)
        self.color_squares()

    def draw_square(self, size, mode):
        '''
            Method -- draw_square
                Draws a square given its color if any. Uses turtle.
            Parameters:
                self -- the current Board object
                size -- the side length of the square, an integer
                mode -- used to indicate pen color option, a string
        '''
        if mode == const.MOVE:
            const.PEN.color('black', 'light gray')
        const.PEN.begin_fill()
        const.PEN.pendown()
        for i in range(const.DRAW_SQUARE):
            const.PEN.forward(size)
            const.PEN.left(const.RIGHT_ANGLE)
        const.PEN.end_fill()
        const.PEN.penup()

    def color_squares(self):
        '''
            Method -- color_squares
                Uses turtle to draw a 8x8 checker board pattern in white
                and light gray.
            Parameters:
                self -- the current Board object
        '''
        const.PEN.color('black', 'white')
        const.PEN.pendown()
        for col in range(const.NUM_SQUARES):
            for row in range(const.NUM_SQUARES):
                if col % 2 == row % 2:
                    const.PEN.setposition(const.CORNER + const.SQUARE_SIZE *
                                          col, const.CORNER + const.SQUARE_SIZE
                                          * row)
                    self.draw_square(const.SQUARE_SIZE, None)
        const.PEN.penup()

    def locate_starting_pieces(self):
        '''
            Method -- locate_starting_pieces
                Places 4 checkers per each 3 rows on the light gray squares. On
                even rows, the checkers will be placed on even squares (0, 2,
                4, 6). On odd rows, the checkers will be place on the odd
                squares (1, 3, 5, 7). Sets up location coordinate for checker
                pieces in starting position
            Parameters:
                self -- the current Board object
            Returns:
                Returns dictionary of starting location of all checker pieces.
                Key = coordinate (row, col), a tuple
                Value = checker piece color, a string
        '''
        self.piece_locations = {}
        for row in range(const.BOTTOM_ROW, const.TOP_ROW + 1):
            for col in range(const.BOTTOM_ROW, const.TOP_ROW + 1):
                if col % 2 == ((row + 1) % 2):
                    # fill lower rows with black checkers
                    if row < const.CENTER_ROW - 1:
                        self.piece_locations[
                            (col + const.COL_SHFT, abs(row + const.ROW_SHFT))
                            ] = 'black'
                    # fill upper rows with dark red checkers
                    elif row > const.CENTER_ROW:
                        self.piece_locations[
                            (col + const.COL_SHFT, abs(row + const.ROW_SHFT))
                            ] = 'dark red'
        return self.piece_locations

    def convert_to_upper_lft_origin(self, inital_coordinate):
        '''
            Method -- convert_to_upper_lft_origin
                Changes checkerboard origin to upper-left corner of board,
                converts row, col coordinate accordingly to shift.
            Parameters:
                self -- the current Board object
                inital_coordinate -- intial row, col coordinate, its origin is
                                     at (0, 0) of the checkerboard
            Returns:
                Returns column, row coordinate equivalent of given x,y
                coordinate
        '''
        col = inital_coordinate[const.COL] - const.COL_SHFT
        row = -(inital_coordinate[const.ROW] + const.ROW_SHFT)
        return (col, row)

    def populate_starting_checkers(self):
        '''
            Method -- populate_starting_checkers
                Places 4 checkers per each 3 rows on the light gray squares. On
                even rows, the checkers will be placed on even squares (0, 2,
                4, 6). On odd rows, the checkers will be place on the odd
                squares (1, 3, 5, 7). Uses turtle for drawing.
            Parameters:
                self -- the current Board object
        '''
        # get coordinate locations of where to place starting pieces
        self.piece_locations = self.locate_starting_pieces()
        for coordinate, color in self.piece_locations.items():
            coord = self.convert_to_upper_lft_origin(coordinate)
            self.checkerutilities.draw_checker_piece(coord[const.COL],
                                                     coord[const.ROW], color)

    def paint_over_square(self, col_row_coord):
        '''
            Method -- paint_over_square
                Draws another square on top of a coordinate which the piece
                has moved from in a capture move. Uses turtle.
            Parameters:
                self -- the current Board object
                col_row_coord -- column, row coordinate of the initial checker
                                 piece position, a tuple
        '''
        const.PEN.penup()
        x = (col_row_coord[const.COL]) * const.SQUARE_SIZE -\
            const.HALF_BOARD
        y = (col_row_coord[const.ROW]) * -const.SQUARE_SIZE +\
            const.HALF_BOARD - const.SQUARE_SIZE
        const.PEN.goto(x, y)
        const.PEN.pendown()
        self.draw_square(const.SQUARE_SIZE, const.MOVE)
        const.PEN.penup()

    def move_piece(self, piece_from, piece_to, checker_color):
        '''
            Method -- move_piece
                Moves selected checker piece by using turtle to draw over
                the initial position checker square, then draw a new checker
                in the selected position to move to. Uses turtle.
            Parameters:
                self -- the current Board object
                piece_from -- column, row coordinate of initial position of the
                              checker piece, a tuple
                piece_to -- column, row coordinate of selected position of the
                            checker piece to move to, a tuple
                checker_color -- color of current turn checker piece, a string
        '''
        # redraw square over the inital checker position
        self.paint_over_square(piece_from)
        new_pos = self.convert_to_upper_lft_origin(piece_to)
        self.checkerutilities.draw_checker_piece(new_pos[const.COL],
                                                 new_pos[const.ROW],
                                                 checker_color)
        # if king, redraw the king symbol in new location
        if piece_from in self.king_pieces:
            self.checkerutilities.draw_king(piece_to)

    def update_checker_loc(self, piece_from, piece_to, checker_color):
        '''
            Method -- update_checker_loc
                Updates piece_locations dictionary which only includes column
                row coordinate of all checker pieces on board.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                piece_to -- column and row coordinate of selected position
                            of the checker piece to move to, a tuple
                checker_color -- color of current turn checker piece, a string
        '''
        self.piece_locations.pop(piece_from)
        self.piece_locations[piece_to] = checker_color
        if piece_from in self.king_pieces:
            self.king_pieces.pop(piece_from)
            self.king_pieces[piece_to] = checker_color

    def locate_red_or_black_checkers(self, checker_color):
        '''
            Method -- locate_red_or_black_checkers
                Locates all checker pieces of requested color.
            Parameters:
                self -- the current Board object
                checker_color -- color of given checker piece, a string
            Returns:
                Returns a list of coordinates of all checker locations of
                given color.
        '''
        checker_locations = []
        for piece_location, color in self.piece_locations.items():
            if color == checker_color:
                checker_locations.append(piece_location)
        return checker_locations

    def move_to_coord_shift(self, piece_from, move_type):
        '''
            Method -- move_to_coord_shift
                Determines the amount of shift the piece coordinates needs
                depending on its move_type.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                piece_to -- column and row coordinate of selected position
                            of the checker piece to move to, a tuple
                move_type -- type of move, either a move (1 square over) or
                             capture (2 squares over), a string
            Returns:
                Returns shift needed for move_type and the possible new
                position's column for both right and left direction, an integer
        '''
        shift = 0
        # if 'move', checker square will shift by 1
        if move_type == const.MOVE:
            shift = const.DIAGONAL_MOVE
        # if 'capture', checker squares will shift by 2
        elif move_type == const.CAPTURE:
            shift = const.CAPTURE_MOVE
        new_col_rt = piece_from[const.COL] + shift
        new_col_lft = piece_from[const.COL] - shift
        return shift, new_col_rt, new_col_lft

    def move_king_to_coord(self, piece_from, move_type):
        '''
            Method -- move_king_to_coord
                Calculates possible move coordinates for king piece given
                its move_type.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                move_type -- type of move, either a move (1 square over) or
                             capture (2 squares over), a string
            Returns:
                Returns 4 coordinates represented by a tuple, a coordinate
                moving to upper-right, upper-left, down-right, and down-left.
        '''
        # find shift needed for move
        shift, new_col_rt, new_col_lft = self.move_to_coord_shift(piece_from,
                                                                  move_type)
        # moves 'upward'
        new_row_up = piece_from[const.ROW] - shift
        move_up_rt = (new_col_rt, new_row_up)
        move_up_lft = (new_col_lft, new_row_up)
        # moves 'downward'
        new_row_down = piece_from[const.ROW] + shift
        move_down_rt = (new_col_rt, new_row_down)
        move_down_lft = (new_col_lft, new_row_down)
        return move_up_rt, move_up_lft, move_down_rt, move_down_lft

    def move_to_coord(self, piece_from, checker_color, move_type):
        '''
            Method -- move_to_coord
                Calculates possible move coordinates for ordinary piece given
                its move_type.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                checker_color -- color of current turn checker piece, a string
                move_type -- type of move, either a move (1 square over) or
                             capture (2 squares over), a string
            Returns:
                Returns 2 coordinates depending on checker_color represented by
                a tuple, a coordinate moving to the right and left.
        '''
        shift, new_col_rt, new_col_lft = self.move_to_coord_shift(piece_from,
                                                                  move_type)
        # possible moves for ordinary pieces
        if checker_color == 'black':
            # moves 'upward'
            new_row_black = piece_from[const.ROW] - shift
            move_black_rt = (new_col_rt, new_row_black)
            move_black_lft = (new_col_lft, new_row_black)
            return move_black_rt, move_black_lft
        elif checker_color == 'dark red':
            # moves 'downward'
            new_row_red = piece_from[const.ROW] + shift
            move_red_rt = (new_col_rt, new_row_red)
            move_red_lft = (new_col_lft, new_row_red)
            return move_red_rt, move_red_lft

    def move_forward(self, piece_from, piece_to, checker_color):
        '''
            Method -- move_forward
                Checks if a piece moves forward given its color and king or
                ordinary piece status.
            Parameters:
                self -- the current Board object
                piece_to -- column and row coordinate of selected position
                            of the checker piece to move to, a tuple
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                checker_color -- color of current turn checker piece, a string
            Returns:
                Returns True if the ordinary with specific checker color moves
                forward or if king piece. Returns False otherwise.
        '''
        if piece_from[const.COL] != piece_to[const.COL]:
            if piece_from not in self.king_pieces:
                if checker_color == 'black':
                    return piece_from[const.ROW] > piece_to[const.ROW]
                elif checker_color == 'dark red':
                    return piece_from[const.ROW] < piece_to[const.ROW]
            elif piece_from in self.king_pieces:
                return True
        return False

    def capture_piece_color(self, capture_piece, capture_move):
        '''
            Method -- capture_piece_color
                Finds the checker color of the capture piece.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                checker_color -- color of current turn checker piece, a string
            Returns:
                Returns the color of the capture piece color if there exists a
                capture piece and it's not in the first or last row/column.
                Returns None otherwise
        '''
        if self.is_not_corner_piece(capture_piece) and\
                capture_piece in self.piece_locations and\
                self.is_empty_square(capture_move):
            return self.piece_locations[capture_piece]
        return None

    def king_can_capture(self, piece_from, checker_color):
        '''
            Method -- king_can_capture
                Finds coordinates of opponent piece to be captured and its
                capture move for a king piece.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                checker_color -- color of current turn checker piece, a string
            Returns:
                Returns 2 coordinates represented by a tuple, all capture piece
                locations and their respective capture moves described by its
                directions (upper-right/left, down-right/left). Returns None
                otherwise.
        '''
        # possible location of piece to be captured
        capture_up_rt, capture_up_lft, capture_down_rt, capture_down_lft =\
            self.move_king_to_coord(piece_from, const.MOVE)
        # possible location of capture move
        piece_up_rt, piece_up_lft, piece_down_rt, piece_down_lft =\
            self.move_king_to_coord(piece_from, const.CAPTURE)
        # initialize list for piece to capture & capture move
        capture_piece = []
        capture_move = []
        # calculate capture piece location and its capture move
        capture_color = self.capture_piece_color(capture_up_rt, piece_up_rt)
        if capture_color is not None and capture_color != checker_color and \
                self.is_not_corner_piece(capture_up_rt):
            capture_piece.append(capture_up_rt)
            capture_move.append(piece_up_rt)

        capture_color = self.capture_piece_color(capture_up_lft, piece_up_lft)
        if capture_color is not None and capture_color != checker_color and \
                self.is_not_corner_piece(capture_up_lft):
            capture_piece.append(capture_up_lft)
            capture_move.append(piece_up_lft)

        capture_color = self.capture_piece_color(capture_down_rt,
                                                 piece_down_rt)
        if capture_color is not None and capture_color != checker_color and \
                self.is_not_corner_piece(capture_down_rt):
            # return capture_down_rt, piece_down_rt
            capture_piece.append(capture_down_rt)
            capture_move.append(piece_down_rt)

        capture_color = self.capture_piece_color(capture_down_lft,
                                                 piece_down_lft)
        if capture_color is not None and capture_color != checker_color and \
                self.is_not_corner_piece(capture_down_lft):
            # return capture_down_lft, piece_down_lft
            capture_piece.append(capture_down_lft)
            capture_move.append(piece_down_lft)
        if (capture_piece and capture_move) is not None:
            return capture_piece, capture_move
        return None, None

    def can_capture(self, piece_from, checker_color):
        '''
            Method -- can_capture
                Finds coordinates of opponent piece to be captured and its
                capture move for an ordinary piece given its checker color.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                checker_color -- color of current turn checker piece, a string
            Returns:
                Returns 2 coordinates represented by a tuple, a capture piece
                location and its capture move described by its direction
                (right or left). Returns None otherwise.
        '''
        # set up possible coordinates of where the capture piece can be
        capture_piece_rt, capture_piece_lft =\
            self.move_to_coord(piece_from, checker_color, const.MOVE)
        piece_to_rt, piece_to_lft =\
            self.move_to_coord(piece_from, checker_color, const.CAPTURE)
        # calculate capture piece location and its capture move
        capture_rt_color = self.capture_piece_color(capture_piece_rt,
                                                    piece_to_rt)
        capture_lft_color = self.capture_piece_color(capture_piece_lft,
                                                     piece_to_lft)
        # initialize list for piece to capture & capture move
        capture_piece = []
        capture_move = []
        # capture on right
        if capture_rt_color is not None and capture_rt_color != checker_color\
                and self.is_not_corner_piece(capture_piece_rt):
            capture_piece.append(capture_piece_rt)
            capture_move.append(piece_to_rt)
        # capture on left
        if capture_lft_color is not None and capture_lft_color !=\
                checker_color and self.is_not_corner_piece(capture_piece_lft):
            capture_piece.append(capture_piece_lft)
            capture_move.append(piece_to_lft)
        if (capture_piece and capture_move) is not None:
            return capture_piece, capture_move
        return None, None

    def is_empty_square(self, piece_to):
        '''
            Method -- is_empty_square
                Checks if the given coordinate has any checker piece
                in its square.
            Parameters:
                self -- the current Board object
                piece_to -- column and row coordinate of selected position
                            of the checker piece to move to, a tuple
            Returns:
                Returns True if the square is empty, returns False if
                there's a checker piece in place.
        '''
        if piece_to in self.piece_locations:
            return False
        return True

    def is_not_corner_piece(self, piece_coord):
        '''
            Method -- is_not_corner_piece
                Checks if the given piece coordinate is at a first/last
                column or row of the checkerboard.
            Parameters:
                self -- the current Board object
                piece_coord -- column, row coordinate of the piece
            Returns:
                Returns True if the piece is not located in the first/last
                column or row, returns False otherwise.
        '''
        if piece_coord[const.COL] != const.LAST_COL and \
                piece_coord[const.COL] != const.FIRST_COL and\
                piece_coord[const.ROW] != const.FIRST_ROW and\
                piece_coord[const.ROW] != const.LAST_ROW:
            return True
        return False

    def make_king_piece(self, piece_to, checker_color):
        '''
            Method -- make_king_piece
                Identifies checker piece as king. Adds piece location
                to king_pieces dictionary and draws a king crown on the
                piece. Uses turtle.
            Parameters:
                self -- the current Board object
        '''
        # add to king pieces dictionary and draw king
        self.king_pieces[piece_to] = checker_color
        self.checkerutilities.draw_king(piece_to)

    def find_capture_moves(self, piece_from, checker_color):
        '''
            Method -- find_capture_moves
                Finds coordinates of opponent piece that can be captured and
                coordinate of capture move.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                checker_color -- color of current turn checker piece, a string
            Returns:
                Returns coordinates of opponent piece to capture and its
                capture move represented by tuples.
        '''
        # king checker piece
        if piece_from in self.king_pieces:
            piece_to_capture, capture_move =\
                 self.king_can_capture(piece_from, checker_color)
            if piece_to_capture and capture_move is not None:
                return piece_to_capture, capture_move
        # ordinary checker piece
        else:
            piece_to_capture, capture_move = self.can_capture(piece_from,
                                                              checker_color)
            if piece_to_capture and capture_move is not None:
                return piece_to_capture, capture_move
        return None, None

    def valid_diag_move(self, piece_from, piece_to, checker_color):
        '''
            Method -- valid_diag_move
                Determines if one move over a square or capture move is
                possible given the initial and next coordinate of the piece,
                its checker color, and its ordinary/king piece status.
            Parameters:
                self -- the current Board object
                piece_from -- column and row coordinate of initial position
                              of the checker piece, a tuple
                piece_to -- column and row coordinate of selected position
                            of the checker piece to move to, a tuple
                checker_color -- color of current turn checker piece, a string
            Returns:
                Returns True and piece_to_capture if there's a capture move
                available, returns False and None otherwise. If no capture
                move is available, move one square over. If valid, return
                True and None, otherwise return False and None.
        '''
        # if opponent checker is 1 square over, capture must be made
        piece_to_capture, capture_move =\
            self.find_capture_moves(piece_from, checker_color)
        if piece_to_capture and capture_move is not None:
            if piece_to not in capture_move:
                return False, None
            piece_to_index = capture_move.index(piece_to)
            match_piece_to_capture = piece_to_capture[piece_to_index]
            match_capture_move = capture_move[piece_to_index]
            if (piece_to_capture is not None) and \
                    (piece_to == match_capture_move):
                return True, match_piece_to_capture
            elif (piece_to_capture is not None) and \
                    (piece_to != match_capture_move):
                return False, None

        # if no caputure move available, move to 1 square over
        if piece_from not in self.king_pieces:
            move_rt, move_lft = self.move_to_coord(piece_from,
                                                   checker_color, const.MOVE)
            if ((move_rt == piece_to) or (move_lft == piece_to)):
                return True, None
        elif piece_from in self.king_pieces:
            move_up_rt, move_up_lft, move_down_rt, move_down_lft =\
                self.move_king_to_coord(piece_from, const.MOVE)
            if (move_up_rt == piece_to) or (move_up_lft == piece_to) or\
               (move_down_rt == piece_to) or (move_down_lft == piece_to):
                return True, None
        # not a permittable move otherwise
        return False, None

    def get_valid_moves_for_bot(self, piece, checker_color):
        '''
            Method -- get_valid_moves_for_bot
                Gets piece_to and capture (if applicable) of valid
                move that can be made by the computer player.
            Parameters:
                self -- the current Board object
        '''
        # potential move for capture move
        piece_to_capture, capture_move =\
            self.find_capture_moves(piece, checker_color)
        if capture_move is not None:
            # {capture_move: piece_to_capture}
            capture_move = dict(zip(capture_move, piece_to_capture))
            return capture_move

        # potential moves for 1 square over move(s)
        possible_noncapture_move = []
        noncapture_moves = {}
        if piece not in self.king_pieces:
            # potential moves of piece
            move_rt, move_lft = self.move_to_coord(piece, checker_color,
                                                   const.MOVE)
            possible_noncapture_move.extend((move_rt, move_lft))
            # check if each move is at empty square and in bound
            for i, coordinate in enumerate(possible_noncapture_move):
                if self.is_empty_square(coordinate) and \
                        self.is_within_bounds(coordinate):
                    noncapture_moves[coordinate] = None
        elif piece in self.king_pieces:
            # potential moves of king piece
            move_up_rt, move_up_lft, move_down_rt, move_down_lft =\
                self.move_king_to_coord(piece, const.MOVE)
            possible_noncapture_move.extend((move_up_rt, move_up_lft,
                                             move_down_rt, move_down_lft))
            for i, coordinate in enumerate(possible_noncapture_move):
                # check if each move is at empty square and in bound
                if self.is_empty_square(coordinate) and\
                        self.is_within_bounds(coordinate):
                    noncapture_moves[coordinate] = None
        # if non-capture moves are unavaliable, return None
        # dict {piece_to: capture}
        if bool(noncapture_moves):
            return noncapture_moves
        return None

    def is_within_bounds(self, coordinate):
        '''
            Method -- is_within_bounds
                Checks if checker piece is within the board.
            Parameters:
                self -- the current Board object
                coordinate -- col, row coordinate of the checker piece, a tuple
            Returns:
                Returns boolean, True if within bounds, False otherwise.
        '''
        if (coordinate[const.COL] in range(const.FIRST_COL, const.COLS)) and \
                coordinate[const.ROW] in range(const.FIRST_ROW, const.ROWS):
            return True
        return False

    def all_possible_moves(self, piece_locations, checker_color):
        '''
            Method -- all_possible_moves
                Identifies all possible future moves given checker color and
                current board location.
            Parameters:
                self -- the current Board object
                piece_locations -- all locations of the checker pieces on
                                   board, a dictionary
                checker_color -- color of current turn checker piece, a string
        '''
        # create a dict of all possible moves {(piece_from, piece_to): capture}
        all_possible_moves = {}
        # get lst of all black/red checker piece locations
        all_pieces = self.locate_red_or_black_checkers(checker_color)
        for piece_from in all_pieces:
            # {capture_move: piece_to_capture} or {piece_to: None}
            possible_moves = self.get_valid_moves_for_bot(piece_from,
                                                          checker_color)
            if possible_moves is not None:
                for piece_to, capture in possible_moves.items():
                    all_possible_moves[(piece_from, piece_to)] = capture
        return all_possible_moves
