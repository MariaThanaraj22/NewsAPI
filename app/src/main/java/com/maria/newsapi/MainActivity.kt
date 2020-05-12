package com.maria.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.maria.newsapi.Adapter.TabAdapter
import com.maria.newsapi.model.KotlinMainResponse
import com.maria.newsapi.retrofit.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var adapter: TabAdapter? = null
    var reponseData: KotlinMainResponse? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiCall("bitcoin")

        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setTitle("About Bitcoin")
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setCheckedItem(R.id.about_bitcoin)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            drawer_layout.closeDrawers()
            when (menuItem.itemId) {
                R.id.about_bitcoin -> {
                    apiCall("bitcoin")
                    actionbar?.setTitle("About Bitcoin")
                }
                R.id.business_headlines -> {
                    apiCall("business")
                    actionbar?.setTitle("Business Headlines")
                }
                R.id.techCrunch -> {
                    apiCall("techCrunch")
                    actionbar?.setTitle("TechCrunch")
                }
                R.id.wall_atreet_journal -> {
                    apiCall("journal")
                    actionbar?.setTitle("Wall Street Journal")
                }

                R.id.mentioning_apple -> {
                    apiCall("apple")
                    actionbar?.setTitle("Mentioning Apple")
                }
            }
            true
        }

        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    private fun apiCall(type: String) {
        val call = when (type) {
            "bitcoin" -> ApiInterface.invoke().getBitcoin()
            "business" -> ApiInterface.invoke().getheadlines()
            "techCrunch" -> ApiInterface.invoke().getTechCrunch()
            "journal" -> ApiInterface.invoke().getWallStreetJournal()
            "apple" -> ApiInterface.invoke().getmentioningApple()
            else -> null
        }
        call!!.enqueue(object : Callback<KotlinMainResponse> {
            override fun onResponse(
                call: Call<KotlinMainResponse>, response: Response<KotlinMainResponse>
            ) {
                if (response.code() == 200) {
                    tabLayout?.removeAllTabs()
                    reponseData = response.body()!!
                    for (index in reponseData!!.data!!.indices) {
                        if (index < reponseData!!.data!!.size) {
                            if (reponseData!!.data!![index].author.isNullOrEmpty()) {
                                reponseData!!.data!!.removeAt(index)
                            } else {
                                //Dynamic TabLayout Add
                                tabLayout?.addTab(
                                    tabLayout?.newTab()!!
                                        .setText("" + reponseData!!.data!![index].author.toString())
                                )
                            }
                        }
                    }
                    adapter =
                        TabAdapter(supportFragmentManager, tabLayout!!.tabCount, reponseData)
                    viewPager?.setAdapter(adapter)
                    viewPager?.offscreenPageLimit = 1
                    viewPager?.addOnPageChangeListener(
                        TabLayout.TabLayoutOnPageChangeListener(
                            tabLayout
                        )
                    )
                }
            }

            override fun onFailure(call: Call<KotlinMainResponse>, t: Throwable) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
