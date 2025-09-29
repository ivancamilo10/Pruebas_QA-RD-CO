from src.main import sum, is_great_than, login
import pytest


def test_sum():
    assert sum(2, 3) == 5


def test_is_great_than():
    assert is_great_than(5, 3) is True
    assert is_great_than(2, 4) is False


@pytest.mark.parametrize(
    "x, y, a",
    [
        (5, 1, 6),
        (3, sum(2, 1), 6),
        (sum(5, 5), -4, 6),
    ]
)
def test_sum_negative(x, y, a):
    assert sum(x, y) == a


def test_Login_pass():
    login_pass = login("Andres", "123")
    assert login_pass

def test_Login_fail():
    login_fail = login("Andres", "321")
    assert not login_fail
