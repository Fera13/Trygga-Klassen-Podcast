package com.example.tryggaklassenpod.helperFunctions

import com.github.javafaker.Faker
import kotlin.random.Random

class UsernameGenerator {

    private val faker = Faker()

    private fun generateNameDog(): String {
        val name = faker.name().username()
        val dog = faker.dog().breed()

        return "$name-$dog"
    }

    private fun generateCatNumber(): String {
        val cat = faker.cat().name()
        val number = Random.nextInt(1, 1001)

        return "$cat-$number"
    }

    private fun generateFunnyName(): String {
        return faker.funnyName().name()
    }

    private fun generateAnimalNumber(): String {
        val animal = faker.animal().name()
        val number = Random.nextInt(1, 1001)

        return "$animal-$number"
    }

    private fun generate2WordsNumber(): String {
        val word1 = faker.lorem().word()
        val word2 = faker.lorem().word()
        val number = Random.nextInt(1, 1001)

        return "$word1-$word2-$number"
    }

    private fun generateUsernameWithWordsAndNumber(): String {
        val animal = faker.animal().name()
        val word = faker.lorem().word()
        val number = Random.nextInt(1, 1001)

        return "$word-$animal-$number"
    }

    fun generateUsername(): String {
        val random = Random.nextInt(1, 7)

        return when(random) {
            1 -> generateNameDog()
            2 -> generateCatNumber()
            3 -> generateFunnyName()
            4 -> generateAnimalNumber()
            5 -> generate2WordsNumber()
            else -> generateUsernameWithWordsAndNumber()
        }
    }
}