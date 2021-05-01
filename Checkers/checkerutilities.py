from constants import SQUARE_SIZE, PEN, RADIUS, RIGHT_ANGLE,\
    CROWN_HALF_BASE, CROWN_HEIGHT, ARCH_ANGLE, NO_OF_ARCHES,\
    KING_COL_SHIFT, KING_ROW_SHIFT, COL, ROW


class CheckerUtilities:
    '''
        Class -- CheckerUtilities
            Represents functionality of the checker piece.
        Methods:
            get_xy_coordinates - calculates center x, y coordinate of checker
                                 square, uses turtle
            draw_checker_piece - draws checker piece given x, y coordinate,
                                 uses turtle
            draw_king - draws crown for king piece, uses turtle
    '''
    def __init__(self):
        '''
            Constructor -- creates a new instance of CheckerUtilities
            Parameters:
                self -- the current CheckerUtilities object
        '''

    def get_xy_coordinates(self, col, row):
        '''
            Method -- get_xy_coordinates
                Calculates x, y coordinate. Column and rows are based on
                its origin being at (0, 0), not upper-left corner. Uses turtle.
            Parameters:
                self -- the current CheckerUtilities object
                col -- column of the checker board, an integer
                row -- row of the checker board, an integer
            Returns:
                Returns center x, y coordinate of the given column and row.
        '''
        x = col * SQUARE_SIZE + SQUARE_SIZE // 2
        y = row * SQUARE_SIZE
        return (x, y)

    def draw_checker_piece(self, col, row, checker_color):
        '''
            Method -- draw_checker_piece
                Draws a filled in circle representing a checker piece given
                its color and coordinate. Uses turtle.
            Parameters:
                self -- the current CheckerUtilities object
                col -- column of the checker board, an integer
                row -- row of the checker board, an integer
                checker_color -- color of the checker, a string
        '''
        PEN.penup()
        PEN.setpos(self.get_xy_coordinates(col, row))
        PEN.color(checker_color)
        PEN.pendown()
        PEN.begin_fill()
        PEN.circle(RADIUS)
        PEN.end_fill()
        PEN.penup()

    def draw_king(self, piece_to):
        '''
            Method -- draw_checker_piece
                Draws a crown for king piece. Uses turtle.
            Parameters:
                self -- the current CheckerUtilities object
        '''
        PEN.penup()
        PEN.hideturtle()
        x = KING_COL_SHIFT + SQUARE_SIZE * piece_to[COL]
        y = KING_ROW_SHIFT - SQUARE_SIZE * piece_to[ROW]
        PEN.setpos(x, y)
        PEN.color('gold')
        PEN.begin_fill()
        PEN.forward(CROWN_HALF_BASE)
        PEN.left(RIGHT_ANGLE)
        PEN.forward(CROWN_HEIGHT)
        PEN.right(-ARCH_ANGLE)
        PEN.forward(CROWN_HEIGHT / 2)
        for i in range(NO_OF_ARCHES):
            if i % 2 != 0:
                PEN.left(RIGHT_ANGLE)
            else:
                PEN.right(RIGHT_ANGLE)
            PEN.forward(CROWN_HEIGHT / 2)
        PEN.left(ARCH_ANGLE)
        PEN.forward(CROWN_HEIGHT)
        PEN.left(RIGHT_ANGLE)
        PEN.forward(CROWN_HALF_BASE)
        PEN.end_fill()
        PEN.penup()
