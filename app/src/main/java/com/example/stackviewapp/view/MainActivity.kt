package com.example.stackviewapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stackviewapp.databinding.StackLayoutBinding
import com.example.stackviewapp.utils.*

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(StackLayoutBinding::inflate)

    private var clickLevelFlag: LEVELS = LEVELS.LEVEL_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*
        * init state
        *
        * Level 2,3,4 containers gone
        * Level 1 -> Expand -> Level 2 container visible -> Level 1 summary visible
        * Level 2 -> Expand -> Level 3 container visible -> Level 1,2 summary visible
        * Level 3 -> Expand -> Level 4 container visible -> Level 1,2,3 summary visible
        * onLevelSummaryClick -> Current level summary gone -> Further down the line Container all gone
        *
        *
        * onBackPressed -> Levels Reverse
        * */
        initViewState()

        initClickState()

        onLevelSummaryClickState()
    }


    private fun initViewState() {
        binding.containerLevel2.gone()
        binding.containerLevel3.gone()
        binding.containerLevel4.gone()
        binding.level1.containerSummary.gone()
    }

    private fun initClickState() {
        binding.level1.btnExpand.setOnClickListener {
            levelOneClickState()
        }
        binding.level2.btnExpand.setOnClickListener {
            levelTwoClickState()
        }
        binding.level3.btnExpand.setOnClickListener {
            levelThreeClickState()
        }
    }


    /*Level Click State Start*/
    private fun levelOneClickState() {
        clickLevelFlag = LEVELS.LEVEL_2
        binding.level1.containerSummary.visibleFade()
        binding.level2.containerSummary.gone()
        binding.containerLevel2.slideUp()//container goes up
        binding.containerLevel3.gone()
        binding.containerLevel4.gone()
    }

    private fun levelTwoClickState() {
        clickLevelFlag = LEVELS.LEVEL_3
        binding.level2.containerSummary.visibleFade()
        binding.level3.containerSummary.gone()
        binding.containerLevel3.slideUp()//container goes up
        binding.containerLevel4.gone()
    }

    private fun levelThreeClickState() {
        clickLevelFlag = LEVELS.LEVEL_4
        binding.level3.containerSummary.visibleFade()
        binding.level4.containerSummary.gone()
        binding.containerLevel4.slideUp()//container goes up
    }

    /*Level Click State End*/

    private fun onLevelSummaryClickState() {
        binding.level3.containerSummary.setOnClickListener {
            it.gone()
            clickLevelFlag = LEVELS.LEVEL_3
            binding.containerLevel4.slideDown()//container goes down
        }
        binding.level2.containerSummary.setOnClickListener {
            it.gone()
            clickLevelFlag = LEVELS.LEVEL_2
            binding.containerLevel3.slideDown()//container goes down
            binding.containerLevel4.gone()
        }
        binding.level1.containerSummary.setOnClickListener {
            it.gone()
            clickLevelFlag = LEVELS.LEVEL_DEFAULT
            binding.containerLevel2.slideDown()//container goes down
            binding.containerLevel3.gone()
            binding.containerLevel4.gone()
        }
    }

    override fun onBackPressed() {
        reverseClickState()
    }

    /**
     * Handling of the reverse Back Pressed with help of Click flags
     * */
    private fun reverseClickState() {
        when (clickLevelFlag) {
            LEVELS.LEVEL_4 -> {
                binding.level3.containerSummary.gone()
                binding.containerLevel4.slideDown()//container goes down
                clickLevelFlag = LEVELS.LEVEL_3
            }
            LEVELS.LEVEL_3 -> {
                binding.level2.containerSummary.gone()
                binding.containerLevel3.slideDown()//container goes down
                clickLevelFlag = LEVELS.LEVEL_2
            }
            LEVELS.LEVEL_2 -> {
                binding.level1.containerSummary.gone()
                binding.containerLevel2.slideDown()//container goes down
                clickLevelFlag = LEVELS.LEVEL_DEFAULT
            }
            LEVELS.LEVEL_DEFAULT -> {
                super.onBackPressed()
            }
        }

    }

    enum class LEVELS {
        LEVEL_DEFAULT,
        LEVEL_2,
        LEVEL_3,
        LEVEL_4
    }
}