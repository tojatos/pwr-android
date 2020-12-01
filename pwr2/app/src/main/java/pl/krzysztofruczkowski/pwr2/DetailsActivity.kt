package pl.krzysztofruczkowski.pwr2

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import pl.krzysztofruczkowski.pwr2.databinding.DetailsActivityBinding
import pl.krzysztofruczkowski.pwr2.fragments.DescriptionFragment
import pl.krzysztofruczkowski.pwr2.fragments.EnemiesFragment
import pl.krzysztofruczkowski.pwr2.fragments.GalleryFragment
import pl.krzysztofruczkowski.pwr2.models.Pokemon

class DetailsActivity : AppCompatActivity() {
    companion object {
        val fragmentMap = listOf(
            Pair(MainActivity.app_resources.getString(R.string.summary), DescriptionFragment()),
            Pair(MainActivity.app_resources.getString(R.string.gallery), GalleryFragment()),
            Pair(MainActivity.app_resources.getString(R.string.enemies), EnemiesFragment()),
        )
    }

    lateinit var pokemon: Pokemon
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = Pokemon.deserialize(intent.getStringExtra(MainActivity.POKEMON)!!)

        val binding = DataBindingUtil.setContentView<DetailsActivityBinding>(this, R.layout.details_activity)

        viewPager = binding.pager
        viewPager.adapter = DetailsActivityAdapter(this)
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = fragmentMap[position].first
        }.attach()

    }
}

class DetailsActivityAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = DetailsActivity.fragmentMap.count()

    override fun createFragment(position: Int): Fragment =
        DetailsActivity.fragmentMap[position].second
}