package com.porashona.studymaster.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.porashona.studymaster.ui.timer.TimerFragment
import com.porashona.studymaster.ui.history.SessionHistoryFragment
import com.porashona.studymaster.ui.routine.RoutineFragment
import com.porashona.studymaster.ui.blocker.BlockerFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onTabSelected: (Int) -> Unit
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TimerFragment()
            1 -> SessionHistoryFragment()
            2 -> RoutineFragment()
            3 -> BlockerFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    fun onPageSelected(position: Int) {
        onTabSelected(position)
    }
}