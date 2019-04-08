package id.jasoet.parkinglot.parser

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CommandLineParserSpec : Spek({
    describe("A CommandLine Parser") {
        context("Processing Valid Commands") {

            describe("create parking lot") {
                val validCommand = "create_parking_lot 5"
                val invalidArgumentType = "create_parking_lot BE23343"
                val tooFewArgument = "create_parking_lot"
                val tooMuchArguments = "create_parking_lot 5 BE124EK"

                it("correct commandLine should produce CreateParkingLot with 1 argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(CreateParkingLot)
                    commandAndArg.arguments.shouldNotBeEmpty()
                    commandAndArg.arguments.size.shouldEqualTo(1)
                }

                it("invalid arguments type should produce Invalid with no argument") {
                    val commandAndArg = invalidArgumentType.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too few arguments should produce Invalid with no argument") {
                    val commandAndArg = tooFewArgument.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("park") {
                val validCommand = "park BE1434EK White"
                val tooFewArgument = "park WHITE"
                val tooMuchArguments = "park BE124EK WHITE DE1242EK"

                it("correct commandLine should produce Park with 2 argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Park)
                    commandAndArg.arguments.shouldNotBeEmpty()
                    commandAndArg.arguments.size.shouldEqualTo(2)
                }

                it("too few arguments should produce Invalid with no argument") {
                    val commandAndArg = tooFewArgument.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("leave") {
                val validCommand = "leave 1"
                val invalidArgumentType = "leave BE323DK"
                val tooFewArgument = "leave"
                val tooMuchArguments = "lave 4 DE1242EK"

                it("correct commandLine should produce Leave with 1 argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Leave)
                    commandAndArg.arguments.shouldNotBeEmpty()
                    commandAndArg.arguments.size.shouldEqualTo(1)
                }

                it("invalid arguments type should produce Invalid with no argument") {
                    val commandAndArg = invalidArgumentType.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too few arguments should produce Invalid with no argument") {
                    val commandAndArg = tooFewArgument.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("status") {
                val validCommand = "status"
                val tooMuchArguments = "status 4 DE1242EK"

                it("correct commandLine should produce Status with no argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Status)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("registration numbers for cars with colour") {
                val validCommand = "registration_numbers_for_cars_with_colour white"
                val tooFewArgument = "registration_numbers_for_cars_with_colour"
                val tooMuchArguments = "registration_numbers_for_cars_with_colour white rose"

                it("correct commandLine should produce RegistrationNumberForCarsWithColour with 1 argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(RegistrationNumberForCarsWithColour)
                    commandAndArg.arguments.shouldNotBeEmpty()
                    commandAndArg.arguments.size.shouldEqualTo(1)
                }

                it("too few arguments should produce Invalid with no argument") {
                    val commandAndArg = tooFewArgument.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("slot numbers for cars with colour") {
                val validCommand = "slot_numbers_for_cars_with_colour white"
                val tooFewArgument = "slot_numbers_for_cars_with_colour"
                val tooMuchArguments = "slot_numbers_for_cars_with_colour white rose"

                it("correct commandLine should produce SlotNumbersForCarsWithColour with 1 argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(SlotNumbersForCarsWithColour)
                    commandAndArg.arguments.shouldNotBeEmpty()
                    commandAndArg.arguments.size.shouldEqualTo(1)
                }

                it("too few arguments should produce Invalid with no argument") {
                    val commandAndArg = tooFewArgument.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("slot numbers for cars with colour") {
                val validCommand = "slot_number_for_registration_number BE2343EK"
                val tooFewArgument = "slot_number_for_registration_number"
                val tooMuchArguments = "slot_number_for_registration_number BE2343EK rose"

                it("correct commandLine should produce SlotNumberForRegistrationNumber with 1 argument") {
                    val commandAndArg = validCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(SlotNumberForRegistrationNumber)
                    commandAndArg.arguments.shouldNotBeEmpty()
                    commandAndArg.arguments.size.shouldEqualTo(1)
                }

                it("too few arguments should produce Invalid with no argument") {
                    val commandAndArg = tooFewArgument.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }

                it("too much arguments should produce Invalid with no argument") {
                    val commandAndArg = tooMuchArguments.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }
        }
        context("Processing Invalid Commands") {

            describe("empty commandLine") {
                val inValidCommand = ""

                it("correct commandLine should produce Invalid with no argument") {
                    val commandAndArg = inValidCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("non exists commandLine") {
                val inValidCommand = "non_exists_command no_arguments"

                it("correct commandLine should produce Invalid with no argument") {
                    val commandAndArg = inValidCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("'invalid' commandLine") {
                val inValidCommand = "invalid"

                it("correct commandLine should produce Invalid with no argument") {
                    val commandAndArg = inValidCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }

            describe("'Invalid' commandLine") {
                val inValidCommand = "Invalid"

                it("correct commandLine should produce Invalid with no argument") {
                    val commandAndArg = inValidCommand.parseCommandLine()
                    commandAndArg.commandLine.shouldEqual(Invalid)
                    commandAndArg.arguments.shouldBeEmpty()
                }
            }
        }
    }
})