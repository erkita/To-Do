import constants as const


class GameState:
    '''
        Class -- GameState
            Represents the rules and the basic mechanism of the game.
        Attributes:
            turn_color -- current turn's checker piece color, a string
            red_kings -- quantity of red king checker pieces, an integer
            black_kings -- quantity of black king checker pieces, an integer
            piece_from -- column, row coordinate of initial position of the
                          checker piece, a tuple
            piece_to -- column, row coordinate of selected position of the
                        checker piece to move to, a tuple
            valid_col_row -- column and row coordinate that lies on the light
                             gray checker square, a tuple
            board -- object of Board type, used to call methods from Board
            black_score -- score keeping for black checker piece player,
                           an integer
            red_score -- score keeping for red checker piece player, an integer
            next_capture -- future capture move used in multiple capture,
                            a tuple
            botplayer -- object of BotPlayer type, used to call methods from
                         BotPlayer
        Methods:
            next_turn - creates turn-taking notion starting from black checkers
            valid_board_pos - checks if checker piece lies on gray checker
                              square
            keep_score_and_piece_count - keeps scores and pieces left for black
                                         and dark red checker players
            write_gameover - writes game over and winner of game, uses turtle
            winner - determines the winner of the game, uses turtle
            move_checker_piece - moves checker pieces in appropriate place and
                                 direction, mandates capture for single and
                                 multiple capture moves, makes piece into king,
                                 uses turtle.
            is_blocked_from_moving - checks if there's any more possible moves
                                     available
            reset_move - resets piece_from and piece_to for new piece selection
            is_first_click - determines if first click is at valid position,
                             returns boolean
            is_second_click - determines if second click is at valid position,
                              returns boolean
            handle_capture_move - moves checker piece by painting over old
                                  location and painting new checker piece in
                                  new location, uses turtle
            move_and_update_piece - moves checker piece to new location and
                                    updates piece_location, uses turtle
            handle_king_piece - counts piece as king if it has reached its
                                opponent's first row, uses turtle
            bot_move - handles the computer player's (red checker) move and
                       capture, uses turtle
            handle_bot_move - handles movement, capture, update of checker
                              location dictionary, and makes red checker piece
                              king, uses turtle
            capture_exists - checks to see if there's any capture available
    '''
    def __init__(self, board, botplayer, turn='black'):
        '''
            Constructor -- creates a new instance of GameState
            Parameters:
                self -- the current GameState object
        '''
        self.turn_color = turn
        self.red_kings = self.black_kings = 0
        self.piece_from = self.piece_to = None
        self.valid_col_row = None
        self.board = board
        self.black_score = self.red_score = 0
        self.next_capture = None
        self.botplayer = botplayer

    def next_turn(self):
        '''
            Method -- next_turn
                Creates turn-taking notion. Starting from 'black' piece player,
                'black' and 'red' takes alternating turns. Resets moves and
                next_capture for the next player.
            Parameters:
                self -- the current GameState object
        '''
        self.reset_move()
        if self.turn_color == 'black':
            self.turn_color = 'dark red'
            const.SCREEN.ontimer(self.bot_player, const.PAUSE)
        else:
            self.turn_color = 'black'
        self.next_capture = None
        print('''{}'s turn'''.format(self.turn_color))

    def valid_board_pos(self, x, y):
        '''
            Method -- valid_board_pos
                Checks if x, y coordinate clicked is inside of the checker
                board and whitin the gray checker square by using rows, column
                coordinate of the checkerboard.
            Parameters:
                self -- curent GameState object
                x -- X coordinate of the click. Automatically provided by
                     Turtle.
                y -- Y coordinate of the click. Automatically provided by
                     Turtle.
            Returns:
                Returns valid row, column coordinate if clicked is inside the
                checkerboard and within the gray checker squares. Returns None
                otherwise.
        '''
        # checks if within checkerboard
        if const.MIN_BOUND < x < const.MAX_BOUND and const.MIN_BOUND < y <\
                const.MAX_BOUND:
            # checks if within range of each light gray rows
            for row in range(const.FIRST_ROW, const.NUM_SQUARES):
                x_min_bound = row * const.SQUARE_SIZE + const.MIN_BOUND
                x_max_bound = row * const.SQUARE_SIZE + const.MIN_BOUND +\
                    const.SQUARE_SIZE
                # checks if within range of each light gray columns
                for col in range(const.FIRST_COL, const.NUM_SQUARES):
                    y_min_bound = col * -const.SQUARE_SIZE + const.MAX_BOUND -\
                        const.SQUARE_SIZE
                    y_max_bound = col * -const.SQUARE_SIZE + const.MAX_BOUND
                    # odd row, odd column
                    if row % 2 != 0 and col % 2 != 0:
                        if x in range(x_min_bound, x_max_bound):
                            if y in range(y_min_bound, y_max_bound):
                                return (row, col)
                    # even row, even column
                    elif row % 2 == 0 and col % 2 == 0:
                        if x in range(x_min_bound, x_max_bound):
                            if y in range(y_min_bound, y_max_bound):
                                return (row, col)
        return None

    def keep_score_and_piece_count(self, piece_captured):
        '''
            Method -- keep_score_and_piece_count
                Keeps score for black and dark red checker players. Adds
                1 point to the player who has captured an opponent piece.
            Parameters:
                self -- curent GameState object
                piece_captured -- opponent checker piece captured, a tuple
        '''
        piece_captured_color = self.board.piece_locations[piece_captured]
        if piece_captured_color == 'dark red':
            self.black_score += 1
            self.board.red_left -= 1
        else:
            self.red_score += 1
            self.board.black_left -= 1

    def write_gameover(self, winner_color):
        '''
            Method -- write_gameover
                Draws "Game Over" to the board and reveals winner. Uses
                turtle.
            Parameters:
                self -- curent GameState object
                winner_color -- winning checker color
        '''
        const.PEN.hideturtle()
        const.PEN.goto(const.CENTER_X, const.CENTER_Y)
        const.PEN.color("green")
        const.PEN.write('Game Over!\n', align='center',
                        font=("Arial", const.FONTSIZE, "bold"))
        const.PEN.write('{} wins'.format(winner_color), align='center',
                        font=("Arial", const.FONTSIZE, "bold"))

    def winner(self):
        '''
            Method -- winner
                Determines the winner if one player exhausts all moves possible
                or no longer have any checker piece. Uses turtle.
            Parameters:
                self -- curent GameState object
            Returns:
                Returns True and shows game over and reveals the winner.
                Returns False otherwise. Uses turtle.
        '''
        # get {(piece_from, piece_to): capture}
        pieces_moves = self.board.all_possible_moves(
            self.board.piece_locations, self.turn_color)
        is_blocked = self.is_blocked_from_moving(pieces_moves)
        if (is_blocked and self.turn_color == const.RED) or \
                self.board.red_left <= 0:
            self.write_gameover(const.BLACK)
            return
        elif (is_blocked and self.turn_color == const.BLACK) or \
                self.board.black_left <= 0:
            self.write_gameover(const.RED)
            return
        self.next_turn()

    def move_checker_piece(self, x, y):
        '''
            Method -- move_checker_piece
                Called when a click occurs. Moves clicked checker piece to
                another clicked position if it is their turn and both clicks
                were within valid coordinates (within gray checkers square,
                no other checker piece in place, in appropriate direction).
                If capture can be made, it must be made for both single and
                multiple captures. Makes piece into kings if it reaches
                opponent's first row. Uses turtle.
            Parameters:
                x -- X coordinate of the click. Automatically provided by
                     Turtle.
                y -- Y coordinate of the click. Automatically provided by
                     Turtle.
        '''
        # for any click, check to see if within gray square
        self.valid_col_row = self.valid_board_pos(x, y)
        if self.valid_col_row is None:
            # if invalid move do nothing
            return

        # 1st click, within gray square & there's a checker piece
        if self.is_first_click():
            # cannot go if it's not in turn
            if self.board.piece_locations.get(self.valid_col_row) !=\
                    self.turn_color:
                self.piece_from = None
                return
            # sets piece_from if it's turn
            self.piece_from = self.valid_col_row

        # 2nd click, valid spot & no checker is in spot
        elif self.is_second_click():
            self.piece_to = self.valid_col_row
            # check to see if there's any available captures
            capture_exists = self.capture_exists()
            if capture_exists and self.piece_to not in capture_exists:
                return
            # if there exist a future capture move for multiple capture,
            # get another future piece_to click from user
            if self.next_capture is not None and\
               self.next_capture != self.piece_to:
                self.piece_to = None
                return
            # checks if piece moves in forward direction
            if not self.board.move_forward(self.piece_from, self.piece_to,
                                           self.turn_color):
                self.reset_move()
                return
            # check if piece moves in an appropriate diagonal direction
            is_valid_move, piece_captured = self.board.valid_diag_move(
                                        self.piece_from, self.piece_to,
                                        self.turn_color)
            # if not valid move, reset moves
            if is_valid_move is False:
                self.reset_move()
                return
            # if valid move, move the piece and update piece location
            self.move_and_update_piece(piece_captured)
            # check if multiple capture is possible
            future_piece_to_capture, future_capture_move =\
                self.board.find_capture_moves(self.piece_to, self.turn_color)
            if piece_captured is not None and \
                    future_piece_to_capture is not None:
                self.next_capture = future_capture_move[0]
                self.piece_from = self.piece_to
                return
            # check for winner
            self.winner()

    def is_blocked_from_moving(self, pieces_moves):
        '''
            Method -- is_blocked_from_moving
                Checks if a piece is blocked from moving or not.
            Parameters:
                self -- curent GameState object
            Returns:
                Returns a boolean, True if there is no more possible
                moves left, returns False otherwise.
        '''
        if not bool(pieces_moves):
            return True
        return False

    def reset_move(self):
        '''
            Method -- reset_move
                Resets piece_from and piece_to. Allows user to select another
                piece to move.
            Parameters:
                self -- curent GameState object
        '''
        self.piece_from = None
        self.piece_to = None

    def is_first_click(self):
        '''
            Method -- is_first_click
                Determines if first click is valid.
            Parameters:
                self -- curent GameState object
            Returns:
                Returns True if clicked position has a checker piece and
                piece_from has yet to be assigned a tuple. Returns False
                otherwise.
        '''
        return self.valid_col_row in self.board.piece_locations and \
            self.piece_from is None

    def is_second_click(self):
        '''
            Method -- is_second_click
                Determines if second click is valid.
            Parameters:
                self -- curent GameState object
            Returns:
                Returns True if there exists a piece_from and next desired move
                position is empty, returns False otherwise.
        '''
        return self.piece_from is not None and self.valid_col_row not \
            in self.board.piece_locations

    def handle_capture_move(self, piece_captured):
        '''
            Method -- handle_capture_move
                Captures the opponent piece by painting over the checker square
                of the captured piece and deleting its element in
                piece_locations. Gives score to the checker player who made the
                capture. Uses turtle.
            Parameters:
                self -- curent GameState object
                piece_captured -- opponent checker piece captured
        '''
        if piece_captured is not None:
            self.board.paint_over_square(piece_captured)
            self.keep_score_and_piece_count(piece_captured)
            self.board.piece_locations.pop(piece_captured)

    def move_and_update_piece(self, piece_captured):
        '''
            Method -- move_and_update_piece
                Moves checker piece to a new location and updates its location
                in piece_location dictionary. Makes piece into king if piece
                has reached opponent's first row. Uses turtle.
            Parameters:
                self -- curent GameState object
                piece_captured -- opponent checker piece captured
        '''
        # move and update location of the checker piece
        self.board.move_piece(self.piece_from, self.piece_to, self.turn_color)
        self.handle_capture_move(piece_captured)
        self.board.update_checker_loc(self.piece_from,
                                      self.piece_to,
                                      self.turn_color)
        self.handle_king_piece(self.piece_to)

    def handle_king_piece(self, piece_to):
        '''
            Method -- handle_king_piece
                Counts piece as a king piece given its color and whether if
                it has reached its opposing player's first row. Uses turtle.
            Parameters:
                self -- curent GameState object
                piece_to -- column, row coordinate of selected position of the
                            checker piece to move to, a tuple
        '''
        if self.turn_color == const.BLACK:
            if (piece_to[const.ROW] == const.RED_FIRST_ROW):
                self.board.make_king_piece(piece_to, self.turn_color)
                self.black_kings += 1
        elif self.turn_color == const.RED:
            if (piece_to[const.ROW] == const.BLACK_FIRST_ROW):
                self.board.make_king_piece(piece_to, self.turn_color)
                self.red_kings += 1

    def bot_player(self):
        '''
            Method -- bot_move
                Handles the computer player's (red checker) move and
                capture. Uses turtle.
            Parameters:
                self -- curent GameState object
        '''
        # get movement coordinates for computer player (red)
        piece_from, piece_to, capture = self.botplayer.bot_move()
        # move the piece, capture and/or make king if applicable
        self.handle_bot_move(piece_from, piece_to, capture)
        future_piece_to_capture, future_capture_move =\
            self.board.find_capture_moves(piece_to, const.RED)
        # check if multiple capture is possible
        while capture is not None and future_piece_to_capture is not None:
            for capture in future_piece_to_capture:
                for capture_move in future_capture_move:
                    const.SCREEN.ontimer(self.handle_bot_move(
                                         piece_to, capture_move, capture),
                                         const.PAUSE)
            future_piece_to_capture, future_capture_move =\
                self.board.find_capture_moves(piece_to, const.RED)
        self.winner()

    def handle_bot_move(self, piece_from, piece_to, capture):
        '''
            Method -- handle_bot_move
                Handles movement, capture, update of checker location
                dictionary, and makes red checker piece king. Uses turtle.
            Parameters:
                self -- curent GameState object
        '''
        self.board.move_piece(piece_from, piece_to, const.RED)
        self.handle_capture_move(capture)
        self.board.update_checker_loc(piece_from, piece_to, const.RED)
        self.handle_king_piece(piece_to)

    def capture_exists(self):
        '''
            Method -- capture_exists
                Checks to see if there's any capture available given all the
                checker piece positions.
            Parameters:
                self -- curent GameState object
            Returns:
                A list of piece_to's that would lead to a capture.
        '''
        capture_available = []
        all_black_pieces = self.board.all_possible_moves(
            self.board.piece_locations, const.BLACK)
        for move, capture in all_black_pieces.items():
            if capture is not None:
                capture_available.append(move[const.PIECE_TO])
        return capture_available
