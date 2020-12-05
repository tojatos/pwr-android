package pl.krzysztofruczkowski.pwr2.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import pl.krzysztofruczkowski.pwr2.R
import pl.krzysztofruczkowski.pwr2.adapters.DetailsNavigationAdapter
import pl.krzysztofruczkowski.pwr2.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    companion object {
        lateinit var fragmentMap : List<Pair<String, Fragment>>
    }

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailsBinding>(inflater,
            R.layout.fragment_details, container, false)

        fragmentMap = listOf(
            Pair(getString(R.string.Summary), DescriptionFragment()),
            Pair(getString(R.string.Gallery), GalleryFragment()),
            Pair(getString(R.string.Enemies), EnemiesFragment()),
        )

        viewPager = binding.pager
        viewPager.adapter = DetailsNavigationAdapter(this)
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = fragmentMap[position].first
        }.attach()

        return binding.root
    }
}

