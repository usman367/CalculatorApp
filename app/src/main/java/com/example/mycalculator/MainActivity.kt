package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //Used for the onDecimalPoint function
    //To make sure we are using the dot in the right place
    //Checks if the last character was a digit
    var lastNumeric : Boolean = false;
    //Checks if the last digit was a dot
    var lastDot : Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        //Gets the text view at the top
        val tvInput = findViewById<TextView>(R.id.tvInput)
        //Gets the the value of the button
        //Displays it in the text view
        tvInput.append((view as Button).text)
        //User has clicked a digit button so set it to true
        lastNumeric = true;
    }

    fun onClear(view: View){
        //Gets the text view at the top
        val tvInput = findViewById<TextView>(R.id.tvInput)
        //When the clear button is pressed it sets the textView to an empty string
        tvInput.text = ""
        //Sets them back to normal
        lastNumeric = false;
        lastDot = false;
    }

    fun onDecimalPoint(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false;
            lastDot = true;
        }
    }

    fun onEqual(view : View){
        val tvInput = findViewById<TextView>(R.id.tvInput)

        if(lastNumeric){
            var tvValue = tvInput.text.toString()

            var prefix = ""

            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    //Substring take the value at the start of the index to the end
                    //E.g., -215 becomes 215 if substring is 1
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    //Splits the value in the text view into two values (one before the minus and one after)
                    val splitValue = tvValue.split("-")
                    //Gets the two values from different positions after they have been split
                    //E.g., if 99-1, one = 99 and two = 1
                    var one = splitValue[0]
                    var two = splitValue[1]

                    //If the string is not empty (i.e. if there is a minus in it)
                    if(!prefix.isEmpty()){
                        //Then add a minus sign to the first number (Because using the substring above removes the minus sign)
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                }else if(tvValue.contains("/")){
                    //Splits the value in the text view into two values (one before the minus and one after)
                    val splitValue = tvValue.split("/")
                    //Gets the two values from different positions after they have been split
                    //E.g., if 99-1, one = 99 and two = 1
                    var one = splitValue[0]
                    var two = splitValue[1]

                    //If the string is not empty (i.e. if there is a minus in it)
                    if(!prefix.isEmpty()){
                        //Then add a minus sign to the first number (Because using the substring above removes the minus sign)
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                } else if(tvValue.contains("+")){
                    //Splits the value in the text view into two values (one before the minus and one after)
                    val splitValue = tvValue.split("+")
                    //Gets the two values from different positions after they have been split
                    //E.g., if 99-1, one = 99 and two = 1
                    var one = splitValue[0]
                    var two = splitValue[1]

                    //If the string is not empty (i.e. if there is a minus in it)
                    if(!prefix.isEmpty()){
                        //Then add a minus sign to the first number (Because using the substring above removes the minus sign)
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if(tvValue.contains("*")){
                    //Splits the value in the text view into two values (one before the minus and one after)
                    val splitValue = tvValue.split("*")
                    //Gets the two values from different positions after they have been split
                    //E.g., if 99-1, one = 99 and two = 1
                    var one = splitValue[0]
                    var two = splitValue[1]

                    //If the string is not empty (i.e. if there is a minus in it)
                    if(!prefix.isEmpty()){
                        //Then add a minus sign to the first number (Because using the substring above removes the minus sign)
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result : String) : String{
        var value = result
        if(result.contains(".0")){
            //If there is a number like 99.0
            //It will removes the last 2 characters
            //So we'll end up with 99
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    //Checks if the user has already used an operator (-, +, *, /)
    //If not then return true
    private fun isOperatorAdded(value: String) : Boolean{
        //If the number starts with a "-" (its a negative number) then don't do anything
        return if(value.startsWith("-")){
            false;
        }else{
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    //Checks that an operator has not been used before allowing it to be used
    fun onOperator(view : View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        //If the last button pressed was a number and we have not already used an operator
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            //Add the operator to the text view
            tvInput.append((view as Button).text)
            //Then set these to their normal values
            lastNumeric = false
            lastDot = false
        }
    }
}