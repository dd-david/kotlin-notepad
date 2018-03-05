package com.udacity.notepad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.udacity.notepad.crud.CreateActivity
import com.udacity.notepad.recycler.NotesAdapter
import com.udacity.notepad.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/*
* https://kotlinlang.org/docs/tutorials/android-plugin.html
*
* View Binding
*   findViewById() causes potential bugs, so several libraries, like ButterKnife,
*   help to this issue with annotations.
*   Kotlin extension plugin allows us to obtain same experience without adding extra codes
*
* Step 1. How to use Kotlin Extension Library
*   Android extension is part of Kotlin plugin for IntelliJ IDEA and AndroidStudio.
*   You do not need to additional install procedure.
*   Just add in build.gradle file
*   (ex) apply plugin: 'kotlin-android-extensions'
*
* Step 2. Import synthetic properties
*   It's convenient to import all widget properties for a specific layout in one go.
*   (ex) import kotlinx.android.synthetic.main.<layout>.*
*
* Todo: Experimental Mode (not ready yet)
*   androidExtensions {
*     experimental = true
*   }
*
* */
class MainActivity : AppCompatActivity() {

    /*
    * https://kotlinlang.org/docs/reference/keyword-reference.html#modifier-keywords
    * modify keywords
    *   kotlin : public override
    *   java   : @Override public
    *
    * */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        fab.setOnClickListener { startActivity(CreateActivity.get(this@MainActivity)) }

        /*
        * https://kotlinlang.org/docs/reference/null-safety.html#the--operator
        * !! Operation : not-null assertion operation
        *                if null, throws an exception
        *
        * After applying kotlin extension plugin, !! is removed,
        * because it automatically knows nullable or not.
        * */
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(SpaceItemDecoration(this, R.dimen.margin_small))
        recycler.adapter = NotesAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    public override fun onDestroy() {
        super.onDestroy()
        recycler.adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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

    private fun refresh() {
        /*
        * https://kotlinlang.org/docs/reference/typecasts.html#unsafe-cast-operator
        * as  : <unsafe cast> means if the cast isn't possible, throw an exception
        *       (ex) val x : String = y as String     // if y is null, exception will be thrown
        *            val x : String? = y as String?   // if y is null, String? may help to cast
        *
        * as? : <safe(nullable) cast>, to avoid an exception being thrown,
        *       which returns null on failure
        *       (ex) val x : String? = y as? String   // right side of <as?> may return nullable on failure
        * */
        (recycler.adapter as NotesAdapter).refresh()
    }
}
