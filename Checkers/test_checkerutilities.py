from checkerutilities import CheckerUtilities


def test_get_coordinates():
    checkerutilities = CheckerUtilities()
    assert checkerutilities.get_xy_coordinates(0, 0) == (25, 0)
    assert checkerutilities.get_xy_coordinates(6, 2) == (325, 100)
    assert checkerutilities.get_xy_coordinates(-4, 3) == (175, 150)
    assert checkerutilities.get_xy_coordinates(-2, -3) == (-75, -150)
    assert checkerutilities.get_xy_coordinates(0, 0) == (175, -200)
