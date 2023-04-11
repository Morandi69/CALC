package com.example.calc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.widget.TextView
import android.widget.Toast
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.button_mult).setOnClickListener{setSignFields("*")}
        findViewById<TextView>(R.id.button_div).setOnClickListener{setSignFields("/")}
        findViewById<TextView>(R.id.button_sum).setOnClickListener{setSignFields("+")}
        findViewById<TextView>(R.id.button_minus).setOnClickListener{setSignFields("-")}
        findViewById<TextView>(R.id.button_point).setOnClickListener {setSignFields(".") }
        findViewById<TextView>(R.id.button_0).setOnClickListener{setDigitFields("0")}
        findViewById<TextView>(R.id.button_1).setOnClickListener{setDigitFields("1")}
        findViewById<TextView>(R.id.button_2).setOnClickListener{setDigitFields("2")}
        findViewById<TextView>(R.id.button_3).setOnClickListener{setDigitFields("3")}
        findViewById<TextView>(R.id.button_4).setOnClickListener{setDigitFields("4")}
        findViewById<TextView>(R.id.button_5).setOnClickListener{setDigitFields("5")}
        findViewById<TextView>(R.id.button_6).setOnClickListener{setDigitFields("6")}
        findViewById<TextView>(R.id.button_7).setOnClickListener{setDigitFields("7")}
        findViewById<TextView>(R.id.button_8).setOnClickListener{setDigitFields("8")}
        findViewById<TextView>(R.id.button_9).setOnClickListener{setDigitFields("9")}

        findViewById<TextView>(R.id.button_del).setOnClickListener{
            findViewById<TextView>(R.id.math_operation).text=findViewById<TextView>(R.id.math_operation).text.dropLast(1)
        }
        findViewById<TextView>(R.id.button_clear).setOnClickListener {
            findViewById<TextView>(R.id.math_operation).text=""
            findViewById<TextView>(R.id.math_result).text=""
        }

        findViewById<TextView>(R.id.button_result).setOnClickListener {
            try {
                val exp=ExpressionBuilder(findViewById<TextView>(R.id.math_operation).text.toString()).build()
                val result=exp.evaluate()

                val longresult=result.toLong()
                if(result==longresult.toDouble()){
                    findViewById<TextView>(R.id.math_result).text="=${longresult.toString()}"
                }else{
                    findViewById<TextView>(R.id.math_result).text="=${result.toString()}"
                }

            }catch (e:java.lang.Exception){
                Log.d("Error","Message: ${e.message}")
            }
        }

        var inputtext = findViewById<TextView>(R.id.math_operation)
        inputtext.addTextChangedListener(object :TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputText=findViewById<TextView>(R.id.math_operation).text.toString()
                val  count=inputText.length-1
                findViewById<TextView>(R.id.math_result).text=""
                for ( i in 0..count){
                    if(inputText[i]=='/'&& i<count){
                        var j =i+1
                        var tempStr =""
                        while (j<=count){
                            if(inputText[j]=='*'||inputText[j]=='-'||inputText[j]=='+'||inputText[j]=='='){
                                break
                            }
                            tempStr=tempStr+inputText[j]
                            j++
                        }
                        if(tempStr.toDouble()==0.0){
                            findViewById<TextView>(R.id.math_result).text="Нельзя делить на ноль"
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    //Добавляем цифру
    fun setDigitFields(str:String){
        findViewById<TextView>(R.id.math_operation).append(str)
    }



    //Добавляем знак и проверям что-бы они не повторялись
    fun setSignFields(str:String){

        if(findViewById<TextView>(R.id.math_operation).text.length==0){
            return
        }
        val lastChar=findViewById<TextView>(R.id.math_operation).text[findViewById<TextView>(R.id.math_operation).text.length-1].toString()
        if(str=="."){
            if(lastChar=="-"||lastChar=="+"||lastChar=="*"||lastChar=="/"||lastChar=="."){
                return
            }
            val text=findViewById<TextView>(R.id.math_operation).text.toString()
            val count=text.length-1
            if(count==-1){return}
            for(i in count downTo  0 ){
                if(text[i]=='.'){
                    return
                }
                if(text[i]=='+'||text[i]=='-'||text[i]=='*'||text[i]=='/'){
                    findViewById<TextView>(R.id.math_operation).append(str)
                    return
                }
            }
        }
        if(lastChar=="-"||lastChar=="+"||lastChar=="*"||lastChar=="/"||lastChar=="."){
            if(lastChar==str){
                return
            }
            else{
                findViewById<TextView>(R.id.math_operation).text=findViewById<TextView>(R.id.math_operation).text.dropLast(1)
            }
        }

        findViewById<TextView>(R.id.math_operation).append(str)
    }




}