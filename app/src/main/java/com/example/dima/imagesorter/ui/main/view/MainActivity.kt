package com.example.dima.imagesorter.ui.main.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.example.dima.imagesorter.R
import com.example.dima.imagesorter.ui.images.view.ImagesScrollFragment
import com.example.dima.imagesorter.ui.base.view.BaseActivity
import com.example.dima.imagesorter.ui.main.presenter.MainMVPPresenter
import com.example.dima.imagesorter.util.log
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMVPView, NavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector {

    @Inject
    internal lateinit var presenter: MainMVPPresenter<MainMVPView>
    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)// as Fragment? ?: createFragment()
        if(fragment == null) fragment = createFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()

        nav_view.setNavigationItemSelectedListener(this)
        "MainActivity created!".log()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showMessage(message: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //FRAGMENT
    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    fun createFragment(): android.support.v4.app.Fragment? {
        return ImagesScrollFragment.newInstance("a", "b")
    }

    override fun onFragmentAttached() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentDetached(tag: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
