package com.example.myapplication_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_category.*

class category : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        btn1.setOnClickListener { view ->
            tv1.text = ""

            if (cb1.isChecked)
                tv1.append("체크 박스 1 체크")

            if (cb2.isChecked)
                tv1.append("체크 박스 2 체크")

            if (cb3.isChecked)
                tv1.append("체크 박스 3 체크")
        }



        var listener = CheckBoxListener()
        cb1.setOnCheckedChangeListener(listener);

        cb2.setOnCheckedChangeListener{compoundButton, b ->
            if(b)
                tv1.text = "Event : CheckBox2 Checked"
            else
                tv1.text = "Event CheckBox2 unChecked"
        }

        cb3.setOnCheckedChangeListener{compoundButton, b ->
            if(b)
                tv1.text = "Event : CheckBox3 Checked"
            else
                tv1.text = "Event CheckBox3 unChecked"
        }
    }

    inner class CheckBoxListener : CompoundButton.OnCheckedChangeListener{
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            if(p1)
                tv1.text = "Event : CheckBox1 Checked"
            else
                tv1.text = "Event CheckBox1 unChecked"
        }
    }
}