package com.dgreenhalgh.android.simpleitemdecorationsample.options

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import com.dgreenhalgh.android.simpleitemdecorationsample.R
import com.dgreenhalgh.android.simpleitemdecorationsample.SampleActivity

class OptionsActivity : AppCompatActivity() {

    private lateinit var showSampleButton: Button
    private lateinit var dividersCheckBox: CheckBox
    private lateinit var startOffsetCheckBox: CheckBox
    private lateinit var endOffsetCheckBox: CheckBox
    private lateinit var layoutManagerRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        dividersCheckBox = findViewById(R.id.dividers_check_box) as CheckBox
        startOffsetCheckBox = findViewById(R.id.start_offset_check_box) as CheckBox
        endOffsetCheckBox = findViewById(R.id.end_offset_check_box) as CheckBox

        layoutManagerRadioGroup = findViewById(R.id.layout_manager_radio_group) as RadioGroup

        showSampleButton = findViewById(R.id.show_sample_button) as Button
        showSampleButton.setOnClickListener {
            val intent = SampleActivity.newIntent(this, getSelectedDecorTypes(), getSelectedOrientation())
            startActivity(intent)
        }
    }

    private fun getSelectedDecorTypes(): List<String> {
        val selectedDecorTypes = mutableListOf<String>()
        if (dividersCheckBox.isChecked) {
            selectedDecorTypes.add(DecorType.DIVIDER.name)
        }

        if (startOffsetCheckBox.isChecked) {
            selectedDecorTypes.add(DecorType.START_OFFSET.name)
        }

        if (endOffsetCheckBox.isChecked) {
            selectedDecorTypes.add(DecorType.END_OFFSET.name)
        }

        return selectedDecorTypes
    }

    private fun getSelectedOrientation(): Int {
        return if (layoutManagerRadioGroup.checkedRadioButtonId == R.id.horizontal_radio_button) {
            LinearLayoutManager.HORIZONTAL
        } else {
            LinearLayoutManager.VERTICAL
        }
    }
}
