package pl.krzysztofruczkowski.pwr2.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.krzysztofruczkowski.pwr2.fragments.DetailsFragment

class DetailsNavigationAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = DetailsFragment.fragmentMap.count()

    override fun createFragment(position: Int): Fragment =
        DetailsFragment.fragmentMap[position].second
}