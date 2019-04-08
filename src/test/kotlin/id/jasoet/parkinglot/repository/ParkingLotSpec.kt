package id.jasoet.parkinglot.repository

import id.jasoet.parkinglot.model.Car
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeLessThan
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.jeasy.random.EasyRandom
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ParkingLotSpec : Spek({
    val easyRandom = EasyRandom()

    Feature("ParkingLot Repository") {
        Scenario("Create Parking Lot with size 10") {
            lateinit var parkingLot: ParkingLot
            val lotSize = 10
            Given("Parking Lot with certain size") {
                parkingLot = ParkingLot(lotSize)
            }

            Then("created lot size should 10") {
                parkingLot.lotSize.shouldEqualTo(10)
            }

            lateinit var indexedSlot: Map<Int, Car>
            When("getting indexed slot") {
                indexedSlot = parkingLot.indexedSlotCar
            }

            Then("it should empty") {
                indexedSlot.shouldBeEmpty()
            }
        }

        Scenario("Store Car to Parking Lot With Available Slot") {
            lateinit var parkingLot: ParkingLot
            val lotSize = 10
            Given("Parking Lot with 5 car stored") {
                parkingLot = ParkingLot(lotSize)
                repeat(5) {
                    parkingLot.storeCar(easyRandom.nextObject(Car::class.java))
                }
            }

            Then("stored car count should be 5 ") {
                parkingLot.storedCarCount.shouldBe(5)
            }

            lateinit var indexedSlot: Map<Int, Car>
            When("getting indexed slot") {
                indexedSlot = parkingLot.indexedSlotCar
            }

            Then("it should not empty and has size 5") {
                indexedSlot.shouldNotBeEmpty()
                indexedSlot.size.shouldEqualTo(5)
            }

            lateinit var status: ParkingLotStatus
            When("a new car stored") {
                status = parkingLot.storeCar(Car("BE 1345 ED", "White"))
            }

            Then("status should be CarStored") {
                status.shouldBeInstanceOf<CarStored>()
            }

            Then("stored car count should be 6") {
                parkingLot.storedCarCount.shouldBe(6)
            }
        }

        Scenario("Store Car to Full Parking Lot") {
            lateinit var parkingLot: ParkingLot
            val lotSize = 10
            Given("Full Parking Lot") {
                parkingLot = ParkingLot(lotSize)
                repeat(lotSize) {
                    parkingLot.storeCar(easyRandom.nextObject(Car::class.java))
                }
            }

            lateinit var status: ParkingLotStatus
            When("a new car stored") {
                status = parkingLot.storeCar(Car("BE 1345 ED", "White"))
            }

            Then("status should Full") {
                status.shouldEqual(Full)
            }
        }

        Scenario("Remove Car from Partially Filled Parking Lot") {
            lateinit var parkingLot: ParkingLot
            val lotSize = 10
            Given("Parking Lot with 3 Slot filled") {
                parkingLot = ParkingLot(lotSize)
                repeat(3) {
                    parkingLot.storeCar(easyRandom.nextObject(Car::class.java))
                }
            }

            Then("stored car count should be equal 3") {
                parkingLot.storedCarCount.shouldEqualTo(3)
            }

            Then("Store Car Count should less than lot size") {
                parkingLot.storedCarCount.shouldBeLessThan(parkingLot.lotSize)
            }

            lateinit var statusRemovePositionMoreThanSize: ParkingLotStatus
            When("remove car from position more than size") {
                statusRemovePositionMoreThanSize = parkingLot.removeCar(parkingLot.lotSize + 3)
            }

            Then("status should equal to InvalidSlotNumber") {
                statusRemovePositionMoreThanSize.shouldEqual(InvalidSlotNumber)
            }

            lateinit var statuRemovePositionLessThanZero: ParkingLotStatus
            When("remove car from position less than zero") {
                statuRemovePositionLessThanZero = parkingLot.removeCar(-1)
            }

            Then("status should equal to InvalidSlotNumber") {
                statuRemovePositionLessThanZero.shouldEqual(InvalidSlotNumber)
            }

            lateinit var statusRemoveEmptyPosition: ParkingLotStatus
            When("remove car from empty position") {
                statusRemoveEmptyPosition = parkingLot.removeCar(5)
            }

            Then("status should equal to EmptySlot") {
                statusRemoveEmptyPosition.shouldEqual(EmptySlot)
            }

            lateinit var carFromSlotStatus: ParkingLotStatus
            When("remove car from valid and non empty position") {
                carFromSlotStatus = parkingLot.removeCar(3)
            }

            Then("status should equal to CarRemoved with non null car") {
                carFromSlotStatus.shouldBeInstanceOf<CarRemoved>()
                (carFromSlotStatus as CarRemoved).car.shouldNotBeNull()
            }

            Then("stored car count should equal to 2") {
                parkingLot.storedCarCount.shouldEqualTo(2)
            }
        }

    }
})