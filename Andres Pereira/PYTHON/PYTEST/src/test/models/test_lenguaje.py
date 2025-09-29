from src.models.lenguaje import lenguajeModel


def test_lenguajeModel_not_none():
    lenguajes = lenguajeModel.getLenguajes()
    assert lenguajes != None


def test_lenguajeModel_length():
    lenguaje = lenguajeModel.getLenguajes()
    assert len(lenguaje) > 0


def test_LenguajeModel_check_element_length():
    lenguaje = lenguajeModel.getLenguajes()
    for lang in lenguaje:
        assert len(lang) > 1