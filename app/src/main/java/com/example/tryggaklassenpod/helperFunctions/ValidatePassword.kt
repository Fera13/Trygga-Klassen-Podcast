package com.example.tryggaklassenpod.helperFunctions

//inspired by Daniel Atitienei's video on YT
class ValidatePassword {

    fun execute(password: String): Boolean {
        val validateOneNumber = validateNumber(password)
        val validateCapitalizedLetter = validateCapitalizedLetter(password)
        val validateMinimum = validateMinimum(password)

        if(validateOneNumber && validateCapitalizedLetter && validateMinimum){
            return true
        }
        return false
    }

    /*private fun validateSpecialCharacter(password: String): Boolean =
        password.matches(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]"))*/

    private fun validateCapitalizedLetter(password: String): Boolean =
        password.matches(Regex(".*[A-Z].*")) // Checks for at least one capital number

    private fun validateMinimum(password: String): Boolean =
        password.matches(Regex(".{8,}")) // Checks that it's at least 8 characters long

    private fun validateNumber(password: String): Boolean =
        password.matches(Regex(".*[0-9].*")) // Checks for at least one number
}