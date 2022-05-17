package racingcar.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class CarsTest : FunSpec({
    test("Cars 객체를 생성한다") {
        // given
        val cars = listOf(
            Car("k5"),
            Car("sm5"),
        )

        // when // then
        shouldNotThrowAny { Cars(cars) }
    }

    test("자동차가 2대 미만일 때, 자동차 경주 게임을 생성하면 예외를 발생시킨다.") {
        // given
        val cars = listOf(Car("sm3"))

        // when // then
        shouldThrowExactly<IllegalArgumentException> { Cars(cars) }
    }

    test("자동차들이 전진하거나 전진하지 않는다.") {
        listOf(
            row(Cars(listOf(Car("k5"), Car("sm5"))), 3, 0),
            row(Cars(listOf(Car("k5"), Car("sm5"))), 4, 1),
        ).forAll { (cars, condition, expected) ->
            // when
            val actual = cars.moveAll { condition }

            // then
            actual.cars[0].position shouldBe expected
            actual.cars[1].position shouldBe expected
        }
    }

    test("우승자를 찾는 기능") {
        // given
        val cars = listOf(
            Car(Name("a"), 3),
            Car(Name("b"), 2),
            Car(Name("c"), 1),
            Car(Name("d"), 3),
        ).let { Cars(it) }

        // when
        val winners = cars.selectWinners()

        // then
        winners.size shouldBe 2
        winners[0].value shouldBe "a"
        winners[1].value shouldBe "d"
    }
})
